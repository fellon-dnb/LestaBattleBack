import java.util.Random;

public class HeroFactory {
    private static int randomTo3() {
        Random random = new Random();

        int randomNumber = random.nextInt(4);
        return randomNumber;
    }

    public static Hero createHero(String name, CharacterClass clazz) {
        Attributes attributes = new Attributes(
                randomTo3(),
                randomTo3(),
                randomTo3()
        );

        int maxHealth = clazz.getStartHealth() + attributes.getEndurance();
        Weapon weapon = clazz.getStartWeapon().toWeapon();
        return new Hero(
                name,
                1,
                attributes,
                maxHealth,
                maxHealth,
                clazz,
                weapon
        );
    }
}
