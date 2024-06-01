package fiap.global.solution.dtos;

import fiap.global.solution.entities.RegistroMapa;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObterTodosRegistrosDto {
    public int id;
    public String altitude;
    public String longitude;
    public String tipoLixo;

    public ObterTodosRegistrosDto(RegistroMapa r){
        this.id = r.id;
        this.altitude = r.altitude;
        this.longitude = r.longitude;
        this.tipoLixo = r.tipoLixo;
    };

}
