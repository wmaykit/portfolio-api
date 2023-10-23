package kabanyok.artyom.portfolio.controllers;

import kabanyok.artyom.portfolio.projects.push_swap.services.PushSwapService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/push-swap")
public class PushSwapController {
    private final PushSwapService pushSwapService;

    public PushSwapController(PushSwapService pushSwapService) {
        this.pushSwapService = pushSwapService;
    }

    @PostMapping("")
    public List<String> buildCommandSequence(@RequestBody List<Integer> numbers) {
        return pushSwapService.executePushSwap(numbers);
    }
}
