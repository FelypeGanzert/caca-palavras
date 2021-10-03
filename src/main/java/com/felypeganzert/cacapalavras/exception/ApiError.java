package com.felypeganzert.cacapalavras.exception;

import lombok.Data;

@Data
public class ApiError {
    
    private String erro;

    public ApiError(String erro){
        this.erro = erro;
    }

}
