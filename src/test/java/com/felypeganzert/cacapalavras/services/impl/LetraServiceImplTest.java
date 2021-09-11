package com.felypeganzert.cacapalavras.services.impl;

import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMaper;
import com.felypeganzert.cacapalavras.repository.LetraRepository;
import com.felypeganzert.cacapalavras.services.LocalizacaoPalavraService;
import com.felypeganzert.cacapalavras.services.TabuleiroService;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class LetraServiceImplTest {

    @InjectMocks
    private LetraServiceImpl service;

    @Mock
    private LetraRepository repository;

    @Mock
    private TabuleiroService serviceTabuleiro;

    @Mock
    private LocalizacaoPalavraService serviceLocalizacaoPalavra;

    // TODO: implementar os testes listados abaixo:
    
    // # adicionarLetra
    // deveChamarSaveComSucesso
    // deveGerarRegraNegocioExceptionAoTentarAdicionarEmUmaPosicaoInexistente
    // deveChamarDeleteAoAdicionarEmPosicaoQueHaviaLetra
    // deveChamarDeleteAllUsandoLetraComALetraAntigaAoAdicionarEmPosicaoQueHaviaLetra
    // naoDeveChamarDeleteAoAdicionarEmPosicaoSemLetra
     
    // # adicionarLetras
    // deveChamarSaveAllComSucesso
    // deveGerarNegocioExceptionAoTentarAdicionarLetrasEmPosicoesInexistentes
    // deveChamarDeleteAllUsandoLetrasPassandoAsLetrasExistentesNasPosicoesAdicionadas
    // deveChamarDeleteAllPassandoAsLetrasExistentesNasPosicoesAdicionadas

    // # findAll
    // deveChamarFindByIdDoServiceTabuleiroERetornarTodasAsLetrasDoTabuleiro
    
    // # findById
    // deveChamarFindByIdDoRepositoryComSucesso
    // deveGerarRecursoNaoPertenceAExceptionAoBuscarLetraNaoPertencenteAoTabuleiro
    
    // # atualizar
    // deveChamarSaveDoRepositoryAoAtualizarComSucesso
    // deveChamarDeleteAllUsandoLetraPassandoALetraAdicionado
    // deveGerarRegraNegocioExceptionAoTentarAtualizarAPosicaoDaLetra

    // # delete
    // deveChamarDeleteDoRepositoryComSucesso
    // deveChamarDeleteAllUsandoLetraPassandoALetraQueSeraDeletada
    
    // # deleteAll
    // deveChamarDeleteAllFromTabuleiroDoRepositoryComSucesso
    // deveChamarDeleteAllAssociadasAoTabuleiroComSucesso

}
