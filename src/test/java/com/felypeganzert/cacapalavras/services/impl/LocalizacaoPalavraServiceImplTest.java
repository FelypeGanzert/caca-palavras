package com.felypeganzert.cacapalavras.services.impl;

import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import com.felypeganzert.cacapalavras.repository.LocalizacaoPalavraRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class LocalizacaoPalavraServiceImplTest {

    @InjectMocks
    private LocalizacaoPalavraServiceImpl service;

    @Mock
    private LocalizacaoPalavraRepository repository;
    
    @Test
    void deveChamarDeleteAllFromTabuleiroIdDoRepositoryComSucesso(){
        int idTabuleiro = 1;
        service.deleteAllAssociadasAoTabuleiro(idTabuleiro);

        Mockito.verify(repository, times(1)).deleteAllFromTabuleiroId(idTabuleiro);
    }
     
    @Test
    void deveChamarDeleteAllFromPalavraIdDoRepositoryComSucesso(){
        int idPalavra = 1;
        service.deleteAllFromPalavra(idPalavra);

        Mockito.verify(repository, times(1)).deleteAllFromPalavraId(idPalavra);
    }
    
    @Test
    void deveChamarDeleteAllUsingLetraIdDoRepositoryComSucesso(){
        int idLetra = 1;
        service.deleteAllUsandoLetra(idLetra);

        Mockito.verify(repository, times(1)).deleteAllUsingLetraId(idLetra);
    }
    
    @Test
    void deveChamarDeleteAllUsingLetrasIdDoRepositoryComSucesso(){
        List<Integer> idLetrasParaExcluir = new ArrayList<>();
        idLetrasParaExcluir.add(1);
        idLetrasParaExcluir.add(2);
        service.deleteAllUsandoLetras(idLetrasParaExcluir);

        Mockito.verify(repository, times(1)).deleteAllUsingLetrasId(idLetrasParaExcluir);
    }

}
