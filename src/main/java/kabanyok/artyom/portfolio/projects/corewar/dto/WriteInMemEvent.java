package kabanyok.artyom.portfolio.projects.corewar.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
public class WriteInMemEvent extends CorewarEvent {
    private int carriageId;
    private int addrInMem;
    private List<String> hexRepresentationMem;
}
