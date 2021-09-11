package com.felypeganzert.cacapalavras.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Direcao;
import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.LocalizacaoLetra;
import com.felypeganzert.cacapalavras.entidades.LocalizacaoPalavra;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;

import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class CacaPalavrasResolver {

    private CacaPalavras cacaPalavras;
    private List<LocalizacaoLetra> localizacoesLetrasEncontradas = new ArrayList<LocalizacaoLetra>();

    public void encontrarPalavrasNoTabuleiro(CacaPalavras cacaPalavras) {
        this.cacaPalavras = cacaPalavras;
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

    private char getLetraDaPosicao(Posicao posicao){
        return getTabuleiro().getLetraDaPosicao(posicao).getLetra();
    }

    private void procurarAPartirDaPosicao(Posicao posicaoDePartida) {
        char letraInicial = getLetraDaPosicao(posicaoDePartida);
        List<Palavra> todasAsPalavras = this.cacaPalavras.getPalavras();

        for (Direcao d : Direcao.values()) {
            List<Palavra> palavrasPossiveis = filtrarPalavrasQueComecamCom(todasAsPalavras, String.valueOf(letraInicial));
            this.localizacoesLetrasEncontradas.clear();
            String palavraFormada = "";
            pesquisarEmDirecao(palavrasPossiveis, posicaoDePartida, palavraFormada, d, false);
        }
    }

    private List<Palavra> filtrarPalavrasQueComecamCom(List<Palavra> palavras, String palavraFormada) {
        return palavras.stream().filter(p -> palavraComecaCom(p, palavraFormada)).collect(Collectors.toList());
    }

    private boolean palavraComecaCom(Palavra palavra, String palavraFormada) {
        return palavra.getPalavra().toUpperCase().startsWith(palavraFormada.toUpperCase());
    }

    private void adicionarPosicaoLetraEncontrada(Letra letra){
        int ordemLetra = localizacoesLetrasEncontradas.size() + 1;
        this.localizacoesLetrasEncontradas.add(new LocalizacaoLetra(ordemLetra, letra));
    }

    private void pesquisarEmDirecao(List<Palavra> palavrasPossiveis, Posicao posicao, String palavraFormada,
            Direcao direcao, boolean avancarNaDirecao) {

        if(avancarNaDirecao){
            if(geraPosicaoInvalidaSeAvancarParaDirecao(posicao, direcao)){
                return;
            }
            posicao = gerarPosicaoAvancandoParaDirecao(posicao, direcao);
        }

        if(getTabuleiro().posicaoNaoExiste(posicao)){
            return;
        }

        Letra letraDaPosicao = getTabuleiro().getLetraDaPosicao(posicao);
        adicionarPosicaoLetraEncontrada(letraDaPosicao);

        palavraFormada += letraDaPosicao.getLetra();
        palavrasPossiveis = filtrarPalavrasQueComecamCom(palavrasPossiveis, palavraFormada);

        tratarPalavrasEncontradasPorCompleto(palavrasPossiveis, palavraFormada);

        if (!palavrasPossiveis.isEmpty()){
            pesquisarEmDirecao(palavrasPossiveis, posicao, palavraFormada, direcao, true);
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
            LocalizacaoPalavra localizacaoPalavra = new LocalizacaoPalavra();
            localizacaoPalavra.setPalavra(p);
            adicionarLetrasEncontradas(localizacaoPalavra);
            p.getLocalizacoes().add(localizacaoPalavra);
        });

        palavrasPossiveis.removeAll(palavrasEncontradasPorCompleto);
    }

    private List<Palavra> getPalavrasIguaisA(List<Palavra> palavras, String palavraFormada){
        return palavras.stream().filter(p -> isPalavraIgualA(p, palavraFormada)).collect(Collectors.toList());
    }

    private boolean isPalavraIgualA(Palavra palavra, String palavraFormada) {
        return palavra.getPalavra().equalsIgnoreCase(palavraFormada);
    }

    private void adicionarLetrasEncontradas(LocalizacaoPalavra localizacaoPalavra){
        this.localizacoesLetrasEncontradas.forEach(l -> {
            localizacaoPalavra.getLocalizacoesLetras().add(new LocalizacaoLetra(localizacaoPalavra, l));
        });
    }

}
