package fiap.global.solution.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class LocalFileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String saveFile(MultipartFile file) throws IOException {
        // Resolve o caminho absoluto do diretório de upload
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        
        // Cria o diretório se ele não existir
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileExtension = "";
        String originalFilename = file.getOriginalFilename();

        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String newFileName = UUID.randomUUID().toString() + fileExtension;

        // Resolve o caminho completo do arquivo
        Path filePath = uploadPath.resolve(newFileName);

        // Salva o arquivo no diretório
        Files.copy(file.getInputStream(), filePath);

        return(newFileName);
    }
}
