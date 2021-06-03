package com.felypeganzert.cacapalavras.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.rest.dto.InformacoesBasicasCacaPalavrasDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CacaPalavrasRepositoryTest {

    @Autowired
    private CacaPalavrasRepository repository;

    @BeforeEach
    void destruir(){
        repository.deleteAll();
    }

    @Test
    void deveRetornarComSucessoInformacoesBasicasQuandoExistir(){
        CacaPalavras cacaPalavras = new CacaPalavras();
        cacaPalavras.setDataCriacao(LocalDate.now());
        cacaPalavras.setCriador("Teste Criador");
        cacaPalavras.setTitulo("Teste Título");
        repository.save(cacaPalavras);

        List<InformacoesBasicasCacaPalavrasDTO> infoCacaPalavras = repository.findAllComInformacoesBasicas();

        assertThat(infoCacaPalavras)
            .isNotNull()
            .hasSize(1);
    }

    @Test
    void deveRetornarInformacoesBasicasVaziaQuandoNaoExistirNada(){
        List<InformacoesBasicasCacaPalavrasDTO> infoCacaPalavras = repository.findAllComInformacoesBasicas();

        assertThat(infoCacaPalavras)
            .isNotNull()
            .isEmpty();
    }
    
}
