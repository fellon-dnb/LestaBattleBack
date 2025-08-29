/**
 * Hello, добро пожаловать в Консольную игру LBB!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello player!");
        Weapon sword = new Weapon("Sword", 10, DamageType.SLASHING);
        Weapon axe = new Weapon("Axe", 15, DamageType.SLASHING);
        // System.out.println(axe);
        //System.out.println(sword);
        Hero hero = new Hero("Conan", 100, 10, CharacterClass.BARBARIAN, axe);
    System.out.println(hero);
    }
}
