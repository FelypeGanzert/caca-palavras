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
public class PalavraRequestDTO {

    @NotBlank(message = "Palavra não pode ser vazia")
    private String palavra;   
    
}
