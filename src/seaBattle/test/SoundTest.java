package seaBattle.test;

import seaBattle.game.SoundManager;

class SoundTest {
    public static void main(String[] args) throws InterruptedException {
        SoundManager test = SoundManager.getInstance();
        test.playHit();
        Thread.sleep(10000);
        test.playMiss();
        Thread.sleep(10000);
        test.playSunk();
        Thread.sleep(10000);
        test.playWin();

    }
}
