package com.fellon.battle.model;

import com.ancevt.d2d2.scene.Node;

import java.util.concurrent.ThreadLocalRandom;

public class MonsterFactory {

    public static Monster createRandomMonster() {
        int rnd = ThreadLocalRandom.current().nextInt(1, 7); // 6 монстров

        return switch (rnd) {
            case 1 -> goblin();
            case 2 -> skeleton();
            case 3 -> slime();
            case 4 -> ghost();
            case 5 -> golem();
            default -> dragon();
        };
    }

    private static Monster goblin() {
        return new Monster(
                "Гоблин",
                10,
                10,
                2,
                new Attributes(2, 1, 2), null,
                WeaponType.SWORD.toWeapon(),
                WeaponType.SWORD.toWeapon()
        );
    }

    private static Monster skeleton() {
        return new Monster(
                "Скелет",
                10,
                10,
                2,
                new Attributes(2, 2, 1),  null,
                WeaponType.CLUB.toWeapon(),
                WeaponType.CLUB.toWeapon()
        );
    }

    private static Monster slime() {
        return new Monster(
                "Слайм",
                8,
                8,
                1,
                new Attributes(1, 2, 1),
                null,
                WeaponType.CLUB.toWeapon(),
                WeaponType.CLUB.toWeapon()
        );
    }

    private static Monster ghost() {
        return new Monster(
                "Призрак",
                20,
                20,
                4,
                new Attributes(3, 3, 3), null,
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
                new Attributes(3, 1, 3), null,
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
                new Attributes(3, 3, 3), null,
                WeaponType.LEGENDARY_SWORD.toWeapon(),
                WeaponType.LEGENDARY_SWORD.toWeapon()
        );
    }
}