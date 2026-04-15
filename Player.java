import java.util.List;

public abstract class Player extends Character implements EventListener {

    protected int specialSkillCooldown;
    protected int defenceBuff;

    protected boolean invulnerable;

    public Player(String name, int health, int attack, int defence, int speed) {
        super(name, health, attack, defence, speed);
        this.specialSkillCooldown = 0;
    }

    public boolean canUseSpecialSkill() {
        return specialSkillCooldown <= 0;
    }

    public int getSpecialSkillCooldown() {
        return specialSkillCooldown;
    }

    public void startSpecialSkillCooldown() {
        this.specialSkillCooldown = 3;
    }

    public void setSpecialSkillCooldown(int rounds) {
        this.specialSkillCooldown = rounds;
    }

    @Override
    public void onTurnBegin() {
        defenceBuff = 0;
        invulnerable = false;
        
        super.onTurnBegin();
    }

    @Override
    public void updateAfterTurn() {
        super.updateAfterTurn();
        specialSkillCooldown = Math.max(0, specialSkillCooldown - 1);
    }

    @Override
    public void onTurnSkipped() {
        super.onTurnSkipped();
    }

    @Override
    public void displayStatus() {
        super.displayStatus();
        if (specialSkillCooldown > 0) {
            System.out.printf("  %14s Special Skill Cooldown: %d round(s)%n",
                    "", specialSkillCooldown);
        }
    }

    public void defend() {
        DefendEffect effect = new DefendEffect(2);
        effect.addListener(this);
        addStatusEffect(effect);
    }

    public void useSmoke() {
        SmokedEffect effect = new SmokedEffect(2);
        effect.addListener(this);
        addStatusEffect(effect);
    }

    public int takeDamage(int damage) {
        if(invulnerable || health <= 0){
            return 0;
        }

        int finalDamage = Math.max(0, damage - defence - defenceBuff);
        health -= finalDamage;
        if (health <= 0) {
            health = 0;
            this.alive = false;
            return 1;
        }
        return 0;
    }

    @Override
    public void onEvent(String event) {
        switch (event) {
            case "Defend":
                defenceBuff = 10;
                break;
            case "Smoked":
                invulnerable = true;
                System.out.println(getName() + " is now invulnerable due to smoke!");
                break;
            default:
                break;
        }
    }

    @Override
    public int getDefence() {
        return super.getDefence() + defenceBuff;
    }

    public abstract String getSpecialSkillName();
    public abstract void useSpecialSkill(Player player, List<Enemy> alive, boolean resetCooldown);
}
