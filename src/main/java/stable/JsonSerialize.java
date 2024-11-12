package stable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class JsonSerialize {

    @Value("${RequestGenerate.ip}")
    private String ip;
    public String getIp() { if (ip.isEmpty()) { return "127.0.0.1:7860"; } else { return ip; } }

    public String json() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml"
        );

        PromptCollect collect = context.getBean("promptCollect", PromptCollect.class);

        collect.work();
//   "\"key\":\""+ "value" + "\",\n" +
         return "{\n" +
                "\"prompt\":\"" + collect.prompt + "\",\n" +
                "\"steps\":\"" + collect.getSteps() + "\",\n" +
                "\"negative_prompt\":\"" + collect.getNegative() + "\",\n" +
                "\"sampler_name\":\"" + collect.getSampler() + "\",\n" +
                "\"width\":\" " + collect.getWidth() + "\",\n" +
                "\"height\":\"" + collect.getHeight() + "\",\n" +
                "\"save_images\":\"" + collect.getSave() + "\"\n" +
                "}";
    }
}

