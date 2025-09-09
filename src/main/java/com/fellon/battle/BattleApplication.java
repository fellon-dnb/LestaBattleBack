package com.fellon.battle;

import com.ancevt.d2d2.ApplicationConfig;
import com.ancevt.d2d2.ApplicationContext;
import com.ancevt.d2d2.D2D2;
import com.ancevt.d2d2.debug.FpsMeter;
import com.ancevt.d2d2.lifecycle.Application;
import com.ancevt.d2d2.log.Log;

public class BattleApplication implements Application {
    public static void main(String[] args) {
        /*

 */
        D2D2.init(new BattleApplication(),
                new ApplicationConfig()
                        .width(1000)
                        .height(800)
                        .args(args)
        );
    }
    private BattleEngine battleEngine;


    @Override
    public void start(ApplicationContext context) {
        D2D2.log.setLevel(Log.DEBUG);

        battleEngine = new BattleEngine();
        battleEngine.start();


        context.stage().addChild(new FpsMeter());
    }

    public void stop() {

    }
}



