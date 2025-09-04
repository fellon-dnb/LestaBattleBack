package com.fellon.battle.model;

import java.util.List;
import java.util.ArrayList;


public class Battle {
    private State state;
    private Hero hero;
    private Monster monster;

    public Battle(Hero hero, Monster monster) {
        this.hero = hero;
        this.monster = monster;
        this.state = State.NON_STARTED;
    }

    private List<BattleListener> listeners = new ArrayList<>();

    public void addListener(BattleListener listener) {
        listeners.add(listener);
    }

    public void removeListener(BattleListener listener) {
        listeners.remove(listener);
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

