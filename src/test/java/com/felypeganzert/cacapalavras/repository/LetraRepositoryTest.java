package com.felypeganzert.cacapalavras.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class LetraRepositoryTest {

    @Autowired
    private LetraRepository repository;

    @Autowired
    private TabuleiroRepository repositoryTabuleiro;

    @BeforeEach
    void destruir() {
        repository.deleteAll();
    }

    @Test
    void deveRemoverTodasAsPalavrasDeUmCacaPalavras() {
        Tabuleiro t1 = criarTabuleiroValido();
        t1 = repositoryTabuleiro.save(t1);

        Letra l1 = new Letra(t1, 'a', new Posicao(1, 1));
        Letra l2 = new Letra(t1, 'b', new Posicao(1, 2));
        repository.saveAll(Arrays.asList(l1, l2));

        List<Letra> allLetras = new ArrayList<Letra>();
        allLetras = repository.findAll();
        assertThat(allLetras).isNotNull().hasSize(2);

        repository.deleteAllFromTabuleiro(t1.getId());
        allLetras = repository.findAll();
        assertThat(allLetras).isNotNull().isEmpty();
    }

    @Test
    void naoDeveRemoverNenhumaPalavraQuandoNaoExistirParaOTabuleiro() {
        Tabuleiro t1 = criarTabuleiroValido();
        Tabuleiro t2 = criarTabuleiroValido();
        t1 = repositoryTabuleiro.save(t1);
        t2 = repositoryTabuleiro.save(t2);

        Letra l1 = new Letra(t1, 'a', new Posicao(1, 1));
        Letra l2 = new Letra(t1, 'b', new Posicao(1, 2));
        repository.saveAll(Arrays.asList(l1, l2));

        List<Letra> allLetras = new ArrayList<Letra>();
        allLetras = repository.findAll();
        assertThat(allLetras).isNotNull().hasSize(2);

        repository.deleteAllFromTabuleiro(t2.getId());
        allLetras = repository.findAll();
        assertThat(allLetras).isNotNull().hasSize(2);
    }

    private Tabuleiro criarTabuleiroValido() {
        Tabuleiro tabuleiro = new Tabuleiro(Tabuleiro.LARGURA_MINIMA, Tabuleiro.ALTURA_MINIMA);
        return tabuleiro;
    }

}
