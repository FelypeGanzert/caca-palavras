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
public class TabuleiroDTO {

    private Integer id;
    private int largura;
    private int altura;
    private List<LetraDTO> letras;
    
}
