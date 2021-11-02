package com.felypeganzert.cacapalavras.services.impl;

import static com.felypeganzert.cacapalavras.utils.AppConstantes.ID;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.LETRA;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.TABULEIRO;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.exception.RecursoNaoEncontradoException;
import com.felypeganzert.cacapalavras.exception.RecursoNaoPertenceAException;
import com.felypeganzert.cacapalavras.exception.RegraNegocioException;
import com.felypeganzert.cacapalavras.repository.LetraRepository;
import com.felypeganzert.cacapalavras.services.LocalizacaoPalavraService;
import com.felypeganzert.cacapalavras.services.TabuleiroService;
import com.felypeganzert.cacapalavras.util.CacaPalavrasCreator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    private final int ID_CACA_PALAVRAS = 1;
    private final int ID_TABULEIRO = 1;

    @BeforeEach
    void setUp() {
        BDDMockito.when(serviceTabuleiro.findByIdEntity(anyInt(), anyInt())).thenReturn(tabuleiroValido());
    }

    @Test
    void deveChamarSaveComSucesso() {
        Letra letra = Letra.builder().letra('a').posicao(new Posicao(1, 1)).build();
        service.adicionarLetra(letra, ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(repository, times(1)).save(letra);
    }

    @Test
    void deveGerarRegraNegocioExceptionAoTentarAdicionarEmUmaPosicaoInexistente() {
        Tabuleiro tabuleiro = tabuleiroValido();
        BDDMockito.when(serviceTabuleiro.findByIdEntity(anyInt(), anyInt())).thenReturn(tabuleiro);

        int xForaDoTabuleiro = tabuleiro.getLargura() + 1;
        Letra letra = Letra.builder().letra('a').posicao(new Posicao(xForaDoTabuleiro, 1)).build();

        RegraNegocioException exceptionEsperada = new RegraNegocioException(
                "Posição " + letra.getPosicao().getPosicaoCartesiana() + " não existe no tabuleiro");

        Assertions.assertThatExceptionOfType(RegraNegocioException.class)
                .isThrownBy(() -> service.adicionarLetra(letra, ID_TABULEIRO, ID_CACA_PALAVRAS))
                .withMessage(exceptionEsperada.getMessage());
    }

    @Test
    void deveChamarDeleteAndDeleteAllUsandoLetraAoAdicionarEmPosicaoQueHaviaLetra() {
        Posicao posicao = new Posicao(1, 1);
        // adiciona a primeira letra
        Tabuleiro tabuleiro = tabuleiroValido();
        Letra letra1 = Letra.builder().letra('a').id(1).tabuleiro(tabuleiro).posicao(posicao).build();
        tabuleiro.getLetras().add(letra1);
        BDDMockito.when(serviceTabuleiro.findByIdEntity(anyInt(), anyInt())).thenReturn(tabuleiro);

        // adiciona outra letra na mesma posicao, a primeira deve ser excluída
        Letra letra2 = Letra.builder().letra('b').posicao(posicao).build();
        service.adicionarLetra(letra2, ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(serviceLocalizacaoPalavra, times(1)).deleteAllUsandoLetra(letra1.getId());
        Mockito.verify(repository, times(1)).delete(letra1);

        // por fim deve salvar a segunda letra
        Mockito.verify(repository, times(1)).save(letra2);
    }

    @Test
    void naoDeveChamarDeleteAoAdicionarEmPosicaoSemLetra() {
        Letra letra = Letra.builder().letra('a').posicao(new Posicao(1, 1)).build();
        service.adicionarLetra(letra, ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(serviceLocalizacaoPalavra, never()).deleteAllUsandoLetra(anyInt());
        Mockito.verify(repository, never()).delete(ArgumentMatchers.any(Letra.class));

        Mockito.verify(repository, times(1)).save(letra);
    }

    @Test
    void deveChamarSaveAllComSucesso() {
        Letra letra1 = Letra.builder().letra('a').posicao(new Posicao(1, 1)).build();
        Letra letra2 = Letra.builder().letra('b').posicao(new Posicao(1, 2)).build();
        List<Letra> letras = new ArrayList<>();
        letras.addAll(java.util.Arrays.asList(letra1, letra2));
        service.adicionarLetras(letras, ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(repository, times(1)).saveAll(letras);
    }

    @Test
    void deveGerarRegraNegocioExceptionAoTentarAdicionarLetrasEmPosicoesInexistentes() {
        Tabuleiro tabuleiro = tabuleiroValido();
        BDDMockito.when(serviceTabuleiro.findByIdEntity(anyInt(), anyInt())).thenReturn(tabuleiro);

        int xForaDoTabuleiro = tabuleiro.getLargura() + 1;
        Letra letraValida1 = Letra.builder().letra('a').posicao(new Posicao(1, 1)).build();
        Letra letraFora1 = Letra.builder().letra('a').posicao(new Posicao(xForaDoTabuleiro, 1)).build();
        Letra letraFora2 = Letra.builder().letra('b').posicao(new Posicao(xForaDoTabuleiro, 2)).build();
        List<Letra> letras = new ArrayList<>();
        letras.addAll(java.util.Arrays.asList(letraValida1, letraFora1, letraFora2));

        RegraNegocioException exceptionEsperada;
        List<Posicao> posicoesNaoExistentes = new ArrayList<>();
        posicoesNaoExistentes.addAll(java.util.Arrays.asList(letraFora1.getPosicao(), letraFora2.getPosicao()));
        String pos = posicoesNaoExistentes.stream().map(p -> p.getPosicaoCartesiana()).collect(Collectors.joining(", "));
        String erro = "As posições [ " + pos + "] não existem no tabuleiro";
        exceptionEsperada = new RegraNegocioException(erro);

        Assertions.assertThatExceptionOfType(RegraNegocioException.class)
                .isThrownBy(() -> service.adicionarLetras(letras, ID_TABULEIRO, ID_CACA_PALAVRAS))
                .withMessage(exceptionEsperada.getMessage());
    }

    @Test
    void deveChamarDeleteAllUsandoLetrasAndDeleteAllPassandoAsLetrasExistentesNasPosicoesAdicionadas() {
        // adiciona a primeira letra
        Tabuleiro tabuleiro = tabuleiroValido();
        Letra letraQueSeraExcluida = Letra.builder().letra('a').id(1).tabuleiro(tabuleiro).posicao(new Posicao(1, 1)).build();
        Letra letraQueSeraMantida = Letra.builder().letra('b').id(2).tabuleiro(tabuleiro).posicao(new Posicao(1, 2)).build();
        tabuleiro.getLetras().addAll(java.util.Arrays.asList(letraQueSeraExcluida, letraQueSeraMantida));
        BDDMockito.when(serviceTabuleiro.findByIdEntity(anyInt(), anyInt())).thenReturn(tabuleiro);

        Letra letraNovaQueIraSobrescrever = Letra.builder().letra('a').posicao(letraQueSeraExcluida.getPosicao()).build();
        Letra letraNova2 = Letra.builder().letra('b').posicao(new Posicao(2, 2)).build();
        List<Letra> letras = new ArrayList<>();
        letras.addAll(java.util.Arrays.asList(letraNovaQueIraSobrescrever, letraNova2));

        List<Letra> letrasAntigasExistentes = new ArrayList<>();
        letrasAntigasExistentes.add(letraQueSeraExcluida);
        List<Integer> idLetrasAntigasExistentes = letrasAntigasExistentes.stream().map(l -> l.getId()).collect(Collectors.toList());
        
        service.adicionarLetras(letras, ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(serviceLocalizacaoPalavra, times(1)).deleteAllUsandoLetras(idLetrasAntigasExistentes);
        Mockito.verify(repository, times(1)).deleteAll(letrasAntigasExistentes);
        Mockito.verify(repository, times(1)).saveAll(letras);
    }

    @Test
    void deveChamarFindByIdDoServiceTabuleiroERetornarTodasAsLetrasDoTabuleiro(){
        Tabuleiro tabuleiro = tabuleiroValido();
        Letra letra1 = Letra.builder().letra('a').id(1).tabuleiro(tabuleiro).posicao(new Posicao(1,1)).build();
        Letra letra2 = Letra.builder().letra('b').id(1).tabuleiro(tabuleiro).posicao(new Posicao(1,2)).build();
        tabuleiro.getLetras().addAll(java.util.Arrays.asList(letra1, letra2));
        BDDMockito.when(serviceTabuleiro.findByIdEntity(anyInt(), anyInt())).thenReturn(tabuleiro);

        List<Letra> allLetras = service.findAll(ID_TABULEIRO, ID_CACA_PALAVRAS);

        Assertions.assertThat(allLetras).isNotEmpty().hasSize(tabuleiro.getLetras().size());
        Mockito.verify(serviceTabuleiro, times(1)).findByIdEntity(ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    // # findById
    @Test
    void deveChamarFindByIdDoRepositoryComSucesso(){
        Tabuleiro tabuleiro = tabuleiroValido();
        int id = 1;
        Letra letra1 = Letra.builder().letra('a').id(id).tabuleiro(tabuleiro).posicao(new Posicao(1,1)).build();
        tabuleiro.getLetras().add(letra1);
        BDDMockito.when(repository.findById(anyInt())).thenReturn(Optional.of(letra1));
        BDDMockito.when(serviceTabuleiro.findByIdEntity(anyInt(), anyInt())).thenReturn(tabuleiro);

        service.findById(id, ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(repository, times(1)).findById(id);
    }
    
    @Test
    void deveGerarRecursoNaoEncontradoExceptionAoBuscarLetraNaoExistente(){
        int id = 1;
        BDDMockito.when(repository.findById(anyInt())).thenReturn(Optional.empty());
        
        RecursoNaoEncontradoException ex = new RecursoNaoEncontradoException(LETRA, ID, id);

        Assertions.assertThatExceptionOfType(RecursoNaoEncontradoException.class)
                .isThrownBy(() -> service.findById(id, ID_TABULEIRO, ID_CACA_PALAVRAS))
                .withMessage(ex.getMessage());
    }

    @Test
    void deveGerarRecursoNaoPertenceAExceptionAoBuscarLetraNaoPertencenteAoTabuleiro(){
        Tabuleiro tabuleiro = tabuleiroValido();
        BDDMockito.when(serviceTabuleiro.findByIdEntity(anyInt(), anyInt())).thenReturn(tabuleiro);
        
        int id = 1;
        Tabuleiro tabuleiro2 = tabuleiroValido();
        tabuleiro2.setId(tabuleiro.getId() + 1);
        Letra letra1 = Letra.builder().letra('a').id(id).tabuleiro(tabuleiro2).posicao(new Posicao(1,1)).build();
        BDDMockito.when(repository.findById(anyInt())).thenReturn(Optional.of(letra1));
        
        RecursoNaoPertenceAException ex = new RecursoNaoPertenceAException(LETRA, TABULEIRO);

        Assertions.assertThatExceptionOfType(RecursoNaoPertenceAException.class)
                .isThrownBy(() -> service.findById(id, ID_TABULEIRO, ID_CACA_PALAVRAS))
                .withMessage(ex.getMessage());
    }


    // # atualizar
    @Test
    void deveChamarSaveDoRepositoryAoAtualizarComSucesso(){
        Tabuleiro tabuleiro = tabuleiroValido();
        int id = 1;
        Letra letra = Letra.builder().letra('a').id(id).tabuleiro(tabuleiro).posicao(new Posicao(1,1)).build();
        tabuleiro.getLetras().add(letra);
        BDDMockito.when(repository.findById(anyInt())).thenReturn(Optional.of(letra));
        BDDMockito.when(serviceTabuleiro.findByIdEntity(anyInt(), anyInt())).thenReturn(tabuleiro);

        service.atualizar('b', id, ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(repository, times(1)).save(ArgumentMatchers.any(Letra.class));
    }
    
    @Test
    void deveChamarDeleteAllUsandoLetraPassandoALetraAdicionado(){
        Tabuleiro tabuleiro = tabuleiroValido();
        int id = 1;
        Letra letra = Letra.builder().letra('a').id(id).tabuleiro(tabuleiro).posicao(new Posicao(1,1)).build();
        tabuleiro.getLetras().add(letra);
        BDDMockito.when(repository.findById(anyInt())).thenReturn(Optional.of(letra));
        BDDMockito.when(serviceTabuleiro.findByIdEntity(anyInt(), anyInt())).thenReturn(tabuleiro);

        service.atualizar('b', id, ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(serviceLocalizacaoPalavra, times(1)).deleteAllUsandoLetra(id);
    }

    @Test
    void deveChamarDeleteDoRepositoryComSucesso(){
        Tabuleiro tabuleiro = tabuleiroValido();
        int id = 1;
        Letra letra = Letra.builder().letra('a').id(id).tabuleiro(tabuleiro).posicao(new Posicao(1,1)).build();
        tabuleiro.getLetras().add(letra);
        BDDMockito.when(repository.findById(anyInt())).thenReturn(Optional.of(letra));
        BDDMockito.when(serviceTabuleiro.findByIdEntity(anyInt(), anyInt())).thenReturn(tabuleiro);

        service.delete(id, ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(repository, times(1)).delete(ArgumentMatchers.any(Letra.class));
    }
    
    @Test
    void deveChamarDeleteAllUsandoLetraPassandoALetraQueSeraDeletada(){
        Tabuleiro tabuleiro = tabuleiroValido();
        int id = 1;
        Letra letra = Letra.builder().letra('a').id(id).tabuleiro(tabuleiro).posicao(new Posicao(1,1)).build();
        tabuleiro.getLetras().add(letra);
        BDDMockito.when(repository.findById(anyInt())).thenReturn(Optional.of(letra));
        BDDMockito.when(serviceTabuleiro.findByIdEntity(anyInt(), anyInt())).thenReturn(tabuleiro);

        service.delete(id, ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(serviceLocalizacaoPalavra, times(1)).deleteAllUsandoLetra(id);
    }

    @Test
    void deveChamarDeleteAllFromTabuleiroDoRepositoryComSucesso(){
        service.deleteAll(ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(repository, times(1)).deleteAllFromTabuleiro(ID_TABULEIRO);
    }

    @Test
    void deveChamarDeleteAllAssociadasAoTabuleiroComSucesso(){
        service.deleteAll(ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(serviceLocalizacaoPalavra, times(1)).deleteAllAssociadasAoTabuleiro(ID_TABULEIRO);
    }

    private CacaPalavras cacaPalavrasValido() {
        return CacaPalavrasCreator.criarCacaPalavrasValido(ID_CACA_PALAVRAS);
    }

    private Tabuleiro tabuleiroValido() {
        return CacaPalavrasCreator.criarTabuleiroValido(ID_TABULEIRO, cacaPalavrasValido());
    }

}
