package com.felypeganzert.cacapalavras.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
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
        BDDMockito.when(service.salvar(ArgumentMatchers.any(CacaPalavrasPostDTO.class)))
                .thenReturn(criarCacaPalavrasValido());
   
        BDDMockito.when(cacaPalavrasMapper.toCacaPalavrasDTO(ArgumentMatchers.any(CacaPalavras.class)))
                .thenReturn(criarCacaPalavrasDTOValido());
    }

    @Test
    void deveSalvarCacaPalavrasComSucesso(){
        CacaPalavrasPostDTO dto = criarCacaPalavrasPostDTOValido();
        Long idCriado = controller.salvar(dto);

        assertThat(idCriado).isNotNull().isEqualTo(criarCacaPalavrasValido().getId());
        Mockito.verify(service).salvar(ArgumentMatchers.any(CacaPalavrasPostDTO.class));
    }

    @Test
    void deveBuscarInformacoesBasicasCacaPalavrasComSucesso(){
        List<InformacoesBasicasCacaPalavrasDTO> infos = controller.findAllComInformacoesBasicas();

        assertThat(infos).isNotNull();
        Mockito.verify(service).findAllComInformacoesBasicas();
    }

    @Test
    void deveEncontrarCacaPalavrasComQuandoExistirSucesso(){
        BDDMockito.when(repository.findById(ArgumentMatchers.any(Long.class)))
                        .thenReturn(Optional.of(criarCacaPalavrasValido()));

        CacaPalavrasDTO dto = controller.findById(1L);

        assertThat(dto).isNotNull();
    }

    @Test
    void deveGerarExceptionNotFoundQuandoAoBuscarIdNaoExistente(){
        BDDMockito.when(repository.findById(ArgumentMatchers.any(Long.class)))
                        .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                    .isThrownBy(() -> controller.findById(1L))
                    .withMessageContaining("Caça Palavras não encontrado");
    }

    @Test
    void deveGerarExceptionNotFoundAoTentarDeletarCacaPalavrasNaoExistente(){
        BDDMockito.when(repository.findById(ArgumentMatchers.any(Long.class)))
                        .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                    .isThrownBy(() -> controller.delete(1L))
                    .withMessageContaining("Caça Palavras não encontrado");
    }

    
    @Test
    void deveChamarComSucessoDeleteDoRepositoryQuandoCacaPalavrasExistir(){
        BDDMockito.when(repository.findById(ArgumentMatchers.any(Long.class)))
                        .thenReturn(Optional.of(criarCacaPalavrasValido()));

        controller.delete(1L);
        
        Mockito.verify(repository).delete(ArgumentMatchers.eq(criarCacaPalavrasValido()));
    }

    private CacaPalavras criarCacaPalavrasValido(){
        CacaPalavras cacaPalavras = new CacaPalavras();
        cacaPalavras.setId(1L);
        cacaPalavras.setDataCriacao(LocalDate.now());
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
                    .id(1L)
                    .criador("Teste Criador")
                    .titulo("Teste Título")
                    .build();
    }
    
}
