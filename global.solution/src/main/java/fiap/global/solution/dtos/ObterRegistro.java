package fiap.global.solution.dtos;

import java.time.LocalDateTime;

import fiap.global.solution.entities.RegistroMapa;

public class ObterRegistro {
    public int id;
    public String altitude;
    public String longitude;
    public LocalDateTime createdAt;
    public String imagem;
    public String tipoLixo;
    public String descricao;

    public ObterRegistro(RegistroMapa registro, String imagem){
        this.id = registro.id;
        this.altitude = registro.altitude;
        this.longitude = registro.longitude;
        this.createdAt = registro.createdAt;
        this.tipoLixo = registro.tipoLixo;
        this.descricao = registro.descricao;
        this.imagem = imagem;
    }
}
