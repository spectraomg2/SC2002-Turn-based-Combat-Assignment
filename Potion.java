import java.util.List;

public class Potion implements Item {
    private static final int HEAL_AMOUNT = 100;

    @Override
    public void use(Player player, List<Enemy> alive) {
        player.heal(HEAL_AMOUNT);
    }
    
}
