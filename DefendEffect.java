
public class DefendEffect extends StatusEffect {
    public DefendEffect(int duration) {
        super(duration);
    }

    @Override
    public void addListener(EventListener listener) {
        super.addListener(listener);
        listener.onEvent("Defend");
    }

    @Override
    public void OnTurnBegin(Combatant target) {
        for(EventListener e : listeners){
            e.onEvent("Defend");
        }
    }

    @Override
    public void OnTurnEnd(Combatant target) {
        duration--;
        if (duration <= 0) {
            //TODO:target.removeStatusEffect(this);
            System.out.println(target.getName() + " is no longer in a defensive stance.");
        }
    }

    @Override
    public void UponEntityDeath(Combatant target) {
        // No special action needed on death
    }
}
