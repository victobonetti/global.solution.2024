package fiap.global.solution.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "T_REGISTRO_MAPA")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Setter
public class RegistroMapa {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    public String altitude;
    public String longitude;
    public LocalDateTime createdAt;
    public String nomeImagem;
    public String tipoLixo;
    public String descricao;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public RegistroMapa(String altitude, String longitude, String nomeImagem) {
        this.altitude = altitude;
        this.longitude = longitude;
        this.nomeImagem = nomeImagem;
    }
}
