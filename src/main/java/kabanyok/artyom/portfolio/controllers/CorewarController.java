package kabanyok.artyom.portfolio.controllers;

import kabanyok.artyom.portfolio.projects.corewar.dto.AsmRequest;
import kabanyok.artyom.portfolio.projects.corewar.dto.AsmResult;
import kabanyok.artyom.portfolio.projects.corewar.dto.CorewarGameResult;
import kabanyok.artyom.portfolio.projects.corewar.services.CorewarAsmService;
import kabanyok.artyom.portfolio.projects.corewar.services.CorewarGameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/corewar")
public class CorewarController {
    private final CorewarGameService corewarService;
    private final CorewarAsmService corewarAsmService;

    public CorewarController(CorewarGameService corewarService, CorewarAsmService corewarAsmService) {
        this.corewarService = corewarService;
        this.corewarAsmService = corewarAsmService;
    }

    @GetMapping
    public CorewarGameResult execCorewar() {
        return corewarService.executeCorewarGame();
    }


    @PostMapping("/asm")
    public AsmResult compileAsmCode(@RequestBody AsmRequest asmRequest) {
        return corewarAsmService.asmChampion(asmRequest);
    }
}
