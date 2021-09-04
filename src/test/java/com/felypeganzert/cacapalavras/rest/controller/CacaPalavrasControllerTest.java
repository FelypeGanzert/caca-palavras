package com.felypeganzert.cacapalavras.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMaper;
import com.felypeganzert.cacapalavras.repository.CacaPalavrasRepository;
import com.felypeganzert.cacapalavras.rest.dto.CacaPalavrasDTO;
import com.felypeganzert.cacapalavras.rest.dto.CacaPalavrasPostDTO;
import com.felypeganzert.cacapalavras.rest.dto.InformacoesBasicasCacaPalavrasDTO;
import com.felypeganzert.cacapalavras.services.CacaPalavrasService;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(SpringExtension.class)
public class CacaPalavrasControllerTest {

    @InjectMocks
    private CacaPalavrasController controller;
    
    @Mock
    private CacaPalavrasService service;

    @Mock
    private CacaPalavrasRepository repository;;

    @Mock
    private CacaPalavrasMaper cacaPalavrasMapper;

    @BeforeEach
    void setUp(){
        BDDMockito.when(service.criarComBasico(ArgumentMatchers.any(CacaPalavrasPostDTO.class)))
                .thenReturn(criarCacaPalavrasValido());
   
        BDDMockito.when(cacaPalavrasMapper.toCacaPalavrasDTO(ArgumentMatchers.any(CacaPalavras.class)))
                .thenReturn(criarCacaPalavrasDTOValido());
    }

    @Test
    void deveSalvarCacaPalavrasComSucesso(){
        CacaPalavrasPostDTO dto = criarCacaPalavrasPostDTOValido();
        Integer idCriado = controller.criarComBasico(dto);

        assertThat(idCriado).isNotNull().isEqualTo(criarCacaPalavrasValido().getId());
        Mockito.verify(service).criarComBasico(ArgumentMatchers.any(CacaPalavrasPostDTO.class));
    }

    @Test
    void deveBuscarInformacoesBasicasCacaPalavrasComSucesso(){
        List<InformacoesBasicasCacaPalavrasDTO> infos = controller.findAllComInformacoesBasicas();

        assertThat(infos).isNotNull();
        Mockito.verify(service).findAllComInformacoesBasicas();
    }

    @Test
    void deveEncontrarCacaPalavrasQuandoExistirSucesso(){
        BDDMockito.when(service.findById(ArgumentMatchers.any(Integer.class)))
                        .thenReturn(criarCacaPalavrasValido());

        CacaPalavrasDTO dto = controller.findById(1);

        assertThat(dto).isNotNull();
    }

    @Test
    void deveGerarExceptionRecursoNaoEncontradoAoBuscarIdNaoExistente(){
        BDDMockito.when(repository.findById(ArgumentMatchers.any(Integer.class)))
                        .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                    .isThrownBy(() -> controller.findById(1))
                    .withMessageContaining("Caça Palavras não encontrado com id: 1");
    }

    @Test
    void deveGerarExceptionRecursoNaoEncontradoAoTentarDeletarCacaPalavrasNaoExistente(){
        BDDMockito.when(repository.findById(ArgumentMatchers.any(Integer.class)))
                        .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                    .isThrownBy(() -> controller.delete(1))
                    .withMessageContaining("Caça Palavras não encontrado com id: 1");
    }

    
    @Test
    void deveChamarComSucessoDeleteDoRepositoryQuandoCacaPalavrasExistir(){
        BDDMockito.when(repository.findById(ArgumentMatchers.any(Integer.class)))
                        .thenReturn(Optional.of(criarCacaPalavrasValido()));

        controller.delete(1);
        
        Mockito.verify(repository).delete(ArgumentMatchers.eq(criarCacaPalavrasValido()));
    }

    private CacaPalavras criarCacaPalavrasValido(){
        CacaPalavras cacaPalavras = new CacaPalavras();
        cacaPalavras.setId(1);
        cacaPalavras.setDataCriacao(LocalDateTime.now());
        cacaPalavras.setCriador("Teste Criador");
        cacaPalavras.setTitulo("Teste Título");
        return cacaPalavras;
    }

    private CacaPalavrasPostDTO criarCacaPalavrasPostDTOValido(){
        return CacaPalavrasPostDTO
                    .builder()
                    .criador("Teste Criador")
                    .titulo("Teste Título")
                    .build();
    }

    private CacaPalavrasDTO criarCacaPalavrasDTOValido(){
        return CacaPalavrasDTO
                    .builder()
                    .id(1)
                    .criador("Teste Criador")
                    .titulo("Teste Título")
                    .build();
    }
    
}
