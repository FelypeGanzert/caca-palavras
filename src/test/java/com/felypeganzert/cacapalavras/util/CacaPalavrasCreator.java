package com.felypeganzert.cacapalavras.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.LocalizacaoLetraNoTabuleiro;
import com.felypeganzert.cacapalavras.entidades.LocalizacaoPalavraNoTabuleiro;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;

public class CacaPalavrasCreator {

    public static CacaPalavras criarComPalavrasLocalizadas() {
        CacaPalavras cacaPalavras = new CacaPalavras();
        cacaPalavras.setTabuleiro(criarTabuleiroComLetras());
        cacaPalavras.setPalavras(criaPalavrasComLocalizacoesNoTabuleiro());
        return cacaPalavras;
    }

    public static CacaPalavras criarComPalavrasNaoLocalizadas() {
        CacaPalavras cacaPalavras = new CacaPalavras();
        cacaPalavras.setTabuleiro(criarTabuleiroComLetras());
        cacaPalavras.setPalavras(criaPalavrasSemLocalizacoesNoTabuleiro());
        return cacaPalavras;
    }

    public static CacaPalavras criarComPalavrasNaoLocalizadasESemLetrasNoTabuleiro() {
        CacaPalavras cacaPalavras = new CacaPalavras();
        cacaPalavras.setTabuleiro(criarTabuleiroSemLetras());
        cacaPalavras.setPalavras(criaPalavrasSemLocalizacoesNoTabuleiro());
        return cacaPalavras;
    }

    private static Tabuleiro criarTabuleiroComLetras() {
        // ====== TABULEIRO ======
        // - 1 2 3 4 5 6
        // 1 L l b o a Z
        // 2 U j d l I e
        // 3 A S O L t j
        // 4 r o E a u l
        // 5 k F p n z u
        // 6 a l O L O B
        // ====== PALAVRAS ======
        // LUA: (1,1), (1,2), (1,3)
        // SOL: (2,3), (3,3), (4,3)
        // FELIZ: (2,5), (3,4), (4,3), (5,2), (6,1)
        // BOLO: (6,6), (5,6), (4,6), (3,6)
        // ======
        // Cria Tabuleiro 6 x 6
        Tabuleiro tabuleiro = new Tabuleiro(1, 6, 6);
        inserirLetrasNaLinha(tabuleiro, "L l b o a Z", 1);
        inserirLetrasNaLinha(tabuleiro, "U j d l I e", 2);
        inserirLetrasNaLinha(tabuleiro, "A S O L t j", 3);
        inserirLetrasNaLinha(tabuleiro, "r o E a u l", 4);
        inserirLetrasNaLinha(tabuleiro, "k F p n z u", 5);
        inserirLetrasNaLinha(tabuleiro, "a l O L O B", 6);
        return tabuleiro;
    }

    public static void inserirLetrasNaLinha(Tabuleiro tabuleiro,String letrasDaLinha, int linha){
        String[] letras = letrasDaLinha.split(" ");
        for(int x = 1; x <= letras.length; x++){
                Letra letra = new Letra(letras[x-1].charAt(0), new Posicao(x, linha));
                adicionarLetra(tabuleiro, letra);
        }
    }

    private static void adicionarLetra(Tabuleiro tabuleiro, Letra letra) {
        validarPosicaoNoTabuleiro(tabuleiro, letra.getPosicao());
        limparPosicaoNoTabuleiro(tabuleiro, letra.getPosicao());
        tabuleiro.getLetras().add(letra);
    }

    private static void validarPosicaoNoTabuleiro(Tabuleiro tabuleiro, Posicao posicao) {
        if (tabuleiro.posicaoNaoExiste(posicao)) {
                throw new IllegalStateException("Posição " + posicao.getPosicaoCartesiana() + " não existe no tabuleiro");
        }
    }

    private static void limparPosicaoNoTabuleiro(Tabuleiro tabuleiro, Posicao posicao) {
        tabuleiro.getLetras().removeIf(l -> isLetraNaPosicao(l, posicao));
    }

    private static boolean isLetraNaPosicao(Letra letra, Posicao posicao) {
        Posicao posicaoLetra = letra.getPosicao();
        return posicaoLetra.equals(posicao);
    }

    private static Tabuleiro criarTabuleiroSemLetras() {
        // Cria Tabuleiro 6 x 6
        Tabuleiro tabuleiro = new Tabuleiro(1, 6, 6);
        return tabuleiro;
    }

    private static List<Palavra> criaPalavrasSemLocalizacoesNoTabuleiro() {
        Palavra lua = new Palavra();
        lua.setPalavra("lua");
        Palavra sol = new Palavra();
        sol.setPalavra("sol");
        Palavra feliz = new Palavra();
        feliz.setPalavra("feliz");
        Palavra bolo = new Palavra();
        bolo.setPalavra("bolo");

        List<Palavra> palavras = new ArrayList<>();
        palavras.addAll(Arrays.asList(lua, sol, feliz, bolo));

        return palavras;
    }

