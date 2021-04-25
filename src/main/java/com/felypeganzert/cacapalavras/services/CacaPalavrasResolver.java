package com.felypeganzert.cacapalavras.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Direcao;
import com.felypeganzert.cacapalavras.entidades.LocalizacaoLetraNoTabuleiro;
import com.felypeganzert.cacapalavras.entidades.LocalizacaoPalavraNoTabuleiro;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;

public class CacaPalavrasResolver {

    private CacaPalavras cacaPalavras;
    private List<LocalizacaoLetraNoTabuleiro> localizacoesLetrasEncontradas = new ArrayList<LocalizacaoLetraNoTabuleiro>();

    public CacaPalavrasResolver(CacaPalavras cacaPalavras) {
        this.cacaPalavras = cacaPalavras;
    }

    public void encontrarPalavrasNoTabuleiro() {
        for (int y = 1; y <= getTabuleiro().getAltura(); y++) {
            for (int x = 1; x <= getTabuleiro().getLargura(); x++) {
                Posicao posicao = new Posicao(x, y);
                procurarAPartirDaPosicao(posicao);
            }
        }
    }

    private Tabuleiro getTabuleiro() {
        return this.cacaPalavras.getTabuleiro();
    }

    private void procurarAPartirDaPosicao(Posicao posicao) {
        Tabuleiro tabuleiro = getTabuleiro();
        String letraInicial = tabuleiro.getLetraDaPosicao(posicao).getLetra();

        List<Palavra> todasAsPalavras = this.cacaPalavras.getPalavras();

        for (Direcao d : Direcao.values()) {
            List<Palavra> palavrasPossiveis = filtrarPalavrasQueComecamCom(todasAsPalavras, letraInicial);
            this.localizacoesLetrasEncontradas.clear();
            int ordemLetra = 1;
            this.localizacoesLetrasEncontradas.add(new LocalizacaoLetraNoTabuleiro(ordemLetra, posicao));
            pesquisarEmDirecao(palavrasPossiveis, posicao, ordemLetra, letraInicial, d);
        }
    }

    private void pesquisarEmDirecao(List<Palavra> palavrasPossiveis, Posicao posicao, int ordemLetra, String palavra,
            Direcao direcao) {
        try {
            Posicao posicaoNova = gerarPosicaoAvancandoParaDirecao(posicao, direcao);
            ordemLetra++;
            this.localizacoesLetrasEncontradas.add(new LocalizacaoLetraNoTabuleiro(ordemLetra, posicaoNova));

            palavra += getTabuleiro().getLetraDaPosicao(posicaoNova).getLetra();
            palavrasPossiveis = filtrarPalavrasQueComecamCom(palavrasPossiveis, palavra);

            tratarPalavrasEncontradasPorCompleto(palavrasPossiveis, palavra);

            if (!palavrasPossiveis.isEmpty()) {
                pesquisarEmDirecao(palavrasPossiveis, posicaoNova, ordemLetra, palavra, direcao);
            }
        } catch (IllegalArgumentException e) {
            // É esperado que seja lançado IllegalArgumentException ao tentar
            // pegar uma letra de uma posição inexistene no tabuleiro
        }
    }

    private List<Palavra> filtrarPalavrasQueComecamCom(List<Palavra> palavras, String texto) {
        return palavras.stream().filter(p -> palavraComecaCom(p, texto)).collect(Collectors.toList());
    }

    private boolean palavraComecaCom(Palavra palavra, String texto) {
        return palavra.getPalavra().toUpperCase().startsWith(texto.toUpperCase());
    }

    private Posicao gerarPosicaoAvancandoParaDirecao(Posicao posicao, Direcao direcao) {
        int x = posicao.getX() + direcao.getX();
        int y = posicao.getY() + direcao.getY();
        return new Posicao(x, y);
    }

    private void tratarPalavrasEncontradasPorCompleto(List<Palavra> palavras, String palavra) {
        List<Palavra> palavrasEncontradasPorCompleto = palavras.stream().filter(p -> isPalavraIgualA(p, palavra))
                .collect(Collectors.toList());

        palavrasEncontradasPorCompleto.forEach(p -> {
            LocalizacaoPalavraNoTabuleiro localizacaoPalavra = new LocalizacaoPalavraNoTabuleiro();
            this.localizacoesLetrasEncontradas.forEach(l -> {
                localizacaoPalavra.getLocalizacoesLetrasNoTabuleiro().add(new LocalizacaoLetraNoTabuleiro(l));
            });
            p.getLocalizacoesNoTabuleiro().add(localizacaoPalavra);
        });

        palavras.removeAll(palavrasEncontradasPorCompleto);
    }

    private boolean isPalavraIgualA(Palavra palavra, String texto) {
        return palavra.getPalavra().equalsIgnoreCase(texto);
    }

}
