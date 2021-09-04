package com.felypeganzert.cacapalavras.services;

import java.util.List;

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

    CacaPalavras findById(Integer id);

    void delete(Integer id);

    CacaPalavras limparLetrasDoTabuleiro(Integer id);

    CacaPalavras encontrarPalavrasNoTabuleiro(Integer id);

    // TODO: mover os dois métodos abaixo para os services adequados
    Tabuleiro criarTabuleiroComBasico(CacaPalavras cacaPalavras, TabuleiroPostDTO dto);

    List<Palavra> adicionarPalavras(CacaPalavras cacaPalavras, List<String> palavras);
}
