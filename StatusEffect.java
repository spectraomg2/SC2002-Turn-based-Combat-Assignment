
import java.util.List;
import java.util.ArrayList;

public abstract class StatusEffect {
    protected int duration;
    protected List<EventListener> listeners;

    public StatusEffect(int duration) {
        this.duration = duration;
        this.listeners = new ArrayList<EventListener>();
    }

    public int getDuration() {
        return duration;
    }

    protected void addListener(EventListener listener) {
        listeners.add(listener);
    }

    public abstract void OnTurnBegin(Combatant target);
    public abstract void OnTurnEnd(Combatant target);

    public abstract void UponEntityDeath(Combatant target);
}