import java.util.ArrayList;
import java.util.List;

public class SpeedBasedTurnOrder implements TurnOrderStrategy {

    @Override
    public List<Combatant> determineOrder(List<Combatant> combatants) {
        List<Combatant> ordered = new ArrayList<>(combatants);

        ordered.sort((a, b) -> b.getSpeed() - a.getSpeed());
        return ordered;
    }
}
