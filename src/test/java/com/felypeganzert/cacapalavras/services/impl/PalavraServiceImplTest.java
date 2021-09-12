package com.felypeganzert.cacapalavras.services.impl;

import static com.felypeganzert.cacapalavras.utils.AppConstantes.CACA_PALAVRAS;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.ID;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.PALAVRA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.LocalizacaoPalavra;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.exception.PalavraJaExisteNoCacaPalavrasException;
import com.felypeganzert.cacapalavras.exception.RecursoNaoEncontradoException;
import com.felypeganzert.cacapalavras.exception.RecursoNaoPertenceAException;
import com.felypeganzert.cacapalavras.exception.RegraNegocioException;
import com.felypeganzert.cacapalavras.repository.PalavraRepository;
import com.felypeganzert.cacapalavras.services.CacaPalavrasService;
import com.felypeganzert.cacapalavras.services.LocalizacaoPalavraService;
import com.felypeganzert.cacapalavras.util.CacaPalavrasCreator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class PalavraServiceImplTest {

    @InjectMocks
    private PalavraServiceImpl service;

    @Mock
    private PalavraRepository repository;

    @Mock
    private CacaPalavrasService serviceCacaPalavras;

    @Mock
    private LocalizacaoPalavraService serviceLocalizacaoPalavra;

    private static final int ID_PALAVRA = 1;
    private static final int ID_CACA_PALAVRAS = 1;

    @BeforeEach
    void setUp() {
        BDDMockito.when(serviceCacaPalavras.findById(anyInt())).thenReturn(cacaPalavrasValido());
        BDDMockito.when(repository.findById(anyInt())).thenReturn(optionalPalavraValido());
    }

    @Test
    void deveChamarSaveDoRepositoryComSucessoAoAdicionarPalavraNova() {
        CacaPalavras cacaPalavras = cacaPalavrasValido();
        Palavra p1 = criarPalavra(1, "Solzinho", cacaPalavras);
        Palavra p2 = criarPalavra(2, "Céuzinho", cacaPalavras);
        cacaPalavras.getPalavras().addAll(Arrays.asList(p1, p2));
        BDDMockito.when(serviceCacaPalavras.findById(anyInt())).thenReturn(cacaPalavras);

        String palavraNova = "Sol";
        service.adicionarPalavra(palavraNova, ID_CACA_PALAVRAS);

        Mockito.verify(repository, times(1)).save(ArgumentMatchers.any(Palavra.class));
    }

    @Test
    void deveAdicionarPalavraESalvarElaRemovendoEspacosComTrim() {
        String palavra = " Sol  ";

        service.adicionarPalavra(palavra, ID_CACA_PALAVRAS);
        final ArgumentCaptor<Palavra> captor = ArgumentCaptor.forClass(Palavra.class);
        Mockito.verify(repository).save(captor.capture());
        final Palavra palavraEnviadaParaSalvar = captor.getValue();

        assertThat(palavraEnviadaParaSalvar.getPalavra()).isNotBlank().isEqualTo(palavra.trim());
    }

    @Test
    void deveGerarExceptionPalavraJaExisteNoCacaPalavrasAoTentarAdicionarPalavraJaExistente() {
        CacaPalavras cacaPalavras = cacaPalavrasValido();
        Palavra p1 = criarPalavra(1, "solzinho", cacaPalavras);
        Palavra p2 = criarPalavra(2, "céuzinho", cacaPalavras);
        cacaPalavras.getPalavras().addAll(Arrays.asList(p1, p2));
        BDDMockito.when(serviceCacaPalavras.findById(anyInt())).thenReturn(cacaPalavras);

        String palavraExistente = p1.getPalavra().toUpperCase();
        PalavraJaExisteNoCacaPalavrasException exception = new PalavraJaExisteNoCacaPalavrasException(palavraExistente);

        Assertions.assertThatExceptionOfType(PalavraJaExisteNoCacaPalavrasException.class)
                .isThrownBy(() -> service.adicionarPalavra(palavraExistente, ID_CACA_PALAVRAS))
                .withMessage(exception.getMessage());

        Mockito.verify(repository, never()).save(ArgumentMatchers.any(Palavra.class));
    }

    @Test
    void deveGerarExceptionRegraNegocioAoTentarAdicionarPalavraComEspaco() {
        String palavra = " Sol e Ceuzinho ";

        Assertions.assertThatExceptionOfType(RegraNegocioException.class)
                .isThrownBy(() -> service.adicionarPalavra(palavra, ID_CACA_PALAVRAS))
                .withMessage("Não é possível adicionar palavras com espaço");

        Mockito.verify(repository, never()).save(ArgumentMatchers.any(Palavra.class));
    }

    @Test
    void deveRetornarTodasAsPalavrasDoCacaPalavrasComSucesso() {
        CacaPalavras cacaPalavras = cacaPalavrasValido();
        Palavra p1 = criarPalavra(1, "solzinho", cacaPalavras);
        Palavra p2 = criarPalavra(2, "céuzinho", cacaPalavras);
        cacaPalavras.getPalavras().addAll(Arrays.asList(p1, p2));
        BDDMockito.when(serviceCacaPalavras.findById(anyInt())).thenReturn(cacaPalavras);

        List<Palavra> palavras = service.findAll(ID_CACA_PALAVRAS);

        assertThat(palavras).isNotEmpty().hasSize(cacaPalavras.getPalavras().size());
    }

    @Test
    void deveChamarFindByIdDoRepositoryComSucesso() {
        service.findById(ID_CACA_PALAVRAS, ID_CACA_PALAVRAS);

        Mockito.verify(repository).findById(ID_CACA_PALAVRAS);
    }

    @Test
    void deveGerarExceptionRecursoNaoEncontradoAoBuscarIdNaoExistente() {
        BDDMockito.when(repository.findById(ArgumentMatchers.any(Integer.class))).thenReturn(Optional.empty());

        RecursoNaoEncontradoException exception = new RecursoNaoEncontradoException(PALAVRA, ID, ID_PALAVRA);

        Assertions.assertThatExceptionOfType(RecursoNaoEncontradoException.class)
                .isThrownBy(() -> service.findById(ID_PALAVRA, ID_CACA_PALAVRAS)).withMessage(exception.getMessage());
    }

    @Test
    void deveGerarExceptionRecursoNaoPertenceAAoBuscaPorPalavraNaoPertencenteAoCacaPalavras() {
        CacaPalavras cacaPalavras2 = new CacaPalavras();
        cacaPalavras2.setId(ID_CACA_PALAVRAS + 1);
        BDDMockito.when(serviceCacaPalavras.findById(ArgumentMatchers.any(Integer.class))).thenReturn(cacaPalavras2);

        RecursoNaoPertenceAException exception = new RecursoNaoPertenceAException(PALAVRA, CACA_PALAVRAS);

        Assertions.assertThatExceptionOfType(RecursoNaoPertenceAException.class)
                .isThrownBy(() -> service.findById(ID_PALAVRA, ID_CACA_PALAVRAS)).withMessage(exception.getMessage());
    }

    @Test
    void deveChamarSaveDoRepositoryComSucessoAoAtualizarUmaPalavraQuandoNaoExisteOutraIdentica() {
        CacaPalavras cacaPalavras = cacaPalavrasValido();
        Palavra palavraParaAtualizar = criarPalavra(1, "Solzinho", cacaPalavras);
        Palavra p1 = criarPalavra(2, "Céuzinho", cacaPalavras);
        cacaPalavras.getPalavras().addAll(Arrays.asList(palavraParaAtualizar, p1));

        BDDMockito.when(serviceCacaPalavras.findById(anyInt())).thenReturn(cacaPalavras);
        BDDMockito.when(repository.findById(anyInt())).thenReturn(Optional.of(palavraParaAtualizar));

        String palavraNova = "Amorzinho";
        service.atualizar(palavraNova, palavraParaAtualizar.getId(), ID_CACA_PALAVRAS);

        Mockito.verify(repository, times(1)).save(ArgumentMatchers.any(Palavra.class));
    }

    @Test
    void deveChamarSaveDoRepositoryComSucessoAoAtualizarUmaPalavraMantendoElaIdentica() {
        CacaPalavras cacaPalavras = cacaPalavrasValido();
        Palavra palavraParaAtualizar = criarPalavra(1, "Solzinho", cacaPalavras);
        Palavra p1 = criarPalavra(2, "Céuzinho", cacaPalavras);
        cacaPalavras.getPalavras().addAll(Arrays.asList(palavraParaAtualizar, p1));

        BDDMockito.when(serviceCacaPalavras.findById(anyInt())).thenReturn(cacaPalavras);
        BDDMockito.when(repository.findById(anyInt())).thenReturn(Optional.of(palavraParaAtualizar));

        String palavraNova = palavraParaAtualizar.getPalavra();
        service.atualizar(palavraNova, palavraParaAtualizar.getId(), ID_CACA_PALAVRAS);

        Mockito.verify(repository, times(1)).save(ArgumentMatchers.any(Palavra.class));
    }

    @Test
    void deveGerarExceptionPalavraJaExisteNoCacaPalavrasAoTentarAtualizarPalavraTendoOutraIdentica() {
        CacaPalavras cacaPalavras = cacaPalavrasValido();
        Palavra palavraParaAtualizar = criarPalavra(1, "Solzinho", cacaPalavras);
        Palavra p1 = criarPalavra(2, "Céuzinho", cacaPalavras);
        cacaPalavras.getPalavras().addAll(Arrays.asList(palavraParaAtualizar, p1));

        BDDMockito.when(serviceCacaPalavras.findById(anyInt())).thenReturn(cacaPalavras);
        BDDMockito.when(repository.findById(anyInt())).thenReturn(Optional.of(palavraParaAtualizar));

        String palavraNova = p1.getPalavra();

        String palavraExistente = p1.getPalavra();
        PalavraJaExisteNoCacaPalavrasException exception = new PalavraJaExisteNoCacaPalavrasException(palavraExistente);

        Assertions.assertThatExceptionOfType(PalavraJaExisteNoCacaPalavrasException.class)
                .isThrownBy(() -> service.atualizar(palavraNova, palavraParaAtualizar.getId(), ID_CACA_PALAVRAS))
                .withMessage(exception.getMessage());

        Mockito.verify(repository, never()).save(ArgumentMatchers.any(Palavra.class));
    }

    @Test
    void deveGerarExceptionRegraNegocioAoTentarAtualizarPalavraComEspaco() {
        CacaPalavras cacaPalavras = cacaPalavrasValido();
        Palavra palavraParaAtualizar = criarPalavra(1, "Solzinho", cacaPalavras);
        Palavra p1 = criarPalavra(2, "Céuzinho", cacaPalavras);
        cacaPalavras.getPalavras().addAll(Arrays.asList(palavraParaAtualizar, p1));

        BDDMockito.when(serviceCacaPalavras.findById(anyInt())).thenReturn(cacaPalavras);
        BDDMockito.when(repository.findById(anyInt())).thenReturn(Optional.of(palavraParaAtualizar));

        String palavraNova = "Solzinho e Céuzinho";

        Assertions.assertThatExceptionOfType(RegraNegocioException.class)
                .isThrownBy(() -> service.atualizar(palavraNova, palavraParaAtualizar.getId(), ID_CACA_PALAVRAS))
                .withMessage("Não é possível adicionar palavras com espaço");

        Mockito.verify(repository, never()).save(ArgumentMatchers.any(Palavra.class));
    }

    @Test
    void deveAtualizarPalavraESalvarElaRemovendoEspacosComTrim() {
        CacaPalavras cacaPalavras = cacaPalavrasValido();
        Palavra palavraParaAtualizar = criarPalavra(1, "Solzinho", cacaPalavras);
        Palavra p1 = criarPalavra(2, "Céuzinho", cacaPalavras);
        cacaPalavras.getPalavras().addAll(Arrays.asList(palavraParaAtualizar, p1));

        BDDMockito.when(serviceCacaPalavras.findById(anyInt())).thenReturn(cacaPalavras);
        BDDMockito.when(repository.findById(anyInt())).thenReturn(Optional.of(palavraParaAtualizar));

        String palavraNova = " Amorzinho  ";

        service.atualizar(palavraNova, palavraParaAtualizar.getId(), ID_CACA_PALAVRAS);
        final ArgumentCaptor<Palavra> captor = ArgumentCaptor.forClass(Palavra.class);
        Mockito.verify(repository).save(captor.capture());
        final Palavra palavraEnviadaParaSalvar = captor.getValue();

        assertThat(palavraEnviadaParaSalvar.getPalavra()).isNotBlank().isEqualTo(palavraNova.trim());
    }

    @Test
    void naoDeveLimparAsLocalizacoesQuandoPalavraAtualizadaForIdentica() {
        CacaPalavras cacaPalavras = cacaPalavrasValido();
        Palavra palavra = criarPalavra(1, "Solzinho", cacaPalavras);
        palavra.getLocalizacoes().add(new LocalizacaoPalavra());
        palavra.getLocalizacoes().add(new LocalizacaoPalavra());
        cacaPalavras.getPalavras().add(palavra);

        BDDMockito.when(serviceCacaPalavras.findById(anyInt())).thenReturn(cacaPalavras);
        BDDMockito.when(repository.findById(anyInt())).thenReturn(Optional.of(palavra));

        assertThat(palavra.getLocalizacoes()).isNotEmpty().hasSize(2);

        String palavraNova = "Solzinho";

        service.atualizar(palavraNova, palavra.getId(), ID_CACA_PALAVRAS);
        final ArgumentCaptor<Palavra> captor = ArgumentCaptor.forClass(Palavra.class);
        Mockito.verify(repository).save(captor.capture());
        final Palavra palavraEnviadaParaSalvar = captor.getValue();

        assertThat(palavraEnviadaParaSalvar.getLocalizacoes()).isNotEmpty().hasSize(2);
        Mockito.verify(serviceLocalizacaoPalavra, never()).deleteAllFromPalavra(ArgumentMatchers.any(Integer.class));
    }

    @Test
    void deveLimparAsLocalizacoesQuandoPalavraAtualizadaForDiferente() {
        CacaPalavras cacaPalavras = cacaPalavrasValido();
        Palavra palavra = criarPalavra(1, "Solzinho", cacaPalavras);
        palavra.getLocalizacoes().add(new LocalizacaoPalavra());
        palavra.getLocalizacoes().add(new LocalizacaoPalavra());
        cacaPalavras.getPalavras().add(palavra);

        BDDMockito.when(serviceCacaPalavras.findById(anyInt())).thenReturn(cacaPalavras);
        BDDMockito.when(repository.findById(anyInt())).thenReturn(Optional.of(palavra));

        assertThat(palavra.getLocalizacoes()).isNotEmpty().hasSize(2);

        String palavraNova = "Amorzinho";

        service.atualizar(palavraNova, palavra.getId(), ID_CACA_PALAVRAS);
        final ArgumentCaptor<Palavra> captor = ArgumentCaptor.forClass(Palavra.class);
        Mockito.verify(repository).save(captor.capture());
        final Palavra palavraEnviadaParaSalvar = captor.getValue();

        assertThat(palavraEnviadaParaSalvar.getLocalizacoes()).isEmpty();
        Mockito.verify(serviceLocalizacaoPalavra, times(1)).deleteAllFromPalavra(ArgumentMatchers.any(Integer.class));
    }

    @Test
    void deveChamarDeleteDoRepositoryComSucesso() {
        service.delete(ID_PALAVRA, ID_CACA_PALAVRAS);

        Mockito.verify(repository).delete(ArgumentMatchers.any(Palavra.class));
    }

    @Test
    void deveChamarDeleteAllFromCacaPalavrasDoRepositoryComSucesso() {
        service.deleteAll(ID_CACA_PALAVRAS);

        Mockito.verify(repository).deleteAllFromCacaPalavras(ID_CACA_PALAVRAS);
    }

    private CacaPalavras cacaPalavrasValido() {
        return CacaPalavrasCreator.criarCacaPalavrasValido(ID_CACA_PALAVRAS);
    }

    private Palavra criarPalavra(Integer id, String palavra, CacaPalavras cacaPalavras) {
        return Palavra.builder().id(id).palavra(palavra).cacaPalavras(cacaPalavras).build();
    }

    private Palavra palavraValido() {
        return criarPalavra(ID_PALAVRA, "Sol", cacaPalavrasValido());
    }

    private Optional<Palavra> optionalPalavraValido() {
        return Optional.of(palavraValido());
    }

}
