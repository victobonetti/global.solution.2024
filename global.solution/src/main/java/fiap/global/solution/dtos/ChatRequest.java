package fiap.global.solution.dtos;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequest {

    public String model;
    public ArrayList<Message> messages;

    public ChatRequest(String model, String prompt, MultipartFile image) throws IOException {
        this.model = model;
        this.messages = new ArrayList<>();
        var list = new ArrayList<>();
        list.add(new ContentTypeText(prompt));
        list.add(new ContentTypeImg(image));
        this.messages.add(new Message("user", list));
    }
}