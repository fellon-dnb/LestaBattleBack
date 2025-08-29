import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Monster implements HasCombatStats {
    private String name;
    private int maxHealth;
    private int currentHealth;
    private int damage;
    private int strength;
    private int agility;
    private int endurance;

    private Weapon weapon;
    private Weapon presentedWeapon;

    @Override
    public Attributes getAttributes() {
        return new Attributes(strength, agility, endurance);
    }
}
