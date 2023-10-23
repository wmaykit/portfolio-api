package kabanyok.artyom.portfolio.projects.corewar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CorewarPlayer {
    int number;
    String name;
    byte[] code;
}
