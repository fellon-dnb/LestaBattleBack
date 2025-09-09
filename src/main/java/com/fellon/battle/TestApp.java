package com.fellon.battle;

import com.fellon.battle.model.*;

import java.util.Scanner;

public class TestApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать в Lesta Battle Back!");

        boolean playing = true;

        while (playing) {
            // --- СОЗДАНИЕ ГЕРОЯ ---
            System.out.print("Введите имя вашего героя: ");
            String name = scanner.nextLine();

            System.out.println("Выберите класс:");
            CharacterClass[] classes = CharacterClass.values();
            for (int i = 0; i < classes.length; i++) {
                System.out.println((i + 1) + ". " + classes[i]);
            }

            int classChoice = -1;
            while (classChoice < 1 || classChoice > classes.length) {
                System.out.print("Введите номер класса: ");
                if (scanner.hasNextInt()) {
                    classChoice = scanner.nextInt();
                    scanner.nextLine(); // Съедаем \n
                } else {
                    System.out.println("Нужно ввести число.");
                    scanner.nextLine(); // Съедаем мусор
                }
            }

            CharacterClass chosenClass = classes[classChoice - 1];
            Hero hero = HeroFactory.createHero(name, chosenClass);

            System.out.println("\n🔷 Герой создан: " + hero.getName());
            System.out.println("Класс: " + hero.getСurrentClass());
            System.out.println("Статы: " + hero.getAttributes());
            System.out.println("Здоровье: " + hero.getMaxHealth());
            System.out.println("Оружие: " + hero.getWeapon().getName() + " (" + hero.getWeapon().getDamage() + " урона)");

            int wins = 0;

            // --- ЦИКЛ БОЯ ---
            while (wins < 3 && hero.getCurrentHealth() > 0) {
                Monster monster = MonsterFactory.createRandomMonster();
                System.out.println("\n🧟 Появляется монстр: " + monster.getName());
                System.out.println("→ Оружие: " + monster.getWeapon().getName());
                System.out.println("→ Здоровье: " + monster.getMaxHealth());
                System.out.println("→ Статы: " + monster.getAttributes());

                Battle battle = new Battle(hero, monster);
                Winner result = battle.fight();

                if (result == Winner.HERO) {
                    wins++;
                    System.out.println("🏆 Победа! Побед подряд: " + wins);
                    hero.restoreHealth(); // Подлечим героя
                    Weapon droppedWeapon = monster.getPresentedWeapon();
                    System.out.println("💥 Монстр уронил: " + droppedWeapon.getName() + " (" + droppedWeapon.getDamage() + " урона)");
                    hero.levelUp(scanner, droppedWeapon);
                } else {
                    System.out.println("💀 Герой погиб. Попробуйте снова.");
                    break;
                }
            }

            // --- ПОБЕДА ---
            if (wins == 3) {
                System.out.println("🎉 Поздравляем! Вы победили всех монстров!");
            }

            // --- ХОТИТЕ СЫГРАТЬ СНОВА? ---
            System.out.print("\nХотите сыграть ещё раз? (да/нет): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (!input.equals("да")) {
                playing = false;
            }
        }

        System.out.println("До свидания, герой!");
        scanner.close();
    }
}
