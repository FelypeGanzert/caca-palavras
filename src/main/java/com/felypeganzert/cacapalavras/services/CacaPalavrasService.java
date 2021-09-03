package com.felypeganzert.cacapalavras.services;

import java.util.List;
import java.util.Optional;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.rest.dto.CacaPalavrasPostDTO;
import com.felypeganzert.cacapalavras.rest.dto.InformacoesBasicasCacaPalavrasDTO;
import com.felypeganzert.cacapalavras.rest.dto.TabuleiroPostDTO;

import org.springframework.stereotype.Service;

@Service
public interface CacaPalavrasService {

    CacaPalavras criarComBasico(CacaPalavrasPostDTO dto);
    List<InformacoesBasicasCacaPalavrasDTO> findAllComInformacoesBasicas();
    Optional<CacaPalavras> findById(Integer id);
    
    Tabuleiro criarTabuleiroComBasico(CacaPalavras cacaPalavras, TabuleiroPostDTO dto);

    List<Palavra> adicionarPalavras(CacaPalavras cacaPalavras, List<String> palavras);

    void encontrarPalavrasNoTabuleiro(CacaPalavras cacaPalavras);
    void limparLetrasDoTabuleiro(CacaPalavras cacaPalavras);
    
}
