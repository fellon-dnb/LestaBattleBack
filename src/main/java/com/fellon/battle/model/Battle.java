package com.fellon.battle.model;

public class Battle {
    private State state;
    private Hero hero;
    private Monster monster;
public Battle(Hero hero, Monster monster) {
    this.hero = hero;
    this.monster = monster;
    this.state = State.NON_STARTED;
}
}
