import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Hero {
   private String name;
   private int health;
   private int strength;
   private CharacterClass characterClass;
   private Weapon weapon;
}
