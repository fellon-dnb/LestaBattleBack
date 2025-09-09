package com.fellon.battle.model;

public interface HasCombatStats {
    String getName();
    Attributes getAttributes();
    Weapon getWeapon();
    int getCurrentHealth();
    void setCurrentHealth(int hp);
}
