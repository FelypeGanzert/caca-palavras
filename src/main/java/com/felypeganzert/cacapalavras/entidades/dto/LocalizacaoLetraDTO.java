package com.felypeganzert.cacapalavras.entidades.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LocalizacaoLetraDTO {

    private Integer id;
    private int ordem;
    private int letraId;
    
}
