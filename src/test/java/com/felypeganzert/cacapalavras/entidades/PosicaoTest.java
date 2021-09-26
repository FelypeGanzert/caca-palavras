package com.felypeganzert.cacapalavras.entidades;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.felypeganzert.cacapalavras.exception.RegraNegocioException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PosicaoTest {

    @Test
    void deveGerarRegraNegocioExceptionAoInstanciarComCoordenadasIguaisAZero() {
        assertThatExceptionOfType(RegraNegocioException.class)
            .isThrownBy(() -> new Posicao(0, 0))
            .withMessage("Posição X e Y precisam ser positivas");
    }

    @Test
    void deveInstanciarComSucessoQuadoCoordenadasForemPositivas() {
        Posicao posicao = new Posicao(1, 1);

        assertThat(posicao).isNotNull();
        assertThat(posicao.getX()).isEqualTo(1);
        Assertions.assertThat(posicao.getY()).isEqualTo(1);
    }

    @Test
    void deveGerarRegraNegocioExceptionAoInstanciarComPosicaoXNegativa() {
        assertThatExceptionOfType(RegraNegocioException.class)
            .isThrownBy(() -> new Posicao(-1, 1))
            .withMessage("Posição X precisa ser positiva");
    }

    @Test
    void deveGerarRegraNegocioExceptionAoInstanciarComPosicaoYNegativa() {
        assertThatExceptionOfType(RegraNegocioException.class)
            .isThrownBy(() -> new Posicao(1, -1))
            .withMessage("Posição Y precisa ser positiva");
    }

    @Test
    void deveGerarRegraNegocioExceptionAoInstanciarComCoordenadasNegativas() {
        assertThatExceptionOfType(RegraNegocioException.class)
            .isThrownBy(() -> new Posicao(-1, -1))
            .withMessage("Posição X e Y precisam ser positivas");
    }

    @Test
    void  deveGerarRegraNegocioExceptionAoAtribuirPosicaoXZero() {
        Posicao posicao = criarPosicaoValida();

        assertThatExceptionOfType(RegraNegocioException.class)
            .isThrownBy(() -> posicao.setX(0))
            .withMessage("Posição X precisa ser positiva");
    }

    @Test
    void deveGerarRegraNegocioExceptionAoAtribuirPosicaoYZero() {
        Posicao posicao = criarPosicaoValida();

        assertThatExceptionOfType(RegraNegocioException.class)
            .isThrownBy(() -> posicao.setY(0))
            .withMessage("Posição Y precisa ser positiva");
    }

    @Test
    void deveAtribuirComSucessoQuandoPosicaoXForPositiva() {
        Posicao posicao = criarPosicaoValida();
        posicao.setX(1);

        Assertions.assertThat(posicao.getX()).isEqualTo(1);
    }

    @Test
    void deveAtribuirComSucessoQuandoPosicaoYForPositiva() {
        Posicao posicao = criarPosicaoValida();
        posicao.setY(1);

        Assertions.assertThat(posicao.getY()).isEqualTo(1);
    }

    @Test
    void deveGerarRegraNegocioExceptionAoAtribuirPosicaoXNegativa() {
        Posicao posicao = criarPosicaoValida();

        assertThatExceptionOfType(RegraNegocioException.class)
            .isThrownBy(() -> posicao.setX(-1))
            .withMessage("Posição X precisa ser positiva");
    }

    @Test
    void deveGerarRegraNegocioExceptionAoAtribuirPosicaoYNegativa() {
        Posicao posicao = criarPosicaoValida();

        assertThatExceptionOfType(RegraNegocioException.class)
            .isThrownBy(() -> posicao.setY(-1))
            .withMessage("Posição Y precisa ser positiva");
    }

    // TODO: criar teste para getPosicaoCartesiana()

    private Posicao criarPosicaoValida() {
        return new Posicao(1, 1);
    }

}
