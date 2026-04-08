public interface Action {

    String getName();

    void execute(Combatant attacker, Combatant target);
}
