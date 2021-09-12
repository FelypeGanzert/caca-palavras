package com.felypeganzert.cacapalavras.services.impl;

import java.util.List;

import com.felypeganzert.cacapalavras.repository.LocalizacaoPalavraRepository;
import com.felypeganzert.cacapalavras.services.LocalizacaoPalavraService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocalizacaoPalavraServiceImpl implements LocalizacaoPalavraService {

    private final LocalizacaoPalavraRepository repository;
    
    @Override
    public void deleteAllAssociadasAoTabuleiro(Integer idTabuleiro) {
        repository.deleteAllFromTabuleiroId(idTabuleiro);
    }

    @Override
    public void deleteAllFromPalavra(Integer idPalavra) {
        repository.deleteAllFromPalavraId(idPalavra);
    }

    @Override
    public void deleteAllUsandoLetra(Integer idLetra) {
        repository.deleteAllUsingLetraId(idLetra);
    }

    @Override
    public void deleteAllUsandoLetras(List<Integer> idsLetras) {
        repository.deleteAllUsingLetrasId(idsLetras);
    }

}
