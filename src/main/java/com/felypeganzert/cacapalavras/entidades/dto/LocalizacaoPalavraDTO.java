package com.felypeganzert.cacapalavras.entidades.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LocalizacaoPalavraDTO {

    private Integer id;
    private List<LocalizacaoLetraDTO> localizacoesLetras;
    
}
