import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DamageType {
    SLASHING ("рубящий") ,BlUDGEONING ("дробящий") ,PIERCING ("колящий");
    public final String DamageType;
    }

