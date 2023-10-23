package kabanyok.artyom.portfolio.projects.corewar;

public enum PackageEventCode {
    INIT_PLAYER(0xff08),
    END_GAME(0xffee),
    GAME_MEMORY(0xff01),
    END_INIT(0xffeb),
    CHANGE_CYCLES(0xff07),
    CHANGE_CARRIAGE_ADDRESS(0xff05),
    WRITE_IN_MEM(0xff02),
    SAY_ALIVE(0xff09),
    CHANGE_CTD(0xff06),
    DEATH_CARRIAGE(0xff04),
    WINNING_PLAYER(0xff10),
    NEW_CARRIAGE(0xff03);

    public final int code;
    PackageEventCode(int code) {
        this.code = code;
    }

    public static PackageEventCode valueOf(char code) {
        for (var val : PackageEventCode.values()) {
            if (val.code == code) {
                return val;
            }
        }
        throw new RuntimeException("Unknown code %04x.".formatted((int) code));
    }

}
