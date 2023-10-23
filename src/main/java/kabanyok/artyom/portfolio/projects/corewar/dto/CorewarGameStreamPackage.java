package kabanyok.artyom.portfolio.projects.corewar.dto;

import kabanyok.artyom.portfolio.projects.corewar.PackageEventCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CorewarGameStreamPackage {
    private PackageEventCode packageCode;
    private byte[] content;
}
