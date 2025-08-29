/**
 * Hello, добро пожаловать в Консольную игру LBB!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello player!");
Hero hero = HeroFactory.createHero("Player 1", CharacterClass.ROGUE);
Hero hero1 = HeroFactory.createHero("Player 2", CharacterClass.BARBARIAN);
        System.out.println(hero);
        System.out.println(hero1);
    }
}
