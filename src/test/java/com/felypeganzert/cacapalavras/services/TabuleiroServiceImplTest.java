package com.felypeganzert.cacapalavras.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.repository.TabuleiroRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class TabuleiroServiceImplTest {

    @InjectMocks
    private TabuleiroServiceImpl tabuleiroService;

    @Mock
    private TabuleiroRepository repository;

    @BeforeEach
    void setUp(){
        BDDMockito.when(repository.findById(anyInt()))
            .thenReturn(criarOptionalTabuleiroValido());

        BDDMockito.when(repository.save(any(Tabuleiro.class)))
            .thenReturn(criarTabuleiroValido());
    }

    @Test
    void deveChamarFindByIdDoRepositoryComSucesso(){        
        Integer id = 1;
        tabuleiroService.findById(id);
        
        Mockito.verify(repository).findById(id);
    }

    @Test
    void deveAdicionarLetrasComSucesso(){
        Integer idTabuleiro = 1;
        List<Letra> letras = new ArrayList<>();
        Letra a = new Letra(1, "a",  new Posicao(1, 1));
        Letra b = new Letra(1, "a",  new Posicao(1, 2));
        letras.add(a);
        letras.add(b);

        tabuleiroService.adicionarLetras(idTabuleiro, letras);
    }

    @Test
    void deveInserirLetraEmCelulaComSucessoEmPosicaoInicialDoTabuleiro() {
        Tabuleiro tabuleiro = criarTabuleiroValido();
        Posicao posicaoInicial = new Posicao(1, 1);
        Letra a = new Letra(1, "a", posicaoInicial);

        tabuleiroService.inserirLetra(tabuleiro, a);

        assertThat(tabuleiro.getLetras()).contains(a);
    }

    @Test
    void deveInserirLetraEmCelulaComSucessoEmPosicaoNoExtremoDoTabuleiro() {
        Tabuleiro tabuleiro = criarTabuleiroValido();
        Posicao posicaoNoExtremo = new Posicao(tabuleiro.getLargura(), tabuleiro.getAltura());
        Letra a = new Letra(1, "a",posicaoNoExtremo);

        tabuleiroService.inserirLetra(tabuleiro, a);

        assertThat(tabuleiro.getLetras()).contains(a);
    }

    @Test
    void deveGerarIllegalStateExceptionAoInserirLetraEmPosicaoForaDoTabuleiro() {
        Tabuleiro tabuleiro = criarTabuleiroValido();
        Posicao posicaoFora = new Posicao(tabuleiro.getLargura() + 1, tabuleiro.getAltura() + 1);
        Letra a = new Letra(1, "a",posicaoFora);

        assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(() -> tabuleiroService.inserirLetra(tabuleiro, a))
            .withMessage("Posição desejada para inserir não existe no tabuleiro");

        assertThat(tabuleiro.getLetras()).doesNotContain(a);
    }
    
    @Test
    void deveGerarIllegalStateExceptionAoInserirLetraEmPosicaoComXForaDoTabuleiro() {
        Tabuleiro tabuleiro = criarTabuleiroValido();
        Posicao posicaoXFora = new Posicao(tabuleiro.getLargura() + 1, tabuleiro.getAltura());
        Letra a = new Letra(1, "a",posicaoXFora);

        assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(() -> tabuleiroService.inserirLetra(tabuleiro, a))
            .withMessage("Posição desejada para inserir não existe no tabuleiro");

        assertThat(tabuleiro.getLetras()).doesNotContain(a);
    }

    @Test
    void deveGerarIllegalStateExceptionAoInserirLetraEmPosicaoComYForaDoTabuleiro() {
        Tabuleiro tabuleiro = criarTabuleiroValido();
        Posicao posicaoYFora = new Posicao(tabuleiro.getLargura(), tabuleiro.getAltura() + 1);
        Letra a = new Letra(1, "a",posicaoYFora);

        assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(() -> tabuleiroService.inserirLetra(tabuleiro, a))
            .withMessage("Posição desejada para inserir não existe no tabuleiro");

        assertThat(tabuleiro.getLetras()).doesNotContain(a);
    }

    @Test
    void deveInserirMaisDeUmaLetraComSucessoEmPosicoesVazia() {
        Tabuleiro tabuleiro = criarTabuleiroValido();
        Letra a = new Letra(1, "a", new Posicao(1, 1));
        Letra b = new Letra(2, "b", new Posicao(1, 2));

        tabuleiroService.inserirLetra(tabuleiro, a);
        tabuleiroService.inserirLetra(tabuleiro, b);

        assertThat(tabuleiro.getLetras()).contains(a, b);
    }

    @Test
    void deveLimparValorAnteriorDaCelulaEInserirNovaLetraComSucessoEmPosicaoQueJaPossuiaUmaLetra() {
        Tabuleiro tabuleiro = criarTabuleiroValido();
        Letra a = new Letra(1, "a", new Posicao(1, 1));
        Letra b = new Letra(2, "b", new Posicao(1, 2));
        tabuleiroService.inserirLetra(tabuleiro, a);
        tabuleiroService.inserirLetra(tabuleiro, b);

        assertThat(tabuleiro.getLetras()).contains(a, b);
        assertThat(tabuleiro.getLetras()).size().isEqualTo(2);

        Posicao posicaoB = new Posicao(1, 2);
        Letra c = new Letra(3, "c",posicaoB);
        tabuleiroService.inserirLetra(tabuleiro, c);

        assertThat(tabuleiro.getLetras()).contains(a, c).doesNotContain(b);
        assertThat(tabuleiro.getLetras()).size().isEqualTo(2);
    }

    private Tabuleiro criarTabuleiroValido() {
        return new Tabuleiro(1,  Tabuleiro.LARGURA_MINIMA, Tabuleiro.ALTURA_MINIMA);
    }

    private Optional<Tabuleiro> criarOptionalTabuleiroValido() {
        return Optional.of(new Tabuleiro(1,  Tabuleiro.LARGURA_MINIMA, Tabuleiro.ALTURA_MINIMA));
    }    

}
