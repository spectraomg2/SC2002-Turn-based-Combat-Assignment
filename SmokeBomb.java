import java.util.List;

public class SmokeBomb implements Item {
    @Override
    public void use(Player player, List<Enemy> alive) {
        player.useSmoke();
    }
}
