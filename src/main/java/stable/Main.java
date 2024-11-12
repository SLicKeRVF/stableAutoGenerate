package stable;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
       RequestGenerate request = new RequestGenerate();
        long startTimer = System.currentTimeMillis();
        long timer = 0L;
        while (timer < 120*60*1000) {
            request.generate();
            timer = (new Date()).getTime() - startTimer;
        }
    }
}
