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

        int turn = 1;

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

            // Поменять местами атакующего и защищающегося
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

        // Пример: скелет получает двойной урон от дубины
        if (defender instanceof Monster) {
            Monster m = (Monster) defender;
            String name = m.getName().toLowerCase();
            if (name.contains("скелет") && attacker.getWeapon().getDamageType().toString().equals("BLUDGEONING")) {
                finalDamage *= 2;
            }

            if (name.contains("слайм") && attacker.getWeapon().getDamageType().toString().equals("SLASHING")) {
                finalDamage = attacker.getAttributes().getStrength(); // режущий не работает
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
