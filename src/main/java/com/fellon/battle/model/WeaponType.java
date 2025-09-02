package com.fellon.battle.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WeaponType {
    SWORD ("Меч", 3, DamageType.SLASHING),
    CLUB ("Дубина", 3, DamageType.BLUDGEONING),
    DAGGER ("Кинжал", 2, DamageType.PIERCING),
    AXE ("Топор", 4, DamageType.SLASHING),
    SPEAR ("Копье", 3, DamageType.PIERCING),
LEGENDARY_SWORD ("Легендарный Меч", 5, DamageType.SLASHING);

    private final String displayName;
    private final int baseDamage;
    private final DamageType damageType;


    public Weapon toWeapon() {
        return new Weapon(this,displayName, baseDamage, damageType);
    }
}
