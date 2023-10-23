package kabanyok.artyom.portfolio.projects.corewar.dto;

import lombok.Builder;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
public class ChangeCycleEvent extends CorewarEvent {
    private long cycle;
}
