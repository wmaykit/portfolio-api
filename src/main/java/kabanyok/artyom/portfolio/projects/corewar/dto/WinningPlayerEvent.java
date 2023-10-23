package kabanyok.artyom.portfolio.projects.corewar.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class WinningPlayerEvent extends CorewarEvent {
    private int playerId;
}
