import java.util.List;
import java.util.ArrayList;

public abstract class Character implements Combatant {

    protected final String name;
    protected int health;
    protected final int maxHealth;
    protected int attack;
    protected int defence;
    protected final int speed;

    protected boolean alive;
    protected boolean stunned;
    protected int stunTurnsRemaining;

    protected List<StatusEffect> statusEffects;

    public Character(String name, int health, int attack, int defence, int speed) {
        this.name    = name;
        this.health  = health;
        this.maxHealth = health;
        this.attack  = attack;
        this.defence = defence;
        this.speed   = speed;
        this.alive   = true;
        this.stunned = false;
        this.stunTurnsRemaining = 0;

        this.statusEffects = new ArrayList<StatusEffect>();
    }

    //Returns 1 if the character dies, 0 otherwise
    @Override
    public int takeDamage(int rawAttack) {
        if(this.health <= 0)
            return 0;

        int actualDamage = Math.max(0, rawAttack - this.defence);
        this.health = Math.max(0, this.health - actualDamage);
        if (this.health <= 0) {
            this.alive = false;
            for (StatusEffect effect : statusEffects) {
                effect.UponEntityDeath(this);
            }

            return 1;
        }
        return 0;
    }

    public void heal(int amount) {
        this.health = Math.min(maxHealth, this.health + amount);
    }

    public void applyStun(int turns) {
        this.stunned = true;
        this.stunTurnsRemaining = turns;
    }

    public void onTurnBegin() {
        applyStatusEffects();
    }

    @Override
    public void applyStatusEffects() {
        for (StatusEffect effect : statusEffects) {
            effect.OnTurnBegin(this);
        }
    }

    @Override
    public void updateAfterTurn() {
        for (StatusEffect effect : statusEffects) {
            effect.OnTurnEnd(this);
        }
        statusEffects.removeIf(effect -> effect.getDuration() <= 0);
        decrementStun();
    }

    @Override
    public void onTurnSkipped() {
        
    }

    private void decrementStun() {
        if (stunTurnsRemaining > 0) {
            stunTurnsRemaining--;
            if (stunTurnsRemaining == 0) {
                stunned = false;
                System.out.println("  >> " + name + " is no longer stunned.");
            }
        }
    }

    public void addStatusEffect(StatusEffect effect) {
        statusEffects.add(effect);
    }

    @Override
    public void displayStatus() {
        System.out.printf("  %-12s | HP: %3d/%-3d | ATK: %2d | DEF: %2d | SPD: %2d%s%n",
                name, health, maxHealth, attack, defence, speed,
                stunned ? " [STUNNED]" : "");
    }

    @Override public String  getName()     { return name; }
    @Override public int     getHealth()   { return health; }
    @Override public int     getMaxHealth(){ return maxHealth; }
    @Override public int     getAttack()   { return attack; }
    @Override public int     getDefence()  { return defence; }
    @Override public int     getSpeed()    { return speed; }
    @Override public boolean isAlive()     { return alive; }
    @Override public boolean isStunned()   { return stunned; }

    protected void setDefence(int defence)  { this.defence = defence; }

    protected void setAttack(int attack)    { this.attack = attack; }

    protected void setHealth(int health) {
        this.health = Math.min(maxHealth, Math.max(0, health));
        this.alive  = this.health > 0;
    }
}
