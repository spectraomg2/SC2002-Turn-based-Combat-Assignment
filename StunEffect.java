
public class StunEffect extends StatusEffect {
    public StunEffect(int duration) {
        super(duration);
    }

    @Override
    public void OnTurnBegin(Combatant target) {
        for(EventListener e : listeners){
            e.onEvent("Skip");
        }
    }

    @Override
    public void OnTurnEnd(Combatant target) {
        duration--;
        if (duration <= 0) {
            //TODO:target.removeStatusEffect(this);
            System.out.println(target.getName() + " is no longer stunned.");
        }
    }

    @Override
    public void UponEntityDeath(Combatant target) {
        // No special action needed on death
    }
}
