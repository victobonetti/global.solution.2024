package fiap.global.solution.dtos;

import java.util.Base64;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageUrlDto {
    public String url;
    public ImageUrlDto(byte[] img){
        String encodedString = Base64.getEncoder().encodeToString(img);
        this.url = "data:image/jpeg;base64," + encodedString;
    }
}
