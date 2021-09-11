package com.felypeganzert.cacapalavras.services.impl;

import java.util.List;

import com.felypeganzert.cacapalavras.services.LocalizacaoPalavraService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocalizacaoPalavraServiceImpl implements LocalizacaoPalavraService {
    
    @Override
    public void deleteAllAssociadasAoTabuleiro(Integer idTabuleiro) {
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteAllFromPalavra(Integer idPalavra) {
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteAllUsandoLetra(Integer idLetra) {
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteAllUsandoLetras(List<Integer> idLetras) {
        // TODO Auto-generated method stub
    }


}
