import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Hero {
   private String name;
   private final int level;
   private final Attributes attributes;
   private int maxHealth;
   private int currentHealth;
   private CharacterClass characterClass;
   private Weapon weapon;
}
