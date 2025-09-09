package com.fellon.battle.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Battle {
    private State state = State.NON_STARTED;
    private final Hero hero;
    private final Monster monster;
    private Winner winner;
    private final List<BattleListener> listeners = new ArrayList<>();
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
                System.out.println(attacker.getName() + " наносит " + damage + " урона " + defender.getName());
            } else {
                System.out.println(attacker.getName() + " промахивается по " + defender.getName());
            }

            fireTurnEnd(attacker, defender, turn);

            if (defender.getCurrentHealth() <= 0) break;

            // Поменять местами
            HasCombatStats temp = attacker;
            attacker = defender;
            defender = temp;

            turn++;
        }

        state = State.FINISHED;

        HasCombatStats winnerStats = hero.getCurrentHealth() > 0 ? hero : monster;
        HasCombatStats loserStats = hero.getCurrentHealth() <= 0 ? hero : monster;

        fireBattleEnd(winnerStats, loserStats);

        winner = (hero.getCurrentHealth() > 0) ? Winner.HERO : Winner.MONSTER;
        return winner;
    }

    private boolean attackHits(HasCombatStats attacker, HasCombatStats defender) {
        int max = attacker.getAttributes().getAgility() + defender.getAttributes().getAgility();
        int roll = new Random().nextInt(max + 1);
        return roll > defender.getAttributes().getAgility();
    }

    private int calculateDamage(HasCombatStats attacker, HasCombatStats defender) {
        int base = attacker.getWeapon().getDamage() + attacker.getAttributes().getStrength();
        int finalDamage = base;

        //спецэффекты атакующего класса
        if (attacker instanceof Hero heroAttacker) {
            CharacterClass currentClass = heroAttacker.getСurrentClass();

            switch (currentClass) {
                case WARRIOR -> {
                    // Порыв к действию
                    if (turn == 1 && attacker == heroAttacker) {
                        System.out.println("🛡 Воин использует Порыв к действию! Урон удваивается.");
                        finalDamage *= 2;
                    }
                }
                case ROGUE -> {
                    // Скрытая атака
                    if (attacker.getAttributes().getAgility() > defender.getAttributes().getAgility()) {
                        System.out.println("🗡 Разбойник использует Скрытую атаку! +1 урон.");
                        finalDamage += 1;
                    }
                }
                case BARBARIAN -> {
                    // Ярость
                    if (turn <= 3) {
                        System.out.println("🔥 Варвар в ярости! +2 урона.");
                        finalDamage += 2;
                    } else {
                        System.out.println("😴 Ярость прошла. -1 урон.");
                        finalDamage -= 1;
                    }
                }
            }
        }

        // Эффекты защиты целей
        if (defender instanceof Hero heroDefender) {
            CharacterClass currentClass = heroDefender.getСurrentClass();

            if (currentClass == CharacterClass.WARRIOR) {
                if (heroDefender.getAttributes().getStrength() > attacker.getAttributes().getStrength()) {
                    System.out.println("🛡 Воин активирует Щит! -3 к получаемому урону.");
                    finalDamage = Math.max(0, finalDamage - 3);
                }
            }
        }

        //  Уязвимости монстров
        if (defender instanceof Monster m) {
            String name = m.getName().toLowerCase();
            if (name.contains("скелет") && attacker.getWeapon().getDamageType() == DamageType.BLUDGEONING) {
                System.out.println("💀 Скелет уязвим к дробящему урону! Урон x2.");
                finalDamage *= 2;
            }

            if (name.contains("слайм") && attacker.getWeapon().getDamageType() == DamageType.SLASHING) {
                System.out.println("🧪 Слайм невосприимчив к рубящему урону! Считается только сила.");
                finalDamage = attacker.getAttributes().getStrength();
            }
        }

        return Math.max(finalDamage, 0);
    }

    private void applyDamage(HasCombatStats target, int damage) {
        target.setCurrentHealth(Math.max(0, target.getCurrentHealth() - damage));
    }

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
            listener.onBatttleEnded(this, winner, loser);
        }
    }
}
