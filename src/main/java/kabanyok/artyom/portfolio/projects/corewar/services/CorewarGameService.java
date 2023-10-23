package kabanyok.artyom.portfolio.projects.corewar.services;

import kabanyok.artyom.portfolio.projects.corewar.CorewarEventType;
import kabanyok.artyom.portfolio.projects.corewar.PackageEventCode;
import kabanyok.artyom.portfolio.projects.corewar.dto.Carriage;
import kabanyok.artyom.portfolio.projects.corewar.dto.ChangeCTDEvent;
import kabanyok.artyom.portfolio.projects.corewar.dto.ChangeCarriageAddressEvent;
import kabanyok.artyom.portfolio.projects.corewar.dto.ChangeCycleEvent;
import kabanyok.artyom.portfolio.projects.corewar.dto.CorewarEvent;
import kabanyok.artyom.portfolio.projects.corewar.dto.CorewarGameResult;
import kabanyok.artyom.portfolio.projects.corewar.dto.CorewarGameStreamPackage;
import kabanyok.artyom.portfolio.projects.corewar.dto.CorewarPlayer;
import kabanyok.artyom.portfolio.projects.corewar.dto.DeathCarriageEvent;
import kabanyok.artyom.portfolio.projects.corewar.dto.SayAliveEvent;
import kabanyok.artyom.portfolio.projects.corewar.dto.WinningPlayerEvent;
import kabanyok.artyom.portfolio.projects.corewar.dto.WriteInMemEvent;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import static kabanyok.artyom.portfolio.projects.corewar.PackageEventCode.END_GAME;

@Service
public class CorewarGameService {

    private final String PATH_TO_COREWAR = System.getenv("COREWAR_PATH");

