import java.util.List;

public class PowerStone implements Item {
    @Override
    public void use(Player player, List<Enemy> alive) {
        player.useSpecialSkill(player,alive,false);
    }
}
