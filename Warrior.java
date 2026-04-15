import java.util.List;
import java.util.Scanner;

public class Warrior extends Player {
    private Scanner sc;

    public Warrior(Scanner sc) {
        super("Warrior", 260, 40, 20, 30);
        this.sc = sc;
    }

    @Override
    public String getSpecialSkillName() {
        return "Shield Bash";
    }

    @Override
    public void useSpecialSkill(Player player, List<Enemy> alive, boolean resetCooldown) {
        System.out.println("Select an enemy to strike with your shield bash:");
        for(int i = 0; i < alive.size(); i++) {
            System.out.println(i + 1 + ": " + alive.get(i).toString());
        }

        int choice = sc.nextInt() - 1;
        if(choice >= 0 && choice < alive.size()) {
            Enemy target = alive.get(choice);
            target.takeDamage(getAttack());
            System.out.println("You used Shield Bash on " + target.toString() + " for " + getAttack() + " damage!");

            StunEffect stun = new StunEffect(2);
            stun.addListener(BattleEngine.instance);
            target.addStatusEffect(stun);
        } else {
            System.out.println("Invalid choice. Special ability failed.");
        }

        if(resetCooldown){
            startSpecialSkillCooldown();
        }
    }
}
