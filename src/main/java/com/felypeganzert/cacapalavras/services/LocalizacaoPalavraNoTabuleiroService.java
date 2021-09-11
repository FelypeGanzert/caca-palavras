package com.felypeganzert.cacapalavras.services;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface LocalizacaoPalavraNoTabuleiroService {
    void deleteAllAssociadasAoTabuleiro(Integer idTabuleiro);

    void deleteAllFromPalavra(Integer idPalavra);

    void deleteAllUsandoLetra(Integer idLetra);

    void deleteAllUsandoLetras(List<Integer> idLetras);
}
