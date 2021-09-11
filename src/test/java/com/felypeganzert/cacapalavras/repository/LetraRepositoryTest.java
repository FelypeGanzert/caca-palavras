package com.felypeganzert.cacapalavras.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.util.CacaPalavrasCreator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class LetraRepositoryTest {

    @Autowired
    private LetraRepository repository;
    
    @Autowired
    private CacaPalavrasRepository repositoryCacaPalavras;

    @BeforeEach
    void destruir() {
        repository.deleteAll();
    }

    @Test
    void deveRemoverTodasAsLetrasDeUmTabuleiro() {
        Tabuleiro t1 = criarTabuleiroValido();
        repositoryCacaPalavras.save(t1.getCacaPalavras());

        Letra l1 = new Letra(t1, 'a', new Posicao(1, 1));
        Letra l2 = new Letra(t1, 'b', new Posicao(1, 2));
        t1.getLetras().addAll(Arrays.asList(l1, l2));
        repository.saveAll(Arrays.asList(l1, l2));

        List<Letra> allLetras = repository.findAll();
        assertThat(allLetras).isNotNull().hasSize(2);

        repository.deleteAllFromTabuleiro(t1.getId());
        allLetras = repository.findAll();
        assertThat(allLetras).isNotNull().isEmpty();
    }

    @Test
    void naoDeveRemoverNenhumaLetraQuandoNaoExistirParaOTabuleiro() {
        Tabuleiro t1 = criarTabuleiroValido();
        Tabuleiro t2 = criarTabuleiroValido();
        repositoryCacaPalavras.save(t1.getCacaPalavras());
        repositoryCacaPalavras.save(t2.getCacaPalavras());

        Letra l1 = new Letra(t1, 'a', new Posicao(1, 1));
        Letra l2 = new Letra(t1, 'b', new Posicao(1, 2));
        t1.getLetras().addAll(Arrays.asList(l1, l2));
        repository.saveAll(Arrays.asList(l1, l2));

        List<Letra> allLetras = repository.findAll();
        assertThat(allLetras).isNotNull().hasSize(2);

        repository.deleteAllFromTabuleiro(t2.getId());
        allLetras = repository.findAll();
        assertThat(allLetras).isNotNull().hasSize(2);
    }

    private Tabuleiro criarTabuleiroValido() {
    	CacaPalavras cacaPalavras = CacaPalavrasCreator.criarCacaPalavrasValido(null);
        Tabuleiro tabuleiro = CacaPalavrasCreator.criarTabuleiroValido(null, cacaPalavras);
        return tabuleiro;
    }

}
