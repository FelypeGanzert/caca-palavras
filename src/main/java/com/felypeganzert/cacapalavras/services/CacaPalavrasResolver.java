package com.felypeganzert.cacapalavras.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Direcao;
import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.LocalizacaoLetraNoTabuleiro;
import com.felypeganzert.cacapalavras.entidades.LocalizacaoPalavraNoTabuleiro;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;

import lombok.Getter;
import lombok.Setter;

public class CacaPalavrasResolver {

    @Getter
    @Setter
    private CacaPalavras cacaPalavras;

    private List<Palavra> palavrasQueIniciamComALetraInicial = new ArrayList<Palavra>();

    private List<Palavra> palavrasPossiveisQueIniciamComPalavraFormada = new ArrayList<Palavra>();

    private List<LocalizacaoLetraNoTabuleiro> localizacoesLetrasEncontradas = new ArrayList<LocalizacaoLetraNoTabuleiro>();

    private static Logger log = Logger.getLogger("CacaPalavrasResolver");

    public CacaPalavrasResolver(CacaPalavras cacaPalavras) {
        this.cacaPalavras = cacaPalavras;
    }

    public void encontrarPalavrasNoTabuleiro() {

        for (int y = 1; y <= getTabuleiro().getAltura(); y++) {
            for (int x = 1; x <= getTabuleiro().getLargura(); x++) {
                palavrasQueIniciamComALetraInicial = cacaPalavras.getPalavras();
                Posicao posicao = new Posicao(x, y);
                procurarAPartirDaPosicao(posicao);
            }
        }

        alterarParaMaiusculasLetrasDePalavrasEncontradas();
    }

    private Tabuleiro getTabuleiro() {
        return cacaPalavras.getTabuleiro();
    }

    private void procurarAPartirDaPosicao(Posicao posicao) {
        Tabuleiro tabuleiro = cacaPalavras.getTabuleiro();
        String letraInicialAtual = tabuleiro.getLetraDaPosicao(posicao).getLetra();

        palavrasQueIniciamComALetraInicial = filtrarPalavrasQueComecamCom(palavrasQueIniciamComALetraInicial,
                letraInicialAtual);

        for(Direcao d : Direcao.values()){
            localizacoesLetrasEncontradas.clear();
            int ordemLetra = 1;
            localizacoesLetrasEncontradas.add(new LocalizacaoLetraNoTabuleiro(0L, ordemLetra, posicao));
            pesquisarEmDirecao(posicao, ordemLetra, letraInicialAtual, d);
        }
    
    }

    private List<Palavra> filtrarPalavrasQueComecamCom(List<Palavra> palavras, String texto) {
        return palavras.stream().filter(p -> p.getPalavra().toUpperCase().startsWith(texto.toUpperCase()))
                .collect(Collectors.toList());
    }

    private void pesquisarEmDirecao(Posicao posicao, int ordemLetra, String palavraFormada, Direcao direcao) {
        try {
            Posicao posicaoNova = gerarPosicaoAvancandoParaDirecao(posicao, direcao);
            ordemLetra += 1;
            localizacoesLetrasEncontradas.add(new LocalizacaoLetraNoTabuleiro(0L, ordemLetra, posicaoNova));

            palavraFormada += getTabuleiro().getLetraDaPosicao(posicaoNova).getLetra();

            palavrasPossiveisQueIniciamComPalavraFormada = filtrarPalavrasQueComecamCom(
                    palavrasQueIniciamComALetraInicial, palavraFormada);

            tratarPalavrasQueForamEncontradasPorCompleto(palavrasPossiveisQueIniciamComPalavraFormada, palavraFormada);

            if(!palavrasPossiveisQueIniciamComPalavraFormada.isEmpty()){
                pesquisarEmDirecao(posicaoNova, ordemLetra, palavraFormada, direcao);
            }

        } catch (IllegalArgumentException e) {

        }

    }

    private Posicao gerarPosicaoAvancandoParaDirecao(Posicao posicao, Direcao direcao) {
        int x = posicao.getX() + direcao.getX();
        int y = posicao.getY() + direcao.getY();
        return new Posicao(x, y);
    }

    private void tratarPalavrasQueForamEncontradasPorCompleto(List<Palavra> palavras, String palavraFormada) {
        List<Palavra> palavrasEncontradasPorCompleto = palavras.stream()
                .filter(p -> p.getPalavra().equalsIgnoreCase(palavraFormada)).collect(Collectors.toList());

        palavrasEncontradasPorCompleto.forEach(p -> {
            LocalizacaoPalavraNoTabuleiro localizacao = new LocalizacaoPalavraNoTabuleiro();
            localizacoesLetrasEncontradas.forEach(l -> {
                localizacao.getLocalizacoesLetrasNoTabuleiro().add(new LocalizacaoLetraNoTabuleiro(l));
            });
            p.getLocalizacoesNoTabuleiro().add(localizacao);
        });

        palavras.removeAll(palavrasEncontradasPorCompleto);
    }

    private void alterarParaMaiusculasLetrasDePalavrasEncontradas(){
        cacaPalavras.getPalavras().forEach(p -> {
            p.getLocalizacoesNoTabuleiro().forEach(loc -> {
                loc.getLocalizacoesLetrasNoTabuleiro().forEach(locLetra ->{
                    Letra letra = getTabuleiro().getLetraDaPosicao(locLetra.getPosicao());
                    letra.setLetra(letra.getLetra().toUpperCase());
                });
            });
        });
    }

    public void printarTabuleiro(Tabuleiro tabuleiro) {
        String tabuleiroString = "\n\n";
        for (int y = 1; y <= tabuleiro.getAltura(); y++) {
            for (int x = 1; x <= tabuleiro.getLargura(); x++) {
                tabuleiroString += tabuleiro.getLetraDaPosicao(new Posicao(x, y)).getLetra() + " ";
            }
            tabuleiroString += "\n";
        }
        tabuleiroString += "\n\n";
        log.info(tabuleiroString);
    }

}
