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
public class LocalizacaoPalavraNoTabuleiroDTO {

    private Integer id;
    private List<LocalizacaoLetraNoTabuleiroDTO> localizacoesLetrasNoTabuleiro;
    
}
