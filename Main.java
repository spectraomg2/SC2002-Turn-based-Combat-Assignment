import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        showLoadingScreen();

        boolean running = true;
        boolean sameSettings = false;
        Player player = null;
        Level level = null;

        while (running) {
            if (!sameSettings) {
                System.out.println("\nChoose character: 1) Warrior  2) Wizard");
                System.out.print("Choice: ");
                player = scanner.nextLine().trim().equals("2") ? new Wizard() : new Warrior(scanner);

                System.out.println("Choose difficulty: 1) Easy  2) Medium  3) Hard");
                System.out.print("Choice: ");
                switch (scanner.nextLine().trim()) {
                    case "2": level = Level.MEDIUM; break;
                    case "3": level = Level.HARD;   break;
                    default:  level = Level.EASY;   break;
                }
            } else {
                player = player instanceof Wizard ? new Wizard() : new Warrior(scanner);
            }

            new BattleEngine(player, level, scanner).startBattle();

            System.out.println("\n1) Replay same settings  2) New game  3) Exit");
            System.out.print("Choice: ");
            switch (scanner.nextLine().trim()) {
                case "1": sameSettings = true;  break;
                case "3": running = false;       break;
                default:  sameSettings = false;  break;
            }
        }

        System.out.println("Goodbye!");
    }

    private static void showLoadingScreen() {
        System.out.println("+==================================================+");
        System.out.println("|          TURN-BASED COMBAT ARENA                 |");
        System.out.println("+==================================================+");

        System.out.println("\n--- PLAYERS ---");
        System.out.println("  1) Warrior");
        System.out.println("     HP: 260 | ATK: 40 | DEF: 20 | SPD: 30");
        System.out.println("     Special: Shield Bash");
        System.out.println("       - Deal Basic Attack damage to one enemy");
        System.out.println("       - Target is stunned for 2 turns | Cooldown: 3");
        System.out.println();
        System.out.println("  2) Wizard");
        System.out.println("     HP: 200 | ATK: 50 | DEF: 10 | SPD: 20");
        System.out.println("     Special: Arcane Blast");
        System.out.println("       - Deal Basic Attack damage to ALL enemies");
        System.out.println("       - Each kill grants +10 ATK until end of level | Cooldown: 3");

        System.out.println("\n--- ENEMIES ---");
        System.out.println("  Goblin | HP:  55 | ATK: 35 | DEF: 15 | SPD: 25");
        System.out.println("  Wolf   | HP:  40 | ATK: 45 | DEF:  5 | SPD: 35");

        System.out.println("\n--- DIFFICULTY ---");
        System.out.println("  1) Easy   - 3 Goblins");
        System.out.println("  2) Medium - 1 Goblin + 1 Wolf | Backup: 2 Wolves");
        System.out.println("  3) Hard   - 2 Goblins         | Backup: 1 Goblin + 2 Wolves");

        System.out.println("\n--- ITEMS (choose 2; duplicates allowed) ---");
        System.out.println("  1) Potion      - Restore 100 HP");
        System.out.println("  2) Smoke Bomb  - Enemy attacks deal 0 damage this turn + next");
        System.out.println("  3) Power Stone - Trigger special skill once, no cooldown cost");
        System.out.println();
    }
}
