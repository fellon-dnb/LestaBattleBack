import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

public class Weapon {
    private final String name;
    private final int damage;
    private final DamageType damageType;
}
