package com.felypeganzert.cacapalavras.rest.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CacaPalavrasDTO {

    private Integer id;
    private LocalDateTime dataCriacao;
    private String criador;
    private String titulo;
    private TabuleiroDTO tabuleiro;
    private List<PalavraDTO> palavras;
    
}
