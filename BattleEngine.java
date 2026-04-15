import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BattleEngine implements EventListener {

    private final Player player;
    private final Level level;
    private final TurnOrderStrategy turnOrderStrategy;
    private final Scanner scanner;

    private ArrayList<Item> inventory;

    private final List<Enemy> activeEnemies;
    private final List<Enemy> initialEnemies;
    private boolean backupSpawned;
    private int roundNumber;

    private boolean skipRound;

    public static BattleEngine instance;

    public BattleEngine(Player player, Level level, Scanner scanner) {
        this.player = player;
        this.level = level;
        this.scanner = scanner;
        this.turnOrderStrategy = new SpeedBasedTurnOrder();
        this.activeEnemies = new ArrayList<>();
        this.initialEnemies = new ArrayList<>();
        this.backupSpawned = false;
        this.roundNumber = 0;

        instance = this;
    }

    @Override
    public void onEvent(String event) {
        switch (event) {
            case "Skip":
                skipRound = true;
                break;
            default:
                break;
        }
    }

    public void startBattle() {
        this.inventory = new ArrayList<Item>();

        List<Enemy> initial = level.getInitialEnemies();
        initialEnemies.addAll(initial);
        activeEnemies.addAll(initial);

        //Choose starting item
        for(int i = 1;i<=2;i++){
            System.out.println("Choose starting item " + i + ": 1) Potion 2) Smoke Bomb 3) Power Stone");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> inventory.add(new Potion());
                case 2 -> inventory.add(new SmokeBomb());
                case 3 -> inventory.add(new PowerStone());
                default -> {
                    System.out.println("Invalid choice, no item added.");
                    i--;
                }
            }
        }

        while (!isBattleOver()) {
            processRound();
        }

        if (!player.isAlive()) {
            long surviving = activeEnemies.stream().filter(Enemy::isAlive).count();
            System.out.println("\n========================================");
            System.out.println("  DEFEATED. Don't give up, try again!");
            System.out.println("========================================");
            System.out.println("  Enemies remaining : " + surviving);
            System.out.println("  Total rounds      : " + roundNumber);
            printItemSummary();
            System.out.println("========================================");
        } else {
            System.out.println("\n========================================");
            System.out.println("  VICTORY! Congratulations!");
            System.out.println("  You have defeated all your enemies.");
            System.out.println("========================================");
            System.out.println("  Remaining HP  : " + player.getHealth() + "/" + player.getMaxHealth());
            System.out.println("  Total rounds  : " + roundNumber);
            printItemSummary();
            System.out.println("========================================");
        }
    }

    //Loop for each round
    private void processRound() {
        roundNumber++;
        System.out.println("\n--- Round " + roundNumber + " ---");
        printStatus();

        List<Combatant> turnOrder = turnOrderStrategy.determineOrder(getAliveCombatants());

        for (Combatant c : turnOrder) {
            skipRound = false;

            if (!c.isAlive()) continue;
            if (!player.isAlive()) break;
            processTurn(c);
        }

        for (Combatant c : getAliveCombatants()) {
            c.updateAfterTurn();
        }

        triggerBackupSpawnIfNeeded();
    }

    private void processTurn(Combatant c) {
        if (!c.isAlive()) return;

        ((Character) c).onTurnBegin();

        if (!c.isAlive()) return;

        if (skipRound) {
            System.out.println(c.getName() + " is stunned — turn skipped.");
            c.onTurnSkipped();
            return;
        }

        if (c instanceof Player) {
            handlePlayerTurn((Player) c);
        } else {
            handleEnemyTurn((Enemy) c);
        }

        //c.updateAfterTurn();
    }

    private void handlePlayerTurn(Player p) {
        List<Enemy> alive = getAliveEnemies();
        if (alive.isEmpty()) return;

        System.out.println("\nChoose action: 1) Basic Attack 2) Use Item 3) Defend 4) Special Ability");
        System.out.print("Choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> {
                Action action = new BasicAttack();
                Enemy target = selectTarget(alive);
                action.execute(p, target);
            }
            case 2 -> {
                if(inventory.isEmpty()){
                    System.out.println("No items available, turn skipped.");
                    return;
                }
                System.out.println("Choose item to use:");
                for (int i = 0; i < inventory.size(); i++) 
                    System.out.println("  " + (i + 1) + ". " + inventory.get(i).getClass().getSimpleName());
                System.out.print("Choice: ");
                int itemChoice = scanner.nextInt() - 1;
                if (itemChoice >= 0 && itemChoice < inventory.size()) {
                    Item item = inventory.get(itemChoice);
                    item.use(p,alive);
                    inventory.remove(itemChoice);
                } else {
                    System.out.println("Invalid choice, turn skipped.");
                }
            }
            case 3 -> p.defend();
            case 4 -> {
                if (p.getSpecialSkillCooldown() > 0) {
                    System.out.println("Special ability on cooldown for " + p.getSpecialSkillCooldown() + " more round(s), turn skipped.");
                    return;
                }
                p.useSpecialSkill(p, alive, true);
            }
            default -> System.out.println("Invalid choice, turn skipped.");
        }
    }

    private void handleEnemyTurn(Enemy e) {
        e.chooseAction().execute(e, player);
    }

    private Enemy selectTarget(List<Enemy> alive) {
        if (alive.size() == 1) return alive.get(0);

        System.out.println("Select target:");
        for (int i = 0; i < alive.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + alive.get(i).getName() + " (HP: " + alive.get(i).getHealth() + ")");
        }
        System.out.print("Choice: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (choice >= 0 && choice < alive.size()) return alive.get(choice);
        } catch (NumberFormatException ignored) {}
        return alive.get(0);
    }

    private void triggerBackupSpawnIfNeeded() {
        if (!backupSpawned && initialEnemies.stream().noneMatch(Enemy::isAlive)) {
            List<Enemy> backup = level.getBackupEnemies();
            if (!backup.isEmpty()) {
                activeEnemies.addAll(backup);
                backupSpawned = true;
                System.out.println("Backup spawn! New enemies enter the battle.");
                for (Enemy e : backup) System.out.println("  " + e.getName());
            }
        }
    }

    private boolean isBattleOver() {
        return !player.isAlive() || activeEnemies.stream().noneMatch(Enemy::isAlive);
    }

    private List<Combatant> getAliveCombatants() {
        List<Combatant> alive = new ArrayList<>();
        if (player.isAlive()) alive.add(player);
        for (Enemy e : activeEnemies) if (e.isAlive()) alive.add(e);
        return alive;
    }

    private List<Enemy> getAliveEnemies() {
        List<Enemy> alive = new ArrayList<>();
        for (Enemy e : activeEnemies) if (e.isAlive()) alive.add(e);
        return alive;
    }

    private void printItemSummary() {
        long potions    = inventory.stream().filter(i -> i instanceof Potion).count();
        long smokeBombs = inventory.stream().filter(i -> i instanceof SmokeBomb).count();
        long powerStones= inventory.stream().filter(i -> i instanceof PowerStone).count();
        System.out.println("  Potions left  : " + potions);
        System.out.println("  Smoke Bombs   : " + smokeBombs);
        System.out.println("  Power Stones  : " + powerStones);
    }

    private void printStatus() {
        player.displayStatus();
        for (Enemy e : activeEnemies) {
            if (e.isAlive()) e.displayStatus();
        }
    }
}
