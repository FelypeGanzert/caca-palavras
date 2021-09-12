package com.felypeganzert.cacapalavras.mapper;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.entidades.dto.CacaPalavrasDTO;
import com.felypeganzert.cacapalavras.entidades.dto.LetraDTO;
import com.felypeganzert.cacapalavras.entidades.dto.PalavraDTO;
import com.felypeganzert.cacapalavras.entidades.dto.TabuleiroDTO;

import org.springframework.stereotype.Component;

@Component
public interface CacaPalavrasMapper {
    CacaPalavrasDTO toCacaPalavrasDTO (CacaPalavras cacaPalavras);

    TabuleiroDTO toTabuleiroDTO(Tabuleiro tabuleiro);

    LetraDTO toLetraDTO(Letra letra);

    List<LetraDTO> toLetrasDTO(List<Letra> letras);

    PalavraDTO toPalavraDTO(Palavra palavra);

    List<PalavraDTO> toPalavrasDTO(List<Palavra> palavras);
}
