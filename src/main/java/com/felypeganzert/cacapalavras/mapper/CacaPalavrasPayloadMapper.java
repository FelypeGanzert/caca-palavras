package com.felypeganzert.cacapalavras.mapper;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.dto.CacaPalavrasDTO;
import com.felypeganzert.cacapalavras.entidades.dto.LetraDTO;
import com.felypeganzert.cacapalavras.entidades.dto.TabuleiroDTO;
import com.felypeganzert.cacapalavras.rest.payload.CacaPalavrasPostDTO;
import com.felypeganzert.cacapalavras.rest.payload.LetraPostDTO;
import com.felypeganzert.cacapalavras.rest.payload.TabuleiroPostDTO;

import org.springframework.stereotype.Component;

@Component
public interface CacaPalavrasPayloadMapper {
    CacaPalavrasDTO toCacaPalavrasDTO (CacaPalavrasPostDTO postDTO);

    TabuleiroDTO toTabuleiroDTO(TabuleiroPostDTO postDTO);
    
    LetraDTO toLetraDTO(LetraPostDTO letraPostDTO);

    List<LetraDTO> toLetrasDTO(List<LetraPostDTO> letrasPostDTO);
}
