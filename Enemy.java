public abstract class Enemy extends Character {

    public Enemy(String name, int health, int attack, int defence, int speed) {
        super(name, health, attack, defence, speed);
    }

    public Action chooseAction() {
        return new BasicAttack();
    }

    @Override
    public String toString() {
        return name;
    }
}
