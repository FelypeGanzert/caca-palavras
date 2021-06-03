package com.felypeganzert.cacapalavras.mapper;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.rest.dto.CacaPalavrasDTO;

import org.springframework.stereotype.Component;

@Component
public interface CacaPalavrasMaper {

    public CacaPalavrasDTO toCacaPalavrasDTO (CacaPalavras cacaPalavras);
    
}
