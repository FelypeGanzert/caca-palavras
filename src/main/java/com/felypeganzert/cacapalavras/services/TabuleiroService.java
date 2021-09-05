package com.felypeganzert.cacapalavras.services;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.rest.dto.TabuleiroPostDTO;

import org.springframework.stereotype.Service;

@Service
public interface TabuleiroService {

    Tabuleiro criarComBasico(TabuleiroPostDTO dto, Integer idCacaPalavras);
    
    Tabuleiro findById(Integer id, Integer idCacaPalavras);

    void delete(Integer id, Integer idCacaPalavras);

    // TODO: mover para o service de letras
    List<Letra> adicionarLetras(Integer idTabuleiro, List<Letra> letras);
    void inserirLetra(Tabuleiro tabuleiro,  Letra letra);
    
}
