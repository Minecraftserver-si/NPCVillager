package si.minecraftserver.bukkit.npcvillager;

/**
 *
 * @author Martin
 */
public enum VillagerSkin {

    FARMER(0),
    LIBRARIAN(1),
    PRIEST(2),
    SMITH(3),
    BUTCHER(4);
    private final int profession;

    private VillagerSkin(final int profession) {
        this.profession = profession;
    }

    public int getProfession() {
        return profession;
    }
}
