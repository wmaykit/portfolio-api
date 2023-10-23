package kabanyok.artyom.portfolio.projects.corewar.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class ChangeCarriageAddressEvent extends CorewarEvent {
    private int carriageId;
    private int addr;
}
