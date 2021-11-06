package com.felypeganzert.cacapalavras.entidades.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PalavraDTO {

    private Integer id;
    private String palavra;
    private Set<LocalizacaoPalavraDTO> localizacoes;
   
}
