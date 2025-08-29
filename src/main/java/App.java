/**
 * Hello, добро пожаловать в Консольную игру LBB!
 */
public class App {
    public static void main(String[] args) {
        Hero hero = HeroFactory.createHero("Player 1", CharacterClass.ROGUE);
        System.out.println(hero);

        Monster monster = MonsterFactory.createRandomMonster();
        System.out.println(monster);
}
    }
