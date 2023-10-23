package kabanyok.artyom.portfolio.projects.corewar.services;

import kabanyok.artyom.portfolio.projects.corewar.dto.AsmRequest;
import kabanyok.artyom.portfolio.projects.corewar.dto.AsmResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class CorewarAsmService {
    private final String PATH_TO_COREWAR = System.getenv("COREWAR_PATH");

    public AsmResult asmChampion(AsmRequest asmRequest) {
        Process process;
        try {
            Path temp = Files.createTempFile(asmRequest.getChampionName(), ".s");
            Files.write(temp, asmRequest.getChampionCode().getBytes());

            System.out.println("Temp file : " + temp);
            process = Runtime.getRuntime().exec(makeCommand(temp));
            return getAsmResult(process, temp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private AsmResult getAsmResult(Process asmProcess, Path sourceFile) {
        AsmResult asmResult = new AsmResult();
        String asmResultMsg;
        String asmErrorMsg;

        try {
            asmResultMsg = new String(asmProcess.getInputStream().readAllBytes());
            asmErrorMsg = new String(asmProcess.getErrorStream().readAllBytes());
            if (asmErrorMsg.isBlank()) {
                asmResult.setCompiled(true);
                asmResult.setCompileMsg(asmResultMsg);
                asmResult.setFile(readCompiledFile(sourceFile));
            } else {
                asmResult.setCompiled(false);
                asmResult.setCompileMsg(asmErrorMsg);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            sourceFile.toFile().delete();
        }
        return asmResult;
    }

    private byte[] readCompiledFile(Path sourceFile) throws IOException {
        byte[] bytes;
        Path compiledFile = Paths.get(sourceFile.toString().replaceFirst("[.][^.]+$", ".cor"));
        Files.readAllBytes(compiledFile);
        bytes = Files.readAllBytes(compiledFile);
        compiledFile.toFile().delete();
        return bytes;
    }

    private String makeCommand(Path toCodeFile) {
        return "%s/asm %s".formatted(PATH_TO_COREWAR, toCodeFile.toAbsolutePath());
    }
}
