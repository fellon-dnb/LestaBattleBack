import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Monster {
    private String name;
    private int maxHealth;
    private int currentHealth;
    private int damage;
    private int strength;
    private int agility;
    private int endurance;

    private Weapon weapon;
    private Weapon presentedWeapon;
}
