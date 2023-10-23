package kabanyok.artyom.portfolio.projects.corewar.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class SayAliveEvent extends CorewarEvent {
    private int playerId;
    private long cycle;
}
