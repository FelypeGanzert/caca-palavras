package com.felypeganzert.cacapalavras.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.dto.CacaPalavrasDTO;
import com.felypeganzert.cacapalavras.repository.CacaPalavrasRepository;
import com.felypeganzert.cacapalavras.repository.LetraRepository;
import com.felypeganzert.cacapalavras.repository.LocalizacaoLetraRepository;
import com.felypeganzert.cacapalavras.repository.LocalizacaoPalavraRepository;
import com.felypeganzert.cacapalavras.repository.PalavraRepository;
import com.felypeganzert.cacapalavras.repository.TabuleiroRepository;
import com.felypeganzert.cacapalavras.rest.payload.CacaPalavrasPostDTO;
import com.felypeganzert.cacapalavras.services.CacaPalavrasService;
import com.felypeganzert.cacapalavras.util.CreatorPataTestesDeIntegracao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class CacaPalavrasControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CacaPalavrasService service;

    
    @Autowired
    private CacaPalavrasRepository repository;
    
    @Autowired
    private PalavraRepository palavraRepository;

    @Autowired
    private TabuleiroRepository tabuleiroRepository;

    @Autowired
    private LetraRepository letraRepository;
    
    @Autowired
    private LocalizacaoPalavraRepository localizacaoPalavraRepository;

    @Autowired
    private LocalizacaoLetraRepository localizacaoLetraRepository;

    private static final String BASE_PATH = "/api/caca-palavras";

    @BeforeEach
    void destruirTudo() {
        localizacaoLetraRepository.deleteAll();
        localizacaoPalavraRepository.deleteAll();
        letraRepository.deleteAll();
        tabuleiroRepository.deleteAll();
        repository.deleteAll();
    }

    @Test
    void deveCriarUmCacaPalavras() throws JsonProcessingException, Exception {
        CacaPalavrasPostDTO postDto = CacaPalavrasPostDTO.builder().criador("Teste Criador").titulo("Teste Título").build();

        ResponseEntity<CacaPalavrasDTO> response = restTemplate
            .postForEntity(BASE_PATH, postDto, CacaPalavrasDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        CacaPalavrasDTO cacaPalavrasCriado = response.getBody();
        assertThat(cacaPalavrasCriado).isNotNull();
    }

    @Test
    void deveRetornarUmCacaPalavrasPeloId() throws JsonProcessingException, Exception {
        CacaPalavras existente = CreatorPataTestesDeIntegracao.cacaPalavrasComPalavrasEComTabuleiroEComTodasAsLetras();
        existente = repository.save(existente);

        ResponseEntity<CacaPalavrasDTO> response = restTemplate
            .getForEntity(BASE_PATH + "/" + existente.getId(), CacaPalavrasDTO.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        CacaPalavrasDTO cacaPalavrasCriado = response.getBody();
        assertThat(cacaPalavrasCriado).isNotNull();
    }

    @Test
    void deveDeletarUmCacaPalavras() throws JsonProcessingException, Exception {
        CacaPalavras existente = CreatorPataTestesDeIntegracao.cacaPalavras();
        existente = repository.save(existente);

        // garante que foi criado registro
        assertThat(repository.findAll()).isNotEmpty();

        ResponseEntity<Void> response = restTemplate
            .exchange(BASE_PATH + "/" + existente.getId(), HttpMethod.DELETE, null, Void.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        
        // verifica se foi excluído o registro
        assertThat(repository.findAll()).hasSize(0);
    }

    @Test
    void deveDeletarUmCacaPalavrasComAsPalavas() throws JsonProcessingException, Exception {
        CacaPalavras existente = CreatorPataTestesDeIntegracao.cacaPalavrasComPalavras();
        existente = repository.save(existente);

        // garante que foi criado registro
        assertThat(repository.findAll()).isNotEmpty();
        assertThat(palavraRepository.findAll()).isNotEmpty();

        ResponseEntity<Void> response = restTemplate
            .exchange(BASE_PATH + "/" + existente.getId(), HttpMethod.DELETE, null, Void.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        
        // verifica se foi excluído o registro
        assertThat(repository.findAll()).hasSize(0);
        assertThat(palavraRepository.findAll()).hasSize(0);
    }

    @Test
    void deveDeletarUmCacaPalavrasComAsPalavasETabuleiroComLetras() throws JsonProcessingException, Exception {
        CacaPalavras existente = CreatorPataTestesDeIntegracao.cacaPalavrasComPalavrasEComTabuleiroEComTodasAsLetras();
        existente = repository.save(existente);

        // garante que foi criado registro
        assertThat(repository.findAll()).isNotEmpty();
        assertThat(palavraRepository.findAll()).isNotEmpty();
        assertThat(tabuleiroRepository.findAll()).isNotEmpty();
        assertThat(letraRepository.findAll()).isNotEmpty();

        ResponseEntity<Void> response = restTemplate
            .exchange(BASE_PATH + "/" + existente.getId(), HttpMethod.DELETE, null, Void.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        
        // verifica se foi excluído o registro
        assertThat(repository.findAll()).hasSize(0);
        assertThat(palavraRepository.findAll()).hasSize(0);
        assertThat(tabuleiroRepository.findAll()).hasSize(0);
        assertThat(letraRepository.findAll()).hasSize(0);
    }

    @Test
    void deveDeletarUmCacaPalavrasResolvidoEEmCascadeDeletarTudo() throws JsonProcessingException, Exception {
        CacaPalavras existente = CreatorPataTestesDeIntegracao.cacaPalavrasComPalavrasEComTabuleiroEComTodasAsLetras();
        existente = repository.save(existente);
        service.resolverCacaPalavras(existente.getId());

        // garante que foi criado registro em todas as tabelas
        assertThat(repository.findAll()).isNotEmpty();
        assertThat(palavraRepository.findAll()).isNotEmpty();
        assertThat(tabuleiroRepository.findAll()).isNotEmpty();
        assertThat(letraRepository.findAll()).isNotEmpty();
        assertThat(localizacaoPalavraRepository.findAll()).isNotEmpty();
        assertThat(localizacaoLetraRepository.findAll()).isNotEmpty();

        ResponseEntity<Void> response = restTemplate
            .exchange(BASE_PATH + "/" + existente.getId(), HttpMethod.DELETE, null, Void.class);
       
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        
        // verifica se foi excluído todos os registros
        assertThat(repository.findAll()).hasSize(0);
        assertThat(palavraRepository.findAll()).hasSize(0);
        assertThat(tabuleiroRepository.findAll()).hasSize(0);
        assertThat(letraRepository.findAll()).hasSize(0);
        assertThat(localizacaoPalavraRepository.findAll()).hasSize(0);
        assertThat(localizacaoLetraRepository.findAll()).hasSize(0);
    }

    @Test
    void deveTrazerTodosOsCacaPalavrasCadastrados() throws JsonProcessingException, Exception {
        repository.save(CreatorPataTestesDeIntegracao.cacaPalavras());
        repository.save(CreatorPataTestesDeIntegracao.cacaPalavrasComPalavrasEComTabuleiroEComQuaseTodasAsLetras());

        ResponseEntity<List<CacaPalavrasDTO>> response = restTemplate
            .exchange(BASE_PATH, HttpMethod.GET, null, new ParameterizedTypeReference<List<CacaPalavrasDTO>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<CacaPalavrasDTO> cacaPalavras = response.getBody();
        assertThat(cacaPalavras).isNotEmpty().hasSize(2);
    }

    @Test
    void deveSerPossivelTentarResolverUmCacaPalavrasSemPalavrasESeLetrasNoTabuleiro() throws JsonProcessingException, Exception {
        CacaPalavras cacaPalavras = repository.save(CreatorPataTestesDeIntegracao.cacaPalavrasComTabuleiro(6,6));

        ResponseEntity<CacaPalavrasDTO> response = restTemplate
            .exchange(BASE_PATH + "/" + cacaPalavras.getId() + "/solucionar",
                HttpMethod.POST, null, CacaPalavrasDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        CacaPalavrasDTO dto = response.getBody();
        assertThat(dto).isNotNull();
    }

    
}
