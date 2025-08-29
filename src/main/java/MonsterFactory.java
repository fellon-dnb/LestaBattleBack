

import java.util.concurrent.ThreadLocalRandom;

public class MonsterFactory {

    public static Monster createRandomMonster() {
        int rnd = ThreadLocalRandom.current().nextInt(1, 7); // 6 монстров

        switch (rnd) {
            case 1:
                return goblin();
            case 2:
                return skeleton();
            case 3:
                return slime();
            case 4:
                return ghost();
            case 5:
                return golem();
            default:
                return dragon();
        }
    }

    private static Monster goblin() {
        return new Monster(
                "Гоблин",
                5,
                5,
                2,
                1,
                1,
                1,
                WeaponType.DAGGER.toWeapon(),
                WeaponType.DAGGER.toWeapon()
        );
    }

    private static Monster skeleton() {
        return new Monster(
                "Скелет",
                10,
                10,
                2,
                2,
                2,
                1,
                WeaponType.CLUB.toWeapon(),
                WeaponType.CLUB.toWeapon()
        );
    }

    private static Monster slime() {
        return new Monster(
                "Слизень",
                8,
                8,
                1,
                3,
                1,
                2,
                WeaponType.SPEAR.toWeapon(),
                WeaponType.SPEAR.toWeapon()
        );
    }

    private static Monster ghost() {
        return new Monster(
                "Призрак",
                20,
                20,
                4,
                3,
                3,
                3,
                WeaponType.LEGENDARY_SWORD.toWeapon(),
                WeaponType.LEGENDARY_SWORD.toWeapon()
        );
    }

    private static Monster golem() {
        return new Monster(
                "Голем",
                10,
                10,
                1,
                3,
                1,
                3,
                WeaponType.AXE.toWeapon(),
                WeaponType.AXE.toWeapon()
        );
    }
    private static Monster dragon() {
        return new Monster(
                "Дракон",
                20,
                20,
                4,
                3,
                3,
                3,
                WeaponType.LEGENDARY_SWORD.toWeapon(),
                WeaponType.LEGENDARY_SWORD.toWeapon()
        );
    }
}