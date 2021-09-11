package com.felypeganzert.cacapalavras.services;

import static com.felypeganzert.cacapalavras.util.CacaPalavrasCreator.inserirLetrasNaLinha;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Comparator;
import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.LocalizacaoLetraNoTabuleiro;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.services.impl.TabuleiroServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CacaPalavrasResolverTest {

    @InjectMocks
    private TabuleiroServiceImpl tabuleiroService;

    @InjectMocks
    private CacaPalavrasResolver resolver;

    @Test
    public void deveEncontrarPalavraNaDiagonalParaNoroeste() {
        CacaPalavras cacaPalavras = new CacaPalavras();
        Tabuleiro tabuleiro8x8 = new Tabuleiro(8,8);
        inserirLetrasNaLinha(tabuleiro8x8, "E D E S T x x E", 1);
        inserirLetrasNaLinha(tabuleiro8x8, "x T x x x x A x", 2);
        inserirLetrasNaLinha(tabuleiro8x8, "x A S x x S x x", 3);
        inserirLetrasNaLinha(tabuleiro8x8, "W x x E E x x x", 4);
        inserirLetrasNaLinha(tabuleiro8x8, "x x x D O x x x", 5);
        inserirLetrasNaLinha(tabuleiro8x8, "x x A x x R Ã x", 6);
        inserirLetrasNaLinha(tabuleiro8x8, "x S x É x x O x", 7);
        inserirLetrasNaLinha(tabuleiro8x8, "N x x x N O R N", 8);
        cacaPalavras.setTabuleiro(tabuleiro8x8);
        Palavra noroeste = new Palavra();
        noroeste.setPalavra("noroeste");
        cacaPalavras.getPalavras().add(noroeste);
    
        resolver.encontrarPalavrasNoTabuleiro(cacaPalavras);

        // Verifica a quantidade de palavras encontradas
        assertThat(noroeste.getLocalizacoes().size()).isEqualTo(1);
        // Verifica a quantidade de letras encontradas
        noroeste.getLocalizacoes().get(0)
            .getLocalizacoesLetrasNoTabuleiro().sort(Comparator.comparing(LocalizacaoLetraNoTabuleiro::getOrdem));
        List<LocalizacaoLetraNoTabuleiro> localizacaoLetras = noroeste.getLocalizacoes().get(0).getLocalizacoesLetrasNoTabuleiro();
        assertThat(localizacaoLetras.size()).isEqualTo(noroeste.getPalavra().length());
        // Verifica a posicao de cada letra
        assertThat(localizacaoLetras.get(0).getLetra().getPosicao()).isEqualTo(new Posicao(8,8));
        assertThat(localizacaoLetras.get(1).getLetra().getPosicao()).isEqualTo(new Posicao(7,7));
        assertThat(localizacaoLetras.get(2).getLetra().getPosicao()).isEqualTo(new Posicao(6,6));
        assertThat(localizacaoLetras.get(3).getLetra().getPosicao()).isEqualTo(new Posicao(5,5));
        assertThat(localizacaoLetras.get(4).getLetra().getPosicao()).isEqualTo(new Posicao(4,4));
        assertThat(localizacaoLetras.get(5).getLetra().getPosicao()).isEqualTo(new Posicao(3,3));
        assertThat(localizacaoLetras.get(6).getLetra().getPosicao()).isEqualTo(new Posicao(2,2));
        assertThat(localizacaoLetras.get(7).getLetra().getPosicao()).isEqualTo(new Posicao(1,1));
    }

    @Test
    public void deveEncontrarPalavraNaHorizontalParaNorte() {
        CacaPalavras cacaPalavras = new CacaPalavras();
        Tabuleiro tabuleiro6X6 = new Tabuleiro(6,6);
        inserirLetrasNaLinha(tabuleiro6X6, "x x O S T P", 1);
        inserirLetrasNaLinha(tabuleiro6X6, "A E x x x x", 2);
        inserirLetrasNaLinha(tabuleiro6X6, "x T x E S x", 3);
        inserirLetrasNaLinha(tabuleiro6X6, "x R T S E O", 4);
        inserirLetrasNaLinha(tabuleiro6X6, "x O T x E x", 5);
        inserirLetrasNaLinha(tabuleiro6X6, "x N x A x x", 6);
        cacaPalavras.setTabuleiro(tabuleiro6X6);
        Palavra norte = new Palavra();
        norte.setPalavra("norte");
        cacaPalavras.getPalavras().add(norte);
    
        resolver.encontrarPalavrasNoTabuleiro(cacaPalavras);

        // Verifica a quantidade de palavras encontradas
        assertThat(norte.getLocalizacoes().size()).isEqualTo(1);
        // Verifica a quantidade de letras encontradas
        norte.getLocalizacoes().get(0)
            .getLocalizacoesLetrasNoTabuleiro().sort(Comparator.comparing(LocalizacaoLetraNoTabuleiro::getOrdem));
        List<LocalizacaoLetraNoTabuleiro> localizacaoLetras = norte.getLocalizacoes().get(0).getLocalizacoesLetrasNoTabuleiro();
        assertThat(localizacaoLetras.size()).isEqualTo(norte.getPalavra().length());
        // Verifica a posicao de cada letra
        assertThat(localizacaoLetras.get(0).getLetra().getPosicao()).isEqualTo(new Posicao(2,6));
        assertThat(localizacaoLetras.get(1).getLetra().getPosicao()).isEqualTo(new Posicao(2,5));
        assertThat(localizacaoLetras.get(2).getLetra().getPosicao()).isEqualTo(new Posicao(2,4));
        assertThat(localizacaoLetras.get(3).getLetra().getPosicao()).isEqualTo(new Posicao(2,3));
        assertThat(localizacaoLetras.get(4).getLetra().getPosicao()).isEqualTo(new Posicao(2,2));
    }

    @Test
    public void deveEncontrarPalavraNaDiagonalParaNordeste() {
        CacaPalavras cacaPalavras = new CacaPalavras();
        Tabuleiro tabuleiro8x8 = new Tabuleiro(8,8);
        inserirLetrasNaLinha(tabuleiro8x8, "x D E S T x x E", 1);
        inserirLetrasNaLinha(tabuleiro8x8, "x x x x x x T x", 2);
        inserirLetrasNaLinha(tabuleiro8x8, "x A x x x S x x", 3);
        inserirLetrasNaLinha(tabuleiro8x8, "W x x x E x x x", 4);
        inserirLetrasNaLinha(tabuleiro8x8, "x x x D x x x x", 5);
        inserirLetrasNaLinha(tabuleiro8x8, "x x R x x x Ã x", 6);
        inserirLetrasNaLinha(tabuleiro8x8, "x O x É x x x x", 7);
        inserirLetrasNaLinha(tabuleiro8x8, "N x x x N O R x", 8);
        cacaPalavras.setTabuleiro(tabuleiro8x8);
        Palavra nordeste = new Palavra();
        nordeste.setPalavra("nordeste");
        cacaPalavras.getPalavras().add(nordeste);
    
        resolver.encontrarPalavrasNoTabuleiro(cacaPalavras);

        // Verifica a quantidade de palavras encontradas
        assertThat(nordeste.getLocalizacoes().size()).isEqualTo(1);
        // Verifica a quantidade de letras encontradas
        nordeste.getLocalizacoes().get(0)
            .getLocalizacoesLetrasNoTabuleiro().sort(Comparator.comparing(LocalizacaoLetraNoTabuleiro::getOrdem));
        List<LocalizacaoLetraNoTabuleiro> localizacaoLetras = nordeste.getLocalizacoes().get(0).getLocalizacoesLetrasNoTabuleiro();
        assertThat(localizacaoLetras.size()).isEqualTo(nordeste.getPalavra().length());
        // Verifica a posicao de cada letra
        assertThat(localizacaoLetras.get(0).getLetra().getPosicao()).isEqualTo(new Posicao(1,8));
        assertThat(localizacaoLetras.get(1).getLetra().getPosicao()).isEqualTo(new Posicao(2,7));
        assertThat(localizacaoLetras.get(2).getLetra().getPosicao()).isEqualTo(new Posicao(3,6));
        assertThat(localizacaoLetras.get(3).getLetra().getPosicao()).isEqualTo(new Posicao(4,5));
        assertThat(localizacaoLetras.get(4).getLetra().getPosicao()).isEqualTo(new Posicao(5,4));
        assertThat(localizacaoLetras.get(5).getLetra().getPosicao()).isEqualTo(new Posicao(6,3));
        assertThat(localizacaoLetras.get(6).getLetra().getPosicao()).isEqualTo(new Posicao(7,2));
        assertThat(localizacaoLetras.get(7).getLetra().getPosicao()).isEqualTo(new Posicao(8,1));
    }

    @Test
    public void deveEncontrarPalavraNaHorizontalParaOeste() {
        CacaPalavras cacaPalavras = new CacaPalavras();
        Tabuleiro tabuleiro7X6 = new Tabuleiro(7,6);
        inserirLetrasNaLinha(tabuleiro7X6, "x x O S T P x", 1);
        inserirLetrasNaLinha(tabuleiro7X6, "A A x x x x x", 2);
        inserirLetrasNaLinha(tabuleiro7X6, "x x x E S x x", 3);
        inserirLetrasNaLinha(tabuleiro7X6, "x E T S E O x", 4);
        inserirLetrasNaLinha(tabuleiro7X6, "x x T x E x x", 5);
        inserirLetrasNaLinha(tabuleiro7X6, "x S x A x x W", 6);
        cacaPalavras.setTabuleiro(tabuleiro7X6);
        Palavra oeste = new Palavra();
        oeste.setPalavra("oeste");
        cacaPalavras.getPalavras().add(oeste);
    
        resolver.encontrarPalavrasNoTabuleiro(cacaPalavras);

        // Verifica a quantidade de palavras encontradas
        assertThat(oeste.getLocalizacoes().size()).isEqualTo(1);
        // Verifica a quantidade de letras encontradas
        oeste.getLocalizacoes().get(0)
            .getLocalizacoesLetrasNoTabuleiro().sort(Comparator.comparing(LocalizacaoLetraNoTabuleiro::getOrdem));
        List<LocalizacaoLetraNoTabuleiro> localizacaoLetras = oeste.getLocalizacoes().get(0).getLocalizacoesLetrasNoTabuleiro();
        assertThat(localizacaoLetras.size()).isEqualTo(oeste.getPalavra().length());
        // Verifica a posicao de cada letra
        assertThat(localizacaoLetras.get(0).getLetra().getPosicao()).isEqualTo(new Posicao(6,4));
        assertThat(localizacaoLetras.get(1).getLetra().getPosicao()).isEqualTo(new Posicao(5,4));
        assertThat(localizacaoLetras.get(2).getLetra().getPosicao()).isEqualTo(new Posicao(4,4));
        assertThat(localizacaoLetras.get(3).getLetra().getPosicao()).isEqualTo(new Posicao(3,4));
        assertThat(localizacaoLetras.get(4).getLetra().getPosicao()).isEqualTo(new Posicao(2,4));
    }

    @Test
    public void deveEncontrarPalavraNaHorizontalParaLeste() {
        CacaPalavras cacaPalavras = new CacaPalavras();
        Tabuleiro tabuleiro7X6 = new Tabuleiro(7,6);
        inserirLetrasNaLinha(tabuleiro7X6, "x x O S T P x", 1);
        inserirLetrasNaLinha(tabuleiro7X6, "A A x x x x x", 2);
        inserirLetrasNaLinha(tabuleiro7X6, "x x x E S x x", 3);
        inserirLetrasNaLinha(tabuleiro7X6, "x L E S T E x", 4);
        inserirLetrasNaLinha(tabuleiro7X6, "x x T x E x x", 5);
        inserirLetrasNaLinha(tabuleiro7X6, "x S x A x x W", 6);
        cacaPalavras.setTabuleiro(tabuleiro7X6);
        Palavra leste = new Palavra();
        leste.setPalavra("leste");
        cacaPalavras.getPalavras().add(leste);
    
        resolver.encontrarPalavrasNoTabuleiro(cacaPalavras);

        // Verifica a quantidade de palavras encontradas
        assertThat(leste.getLocalizacoes().size()).isEqualTo(1);
        // Verifica a quantidade de letras encontradas
        leste.getLocalizacoes().get(0)
            .getLocalizacoesLetrasNoTabuleiro().sort(Comparator.comparing(LocalizacaoLetraNoTabuleiro::getOrdem));
        List<LocalizacaoLetraNoTabuleiro> localizacaoLetras = leste.getLocalizacoes().get(0).getLocalizacoesLetrasNoTabuleiro();
        assertThat(localizacaoLetras.size()).isEqualTo(leste.getPalavra().length());
        // Verifica a posicao de cada letra
        assertThat(localizacaoLetras.get(0).getLetra().getPosicao()).isEqualTo(new Posicao(2,4));
        assertThat(localizacaoLetras.get(1).getLetra().getPosicao()).isEqualTo(new Posicao(3,4));
        assertThat(localizacaoLetras.get(2).getLetra().getPosicao()).isEqualTo(new Posicao(4,4));
        assertThat(localizacaoLetras.get(3).getLetra().getPosicao()).isEqualTo(new Posicao(5,4));
        assertThat(localizacaoLetras.get(4).getLetra().getPosicao()).isEqualTo(new Posicao(6,4));
    }

    @Test
    public void deveEncontrarPalavraNaDiagonalParaSudoeste() {
        CacaPalavras cacaPalavras = new CacaPalavras();
        Tabuleiro tabuleiro8x8 = new Tabuleiro(8,8);
        inserirLetrasNaLinha(tabuleiro8x8, "x D E S T x x S", 1);
        inserirLetrasNaLinha(tabuleiro8x8, "x x x x x x U x", 2);
        inserirLetrasNaLinha(tabuleiro8x8, "x A x x x D x x", 3);
        inserirLetrasNaLinha(tabuleiro8x8, "W x x x O x x x", 4);
        inserirLetrasNaLinha(tabuleiro8x8, "x x x E x x x x", 5);
        inserirLetrasNaLinha(tabuleiro8x8, "x x S x x x Ã x", 6);
        inserirLetrasNaLinha(tabuleiro8x8, "x T x É x x x x", 7);
        inserirLetrasNaLinha(tabuleiro8x8, "E x x x N O R x", 8);
        cacaPalavras.setTabuleiro(tabuleiro8x8);
        Palavra sudoeste = new Palavra();
        sudoeste.setPalavra("sudoeste");
        cacaPalavras.getPalavras().add(sudoeste);
    
        resolver.encontrarPalavrasNoTabuleiro(cacaPalavras);

        // Verifica a quantidade de palavras encontradas
        assertThat(sudoeste.getLocalizacoes().size()).isEqualTo(1);
        // Verifica a quantidade de letras encontradas
        sudoeste.getLocalizacoes().get(0)
            .getLocalizacoesLetrasNoTabuleiro().sort(Comparator.comparing(LocalizacaoLetraNoTabuleiro::getOrdem));
        List<LocalizacaoLetraNoTabuleiro> localizacaoLetras = sudoeste.getLocalizacoes().get(0).getLocalizacoesLetrasNoTabuleiro();
        assertThat(localizacaoLetras.size()).isEqualTo(sudoeste.getPalavra().length());
        // Verifica a posicao de cada letra
        assertThat(localizacaoLetras.get(0).getLetra().getPosicao()).isEqualTo(new Posicao(8,1));
        assertThat(localizacaoLetras.get(1).getLetra().getPosicao()).isEqualTo(new Posicao(7,2));
        assertThat(localizacaoLetras.get(2).getLetra().getPosicao()).isEqualTo(new Posicao(6,3));
        assertThat(localizacaoLetras.get(3).getLetra().getPosicao()).isEqualTo(new Posicao(5,4));
        assertThat(localizacaoLetras.get(4).getLetra().getPosicao()).isEqualTo(new Posicao(4,5));
        assertThat(localizacaoLetras.get(5).getLetra().getPosicao()).isEqualTo(new Posicao(3,6));
        assertThat(localizacaoLetras.get(6).getLetra().getPosicao()).isEqualTo(new Posicao(2,7));
        assertThat(localizacaoLetras.get(7).getLetra().getPosicao()).isEqualTo(new Posicao(1,8));
    }

    @Test
    public void deveEncontrarPalavraNaHorizontalParaSul() {
        CacaPalavras cacaPalavras = new CacaPalavras();
        Tabuleiro tabuleiro6X6 = new Tabuleiro(6,6);
        inserirLetrasNaLinha(tabuleiro6X6, "x x O S T P", 1);
        inserirLetrasNaLinha(tabuleiro6X6, "A E x x x x", 2);
        inserirLetrasNaLinha(tabuleiro6X6, "x S x E S x", 3);
        inserirLetrasNaLinha(tabuleiro6X6, "x U x S E O", 4);
        inserirLetrasNaLinha(tabuleiro6X6, "x L x x E x", 5);
        inserirLetrasNaLinha(tabuleiro6X6, "x N x A x x", 6);
        cacaPalavras.setTabuleiro(tabuleiro6X6);
        Palavra sul = new Palavra();
        sul.setPalavra("sul");
        cacaPalavras.getPalavras().add(sul);
    
        resolver.encontrarPalavrasNoTabuleiro(cacaPalavras);

        // Verifica a quantidade de palavras encontradas
        assertThat(sul.getLocalizacoes().size()).isEqualTo(1);
        // Verifica a quantidade de letras encontradas
        sul.getLocalizacoes().get(0)
            .getLocalizacoesLetrasNoTabuleiro().sort(Comparator.comparing(LocalizacaoLetraNoTabuleiro::getOrdem));
        List<LocalizacaoLetraNoTabuleiro> localizacaoLetras = sul.getLocalizacoes().get(0).getLocalizacoesLetrasNoTabuleiro();
        assertThat(localizacaoLetras.size()).isEqualTo(sul.getPalavra().length());
        // Verifica a posicao de cada letra
        assertThat(localizacaoLetras.get(0).getLetra().getPosicao()).isEqualTo(new Posicao(2,3));
        assertThat(localizacaoLetras.get(1).getLetra().getPosicao()).isEqualTo(new Posicao(2,4));
        assertThat(localizacaoLetras.get(2).getLetra().getPosicao()).isEqualTo(new Posicao(2,5));
    }

    @Test
    public void deveEncontrarPalavraNaDiagonalParaSudeste() {
        CacaPalavras cacaPalavras = new CacaPalavras();
        Tabuleiro tabuleiro8x8 = new Tabuleiro(8,8);
        inserirLetrasNaLinha(tabuleiro8x8, "S D E S T x x E", 1);
        inserirLetrasNaLinha(tabuleiro8x8, "x U x x x x A x", 2);
        inserirLetrasNaLinha(tabuleiro8x8, "x A D x x S x x", 3);
        inserirLetrasNaLinha(tabuleiro8x8, "W x x E E x x x", 4);
        inserirLetrasNaLinha(tabuleiro8x8, "x x x D S x x x", 5);
        inserirLetrasNaLinha(tabuleiro8x8, "x x A x x T Ã x", 6);
        inserirLetrasNaLinha(tabuleiro8x8, "x S x É x x E x", 7);
        inserirLetrasNaLinha(tabuleiro8x8, "N x x x N O R N", 8);
        cacaPalavras.setTabuleiro(tabuleiro8x8);
        Palavra sudeste = new Palavra();
        sudeste.setPalavra("sudeste");
        cacaPalavras.getPalavras().add(sudeste);
    
        resolver.encontrarPalavrasNoTabuleiro(cacaPalavras);

        // Verifica a quantidade de palavras encontradas
        assertThat(sudeste.getLocalizacoes().size()).isEqualTo(1);
        // Verifica a quantidade de letras encontradas
        sudeste.getLocalizacoes().get(0)
            .getLocalizacoesLetrasNoTabuleiro().sort(Comparator.comparing(LocalizacaoLetraNoTabuleiro::getOrdem));
        List<LocalizacaoLetraNoTabuleiro> localizacaoLetras = sudeste.getLocalizacoes().get(0).getLocalizacoesLetrasNoTabuleiro();
        assertThat(localizacaoLetras.size()).isEqualTo(sudeste.getPalavra().length());
        // Verifica a posicao de cada letra
        assertThat(localizacaoLetras.get(0).getLetra().getPosicao()).isEqualTo(new Posicao(1,1));
        assertThat(localizacaoLetras.get(1).getLetra().getPosicao()).isEqualTo(new Posicao(2,2));
        assertThat(localizacaoLetras.get(2).getLetra().getPosicao()).isEqualTo(new Posicao(3,3));
        assertThat(localizacaoLetras.get(3).getLetra().getPosicao()).isEqualTo(new Posicao(4,4));
        assertThat(localizacaoLetras.get(4).getLetra().getPosicao()).isEqualTo(new Posicao(5,5));
        assertThat(localizacaoLetras.get(5).getLetra().getPosicao()).isEqualTo(new Posicao(6,6));
        assertThat(localizacaoLetras.get(6).getLetra().getPosicao()).isEqualTo(new Posicao(7,7));
    }

    @Test
    public void deveEncontrarAMesmaPalavraNaDiagonalParaNoroesteENaVerticalParaNorte() {
        CacaPalavras cacaPalavras = new CacaPalavras();
        Tabuleiro tabuleiro8x8 = new Tabuleiro(8,8);
        inserirLetrasNaLinha(tabuleiro8x8, "x x A B E x Q x", 1);
        inserirLetrasNaLinha(tabuleiro8x8, "x x S x E L x x", 2);
        inserirLetrasNaLinha(tabuleiro8x8, "x A A B A x H x", 3);
        inserirLetrasNaLinha(tabuleiro8x8, "x x x H H x x A", 4);
        inserirLetrasNaLinha(tabuleiro8x8, "x R x D L T D x", 5);
        inserirLetrasNaLinha(tabuleiro8x8, "x x x x E E x x", 6);
        inserirLetrasNaLinha(tabuleiro8x8, "x S x x B x B x", 7);
        inserirLetrasNaLinha(tabuleiro8x8, "x x E x A x x A", 8);
        cacaPalavras.setTabuleiro(tabuleiro8x8);
        Palavra abelha = new Palavra();
        abelha.setPalavra("abelha");
        cacaPalavras.getPalavras().add(abelha);
    
        resolver.encontrarPalavrasNoTabuleiro(cacaPalavras);

        // Verifica a quantidade de palavras encontradas
        assertThat(abelha.getLocalizacoes().size()).isEqualTo(2);

        // Como sao dois resultados, sera feito a ordenacao das letras em cada uma das localizacoes
        // encontradas tendo como referencia a ordem da letra
        abelha.getLocalizacoes().get(0)
            .getLocalizacoesLetrasNoTabuleiro().sort(Comparator.comparing(LocalizacaoLetraNoTabuleiro::getOrdem));
        abelha.getLocalizacoes().get(1)
            .getLocalizacoesLetrasNoTabuleiro().sort(Comparator.comparing(LocalizacaoLetraNoTabuleiro::getOrdem));
        List<LocalizacaoLetraNoTabuleiro> localizacaoLetras1 = abelha.getLocalizacoes().get(0).getLocalizacoesLetrasNoTabuleiro();
        List<LocalizacaoLetraNoTabuleiro> localizacaoLetras2 = abelha.getLocalizacoes().get(1).getLocalizacoesLetrasNoTabuleiro();
        
        // Verifica se a quantidade de letras encontradas nos dois casos
        // corresponde com a quantidade de letras na palavra
        assertThat(localizacaoLetras1.size()).isEqualTo(abelha.getPalavra().length());
        assertThat(localizacaoLetras2.size()).isEqualTo(abelha.getPalavra().length());

        // Verifica se localizacaoLetras1 corresponde a primeira localizacao da letra
        // para NOROESTE do contrario localicacaoLetras1 correspondera a para NORTE
        // e então é verificado a posicao de cada letra nos dois resultados
        if(localizacaoLetras1.get(0).getLetra().getPosicao().equals(new Posicao(8,8))){
            // localizaLetras1 = NOROESTE
            assertThat(localizacaoLetras1.get(0).getLetra().getPosicao()).isEqualTo(new Posicao(8,8));
            assertThat(localizacaoLetras1.get(1).getLetra().getPosicao()).isEqualTo(new Posicao(7,7));
            assertThat(localizacaoLetras1.get(2).getLetra().getPosicao()).isEqualTo(new Posicao(6,6));
            assertThat(localizacaoLetras1.get(3).getLetra().getPosicao()).isEqualTo(new Posicao(5,5));
            assertThat(localizacaoLetras1.get(4).getLetra().getPosicao()).isEqualTo(new Posicao(4,4));
            assertThat(localizacaoLetras1.get(5).getLetra().getPosicao()).isEqualTo(new Posicao(3,3));

            // localizaLetras1 = NORTE
            assertThat(localizacaoLetras2.get(0).getLetra().getPosicao()).isEqualTo(new Posicao(5,8));
            assertThat(localizacaoLetras2.get(1).getLetra().getPosicao()).isEqualTo(new Posicao(5,7));
            assertThat(localizacaoLetras2.get(2).getLetra().getPosicao()).isEqualTo(new Posicao(5,6));
            assertThat(localizacaoLetras2.get(3).getLetra().getPosicao()).isEqualTo(new Posicao(5,5));
            assertThat(localizacaoLetras2.get(4).getLetra().getPosicao()).isEqualTo(new Posicao(5,4));
            assertThat(localizacaoLetras2.get(5).getLetra().getPosicao()).isEqualTo(new Posicao(5,3));
        } else {
             // localizaLetras2 = NOROESTE
             assertThat(localizacaoLetras2.get(0).getLetra().getPosicao()).isEqualTo(new Posicao(8,8));
             assertThat(localizacaoLetras2.get(1).getLetra().getPosicao()).isEqualTo(new Posicao(7,7));
             assertThat(localizacaoLetras2.get(2).getLetra().getPosicao()).isEqualTo(new Posicao(6,6));
             assertThat(localizacaoLetras2.get(3).getLetra().getPosicao()).isEqualTo(new Posicao(5,5));
             assertThat(localizacaoLetras2.get(4).getLetra().getPosicao()).isEqualTo(new Posicao(4,4));
             assertThat(localizacaoLetras2.get(5).getLetra().getPosicao()).isEqualTo(new Posicao(3,3));
 
             // localizaLetras1 = NORTE
             assertThat(localizacaoLetras1.get(0).getLetra().getPosicao()).isEqualTo(new Posicao(5,8));
             assertThat(localizacaoLetras1.get(1).getLetra().getPosicao()).isEqualTo(new Posicao(5,7));
             assertThat(localizacaoLetras1.get(2).getLetra().getPosicao()).isEqualTo(new Posicao(5,6));
             assertThat(localizacaoLetras1.get(3).getLetra().getPosicao()).isEqualTo(new Posicao(5,5));
             assertThat(localizacaoLetras1.get(4).getLetra().getPosicao()).isEqualTo(new Posicao(5,4));
             assertThat(localizacaoLetras1.get(5).getLetra().getPosicao()).isEqualTo(new Posicao(5,3));
        }
    }

    @Test
    public void deveEncontrarPalavrasQueFazemParteDeOutraNaHorizontalParaLeste() {
        CacaPalavras cacaPalavras = new CacaPalavras();
        Tabuleiro tabuleiro8x6 = new Tabuleiro(8,6);
        inserirLetrasNaLinha(tabuleiro8x6, "x x x x x x x x", 1);
        inserirLetrasNaLinha(tabuleiro8x6, "x x x x x x x x", 2);
        inserirLetrasNaLinha(tabuleiro8x6, "N A M O R A D A", 3);
        inserirLetrasNaLinha(tabuleiro8x6, "x x x x x x x x", 4);
        inserirLetrasNaLinha(tabuleiro8x6, "x x x x x x x x", 5);
        inserirLetrasNaLinha(tabuleiro8x6, "x x x x x x x x", 6);
        cacaPalavras.setTabuleiro(tabuleiro8x6);
        // namorada
        Palavra namorada = new Palavra();
        namorada.setPalavra("namorada");
        cacaPalavras.getPalavras().add(namorada);
        // amora
        Palavra morada = new Palavra();
        morada.setPalavra("morada");
        cacaPalavras.getPalavras().add(morada);
        // amor
        Palavra amor = new Palavra();
        amor.setPalavra("amor");
        cacaPalavras.getPalavras().add(amor);
    
        resolver.encontrarPalavrasNoTabuleiro(cacaPalavras);

        // Verifica se foi encontrada uma palavra para cada palavra pesquisada
        assertThat(namorada.getLocalizacoes().size()).isEqualTo(1);
        assertThat(morada.getLocalizacoes().size()).isEqualTo(1);
        assertThat(amor.getLocalizacoes().size()).isEqualTo(1);

        // Ordena as letras para cada palavra encontrada tendo como referencia a ordem da letra
        namorada.getLocalizacoes().get(0)
            .getLocalizacoesLetrasNoTabuleiro().sort(Comparator.comparing(LocalizacaoLetraNoTabuleiro::getOrdem));
        morada.getLocalizacoes().get(0)
            .getLocalizacoesLetrasNoTabuleiro().sort(Comparator.comparing(LocalizacaoLetraNoTabuleiro::getOrdem));
        amor.getLocalizacoes().get(0)
            .getLocalizacoesLetrasNoTabuleiro().sort(Comparator.comparing(LocalizacaoLetraNoTabuleiro::getOrdem)); 
        List<LocalizacaoLetraNoTabuleiro> localizacaoNamorada = namorada.getLocalizacoes().get(0).getLocalizacoesLetrasNoTabuleiro();
        List<LocalizacaoLetraNoTabuleiro> localizacaoMorada = morada.getLocalizacoes().get(0).getLocalizacoesLetrasNoTabuleiro();
        List<LocalizacaoLetraNoTabuleiro> localizacaoAmor = amor.getLocalizacoes().get(0).getLocalizacoesLetrasNoTabuleiro();
        
        // Verifica se a quantidade de letras encontradas em cada
        // caso corresponde com a quantidade de letras na palavra
        assertThat(localizacaoNamorada.size()).isEqualTo(namorada.getPalavra().length());
        assertThat(localizacaoMorada.size()).isEqualTo(morada.getPalavra().length());
        assertThat(localizacaoAmor.size()).isEqualTo(amor.getPalavra().length());

        // Verifica a posicao de cada letra para cada palvra encontrada
        // NAMORADA
        assertThat(localizacaoNamorada.get(0).getLetra().getPosicao()).isEqualTo(new Posicao(1,3));
        assertThat(localizacaoNamorada.get(1).getLetra().getPosicao()).isEqualTo(new Posicao(2,3));
        assertThat(localizacaoNamorada.get(2).getLetra().getPosicao()).isEqualTo(new Posicao(3,3));
        assertThat(localizacaoNamorada.get(3).getLetra().getPosicao()).isEqualTo(new Posicao(4,3));
        assertThat(localizacaoNamorada.get(4).getLetra().getPosicao()).isEqualTo(new Posicao(5,3));
        assertThat(localizacaoNamorada.get(5).getLetra().getPosicao()).isEqualTo(new Posicao(6,3));
        assertThat(localizacaoNamorada.get(6).getLetra().getPosicao()).isEqualTo(new Posicao(7,3));
        assertThat(localizacaoNamorada.get(7).getLetra().getPosicao()).isEqualTo(new Posicao(8,3));
        // MORADA
        assertThat(localizacaoMorada.get(0).getLetra().getPosicao()).isEqualTo(new Posicao(3,3));
        assertThat(localizacaoMorada.get(1).getLetra().getPosicao()).isEqualTo(new Posicao(4,3));
        assertThat(localizacaoMorada.get(2).getLetra().getPosicao()).isEqualTo(new Posicao(5,3));
        assertThat(localizacaoMorada.get(3).getLetra().getPosicao()).isEqualTo(new Posicao(6,3));
        assertThat(localizacaoMorada.get(4).getLetra().getPosicao()).isEqualTo(new Posicao(7,3));
        assertThat(localizacaoMorada.get(5).getLetra().getPosicao()).isEqualTo(new Posicao(8,3));
        // AMOR
        assertThat(localizacaoAmor.get(0).getLetra().getPosicao()).isEqualTo(new Posicao(2,3));
        assertThat(localizacaoAmor.get(1).getLetra().getPosicao()).isEqualTo(new Posicao(3,3));
        assertThat(localizacaoAmor.get(2).getLetra().getPosicao()).isEqualTo(new Posicao(4,3));
        assertThat(localizacaoAmor.get(3).getLetra().getPosicao()).isEqualTo(new Posicao(5,3));
    }

    @Test
    public void naoDeveEncontrarNenhumaPalavra() {
        CacaPalavras cacaPalavras = new CacaPalavras();
        Tabuleiro tabuleiro8x6 = new Tabuleiro(8,6);
        inserirLetrasNaLinha(tabuleiro8x6, "x x x x x x x x", 1);
        inserirLetrasNaLinha(tabuleiro8x6, "x x x x x x x x", 2);
        inserirLetrasNaLinha(tabuleiro8x6, "x x x x x x x x", 3);
        inserirLetrasNaLinha(tabuleiro8x6, "x x x x x x x x", 4);
        inserirLetrasNaLinha(tabuleiro8x6, "x x x x x x x x", 5);
        inserirLetrasNaLinha(tabuleiro8x6, "x x x x x x x x", 6);
        cacaPalavras.setTabuleiro(tabuleiro8x6);
        Palavra palavra = new Palavra();
        palavra.setPalavra("palavra");
        cacaPalavras.getPalavras().add(palavra);
    
        resolver.encontrarPalavrasNoTabuleiro(cacaPalavras);

        // Verifica se nao foi encontrado nenhuma palavra
        assertThat(palavra.getLocalizacoes()).isEmpty();
    }

    @Test
    public void naoDeveEncontrarNenhumaPalavraQuandoUmaQuaseCorresponder() {
        CacaPalavras cacaPalavras = new CacaPalavras();
        Tabuleiro tabuleiro8x6 = new Tabuleiro(8,6);
        inserirLetrasNaLinha(tabuleiro8x6, "x x x x x x x x", 1);
        inserirLetrasNaLinha(tabuleiro8x6, "x P A L V R x x", 2);
        inserirLetrasNaLinha(tabuleiro8x6, "A x x x x x A x", 3);
        inserirLetrasNaLinha(tabuleiro8x6, "x R x x x x x x", 4);
        inserirLetrasNaLinha(tabuleiro8x6, "x x V A L A P x", 5);
        inserirLetrasNaLinha(tabuleiro8x6, "A R x x x x x x", 6);
        cacaPalavras.setTabuleiro(tabuleiro8x6);
        Palavra palavra = new Palavra();
        palavra.setPalavra("palavra");
        cacaPalavras.getPalavras().add(palavra);
    
        resolver.encontrarPalavrasNoTabuleiro(cacaPalavras);

        // Verifica se nao foi encontrado nenhuma palavra
        assertThat(palavra.getLocalizacoes()).isEmpty();
    }

}