    public CorewarGameResult executeCorewarGame() {
        Process process;
        try {
            process = Runtime.getRuntime().exec(makeCommand());
            return readGameResult(process.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String makeCommand() {
        return "%s -stream %1$s%2$s %1$s%2$s".formatted(PATH_TO_COREWAR, "/kuzia.cor");
    }

    private CorewarGameResult readGameResult(InputStream in) {
        List<CorewarPlayer> players = new ArrayList<>();
        List<Carriage> carriages = new ArrayList<>();
        List<String> memory = null;
        List<CorewarEvent> events = new ArrayList<>();

        try {
            var gameResult = mapToBytebuffer(in.readAllBytes());
            CorewarGameStreamPackage streamPackage;
            while ((streamPackage = deserializeCorewarGamePackage(gameResult)).getPackageCode() != END_GAME) {
                switch (streamPackage.getPackageCode()) {
                    case INIT_PLAYER -> players.add(deserializePlayer(streamPackage.getContent()));
                    case NEW_CARRIAGE -> carriages.add(deserializeCarriage(streamPackage.getContent()));
                    case GAME_MEMORY -> memory = mapMemoryToHexRepresentation(streamPackage.getContent());
                    case END_INIT -> {}
                    case CHANGE_CYCLES -> events.add(deserializeChangeCycleEvent(streamPackage.getContent()));
                    case CHANGE_CARRIAGE_ADDRESS -> events.add(deserializeChangeCarriageAddressEvent(streamPackage.getContent()));
                    case WRITE_IN_MEM -> events.add(deserializeWriteInMemEvent(streamPackage.getContent()));
                    case SAY_ALIVE -> events.add(deserializeSayAliveEvent(streamPackage.getContent()));
                    case CHANGE_CTD -> events.add(deserializeChangeCTDEvent(streamPackage.getContent()));
                    case DEATH_CARRIAGE -> events.add(deserializeDeathCarriageEvent(streamPackage.getContent()));
                    case WINNING_PLAYER -> events.add(deserializeWinningPlayerEvent(streamPackage.getContent()));
                }
            }
            return new CorewarGameResult(players, memory, carriages, events);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> mapMemoryToHexRepresentation(byte[] gameMemory) {
        List<String> hexRepresentation = new ArrayList<>();
        for (byte b : gameMemory) {
            hexRepresentation.add("%02x".formatted(b));
        }
        return hexRepresentation;
    }

    private ChangeCycleEvent deserializeChangeCycleEvent(byte[] rawCycle) {
        ByteBuffer buffer = mapToBytebuffer(rawCycle);
        return ChangeCycleEvent.builder()
                .type(CorewarEventType.CHANGE_CYCLE)
                .cycle(buffer.getLong())
                .build();
    }

    private ChangeCarriageAddressEvent deserializeChangeCarriageAddressEvent(byte[] rawEvent) {
        ByteBuffer buffer = mapToBytebuffer(rawEvent);
        return ChangeCarriageAddressEvent.builder()
                .type(CorewarEventType.CHANGE_CARRIAGE_ADDRESS)
                .carriageId(buffer.getInt())
                .addr(buffer.getInt())
                .build();
    }

    private WriteInMemEvent deserializeWriteInMemEvent(byte[] rawEvent) {
        ByteBuffer buffer = mapToBytebuffer(rawEvent);
        return WriteInMemEvent.builder()
                .type(CorewarEventType.WRITE_IN_MEM)
                .carriageId(buffer.getInt())
                .addrInMem(buffer.getInt())
                .hexRepresentationMem(toHexRepresentation(buffer, 4))
                .build();
    }

    private SayAliveEvent deserializeSayAliveEvent(byte[] rawEvent) {
        ByteBuffer buffer = mapToBytebuffer(rawEvent);
        return SayAliveEvent.builder()
                .type(CorewarEventType.SAY_ALIVE)
                .playerId(buffer.getInt())
                .cycle(buffer.getLong())
                .build();
    }

    private ChangeCTDEvent deserializeChangeCTDEvent(byte[] rawEvent) {
        ByteBuffer buffer = mapToBytebuffer(rawEvent);
        return ChangeCTDEvent.builder()
                .type(CorewarEventType.CHANGE_CTD)
                .cycleToDie(buffer.getInt())
                .build();
    }

    private DeathCarriageEvent deserializeDeathCarriageEvent(byte[] rawEvent) {
        ByteBuffer buffer = mapToBytebuffer(rawEvent);
        return DeathCarriageEvent.builder()
                .type(CorewarEventType.DEATH_CARRIAGE)
                .carriageId(buffer.getInt())
                .amountCarriages(buffer.getInt())
                .build();
    }

    private WinningPlayerEvent deserializeWinningPlayerEvent(byte[] rawEvent) {
        ByteBuffer buffer = mapToBytebuffer(rawEvent);
        return WinningPlayerEvent.builder()
                .type(CorewarEventType.WINNING_PLAYER)
                .playerId(buffer.getInt())
                .build();
    }

    private List<String> toHexRepresentation(ByteBuffer buffer, int byteCont) {
        List<String> hexRepresentation = new ArrayList<>();
        byte[] bytes = new byte[byteCont];
        buffer.get(bytes);
        for(byte b : bytes) {
            hexRepresentation.add("%02x".formatted(b));
        }
        return hexRepresentation;
    }

    private CorewarGameStreamPackage deserializeCorewarGamePackage(ByteBuffer byteBuffer) {
        CorewarGameStreamPackage gameStreamPackage = new CorewarGameStreamPackage();
        gameStreamPackage.setPackageCode(PackageEventCode.valueOf(byteBuffer.getChar()));
        byte[] packageContent = new byte[byteBuffer.getInt()];
        byteBuffer.get(packageContent);
        gameStreamPackage.setContent(packageContent);
        return gameStreamPackage;
    }

    private CorewarPlayer deserializePlayer(byte[] bytes) {
        ByteBuffer byteBuffer = mapToBytebuffer(bytes);
        CorewarPlayer.CorewarPlayerBuilder builder = CorewarPlayer.builder()
                .number(byteBuffer.getInt())
                .code(new byte[byteBuffer.getInt()]);
        byte[] name = new byte[byteBuffer.getInt()];
        byteBuffer.get(name);
        builder.name(new String(name));
        return builder.build();
    }

    private Carriage deserializeCarriage(byte[] bytes) {
        ByteBuffer byteBuffer = mapToBytebuffer(bytes);
        return Carriage.builder()
                .id(byteBuffer.getInt())
                .address(byteBuffer.getInt())
                .build();
    }

    private ByteBuffer mapToBytebuffer(byte[] bytes) {
        return  ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
    }
}
