package com.felypeganzert.cacapalavras.mapper;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.rest.dto.CacaPalavrasDTO;
import com.felypeganzert.cacapalavras.rest.dto.LetraDTO;
import com.felypeganzert.cacapalavras.rest.dto.LetraPostDTO;
import com.felypeganzert.cacapalavras.rest.dto.PalavraDTO;
import com.felypeganzert.cacapalavras.rest.dto.TabuleiroDTO;

import org.springframework.stereotype.Component;

@Component
public interface CacaPalavrasMaper {

    CacaPalavrasDTO toCacaPalavrasDTO (CacaPalavras cacaPalavras);

    TabuleiroDTO toTabuleiroDTO(Tabuleiro tabuleiro);

    LetraDTO toLetraDTO(Letra letra);

    List<LetraDTO> toLetrasDTO(List<Letra> letras);

    PalavraDTO toPalavraDTO(Palavra palavra);

    List<PalavraDTO> toPalavrasDTO(List<Palavra> palavras);

    Letra toLetra(LetraPostDTO dto);

    List<Letra> toLetras(List<LetraPostDTO> dto);
    
}
