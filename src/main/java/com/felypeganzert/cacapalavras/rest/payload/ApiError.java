package com.felypeganzert.cacapalavras.rest.payload;

import lombok.Data;

@Data
public class ApiError {
    
    private String erro;

    public ApiError(String erro){
        this.erro = erro;
    }

}
