package com.felypeganzert.cacapalavras.services.impl;

import static com.felypeganzert.cacapalavras.utils.AppConstantes.CACA_PALAVRAS;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.ID;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Palavra;
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

        Integer id = 1;
        service.findById(id);

        Mockito.verify(repository).findById(id);
    }

    @Test
    void deveChamarDeleteDoRepositoryComSucesso() {
        CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasNaoLocalizadasESemLetrasNoTabuleiro();
        deveRetornarIssoQuandoRepositoryFindByIdForChamado(cacaPalavras);

        Integer id = 1;
        service.delete(id);

        Mockito.verify(repository).delete(cacaPalavras);
    }

    @Test
    void deveGerarExceptionRecursoNaoEncontradoAoBuscarIdNaoExistente() {
        BDDMockito.when(repository.findById(ArgumentMatchers.any(Integer.class))).thenReturn(Optional.empty());

        Integer id = 1;
        RecursoNaoEncontradoException exception = new RecursoNaoEncontradoException(CACA_PALAVRAS, ID, id);

        Assertions.assertThatExceptionOfType(RecursoNaoEncontradoException.class).isThrownBy(() -> service.findById(id))
                .withMessageContaining(exception.getMessage());
    }

    @Test
    void deveLimparTodasAsLetrasDoTabuleiroComSucesso() {
        CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasLocalizadas(tabuleiroService);
        assertThat(cacaPalavras.getTabuleiro().getLetras()).isNotEmpty();
        deveRetornarIssoQuandoRepositoryFindByIdForChamado(cacaPalavras);

        Integer id = 1;
        service.limparLetrasDoTabuleiro(id);

        assertThat(cacaPalavras.getTabuleiro().getLetras()).isEmpty();
    }

    @Test
    void deveLimparTodasAsLetrasDoTabuleiroEAsLocalizacoesDasPalavrasComSucesso() {
        CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasLocalizadas(tabuleiroService);
        assertThat(cacaPalavras.getTabuleiro().getLetras()).isNotEmpty();
        assertThat(cacaPalavras.getPalavras()).isNotEmpty();
        for (Palavra palavra : cacaPalavras.getPalavras()) {
            assertThat(palavra.getLocalizacoesNoTabuleiro()).isNotEmpty();
        }
        deveRetornarIssoQuandoRepositoryFindByIdForChamado(cacaPalavras);

        Integer id = 1;
        service.limparLetrasDoTabuleiro(id);

        assertThat(cacaPalavras.getTabuleiro().getLetras()).isEmpty();
        assertThat(cacaPalavras.getPalavras()).isNotEmpty();
        for (Palavra palavra : cacaPalavras.getPalavras()) {
            assertThat(palavra.getLocalizacoesNoTabuleiro()).isEmpty();
        }
    }

    @Test
    void naoDeveGerarExceptionAoLimparAsLetrasDoTabuleiroQueNaoContemLetras() {
        CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasNaoLocalizadasESemLetrasNoTabuleiro();
        assertThat(cacaPalavras.getTabuleiro().getLetras()).isEmpty();
        deveRetornarIssoQuandoRepositoryFindByIdForChamado(cacaPalavras);

        Integer id = 1;
        service.limparLetrasDoTabuleiro(id);
    }

    @Test
    void deveChamarSaveDoRepositoryComSucessoAposLimparLetras() {
        CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasLocalizadas(tabuleiroService);
        assertThat(cacaPalavras.getTabuleiro().getLetras()).isNotEmpty();
        deveRetornarIssoQuandoRepositoryFindByIdForChamado(cacaPalavras);

        Integer id = 1;
        service.limparLetrasDoTabuleiro(id);

        assertThat(cacaPalavras.getTabuleiro().getLetras()).isEmpty();
        Mockito.verify(repository).save(Mockito.any(CacaPalavras.class));
    }

    @Test
    void deveChamarEncontrarPalavrasNoTabuleiroDoResolverESaveDoRepositoryComSucesso() {
        CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasNaoLocalizadasESemLetrasNoTabuleiro();
        deveRetornarIssoQuandoRepositoryFindByIdForChamado(cacaPalavras);

        Integer id = 1;
        service.encontrarPalavrasNoTabuleiro(id);

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
