import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WeaponType {
    SWORD ("Меч", 3, DamageType.SLASHING),
    CLUB ("Дубина", 3, DamageType.BlUDGEONING),
    DAGGER ("Кинжал", 2, DamageType.PIERCING),
    AXE ("Топор", 4, DamageType.SLASHING),
    SPEAR ("Копье", 3, DamageType.PIERCING),
LEGENDARY_SWORD ("Легендарный Меч", 5, DamageType.SLASHING);

    private final String name;
    private final int damage;
    private final DamageType damageType;


    public Weapon toWeapon() {
        return new Weapon(name, damage, damageType);
    }
}
