package com.fellon.battle.model;
import java.util.Scanner;
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
   public void restoreHealth() {
      setCurrentHealth(getMaxHealth());
   }
   public void levelUp(Scanner scanner, Weapon droppedWeapon) {
      System.out.println("\n📈 Повышение уровня!");

      // Выбор нового класса
      System.out.println("Выберите класс для повышения уровня:");
      CharacterClass[] classes = CharacterClass.values();
      for (int i = 0; i < classes.length; i++) {
         System.out.println((i + 1) + ". " + classes[i]);
      }

      int classChoice = -1;
      while (classChoice < 1 || classChoice > classes.length) {
         System.out.print("Введите номер класса: ");
         if (scanner.hasNextInt()) {
            classChoice = scanner.nextInt();
            scanner.nextLine();
         } else {
            System.out.println("Нужно ввести число.");
            scanner.nextLine();
         }
      }

      CharacterClass newClass = classes[classChoice - 1];
      this.сurrentClass = newClass; // обновим текущий класс (технически тут можно реализовать список классов, если хочешь "честный" мультикласс)

      // Применяем бонусы в зависимости от уровня
      switch (newClass) {
         case WARRIOR -> attributes.setStrength(attributes.getStrength() + 1);
         case ROGUE -> attributes.setAgility(attributes.getAgility() + 1);
         case BARBARIAN -> attributes.setEndurance(attributes.getEndurance() + 1);
      }

      // Увеличим уровень и здоровье
      int healthPerLevel = newClass.getHealthPerLevel(); // этот метод должен быть в CharacterClass
      maxHealth += healthPerLevel + attributes.getEndurance();
      currentHealth = maxHealth;

      System.out.println("🎯 Новый уровень: " + (level + 1));
      System.out.println("❤️ Здоровье увеличено до: " + maxHealth);
      System.out.println("📊 Атрибуты теперь: " + attributes);

      // Выбор: взять новое оружие или оставить старое
      System.out.println("Хотите заменить текущее оружие (" + weapon.getName() + ") на " + droppedWeapon.getName() + "? (да/нет)");
      String answer = scanner.nextLine().trim().toLowerCase();
      if (answer.equals("да")) {
         weapon = droppedWeapon;
         System.out.println("🔪 Оружие заменено на " + weapon.getName());
      } else {
         System.out.println("🗡 Вы оставили текущее оружие.");
      }
   }

}
