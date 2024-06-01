package fiap.global.solution.dtos;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentTypeImg {
    public ContentTypeImg(MultipartFile image) throws IOException {
        this.image_url = new ImageUrlDto(image.getBytes());
    }
    public String type = "image_url";
    public ImageUrlDto image_url;
}
