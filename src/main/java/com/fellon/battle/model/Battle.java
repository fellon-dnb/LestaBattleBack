package com.fellon.battle.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Battle {
    private State state = State.NON_STARTED;
    private final Hero hero;
    private final Monster monster;
    private Winner winner;
    private final List<BattleListener> listeners = new ArrayList<>();
    private final Random rng = new Random();
    private int turn = 1;

    public Battle(Hero hero, Monster monster) {
        this.hero = hero;
        this.monster = monster;
    }

    public void addListener(BattleListener listener) {
        listeners.add(listener);
    }

    public void removeListener(BattleListener listener) {
        listeners.remove(listener);
    }

    public Winner fight() {
        state = State.IN_PROGRESS;
        fireBattleStart();

        HasCombatStats attacker;
        HasCombatStats defender;

        // кто ходит первым: у кого больше ловкость; при равенстве — герой
        if (hero.getAttributes().getAgility() >= monster.getAttributes().getAgility()) {
            attacker = hero;
            defender = monster;
        } else {
            attacker = monster;
            defender = hero;
        }

        while (hero.getCurrentHealth() > 0 && monster.getCurrentHealth() > 0) {
            fireTurnStart(attacker, defender, turn);

            if (attackHits(attacker, defender)) {
                int damage = calculateDamage(attacker, defender);
                applyDamage(defender, damage);
                System.out.println(nameOf(attacker) + " наносит " + damage + " урона " + nameOf(defender));
            } else {
                System.out.println(nameOf(attacker) + " промахивается по " + nameOf(defender));
            }

            fireTurnEnd(attacker, defender, turn);

            if (defender.getCurrentHealth() <= 0) break;

            // смена ролей
            HasCombatStats temp = attacker;
            attacker = defender;
            defender = temp;

            turn++;
        }

        state = State.FINISHED;

        HasCombatStats winnerStats = hero.getCurrentHealth() > 0 ? hero : monster;
        HasCombatStats loserStats  = hero.getCurrentHealth() <= 0 ? hero : monster;

        fireBattleEnd(winnerStats, loserStats);

        winner = (hero.getCurrentHealth() > 0) ? Winner.HERO : Winner.MONSTER;
        return winner;
    }

    // --- combat helpers ---

    // ТЗ: бросок 1..(AGI атакера + AGI цели); если roll <= AGI цели — промах
    private boolean attackHits(HasCombatStats attacker, HasCombatStats defender) {
        int max = attacker.getAttributes().getAgility() + defender.getAttributes().getAgility();
        int roll = rng.nextInt(max) + 1; // [1..max]
        return roll > defender.getAttributes().getAgility();
    }

    private int calculateDamage(HasCombatStats attacker, HasCombatStats defender) {
        int weaponDamage = attacker.getWeapon().getDamage();
        int strength     = attacker.getAttributes().getStrength();

        // базовый урон до модификаторов
        int finalDamage = weaponDamage + strength;

        // ===== Классовые бонусы атакующего героя =====
        if (attacker instanceof Hero heroAttacker) {
            CharacterClass cls = heroAttacker.getСurrentClass();

            switch (cls) {
                case WARRIOR -> {
                    // Порыв к действию: первый ход — двойной УРОН ОРУЖИЕМ (строго по ТЗ)
                    if (turn == 1) {
                        System.out.println("🛡 Воин использует Порыв к действию! Урон оружием x2.");
                        finalDamage = (weaponDamage * 2) + strength;
                    }
                }
                case ROGUE -> {
                    // Скрытая атака: +1, если ловкость выше
                    if (attacker.getAttributes().getAgility() > defender.getAttributes().getAgility()) {
                        System.out.println("🗡 Разбойник: Скрытая атака (+1).");
                        finalDamage += 1;
                    }
                }
                case BARBARIAN -> {
                    // Ярость: ходы 1..3 => +2, потом -1
                    if (turn <= 3) {
                        System.out.println("🔥 Варвар в ярости: +2 урона.");
                        finalDamage += 2;
                    } else {
                        System.out.println("😴 Варвар устал: -1 урона.");
                        finalDamage -= 1;
                    }
                }
            }
        }

        //  Эффекты/уязвимости цели-героя
        if (defender instanceof Hero heroDefender) {
            CharacterClass cls = heroDefender.getСurrentClass();

            // Воин (щит): -3 к получаемому урону, если сила воина > силы атакующего
            if (cls == CharacterClass.WARRIOR) {
                if (heroDefender.getAttributes().getStrength() > attacker.getAttributes().getStrength()) {
                    System.out.println("🛡 Воин блокирует щитом: -3 к полученному урону.");
                    finalDamage = Math.max(0, finalDamage - 3);
                }
            }
        }

        //  Особенности монстров
        if (defender instanceof Monster defMonster) {
            String defName = defMonster.getName().toLowerCase();

            // Скелет: получает x2 от дробящего
            if (defName.contains("скелет")
                    && attacker.getWeapon().getDamageType() == DamageType.BLUDGEONING) {
                System.out.println("💀 Скелет уязвим к дробящему: урон x2.");
                finalDamage *= 2;
            }

            // Слайм: рубящее ОРУЖИЕ не наносит урон (но сила и прочие бонусы работают)
            if (defName.contains("слайм")
                    && attacker.getWeapon().getDamageType() == DamageType.SLASHING) {
                System.out.println("🧪 Слайм игнорирует рубящее оружие: урон оружием = 0.");
                // пересчёт: только сила + уже начисленные классовые модификаторы, вычтя вклад оружия
                finalDamage -= attacker.getWeapon().getDamage();
                finalDamage = Math.max(finalDamage, strength); // минимум сила (если модификаторы ушли в минус)
            }

            // Голем: Каменная кожа — снижает получаемый урон на свою выносливость
            if (defName.contains("голем")) {
                int reduce = defMonster.getAttributes().getEndurance();
                if (reduce > 0) {
                    System.out.println("🪨 Голем: Каменная кожа (-" + reduce + ").");
                    finalDamage = Math.max(0, finalDamage - reduce);
                }
            }
        }

        if (attacker instanceof Monster atkMonster) {
            String atkName = atkMonster.getName().toLowerCase();

            // Призрак: Скрытая атака как у разбойника 1 уровня
            if (atkName.contains("призрак")) {
                if (attacker.getAttributes().getAgility() > defender.getAttributes().getAgility()) {
                    System.out.println("👻 Призрак бьёт из тени: +1 урон.");
                    finalDamage += 1;
                }
            }

            // Дракон: каждый 3-й ход дополнительно +3 урона (дыхание огнём)
            if (atkName.contains("дракон") && (turn % 3 == 0)) {
                System.out.println("🔥 Дракон дышит огнём: +3 урона.");
                finalDamage += 3;
            }
        }

        return Math.max(finalDamage, 0);
    }

    private void applyDamage(HasCombatStats target, int damage) {
        target.setCurrentHealth(Math.max(0, target.getCurrentHealth() - damage));
    }

    //  events

    private void fireBattleStart() {
        for (BattleListener listener : listeners) {
            listener.onBattleStarted(this);
        }
    }

    private void fireTurnStart(HasCombatStats attacker, HasCombatStats defender, int turn) {
        for (BattleListener listener : listeners) {
            listener.onTurnStarted(this, attacker, defender, turn);
        }
    }

    private void fireTurnEnd(HasCombatStats attacker, HasCombatStats defender, int turn) {
        for (BattleListener listener : listeners) {
            listener.onTurnEnded(this, attacker, defender, turn);
        }
    }

    private void fireBattleEnd(HasCombatStats winner, HasCombatStats loser) {
        for (BattleListener listener : listeners) {
            listener.onBattleEnded(this, winner, loser);
        }
    }

    //  util

    private String nameOf(HasCombatStats unit) {
        if (unit instanceof Hero h) return h.getName();
        if (unit instanceof Monster m) return m.getName();
        return unit.toString();
    }
}
