package com.fellon.battle.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Hero implements HasCombatStats {
   private String name;
   private final int level;
   private final Attributes attributes;
   private int maxHealth;
   private int currentHealth;
   private CharacterClass сurrentClass;
   private Weapon weapon;

   @Override
   public void setCurrentHealth(int currentHealth) {
      this.currentHealth = currentHealth;
   }
}
