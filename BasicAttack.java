public class BasicAttack implements Action {

    @Override
    public String getName() {
        return "Basic Attack";
    }

    @Override
    public void execute(Combatant attacker, Combatant target) {
        int rawAttack    = attacker.getAttack();
        int defence      = target.getDefence();
        int actualDamage = Math.max(0, rawAttack - defence);

        target.takeDamage(rawAttack);

        System.out.printf("  %s → Basic Attack → %s: %d − %d = %d damage | %s HP: %d/%d%n",
                attacker.getName(), target.getName(),
                rawAttack, defence, actualDamage,
                target.getName(), target.getHealth(), target.getMaxHealth());

        if (!target.isAlive()) {
            System.out.printf("  >> %s has been ELIMINATED! ✗%n", target.getName());
        }
    }
}
