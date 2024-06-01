package fiap.global.solution.dtos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatResponse {

    private List<Choice> choices;

    public static class Choice {

        public int index;
        public Message message;

    }
} 
