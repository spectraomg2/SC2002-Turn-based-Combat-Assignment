public class Wizard extends Player {

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
}
