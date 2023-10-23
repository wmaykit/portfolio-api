package kabanyok.artyom.portfolio.projects.push_swap.services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Service
public class PushSwapService {
    private final String PATH_TO_PUSH_SWAP =  System.getenv("PUSH_SWAP_PATH");;

    public List<String> executePushSwap(List<Integer> numbers) {
        Process process;
        try {
            process = Runtime.getRuntime().exec(makeCommand(numbers));
            return readAllLines(process.inputReader());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String makeCommand(List<Integer> numbers) {
        return "%s %s".formatted(PATH_TO_PUSH_SWAP, numbers.stream().map(String::valueOf).collect(joining(" ")));
    }

    private List<String> readAllLines(BufferedReader bufferedReader) {
        String line;
        List<String> commands = new ArrayList<>();
        try {
            while ((line = bufferedReader.readLine()) != null) {
                commands.add(line);
            }
            return commands;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
