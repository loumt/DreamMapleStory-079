package cn.ms.dm.maple.constant.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CharacterStat {
    SKIN(0x1),
    FACE(0x2),
    HAIR(0x4),
    LEVEL(0x40),
    JOB(0x80),
    STR(0x100),
    DEX(0x200),
    INT(0x400),
    LUK(0x800),
    HP(0x1000),
    MAX_HP(0x2000),
    MP(0x4000),
    MAX_MP(0x8000),
    AVAILABLE_AP(0x10000),
    AVAILABLE_SP(0x20000),
    EXP(0x40000),
    FAME(0x80000),
    MESO(0x100000),
    PET(0x38),


    ;

    private final int code;

    public static CharacterStat codeOf(final int code) {
        for (final CharacterStat stat : CharacterStat.values()) {
            if (stat.code == code) {
                return stat;
            }
        }
        return null;
    }
}
