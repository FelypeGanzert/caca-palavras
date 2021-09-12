package com.felypeganzert.cacapalavras.mapper;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.dto.CacaPalavrasDTO;
import com.felypeganzert.cacapalavras.entidades.dto.TabuleiroDTO;
import com.felypeganzert.cacapalavras.rest.payload.CacaPalavrasPostDTO;
import com.felypeganzert.cacapalavras.rest.payload.LetraPostDTO;
import com.felypeganzert.cacapalavras.rest.payload.TabuleiroPostDTO;

import org.springframework.stereotype.Component;

@Component
public interface CacaPalavrasPayloadMaper {
    CacaPalavrasDTO toCacaPalavrasDTO (CacaPalavrasPostDTO postDTO);

    TabuleiroDTO toTabuleiroDTO (TabuleiroPostDTO postDTO);
    
    Letra toLetra(LetraPostDTO letraPostDTO);

    List<Letra> toLetras(List<LetraPostDTO> letrasPostDTO);
}
