package com.felypeganzert.cacapalavras.services.impl;

import com.felypeganzert.cacapalavras.repository.LocalizacaoPalavraRepository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class LocalizacaoPalavraServiceImplTest {

    @InjectMocks
    private LocalizacaoPalavraServiceImpl service;

    @Mock
    private LocalizacaoPalavraRepository repository;

    // TODO: implementar os testes listados abaixo:
    
    // # deleteAllAssociadasAoTabuleiro
    // deveChamarDeleteAllFromTabuleiroIdDoRepositoryComSucesso
     
    // # deleteAllFromPalavra
    // deveChamarDeleteAllFromPalavraIdDoRepositoryComSucesso

    // # findAll
    // deveChamarFindByIdDoServiceTabuleiroERetornarTodasAsLetrasDoTabuleiro
    
    // # deleteAllUsandoLetra
    // deveChamarDeleteAllUsingLetraIdDoRepositoryComSucesso
    
    // # deleteAllUsandoLetras
    // implementar lógica do método

}
