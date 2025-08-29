import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Weapon {
    private WeaponType weaponType;
    private final String name;
    private final int damage;
    private final DamageType damageType;
}
