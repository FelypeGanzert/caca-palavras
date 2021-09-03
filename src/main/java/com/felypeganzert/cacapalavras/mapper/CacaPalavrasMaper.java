package com.felypeganzert.cacapalavras.mapper;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.rest.dto.CacaPalavrasDTO;
import com.felypeganzert.cacapalavras.rest.dto.LetraDTO;
import com.felypeganzert.cacapalavras.rest.dto.PalavraDTO;
import com.felypeganzert.cacapalavras.rest.dto.TabuleiroDTO;

import org.springframework.stereotype.Component;

@Component
public interface CacaPalavrasMaper {

    CacaPalavrasDTO toCacaPalavrasDTO (CacaPalavras cacaPalavras);

    TabuleiroDTO toTabuleiroDTO(Tabuleiro tabuleiro);

    List<LetraDTO> toLetrasDTO(List<Letra> letras);

    List<PalavraDTO> toPalavrasDTO(List<Palavra> palavras);
    
}
