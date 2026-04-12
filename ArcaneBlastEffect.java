
public class ArcaneBlastEffect extends StatusEffect {
    public ArcaneBlastEffect(int duration) {
        super(duration);
    }

    @Override
    public void OnTurnBegin(Combatant target) {
        // No action needed at the start of the turn
    }

    @Override
    public void OnTurnEnd(Combatant target) {
        duration--;
        if (duration <= 0) {
            //TODO:target.removeStatusEffect(this);
            System.out.println(target.getName() + " is no longer affected by Arcane Blast.");
        }
    }

    @Override
    public void UponEntityDeath(Combatant target) {
        for(EventListener e : listeners){
            e.onEvent("Blasted");
        }
    }
}
