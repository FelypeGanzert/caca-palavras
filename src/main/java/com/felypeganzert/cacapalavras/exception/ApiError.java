package com.felypeganzert.cacapalavras.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiError {
    
    private List<String> erros = new ArrayList<>();

    public ApiError(String erro){
        this.erros.add(erro);
    }

}
