package stable;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml"
        );
        PromptCollect collect = context.getBean("promptCollect", PromptCollect.class);
        RequestGenerate request = new RequestGenerate();
        long startTimer = System.currentTimeMillis();
        long timer = 0L;
        while (timer <= collect.getTime() * 60 * 1000) {
            request.generate();
            timer = (new Date()).getTime() - startTimer;
        }
        context.close();
    }
}
