package kabanyok.artyom.portfolio.projects.corewar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CorewarGameResult {
    private List<CorewarPlayer> players;
    private List<String> memory;
    private List<Carriage> carriages;
    private List<CorewarEvent> events;
}
