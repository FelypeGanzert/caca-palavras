package com.felypeganzert.cacapalavras.rest.dto;

import java.util.List;

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
    private List<LocalizacaoPalavraDTO> localizacoes;
   
}
