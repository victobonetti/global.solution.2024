package fiap.global.solution.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentTypeText {
    public ContentTypeText(String prompt) {
        this.text = prompt;
    }
    public String type = "text";
    public String text;
}
