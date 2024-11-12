package stable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class PromptCollect {
    @Value("#{'${PromptCollect.object}'.split(',+?')}")
    private String[] object;
    @Value("#{'${PromptCollect.random}'.split(',+?')}")
    private String[] random;
    @Value("${PromptCollect.fixed}")
    private String fixed;
    @Value("${PromptCollect.negative}")
    private String negative;
    @Value("#{'${PromptCollect.lora}'.split(' +')}")
    private String[] lora;
    @Value("#{'${PromptCollect.clothing}'.split(',+?')}")
    private String[] clothing;
    @Value("#{'${PromptCollect.pose}'.split(',+?')}")
    private String[] pose;
    @Value("#{'${PromptCollect.emotion}'.split(',+?')}")
    private String[] emotion;
    @Value("${PromptCollect.steps}")
    private Integer steps;
    @Value("${PromptCollect.width}")
    private Integer width;
    @Value("${PromptCollect.height}")
    private Integer height;
    @Value("${PromptCollect.saveImage}")
    private String save;
    @Value("${PromptCollect.sampler}")
    private String sampler;


    // Getters for simple values
    public String getNegative() { return negative; }
    public int getSteps() { if (steps == null) { return 20; } else { return steps; } }
    public Integer getWidth() { if (width == null) { return 500; } else { return width; }  }
    public Integer getHeight() { if (height == null) { return 500; } else { return height; }  }
    public String getSave() { if (save.isEmpty()) { return "true"; } else { return save; } }
    public String getSampler() { if (sampler.isEmpty()) { return "Euler a"; } else { return sampler; } }

    Random digit = new Random();
    public final StringBuilder prompt = new StringBuilder();

    public void work() {
        // check if values is empty
        if (object.length > 1 || !lora[0].isEmpty()) { randomObject(); }
        if (random.length > 1 || !lora[0].isEmpty()) { randomPrompt(); }
        if (emotion.length > 1 || !lora[0].isEmpty()) { randomEmotion(); }
        if (pose.length > 1 || !lora[0].isEmpty()) { randomPose(); }
        if (clothing.length > 1 || !lora[0].isEmpty()) { randomClothing(); }
        if (!fixed.isEmpty() ) { fixedPrompt(); }
        if (lora.length > 1 || !lora[0].isEmpty()) { randomLora(); }
        // the last comma is deleted if lora is not used
        if (prompt.lastIndexOf(",")+1 == prompt.length()-1) { prompt.replace(prompt.length()-2, prompt.length(), ""); }
    }

    private void randomObject() {
        prompt.append(object[digit.nextInt(object.length)].trim()).append(", ");
    }

    private void randomPrompt() {
        for (String s : random) {
            if (digit.nextInt(5) == 2) {
                prompt.append(s.trim()).append(", ");
            }
        }
    }

    private void randomEmotion() {
        for (String s : emotion) {
            if (digit.nextInt(7) == 2) {
                prompt.append(s.trim()).append(", ");
            }
        }
    }

    private void randomPose() {
        if (digit.nextInt(5)>3) {
            prompt.append(pose[digit.nextInt(pose.length)].trim()).append(", ");
        }
    }

    private void randomClothing() {
        prompt.append(clothing[digit.nextInt(clothing.length)].trim()).append(", ");
    }

    private void fixedPrompt() {
        prompt.append(fixed.trim());
    }

    private void randomLora() {
        int d = digit.nextInt(lora.length);
        String[] nlora = lora[d].split(":?:");
        double weight = 1;
        int rDigit = digit.nextInt(10);
        if (rDigit > 7) {
            weight = (double) rDigit / 10;
        }
        prompt.append(lora[d].trim()).append(weight).append(">").insert(0, nlora[1] + ", ");
    }

}
