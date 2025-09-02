package com.fellon.battle;

import com.ancevt.d2d2.ApplicationConfig;
import com.ancevt.d2d2.ApplicationContext;
import com.ancevt.d2d2.D2D2;
import com.ancevt.d2d2.debug.FpsMeter;
import com.ancevt.d2d2.lifecycle.Application;

public class BattleApplication implements Application {
    public static void main(String[] args) {
        /*
        Hero hero = HeroFactory.createHero("Player 1", CharacterClass.ROGUE);
        System.out.println(hero);

        Monster monster = MonsterFactory.createRandomMonster();
        System.out.println(monster);
        */

        D2D2.init(new BattleApplication(),
                new ApplicationConfig()
                        .width(1000)
                        .height(800)
                        .args(args)
        );
    }

    @Override
    public void start(ApplicationContext context) {

        context.stage().addChild(new FpsMeter());
    }

    @Override
    public void stop() {
        // TODO: save stats
    }
}
