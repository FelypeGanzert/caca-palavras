package com.felypeganzert.cacapalavras.services;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.rest.dto.CacaPalavrasPostDTO;
import com.felypeganzert.cacapalavras.rest.dto.InformacoesBasicasCacaPalavrasDTO;

import org.springframework.stereotype.Service;

@Service
public interface CacaPalavrasService {

    CacaPalavras criarComBasico(CacaPalavrasPostDTO dto);

    List<InformacoesBasicasCacaPalavrasDTO> findAllComInformacoesBasicas();

    CacaPalavras findById(Integer id);

    void delete(Integer id);

    CacaPalavras resolverCacaPalavras(Integer id);

    // TODO: mover para o service de letras
    CacaPalavras limparLetrasDoTabuleiro(Integer id);

    // TODO: mover para o service de palavras
    List<Palavra> adicionarPalavras(CacaPalavras cacaPalavras, List<String> palavras);
}
