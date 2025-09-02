package com.fellon.battle.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Monster implements HasCombatStats {
    private String name;
    private int maxHealth;
    private int currentHealth;
    private int damage;
    private Attributes attributes;

    private Weapon weapon;
    private Weapon presentedWeapon;

}
