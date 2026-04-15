import java.util.List;

public class Wizard extends Player{

    private int arcaneBlastBonus = 0;

    public Wizard() {
        super("Wizard", 200, 50, 10, 20);
    }

    public void addArcaneBlastBonus() {
        arcaneBlastBonus += 10;
    }

    public int getArcaneBlastBonus() {
        return arcaneBlastBonus;
    }

    @Override
    public int getAttack() {
        return super.getAttack() + arcaneBlastBonus;
    }

    @Override
    public String getSpecialSkillName() {
        return "Arcane Blast";
    }

    @Override
    public void displayStatus() {
        super.displayStatus();
        if (arcaneBlastBonus > 0) {
            System.out.printf("  %14s Arcane Blast ATK Bonus: +%d%n", "", arcaneBlastBonus);
        }
    }

    @Override
    public void useSpecialSkill(Player player, List<Enemy> alive, boolean resetCooldown) {
        for(Enemy enemy : alive) {
/*            if(enemy.takeDamage(getAttack()) > 0){
                addArcaneBlastBonus();
            }*/
            ArcaneBlastEffect effect = new ArcaneBlastEffect(0);//Make it last until the end of the next turn
            effect.addListener(this);
            enemy.addStatusEffect(effect);

            enemy.takeDamage(getAttack());
        }

        if(resetCooldown){
            startSpecialSkillCooldown();
        }
    }

    @Override
    public void onEvent(String event) {
        super.onEvent(event);
        if (event.equals("Blasted")) {
            addArcaneBlastBonus();
            System.out.println(getName() + " gains an Arcane Blast bonus! Current bonus: " + arcaneBlastBonus);
        }   
    }
}
