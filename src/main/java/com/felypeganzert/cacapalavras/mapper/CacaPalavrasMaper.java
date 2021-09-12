package com.felypeganzert.cacapalavras.mapper;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.entidades.dto.CacaPalavrasDTO;
import com.felypeganzert.cacapalavras.entidades.dto.PalavraDTO;
import com.felypeganzert.cacapalavras.entidades.dto.TabuleiroDTO;
import com.felypeganzert.cacapalavras.rest.payload.LetraPostDTO;
import com.felypeganzert.cacapalavras.rest.payload.LetraPutDTO;

import org.springframework.stereotype.Component;

@Component
public interface CacaPalavrasMaper {

    CacaPalavrasDTO toCacaPalavrasDTO (CacaPalavras cacaPalavras);

    TabuleiroDTO toTabuleiroDTO(Tabuleiro tabuleiro);

    LetraPutDTO toLetraDTO(Letra letra);

    List<LetraPutDTO> toLetrasDTO(List<Letra> letras);

    PalavraDTO toPalavraDTO(Palavra palavra);

    List<PalavraDTO> toPalavrasDTO(List<Palavra> palavras);

    Letra toLetra(LetraPostDTO dto);

    List<Letra> toLetras(List<LetraPostDTO> dto);
    
}
