package com.felypeganzert.cacapalavras.util;

import java.util.ArrayList;
import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.LocalizacaoLetraNoTabuleiro;
import com.felypeganzert.cacapalavras.entidades.LocalizacaoPalavraNoTabuleiro;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.services.TabuleiroService;

public class CacaPalavrasCreator {

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

    public static CacaPalavras criarComPalavrasLocalizadas(TabuleiroService tabuleiroService) {
        CacaPalavras cacaPalavras = new CacaPalavras();

        cacaPalavras.setTabuleiro(criarTabuleiroComLetras(tabuleiroService));
        cacaPalavras.setPalavras(criaPalavrasComLocalizacoesNoTabuleiro());

        return cacaPalavras;
    }

    public static CacaPalavras criarComPalavrasNaoLocalizadas(TabuleiroService tabuleiroService) {
        CacaPalavras cacaPalavras = new CacaPalavras();

        cacaPalavras.setTabuleiro(criarTabuleiroComLetras(tabuleiroService));
        cacaPalavras.setPalavras(criaPalavrasSemLocalizacoesNoTabuleiro());

        return cacaPalavras;
    }

    public static CacaPalavras criarComPalavrasNaoLocalizadasESemLetrasNoTabuleiro(TabuleiroService tabuleiroService) {
        CacaPalavras cacaPalavras = new CacaPalavras();

        cacaPalavras.setTabuleiro(criarTabuleiroSemLetras());
        cacaPalavras.setPalavras(criaPalavrasSemLocalizacoesNoTabuleiro());

        return cacaPalavras;
    }

    private static Tabuleiro criarTabuleiroComLetras(TabuleiroService tabuleiroService) {
        // Cria Tabuleiro 6 x 6
        Tabuleiro tabuleiro = new Tabuleiro(1L, 6, 6);
        // Coluna 1
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('l', new Posicao(1, 1)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('u', new Posicao(1, 2)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('a', new Posicao(1, 3)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('r', new Posicao(1, 4)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('k', new Posicao(1, 5)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('a', new Posicao(1, 6)));
        // Coluna 2
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('l', new Posicao(2, 1)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('j', new Posicao(2, 2)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('s', new Posicao(2, 3)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('o', new Posicao(2, 4)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('f', new Posicao(2, 5)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('l', new Posicao(2, 6)));
        // Coluna 3
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('b', new Posicao(3, 1)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('d', new Posicao(3, 2)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('o', new Posicao(3, 3)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('e', new Posicao(3, 4)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('p', new Posicao(3, 5)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('o', new Posicao(3, 6)));
        // Coluna 4
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('o', new Posicao(4, 1)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('l', new Posicao(4, 2)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('l', new Posicao(4, 3)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('a', new Posicao(4, 4)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('n', new Posicao(4, 5)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('l', new Posicao(4, 6)));
        // Coluna 5
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('a', new Posicao(5, 1)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('i', new Posicao(5, 2)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('t', new Posicao(5, 3)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('u', new Posicao(5, 4)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('z', new Posicao(5, 5)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('o', new Posicao(5, 6)));
        // Coluna 6
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('a', new Posicao(6, 1)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('e', new Posicao(6, 2)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('j', new Posicao(6, 3)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('l', new Posicao(6, 4)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('u', new Posicao(6, 5)));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, new Letra('b', new Posicao(6, 6)));

        return tabuleiro;
    }

    private static Tabuleiro criarTabuleiroSemLetras() {
        // Cria Tabuleiro 6 x 6
        Tabuleiro tabuleiro = new Tabuleiro(1L, 6, 6);
        return tabuleiro;
    }

    private static List<Palavra> criaPalavrasSemLocalizacoesNoTabuleiro() {
        // LUA
        Palavra lua = new Palavra();
        lua.setPalavra("lua");

        // SOL
        Palavra sol = new Palavra();
        sol.setPalavra("sol");

        // FELIZ
        Palavra feliz = new Palavra();
        feliz.setPalavra("feliz");

        // BOLO
        Palavra bolo = new Palavra();
        bolo.setPalavra("bolo");

        List<Palavra> palavras = new ArrayList<>();
        palavras.add(lua);
        palavras.add(sol);
        palavras.add(feliz);
        palavras.add(bolo);

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
        palavras.add(lua);
        palavras.add(sol);
        palavras.add(feliz);
        palavras.add(bolo);

        return palavras;
    }

}
