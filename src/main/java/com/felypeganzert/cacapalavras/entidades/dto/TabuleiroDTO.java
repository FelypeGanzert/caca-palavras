package com.felypeganzert.cacapalavras.entidades.dto;

import java.util.List;

import com.felypeganzert.cacapalavras.rest.payload.LetraPutDTO;

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
    private List<LetraPutDTO> letras;
    
}
