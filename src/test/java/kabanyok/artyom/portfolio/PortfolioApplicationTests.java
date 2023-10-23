package kabanyok.artyom.portfolio;

import kabanyok.artyom.portfolio.projects.corewar.services.CorewarGameService;
import kabanyok.artyom.portfolio.projects.push_swap.services.PushSwapService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@SpringBootTest
class PortfolioApplicationTests {
    @Autowired
    private PushSwapService pushSwapService;
    @Autowired
    private CorewarGameService corewarService;

    @Test
    void contextLoads() {
        System.out.println(pushSwapService.executePushSwap(List.of(63 , 49 , 22 , 42 , 66 , 32 , 19 , 14 , 77 , 62 , 33 , 47 , 70 , 61 , 91 , 41 , 55 , 17 , 48 , 54 , 36 , 34 , 64 , 7 , 3 , 20 , 67 , 6 , 25 , 27 , 4 , 69 , 58 , 37 , 38 , 39 , 23 , 53 , 40 , 57 , 81 , 75 , 51 , 9 , 87 , 12 , 35 , 98 , 86 , 43 , 65 , 94 , 95 , 31 , 11 , 26 , 8 , 74 , 60 , 50 , 80 , 30 , 97 , 56 , 46 , 29 , 68 , 13 , 79 , 5 , 16 , 100 , 45 , 2 , 21 , 1 , 72 , 28 , 93 , 78 , 59 , 89 , 24 , 15 , 92 , 85 , 44 , 18 , 82 , 99 , 10 , 76 , 88 , 83 , 90 , 52 , 84 , 96 , 73 , 71)));
    }

    @Test
    void testCorewar() {
        Instant start = Instant.now();
        corewarService.executeCorewarGame();
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
    }

}
