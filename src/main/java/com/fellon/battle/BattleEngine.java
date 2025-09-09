package com.fellon.battle;

import com.ancevt.d2d2.D2D2;
import com.ancevt.d2d2.time.Timer;

public class BattleEngine {

    private int time;
    private Timer perSecondTimer;

    public void start() {
        if (perSecondTimer != null) perSecondTimer.stop();

        perSecondTimer = Timer.setInterval(1000, this::perSecond);
    }

    public void stop() {
        if (perSecondTimer != null) {
            perSecondTimer.stop();
            perSecondTimer = null;
        } else {
            throw new IllegalStateException("Timer is not running");
        }
    }

    private void perSecond(Timer timer) {
        time++;
        D2D2.log.debug(BattleEngine.class, "Time: " + time);

        tick();
    }

    public void tick() {
        // Здесь посекундная логика
    }
}

