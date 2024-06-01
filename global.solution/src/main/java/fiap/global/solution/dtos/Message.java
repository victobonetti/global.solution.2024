package fiap.global.solution.dtos;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

    public Message(String role, ArrayList<Object> content) {
        this.role = role;
        this.content = content;
    }
    public String role;
    public ArrayList<Object> content;

}