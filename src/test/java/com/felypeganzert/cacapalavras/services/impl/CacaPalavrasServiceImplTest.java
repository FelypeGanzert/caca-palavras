package com.felypeganzert.cacapalavras.services.impl;

import static com.felypeganzert.cacapalavras.utils.AppConstantes.CACA_PALAVRAS;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.ID;

import java.util.Optional;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.exception.RecursoNaoEncontradoException;
import com.felypeganzert.cacapalavras.repository.CacaPalavrasRepository;
import com.felypeganzert.cacapalavras.repository.TabuleiroRepository;
import com.felypeganzert.cacapalavras.rest.dto.CacaPalavrasPostDTO;
import com.felypeganzert.cacapalavras.services.CacaPalavrasResolver;
import com.felypeganzert.cacapalavras.util.CacaPalavrasCreator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CacaPalavrasServiceImplTest {

    @InjectMocks
    private CacaPalavrasServiceImpl service;

    @InjectMocks
    private TabuleiroServiceImpl tabuleiroService;

    @Mock
    private CacaPalavrasResolver resolver;

    @Mock
    private CacaPalavrasRepository repository;

    @Mock
    private TabuleiroRepository tabuleiroRepository;

    private static final int ID_CACA_PALAVRAS = 1;

    @Test
    void deveChamarSaveDoRepositoryComSucessoAoCriarComBasico() {
        CacaPalavrasPostDTO dto = criarCacaPalavrasPostDTOValido();

        service.criarComBasico(dto);

        Mockito.verify(repository).save(Mockito.any(CacaPalavras.class));
    }

    @Test
    void deveChamarFindAllComInformacoesBasicasDoRepositoryComSucesso() {
        service.findAllComInformacoesBasicas();

        Mockito.verify(repository).findAllComInformacoesBasicas();
    }

    @Test
    void deveChamarFindByIdDoRepositoryComSucesso() {
        CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasNaoLocalizadasESemLetrasNoTabuleiro();
        deveRetornarIssoQuandoRepositoryFindByIdForChamado(cacaPalavras);

        service.findById(ID_CACA_PALAVRAS);

        Mockito.verify(repository).findById(ID_CACA_PALAVRAS);
    }

    @Test
    void deveGerarExceptionRecursoNaoEncontradoAoBuscarIdNaoExistente() {
        BDDMockito.when(repository.findById(ArgumentMatchers.any(Integer.class))).thenReturn(Optional.empty());

        RecursoNaoEncontradoException exception = new RecursoNaoEncontradoException(CACA_PALAVRAS, ID,
                ID_CACA_PALAVRAS);

        Assertions.assertThatExceptionOfType(RecursoNaoEncontradoException.class)
                .isThrownBy(() -> service.findById(ID_CACA_PALAVRAS)).withMessageContaining(exception.getMessage());
    }

    @Test
    void deveChamarDeleteDoRepositoryComSucesso() {
        CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasNaoLocalizadasESemLetrasNoTabuleiro();
        deveRetornarIssoQuandoRepositoryFindByIdForChamado(cacaPalavras);

        service.delete(ID_CACA_PALAVRAS);

        Mockito.verify(repository).delete(cacaPalavras);
    }

    // TODO: mover para o LetraServiceImpltTest
    // @Test
    // void deveLimparTodasAsLetrasDoTabuleiroComSucesso() {
    //     CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasLocalizadas(tabuleiroService);
    //     assertThat(cacaPalavras.getTabuleiro().getLetras()).isNotEmpty();
    //     deveRetornarIssoQuandoRepositoryFindByIdForChamado(cacaPalavras);

    //     service.limparLetrasDoTabuleiro(ID_CACA_PALAVRAS);

    //     assertThat(cacaPalavras.getTabuleiro().getLetras()).isEmpty();
    // }

    // @Test
    // void deveLimparTodasAsLetrasDoTabuleiroEAsLocalizacoesDasPalavrasComSucesso() {
    //     CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasLocalizadas(tabuleiroService);
    //     assertThat(cacaPalavras.getTabuleiro().getLetras()).isNotEmpty();
    //     assertThat(cacaPalavras.getPalavras()).isNotEmpty();
    //     for (Palavra palavra : cacaPalavras.getPalavras()) {
    //         assertThat(palavra.getLocalizacoesNoTabuleiro()).isNotEmpty();
    //     }
    //     deveRetornarIssoQuandoRepositoryFindByIdForChamado(cacaPalavras);

    //     service.limparLetrasDoTabuleiro(ID_CACA_PALAVRAS);

    //     assertThat(cacaPalavras.getTabuleiro().getLetras()).isEmpty();
    //     assertThat(cacaPalavras.getPalavras()).isNotEmpty();
    //     for (Palavra palavra : cacaPalavras.getPalavras()) {
    //         assertThat(palavra.getLocalizacoesNoTabuleiro()).isEmpty();
    //     }
    // }

    // @Test
    // void naoDeveGerarExceptionAoLimparAsLetrasDoTabuleiroQueNaoContemLetras() {
    //     CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasNaoLocalizadasESemLetrasNoTabuleiro();
    //     assertThat(cacaPalavras.getTabuleiro().getLetras()).isEmpty();
    //     deveRetornarIssoQuandoRepositoryFindByIdForChamado(cacaPalavras);

    //     service.limparLetrasDoTabuleiro(ID_CACA_PALAVRAS);
    // }

    // @Test
    // void deveChamarSaveDoRepositoryComSucessoAposLimparLetras() {
    //     CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasLocalizadas(tabuleiroService);
    //     assertThat(cacaPalavras.getTabuleiro().getLetras()).isNotEmpty();
    //     deveRetornarIssoQuandoRepositoryFindByIdForChamado(cacaPalavras);

    //     service.limparLetrasDoTabuleiro(ID_CACA_PALAVRAS);

    //     assertThat(cacaPalavras.getTabuleiro().getLetras()).isEmpty();
    //     Mockito.verify(repository).save(Mockito.any(CacaPalavras.class));
    // }

    @Test
    void deveChamarEncontrarPalavrasNoTabuleiroDoResolverESaveDoRepositoryComSucesso() {
        CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasNaoLocalizadasESemLetrasNoTabuleiro();
        deveRetornarIssoQuandoRepositoryFindByIdForChamado(cacaPalavras);

        service.resolverCacaPalavras(ID_CACA_PALAVRAS);

        Mockito.verify(resolver).encontrarPalavrasNoTabuleiro(cacaPalavras);
        Mockito.verify(repository).save(Mockito.any(CacaPalavras.class));
    }

    private void deveRetornarIssoQuandoRepositoryFindByIdForChamado(CacaPalavras cacaPalavras) {
        BDDMockito.when(repository.findById(ArgumentMatchers.any(Integer.class))).thenReturn(Optional.of(cacaPalavras));
    }

    private CacaPalavrasPostDTO criarCacaPalavrasPostDTOValido() {
        return CacaPalavrasPostDTO.builder().criador("Criador").titulo("Título").build();
    }

}
