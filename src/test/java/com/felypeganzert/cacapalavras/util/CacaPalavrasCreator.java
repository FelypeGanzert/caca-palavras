package com.felypeganzert.cacapalavras.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.LocalizacaoLetraNoTabuleiro;
import com.felypeganzert.cacapalavras.entidades.LocalizacaoPalavra;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;

public class CacaPalavrasCreator {

    public static CacaPalavras criarCacaPalavrasValido(Integer id) {
        CacaPalavras cacaPalavras = new CacaPalavras();
        cacaPalavras.setCriador("Criador");
        cacaPalavras.setTitulo("Título");
        cacaPalavras.setDataCriacao(LocalDateTime.now());
        cacaPalavras.setId(id);
        return cacaPalavras;
    }

    public static Tabuleiro criarTabuleiroValido(Integer id, CacaPalavras cacaPalavras) {
        Tabuleiro tabuleiro = new Tabuleiro(id, Tabuleiro.LARGURA_MINIMA, Tabuleiro.ALTURA_MINIMA);
        tabuleiro.setCacaPalavras(cacaPalavras);
        cacaPalavras.setTabuleiro(tabuleiro);
        return tabuleiro;
    }

    public static CacaPalavras criarComPalavrasEComLetrasNoTabuleiro() {
        CacaPalavras cacaPalavras = criarCacaPalavrasValido(1);
        cacaPalavras.setTabuleiro(criarTabuleiroComLetras(1, cacaPalavras));
        cacaPalavras.setPalavras(criaPalavrasSemLocalizacoesNoTabuleiro(cacaPalavras));
        return cacaPalavras;
    }

    public static CacaPalavras criarComPalavrasNaoLocalizadasESemLetrasNoTabuleiro() {
        CacaPalavras cacaPalavras = criarCacaPalavrasValido(0);
        cacaPalavras.setTabuleiro(criarTabuleiroSemLetras(1, cacaPalavras));
        cacaPalavras.setPalavras(criaPalavrasSemLocalizacoesNoTabuleiro(cacaPalavras));
        return cacaPalavras;
    }

    private static Tabuleiro criarTabuleiroComLetras(Integer id, CacaPalavras cacaPalavras) {
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
        Tabuleiro tabuleiro = new Tabuleiro(id, 6, 6);
        tabuleiro.setCacaPalavras(cacaPalavras);
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
                //letra.setId(id_letra++);
                letra.setTabuleiro(tabuleiro);
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

    private static Tabuleiro criarTabuleiroSemLetras(Integer id, CacaPalavras cacaPalavras) {
        // Cria Tabuleiro 6 x 6
        Tabuleiro tabuleiro = new Tabuleiro(id, 6, 6);
        tabuleiro.setCacaPalavras(cacaPalavras);
        return tabuleiro;
    }

    private static List<Palavra> criaPalavrasSemLocalizacoesNoTabuleiro(CacaPalavras cacaPalavras) {
        Palavra lua = criarPalavraValida(cacaPalavras, "lua");
        Palavra sol = criarPalavraValida(cacaPalavras, "sol");
        Palavra feliz = criarPalavraValida(cacaPalavras, "feliz");
        Palavra bolo = criarPalavraValida(cacaPalavras, "bolo");

        List<Palavra> palavras = new ArrayList<>();
        palavras.addAll(Arrays.asList(lua, sol, feliz, bolo));

        return palavras;
    }

    public static Palavra criarPalavraValida(CacaPalavras c, String p){
        return Palavra.builder().cacaPalavras(c).palavra(p).build();
    }

    public static LocalizacaoPalavra criarLocalizacaoPalavraValida(Palavra p){
        return LocalizacaoPalavra.builder().palavra(p).build();
    }

    public static LocalizacaoLetraNoTabuleiro criarLocalizacaoLetraValida(int ordem, Letra letra, LocalizacaoPalavra localizacaoPalavra){
        return LocalizacaoLetraNoTabuleiro
                    .builder()
                    .ordem(ordem)
                    .letra(letra)
                    .localizacaoPalavra(localizacaoPalavra)
                    .build();
    }

}