    private static List<Palavra> criaPalavrasComLocalizacoesNoTabuleiro() {
        // LUA
        Palavra lua = new Palavra();
        lua.setPalavra("lua");
        LocalizacaoPalavraNoTabuleiro localizao1Lua = new LocalizacaoPalavraNoTabuleiro();
        localizao1Lua.getLocalizacoesLetrasNoTabuleiro()
                .add(LocalizacaoLetraNoTabuleiro.builder().ordem(1).posicao(new Posicao(1, 1)).build());
        localizao1Lua.getLocalizacoesLetrasNoTabuleiro()
                .add(LocalizacaoLetraNoTabuleiro.builder().ordem(2).posicao(new Posicao(1, 2)).build());
        localizao1Lua.getLocalizacoesLetrasNoTabuleiro()
                .add(LocalizacaoLetraNoTabuleiro.builder().ordem(3).posicao(new Posicao(1, 3)).build());
        lua.getLocalizacoesNoTabuleiro().add(localizao1Lua);

        // SOL
        Palavra sol = new Palavra();
        sol.setPalavra("sol");
        LocalizacaoPalavraNoTabuleiro localizao1Sol = new LocalizacaoPalavraNoTabuleiro();
        localizao1Sol.getLocalizacoesLetrasNoTabuleiro()
                .add(LocalizacaoLetraNoTabuleiro.builder().ordem(1).posicao(new Posicao(2, 3)).build());
        localizao1Sol.getLocalizacoesLetrasNoTabuleiro()
                .add(LocalizacaoLetraNoTabuleiro.builder().ordem(2).posicao(new Posicao(3, 3)).build());
        localizao1Sol.getLocalizacoesLetrasNoTabuleiro()
                .add(LocalizacaoLetraNoTabuleiro.builder().ordem(3).posicao(new Posicao(4, 3)).build());
        sol.getLocalizacoesNoTabuleiro().add(localizao1Sol);

        // FELIZ
        Palavra feliz = new Palavra();
        feliz.setPalavra("feliz");
        LocalizacaoPalavraNoTabuleiro localizao1Feliz = new LocalizacaoPalavraNoTabuleiro();
        localizao1Feliz.getLocalizacoesLetrasNoTabuleiro()
                .add(LocalizacaoLetraNoTabuleiro.builder().ordem(1).posicao(new Posicao(2, 5)).build());
        localizao1Feliz.getLocalizacoesLetrasNoTabuleiro()
                .add(LocalizacaoLetraNoTabuleiro.builder().ordem(2).posicao(new Posicao(3, 4)).build());
        localizao1Feliz.getLocalizacoesLetrasNoTabuleiro()
                .add(LocalizacaoLetraNoTabuleiro.builder().ordem(3).posicao(new Posicao(4, 3)).build());
        localizao1Feliz.getLocalizacoesLetrasNoTabuleiro()
                .add(LocalizacaoLetraNoTabuleiro.builder().ordem(4).posicao(new Posicao(5, 2)).build());
        localizao1Feliz.getLocalizacoesLetrasNoTabuleiro()
                .add(LocalizacaoLetraNoTabuleiro.builder().ordem(5).posicao(new Posicao(6, 1)).build());
        feliz.getLocalizacoesNoTabuleiro().add(localizao1Feliz);

        // BOLO
        Palavra bolo = new Palavra();
        bolo.setPalavra("bolo");
        LocalizacaoPalavraNoTabuleiro localizao1Bolo = new LocalizacaoPalavraNoTabuleiro();
        localizao1Bolo.getLocalizacoesLetrasNoTabuleiro()
                .add(LocalizacaoLetraNoTabuleiro.builder().ordem(1).posicao(new Posicao(6, 6)).build());
        localizao1Bolo.getLocalizacoesLetrasNoTabuleiro()
                .add(LocalizacaoLetraNoTabuleiro.builder().ordem(2).posicao(new Posicao(5, 6)).build());
        localizao1Bolo.getLocalizacoesLetrasNoTabuleiro()
                .add(LocalizacaoLetraNoTabuleiro.builder().ordem(3).posicao(new Posicao(4, 6)).build());
        localizao1Bolo.getLocalizacoesLetrasNoTabuleiro()
                .add(LocalizacaoLetraNoTabuleiro.builder().ordem(4).posicao(new Posicao(3, 6)).build());
        bolo.getLocalizacoesNoTabuleiro().add(localizao1Bolo);

        List<Palavra> palavras = new ArrayList<>();
        palavras.addAll(Arrays.asList(lua, sol, feliz, bolo));

        return palavras;
    }

}
