public interface Combatant {

    String getName();
    int getHealth();
    int getMaxHealth();
    int getAttack();
    int getDefence();
    int getSpeed();

    boolean isAlive();
    boolean isStunned();

    int takeDamage(int rawAttack);

    void applyStatusEffects();

    void updateAfterTurn();

    void onTurnSkipped();

    void displayStatus();
}
