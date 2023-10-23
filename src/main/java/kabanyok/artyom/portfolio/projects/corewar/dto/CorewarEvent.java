package kabanyok.artyom.portfolio.projects.corewar.dto;

import kabanyok.artyom.portfolio.projects.corewar.CorewarEventType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
public abstract class CorewarEvent {
    private CorewarEventType type;
}