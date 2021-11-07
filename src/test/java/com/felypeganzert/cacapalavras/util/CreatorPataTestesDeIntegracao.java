package com.felypeganzert.cacapalavras.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;

public class CreatorPataTestesDeIntegracao {

    public static CacaPalavras cacaPalavras() {
        CacaPalavras cacaPalavras = new CacaPalavras();
        cacaPalavras.setCriador("Criador");
        cacaPalavras.setTitulo("Título");
        cacaPalavras.setDataCriacao(LocalDateTime.now());
        return cacaPalavras;
    }

    public static CacaPalavras cacaPalavrasComPalavras(){
        CacaPalavras cacaPalavras = cacaPalavras();
        cacaPalavras.setPalavras(palavras(cacaPalavras));
        return cacaPalavras;
    }

    private static List<Palavra> palavras(CacaPalavras cacaPalavras) {
        Palavra lua = criarPalavraValida(cacaPalavras, "lua");
        Palavra sol = criarPalavraValida(cacaPalavras, "sol");
        Palavra feliz = criarPalavraValida(cacaPalavras, "felIZ");
        Palavra bolo = criarPalavraValida(cacaPalavras, "BOLO");
        Palavra solzinho = criarPalavraValida(cacaPalavras, "Solzinho");
        Palavra amorzinho = criarPalavraValida(cacaPalavras, "amorzinho");

        List<Palavra> palavras = new ArrayList<Palavra>();
        palavras.addAll(Arrays.asList(lua, sol, feliz, bolo, solzinho, amorzinho));
        return palavras;
    }

    private static Palavra criarPalavraValida(CacaPalavras c, String p){
        return Palavra.builder().cacaPalavras(c).palavra(p).build();
    }

    public static CacaPalavras cacaPalavrasComTabuleiro(int largura, int altura) {
        CacaPalavras cacaPalavras = cacaPalavras();
        cacaPalavras.setTabuleiro(tabuleiro(cacaPalavras, largura, altura));
        return cacaPalavras;
    }
    
    public static CacaPalavras cacaPalavrasComPalavrasEComTabuleiro(int largura, int altura) {
        CacaPalavras cacaPalavras = cacaPalavrasComPalavras();
        cacaPalavras.setTabuleiro(tabuleiro(cacaPalavras, largura, altura));
        return cacaPalavras;
    }

    private static Tabuleiro tabuleiro(CacaPalavras cacaPalavras, int largura, int altura){
        Tabuleiro tabuleiro = new Tabuleiro(largura, altura);
        tabuleiro.setCacaPalavras(cacaPalavras);
        cacaPalavras.setTabuleiro(tabuleiro);
        return tabuleiro;
    }

    public static CacaPalavras cacaPalavrasComTabuleiroEComTodasAsLetras() {
        CacaPalavras cacaPalavras = cacaPalavras();
        cacaPalavras.setTabuleiro(tabuleiroComTodasAsLetras(cacaPalavras));
        return cacaPalavras;
    }

    public static CacaPalavras cacaPalavrasComPalavrasEComTabuleiroEComTodasAsLetras() {
        CacaPalavras cacaPalavras = cacaPalavrasComPalavras();
        cacaPalavras.setTabuleiro(tabuleiroComTodasAsLetras(cacaPalavras));
        return cacaPalavras;
    }

