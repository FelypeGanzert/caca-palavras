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

    @Test
    void deveRetornarSomenteAsPalavrasDeUmCacaPalavras() {
        CacaPalavras c1 = criarCacaPalavrasComBasico();
        c1 = repositoryCacaPalavras.save(c1);

        CacaPalavras c2 = criarCacaPalavrasComBasico();
        c2 = repositoryCacaPalavras.save(c2);

        Palavra p1C1 = Palavra.builder().cacaPalavras(c1).palavra("AA").build();
        Palavra p2C1 = Palavra.builder().cacaPalavras(c1).palavra("BB").build();

        Palavra p1C2 = Palavra.builder().cacaPalavras(c2).palavra("AA").build();
        Palavra p2C2 = Palavra.builder().cacaPalavras(c2).palavra("BB").build();
        repository.saveAll(Arrays.asList(p1C1, p2C1, p1C2, p2C2));

        List<Palavra> palavrasC1 = new ArrayList<Palavra>();
        palavrasC1 = repository.findAllByCacaPalavrasId(c1.getId());
        assertThat(palavrasC1.get(0)).isEqualTo(p1C1);
        assertThat(palavrasC1.get(1)).isEqualTo(p2C1);
    }

    private CacaPalavras criarCacaPalavrasComBasico() {
        CacaPalavras cacaPalavras = new CacaPalavras();
        cacaPalavras.setDataCriacao(LocalDateTime.now());
        cacaPalavras.setCriador("Teste Criador");
        cacaPalavras.setTitulo("Teste Título");
        return cacaPalavras;
    }

}
