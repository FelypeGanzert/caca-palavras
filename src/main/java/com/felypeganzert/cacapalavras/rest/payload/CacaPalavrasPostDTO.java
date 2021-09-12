package com.felypeganzert.cacapalavras.rest.payload;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CacaPalavrasPostDTO {

    @NotBlank(message = "Criador não pode ser vazio")
    private String criador;
    
    @NotBlank(message = "Título não pode ser vazio")
    private String titulo;

}
