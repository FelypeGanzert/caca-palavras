package com.felypeganzert.cacapalavras.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LocalizacaoLetraNoTabuleiroDTO {

    private Integer id;
    private int ordem;
    private int letraId;
    
}
