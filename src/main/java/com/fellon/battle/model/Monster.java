package com.fellon.battle.model;

import com.ancevt.d2d2.scene.Node;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Monster implements HasCombatStats {
    private String name;
    private int maxHealth;
    private int currentHealth;
    private int damage;
    private Attributes attributes;
    @Setter
    @Getter
    private Node view;

    private Weapon weapon;
    private Weapon presentedWeapon;

}
