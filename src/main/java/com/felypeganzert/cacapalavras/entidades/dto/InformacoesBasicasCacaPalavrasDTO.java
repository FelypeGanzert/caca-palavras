package com.felypeganzert.cacapalavras.entidades.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InformacoesBasicasCacaPalavrasDTO {

    private Integer id;
    private LocalDateTime dataCriacao;
    private String criador;
    private String titulo;

}
