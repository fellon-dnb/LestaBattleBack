import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DamageType {
    SLASHING ("рубящий") ,
    BLUDGEONING ("дробящий") ,
    PIERCING ("колящий");
    public final String dysplayName;
    }

