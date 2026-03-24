package cn.ms.dm.maple.constant.pet;

public enum PetFlag {
    ITEM_PICKUP(0x01, 5190000, 5191000), //撿道具技能
    EXPAND_PICKUP(0x02, 5190002, 5191002), //擴大移動範圍技能
    AUTO_PICKUP(0x04, 5190003, 5191003), //範圍自動撿起功能
    UNPICKABLE(0x08, 5190005, -1), //勿撿特定道具技能
    LEFTOVER_PICKUP(0x10, 5190004, 5191004), //撿起無所有權道具&楓幣技能
    HP_CHARGE(0x20, 5190001, 5191001), //自動服用HP藥水技能
    MP_CHARGE(0x40, 5190006, -1), //自動服用MP藥水技能
    PET_BUFF(0x80, -1, -1), //idk
    PET_DRAW(0x100, 5190007, -1), //寵物召喚
    PET_DIALOGUE(0x200, 5190008, -1); //自言自語

    private final int i, item, remove;

    private PetFlag(int i, int item, int remove) {
        this.i = i;
        this.item = item;
        this.remove = remove;
    }

    public final int getValue() {
        return i;
    }

    public final boolean check(int flag) {
        return (flag & i) == i;
    }

    public static final PetFlag getByAddId(final int itemId) {
        for (PetFlag flag : PetFlag.values()) {
            if (flag.item == itemId) {
                return flag;
            }
        }
        return null;
    }

    public static final PetFlag getByDelId(final int itemId) {
        for (PetFlag flag : PetFlag.values()) {
            if (flag.remove == itemId) {
                return flag;
            }
        }
        return null;
    }
}
