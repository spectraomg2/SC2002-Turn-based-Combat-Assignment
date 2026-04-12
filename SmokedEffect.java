public class SmokedEffect extends StatusEffect {
    public SmokedEffect(int duration) {
        super(duration);
    }

    @Override
    public void addListener(EventListener listener) {
        super.addListener(listener);
        listener.onEvent("Smoked");
    }

    @Override
    public void OnTurnBegin(Combatant target) {
        for(EventListener e : listeners){
            //System.out.println(target.getName() + " is affected by smoke and becomes invulnerable!");
            e.onEvent("Smoked");
        }
    }

    @Override
    public void OnTurnEnd(Combatant target) {
        duration--;
        if (duration <= 0) {
            //TODO:target.removeStatusEffect(this);
            System.out.println(target.getName() + " is no longer affected by smoke.");
        }
    }

    @Override
    public void UponEntityDeath(Combatant target) {
        // No special action needed on death
    }
}
