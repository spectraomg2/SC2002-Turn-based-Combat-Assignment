import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Turn-Based Combat Arena ===");

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
}
