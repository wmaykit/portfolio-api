package kabanyok.artyom.portfolio.projects.corewar.dto;

import lombok.Data;

@Data
public class AsmResult {
    private boolean compiled;
    private String compileMsg;
    private byte[] file;
}
