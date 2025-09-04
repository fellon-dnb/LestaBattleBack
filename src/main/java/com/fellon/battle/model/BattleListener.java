package com.fellon.battle.model;

public interface BattleListener {
default void onBattleStarted(Battle battle) {}
    default void onTurnStarted(Battle battle, HasCombatStats attacker, HasCombatStats deffender, int turnNumber) {}
default  void onTurnEnded(Battle battle, HasCombatStats attacker, HasCombatStats deffender, int turnNumber) {}
default void onBatttleEnded(Battle battle, HasCombatStats winner, HasCombatStats loser) {}
}

