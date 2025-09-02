package com.fellon.battle.model;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum CharacterClass {
    BARBARIAN ("Варвар",6, WeaponType.CLUB ),
    ROGUE ("Разбойник",4,WeaponType.DAGGER),
    WARRIOR ("Воин",5,WeaponType.SWORD);

    private final String displayName;
    private final int startHealth;
    private final WeaponType startWeapon;
}