    private static Tabuleiro tabuleiroComTodasAsLetras(CacaPalavras cacaPalavras) {
        // ====== TABULEIRO ======
        // x 1 2 3 4 5 6
        // 1 L x x x x Z
        // 2 U x x x I x
        // 3 A S O L x S
        // 4 x x E O x O
        // 5 x F x S x L
        // 6 x x O L O B
        // ====== PALAVRAS ======
        // LUA: {(1,1), (1,2), (1,3)}
        // SOL: {(2,3), (3,3), (4,3)} {(4,5), (4,4), (4,3)} {(6,3), (6,4), (6,5)}
        // FELIZ: (2,5), (3,4), (4,3), (5,2), (6,1)
        // BOLO: (6,6), (5,6), (4,6), (3,6)
        // Solzinho
        // Amorzinho
        // ======
        // Cria Tabuleiro 6 x 6
        Tabuleiro tabuleiro = new Tabuleiro(6, 6);
        tabuleiro.setCacaPalavras(cacaPalavras);
        inserirLetrasNaLinha(tabuleiro, "L l b l u Z", 1);
        inserirLetrasNaLinha(tabuleiro, "U j d l I e", 2);
        inserirLetrasNaLinha(tabuleiro, "A S O L t S", 3);
        inserirLetrasNaLinha(tabuleiro, "r o E O u O", 4);
        inserirLetrasNaLinha(tabuleiro, "k F p S z L", 5);
        inserirLetrasNaLinha(tabuleiro, "a l O L O B", 6);
        return tabuleiro;
    }

    public static CacaPalavras cacaPalavrasComTabuleiroEComQuaseTodasAsLetras() {
        CacaPalavras cacaPalavras = cacaPalavras();
        cacaPalavras.setTabuleiro(tabuleiroComQuaseTodasAsLetras(cacaPalavras));
        return cacaPalavras;
    }

    public static CacaPalavras cacaPalavrasComPalavrasEComTabuleiroEComQuaseTodasAsLetras() {
        CacaPalavras cacaPalavras = cacaPalavrasComPalavras();
        cacaPalavras.setTabuleiro(tabuleiroComQuaseTodasAsLetras(cacaPalavras));
        return cacaPalavras;
    }

    private static Tabuleiro tabuleiroComQuaseTodasAsLetras(CacaPalavras cacaPalavras) {
        // ====== TABULEIRO ======
        // x 1 2 3 4 5 6
        // 1 L x x x x Z
        // 2 U x x x I x
        // 3 A S O L x S
        // 4 x x E O x O
        // 5 x F x S x L
        // 6 x x O L O B
        // ====== PALAVRAS ======
        // LUA: {(1,1), (1,2), (1,3)}
        // SOL: {(2,3), (3,3), (4,3)} {(4,5), (4,4), (4,3)} {(6,3), (6,4), (6,5)}
        // FELIZ: (2,5), (3,4), (4,3), (5,2), (6,1)
        // BOLO: (6,6), (5,6), (4,6), (3,6)
        // Solzinho
        // Amorzinho
        // ======
        // Cria Tabuleiro 6 x 6
        // Total de letras (com @) = 36
        // Total de @ = 7
        // Total de letras (sem @): 29
        Tabuleiro tabuleiro = new Tabuleiro(6, 6);
        tabuleiro.setCacaPalavras(cacaPalavras);
        inserirLetrasNaLinha(tabuleiro, "L l @ l u Z", 1);
        inserirLetrasNaLinha(tabuleiro, "U j @ l I e", 2);
        inserirLetrasNaLinha(tabuleiro, "A S O L @ S", 3);
        inserirLetrasNaLinha(tabuleiro, "r o E O u O", 4);
        inserirLetrasNaLinha(tabuleiro, "@ F @ S @ L", 5);
        inserirLetrasNaLinha(tabuleiro, "a @ O L O B", 6);
        return tabuleiro;
    }
    
    // Caso seja passado um char com o valor de @ ele não cria uma Letra
    private static void inserirLetrasNaLinha(Tabuleiro tabuleiro, String letrasDaLinha, int linha){
        String[] letras = letrasDaLinha.split(" ");
        for(int x = 1; x <= letras.length; x++){
            char letraChar = letras[x-1].charAt(0);
            if(letraChar != '@'){
                Letra letra = new Letra(letraChar, new Posicao(x, linha));
                letra.setTabuleiro(tabuleiro);
                tabuleiro.getLetras().add(letra);
            }
        }
    }
    
}
