package fiap.global.solution.controllers;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fiap.global.solution.dtos.ChatRequest;
import fiap.global.solution.dtos.ChatResponse;
import fiap.global.solution.dtos.ObterRegistro;
import fiap.global.solution.dtos.ObterTodosRegistrosDto;
import fiap.global.solution.entities.RegistroMapa;
import fiap.global.solution.repositories.RegistroMapaRepository;
import fiap.global.solution.services.LocalFileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private LocalFileService localFileService;

    @Autowired
    private RegistroMapaRepository repo;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String openaiApiKey;

    private static String prompt = "Crie um descrição resumida para a imagem e atribua obrigatoriamente o lixo presente ou não na imagem em uma das categorias: ('Metal', 'Papel', 'Orgânico', 'Plástico', 'Detritos marítimos'). A resposta deve vir no formato 'descricao;tipo'";

    @PostMapping
    public RegistroMapa create(@RequestParam("imagem") MultipartFile imagem, @RequestParam("altitude") String altitude,
            @RequestParam("longitude") String longitude) throws IOException {
        String newFileName = localFileService.saveFile(imagem);
        RegistroMapa registro = new RegistroMapa(altitude, longitude, newFileName);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + openaiApiKey);
            return execution.execute(request, body);
        });
        ChatRequest request = new ChatRequest(model, prompt, imagem);
        System.out.println(request);
        try {
            JsonNode response = restTemplate.postForObject(apiUrl, request, JsonNode.class);

            if (response != null && response.has("choices") && response.get("choices").isArray()) {
                JsonNode firstChoice = response.get("choices").get(0);
                if (firstChoice.has("message") && firstChoice.get("message").has("content")) {
                    String content = firstChoice.get("message").get("content").asText();

                    String[] data = content.split(";");

                    if (data.length >= 2) {
                        String descricao = data[0];
                        String tipo = data[1];
                        registro.descricao = descricao;
                        registro.tipoLixo = tipo;
                        repo.save(registro);
                        return registro;
                    } else {
                        System.out.println("Dados insuficientes na resposta.");
                    }
                } else {
                    System.out.println("Campo 'content' não encontrado na resposta.");
                }
            } else {
                System.out.println("Campo 'choices' não encontrado na resposta.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao processar a resposta da API.");
        }

        return null;
    }

    @GetMapping
    public List<ObterTodosRegistrosDto> getAll() {
        return repo.findAll()
                .stream()
                .map(r -> new ObterTodosRegistrosDto(r))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ObterRegistro getById(@PathVariable(value = "id") int id) throws IOException {
        var registro = repo.findById(id).get();
        // # obter registro de /files/<registro.nomeImagem>
        Path filePath = Paths.get("files", registro.getNomeImagem());
        var tempImagem = Files.readAllBytes(filePath);
        String imagem = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(tempImagem);
        return new ObterRegistro(registro, imagem);
    }
}
