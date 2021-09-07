package com.felypeganzert.cacapalavras.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Palavra;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PalavraRepositoryTest {

    @Autowired
    private PalavraRepository repository;

    @Autowired
    private CacaPalavrasRepository repositoryCacaPalavras;

    @BeforeEach
    void destruir() {
        repository.deleteAll();
    }

    @Test
    void deveRemoverTodasAsPalavrasDeUmCacaPalavras() {
        CacaPalavras cacaPalavras = criarCacaPalavrasComBasico();
        cacaPalavras = repositoryCacaPalavras.save(cacaPalavras);

        Palavra p1 = Palavra.builder().cacaPalavras(cacaPalavras).palavra("AA").build();
        Palavra p2 = Palavra.builder().cacaPalavras(cacaPalavras).palavra("BB").build();
        repository.saveAll(Arrays.asList(p1, p2));

        List<Palavra> allPalavras = new ArrayList<Palavra>();
        allPalavras = repository.findAll();
        assertThat(allPalavras).isNotNull().hasSize(2);

        repository.deleteAllFromCacaPalavras(cacaPalavras.getId());
        allPalavras = repository.findAll();
        assertThat(allPalavras).isNotNull().isEmpty();
    }

    @Test
    void naoDeveRemoverNenhumaPalavraQuandoNaoExistirParaOTabuleiro() {
        CacaPalavras c1 = criarCacaPalavrasComBasico();
        CacaPalavras c2 = criarCacaPalavrasComBasico();
        c1 = repositoryCacaPalavras.save(c1);
        c2 = repositoryCacaPalavras.save(c2);

        Palavra p1 = Palavra.builder().cacaPalavras(c1).palavra("AA").build();
        Palavra p2 = Palavra.builder().cacaPalavras(c1).palavra("BB").build();
        repository.saveAll(Arrays.asList(p1, p2));

        List<Palavra> allPalavras = new ArrayList<Palavra>();
        allPalavras = repository.findAll();
        assertThat(allPalavras).isNotNull().hasSize(2);

        repository.deleteAllFromCacaPalavras(c2.getId());
        allPalavras = repository.findAll();
        assertThat(allPalavras).isNotNull().hasSize(2);
    }

    private CacaPalavras criarCacaPalavrasComBasico() {
        CacaPalavras cacaPalavras = new CacaPalavras();
        cacaPalavras.setDataCriacao(LocalDateTime.now());
        cacaPalavras.setCriador("Teste Criador");
        cacaPalavras.setTitulo("Teste Título");
        return cacaPalavras;
    }

}
