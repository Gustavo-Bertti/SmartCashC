package br.com.fiap.SmartCash.Assinatura;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "TB_ASSINATURA")
@Builder
@NoArgsConstructor
@AllArgsConstructor 

public class Assinatura {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID_ASSINATURA;

    @NotBlank @Column(unique =true) @Size(max = 15)
    private String TIPO;

    @Positive
    private BigDecimal VALOR;

}
