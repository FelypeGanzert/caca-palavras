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

    private String getLetraDaPosicao(Posicao posicao){
        return getTabuleiro().getLetraDaPosicao(posicao).getLetra();
    }

    private void procurarAPartirDaPosicao(Posicao posicaoDePartida) {
        String letraInicial = getLetraDaPosicao(posicaoDePartida);
        List<Palavra> todasAsPalavras = this.cacaPalavras.getPalavras();

        for (Direcao d : Direcao.values()) {
            List<Palavra> palavrasPossiveis = filtrarPalavrasQueComecamCom(todasAsPalavras, letraInicial);
            this.localizacoesLetrasEncontradas.clear();
            adicionarPosicaoLetraEncontrada(posicaoDePartida);
            pesquisarEmDirecao(palavrasPossiveis, posicaoDePartida, letraInicial, d);
        }
    }

    private List<Palavra> filtrarPalavrasQueComecamCom(List<Palavra> palavras, String palavraFormada) {
        return palavras.stream().filter(p -> palavraComecaCom(p, palavraFormada)).collect(Collectors.toList());
    }

    private boolean palavraComecaCom(Palavra palavra, String palavraFormada) {
        return palavra.getPalavra().toUpperCase().startsWith(palavraFormada.toUpperCase());
    }

    private void adicionarPosicaoLetraEncontrada(Posicao posicao){
        int ordemLetra = localizacoesLetrasEncontradas.size() + 1;
        this.localizacoesLetrasEncontradas.add(new LocalizacaoLetraNoTabuleiro(ordemLetra, posicao));
    }

    private void pesquisarEmDirecao(List<Palavra> palavrasPossiveis, Posicao posicao, String palavraFormada, Direcao direcao) {
        if(geraPosicaoInvalidaSeAvancarParaDirecao(posicao, direcao)){
            return;
        }

        Posicao posicaoNova = gerarPosicaoAvancandoParaDirecao(posicao, direcao);
        if(getTabuleiro().posicaoNaoExiste(posicaoNova)){
            return;
        }

        adicionarPosicaoLetraEncontrada(posicaoNova);

        String letraDaPosicaoNova = getTabuleiro().getLetraDaPosicao(posicaoNova).getLetra();
        palavraFormada += letraDaPosicaoNova;
        palavrasPossiveis = filtrarPalavrasQueComecamCom(palavrasPossiveis, palavraFormada);

        tratarPalavrasEncontradasPorCompleto(palavrasPossiveis, palavraFormada);

        if (!palavrasPossiveis.isEmpty()){
            pesquisarEmDirecao(palavrasPossiveis, posicaoNova, palavraFormada, direcao);
        }
    }

    private boolean geraPosicaoInvalidaSeAvancarParaDirecao(Posicao posicao, Direcao direcao) {
        int novoX = posicao.getX() + direcao.getX();
        int novoY = posicao.getY() + direcao.getY();
        return novoX <= 0 || novoY <= 0;
    }

    private Posicao gerarPosicaoAvancandoParaDirecao(Posicao posicao, Direcao direcao) {
        int x = posicao.getX() + direcao.getX();
        int y = posicao.getY() + direcao.getY();
        return new Posicao(x, y);
    }

    private void tratarPalavrasEncontradasPorCompleto(List<Palavra> palavrasPossiveis, String palavra) {
        List<Palavra> palavrasEncontradasPorCompleto = getPalavrasIguaisA(palavrasPossiveis, palavra);

        palavrasEncontradasPorCompleto.forEach(p -> {
            LocalizacaoPalavraNoTabuleiro localizacaoPalavra = new LocalizacaoPalavraNoTabuleiro();
            adicionarLetrasEncontradas(localizacaoPalavra);
            p.getLocalizacoesNoTabuleiro().add(localizacaoPalavra);
        });

        palavrasPossiveis.removeAll(palavrasEncontradasPorCompleto);
    }

    private List<Palavra> getPalavrasIguaisA(List<Palavra> palavras, String palavraFormada){
        return palavras.stream().filter(p -> isPalavraIgualA(p, palavraFormada)).collect(Collectors.toList());
    }

    private boolean isPalavraIgualA(Palavra palavra, String palavraFormada) {
        return palavra.getPalavra().equalsIgnoreCase(palavraFormada);
    }

    private void adicionarLetrasEncontradas(LocalizacaoPalavraNoTabuleiro localizacaoPalavra){
        this.localizacoesLetrasEncontradas.forEach(l -> {
            localizacaoPalavra.getLocalizacoesLetrasNoTabuleiro().add(new LocalizacaoLetraNoTabuleiro(l));
        });
    }

}
