package com.felypeganzert.cacapalavras.rest.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InformacoesBasicasCacaPalavrasDTO {

    private Long id;
    private LocalDate dataCriacao;
    private String criador;
    private String titulo;

}
