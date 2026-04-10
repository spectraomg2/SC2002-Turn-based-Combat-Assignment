import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Level {

    EASY(1, "Easy"),
    MEDIUM(2, "Medium"),
    HARD(3, "Hard");

    private final int    levelNumber;
    private final String displayName;

    Level(int levelNumber, String displayName) {
        this.levelNumber = levelNumber;
        this.displayName = displayName;
    }

    public List<Enemy> getInitialEnemies() {
        switch (this) {
            case EASY:
                return Arrays.asList(
                        new Goblin("Goblin A"),
                        new Goblin("Goblin B"),
                        new Goblin("Goblin C"));
            case MEDIUM:
                return Arrays.asList(
                        new Goblin("Goblin"),
                        new Wolf("Wolf"));
            case HARD:
                return Arrays.asList(
                        new Goblin("Goblin A"),
                        new Goblin("Goblin B"));
            default:
                return Collections.emptyList();
        }
    }

    public List<Enemy> getBackupEnemies() {
        switch (this) {
            case EASY:
                return Collections.emptyList();
            case MEDIUM:
                return Arrays.asList(
                        new Wolf("Wolf A"),
                        new Wolf("Wolf B"));
            case HARD:
                return Arrays.asList(
                        new Goblin("Goblin C"),
                        new Wolf("Wolf A"),
                        new Wolf("Wolf B"));
            default:
                return Collections.emptyList();
        }
    }

    public boolean hasBackupSpawn() {
        return !getBackupEnemies().isEmpty();
    }

    public int    getLevelNumber() { return levelNumber; }
    public String getDisplayName() { return displayName; }

    @Override
    public String toString()       { return displayName; }
}
