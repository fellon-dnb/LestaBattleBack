package com.fellon.battle.model;

import com.ancevt.d2d2.scene.Node;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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
   @Getter
   @Setter
   private Node view;

   @Override
   public void setCurrentHealth(int currentHealth) {
      this.currentHealth = currentHealth;
   }
}
