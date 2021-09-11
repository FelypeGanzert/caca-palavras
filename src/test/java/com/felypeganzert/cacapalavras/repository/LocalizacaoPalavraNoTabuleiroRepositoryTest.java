package com.felypeganzert.cacapalavras.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.LocalizacaoPalavraNoTabuleiro;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.util.CacaPalavrasCreator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class LocalizacaoPalavraNoTabuleiroRepositoryTest {

    @Autowired
    private LocalizacaoPalavraNoTabuleiroRepository repository;

    @Autowired
    private LocalizacaoLetraNoTabuleiroRepository repositoryLocalizacaoLetra;

    @Autowired
    private CacaPalavrasRepository repositoryCacaPalavras;    

    @BeforeEach
    void destruir() {
        repository.deleteAll();
    }

    @Test
    void deveRemoverAsLocalizacaoPalavraDeSomenteDeterminadoTabuleiro() {
        CacaPalavras c = criarCacaPalavrasComTabuleiroValido();
        Palavra sol = CacaPalavrasCreator.criarPalavraValida(c, "sol");
        LocalizacaoPalavraNoTabuleiro localizacao1Sol = CacaPalavrasCreator.criarLocalizacaoPalavraValida(sol);
        LocalizacaoPalavraNoTabuleiro localizacao2Sol = CacaPalavrasCreator.criarLocalizacaoPalavraValida(sol);
        sol.getLocalizacoesNoTabuleiro().addAll(java.util.Arrays.asList(localizacao1Sol, localizacao2Sol));
        c.getPalavras().add(sol);

        CacaPalavras c2 = criarCacaPalavrasComTabuleiroValido();
        Palavra ceu = CacaPalavrasCreator.criarPalavraValida(c2, "ceuzinho");
        LocalizacaoPalavraNoTabuleiro localizacao1Ceu = CacaPalavrasCreator.criarLocalizacaoPalavraValida(ceu);
        LocalizacaoPalavraNoTabuleiro localizacao2Ceu = CacaPalavrasCreator.criarLocalizacaoPalavraValida(ceu);
        ceu.getLocalizacoesNoTabuleiro().addAll(java.util.Arrays.asList(localizacao1Ceu, localizacao2Ceu));
        c2.getPalavras().add(ceu);

        c = repositoryCacaPalavras.save(c);
        c2 = repositoryCacaPalavras.save(c2);

        assertThat(repository.findAll()).isNotEmpty().hasSize(4);

        final int idTabuleiroParaExcluir = c.getTabuleiro().getId();
        final int idTabuleiroNaoExcluido = c2.getTabuleiro().getId();

        repository.deleteAllFromTabuleiroId(idTabuleiroParaExcluir);

        List<LocalizacaoPalavraNoTabuleiro> localizacoes = repository.findAll();
        assertThat(localizacoes).isNotEmpty().hasSize(2);
        localizacoes.forEach(l -> {
            int idTabuleiroLocalizaco = l.getPalavra().getCacaPalavras().getTabuleiro().getId();
            assertThat(idTabuleiroLocalizaco).isEqualTo(idTabuleiroNaoExcluido);
        });
    }

    @Test
    void deveRemoverAsLocalizacaoPalavraEAsLocalizacaoLetraEmCascadeAssociadasADeterminadaLetra() {
        CacaPalavras c = criarCacaPalavrasComTabuleiroValido();
        Letra l1 = new Letra(c.getTabuleiro(), '1', new Posicao(1,1));
        Letra l2 = new Letra(c.getTabuleiro(), '2', new Posicao(1,2));
        c.getTabuleiro().getLetras().addAll(java.util.Arrays.asList(l1, l2));

        Palavra sol = CacaPalavrasCreator.criarPalavraValida(c, "sol");
        // Localização com letra 1 e 2 - aqui ele irá deletar a letra 1, e consequentemente a localização palavra
        // e portanto a localizacaoLetra com a l2 será excluída também
        LocalizacaoPalavraNoTabuleiro locComLetra1ELetra2 = CacaPalavrasCreator.criarLocalizacaoPalavraValida(sol);
        locComLetra1ELetra2.getLocalizacoesLetrasNoTabuleiro().addAll(
            java.util.Arrays.asList(
                CacaPalavrasCreator.criarLocalizacaoLetraValida(1, l1, locComLetra1ELetra2),
                CacaPalavrasCreator.criarLocalizacaoLetraValida(2, l2, locComLetra1ELetra2)
            )
        );
        // Localização com letra 1
        LocalizacaoPalavraNoTabuleiro locComLetra1 = CacaPalavrasCreator.criarLocalizacaoPalavraValida(sol);
        locComLetra1.getLocalizacoesLetrasNoTabuleiro().add(CacaPalavrasCreator.criarLocalizacaoLetraValida(1, l1, locComLetra1));
        // Localização com letra 2
        LocalizacaoPalavraNoTabuleiro locComLetra2 = CacaPalavrasCreator.criarLocalizacaoPalavraValida(sol);
        locComLetra2.getLocalizacoesLetrasNoTabuleiro().add(CacaPalavrasCreator.criarLocalizacaoLetraValida(1, l2, locComLetra2));

        sol.getLocalizacoesNoTabuleiro().addAll(java.util.Arrays.asList(locComLetra1ELetra2, locComLetra1, locComLetra2));
        c.getPalavras().add(sol);

        c = repositoryCacaPalavras.save(c);

        int totalLocalizacaoLetra = 4;
        int totalLocalizacaoLetraExcluidas = 3;
        int totalLocalizacaoPalavra = 3;
        int totalLocalizacaoPalavraExcluidas = 2;

        assertThat(repository.findAll()).isNotEmpty().hasSize(totalLocalizacaoPalavra);
        assertThat(repositoryLocalizacaoLetra.findAll()).isNotEmpty().hasSize(totalLocalizacaoLetra);

        final int idLetraParaExcluir = l1.getId();
        final int idLetraNaoExcluida = l2.getId();

        repository.deleteAllUsingLetraId(idLetraParaExcluir);

        assertThat(repositoryLocalizacaoLetra.findAll()).isNotEmpty().hasSize(totalLocalizacaoLetra - totalLocalizacaoLetraExcluidas);

        List<LocalizacaoPalavraNoTabuleiro> localizacoes = repository.findAll();
        assertThat(localizacoes).isNotEmpty().hasSize(totalLocalizacaoPalavra - totalLocalizacaoPalavraExcluidas);
        localizacoes.forEach(l -> {
            l.getLocalizacoesLetrasNoTabuleiro().forEach(ll -> {
                int idLetraLocalizacao = ll.getLetra().getId();
                assertThat(idLetraLocalizacao).isEqualTo(idLetraNaoExcluida);
            });
        });
    }

    private CacaPalavras criarCacaPalavrasComTabuleiroValido(){
        CacaPalavras c = CacaPalavrasCreator.criarCacaPalavrasValido(null);
        CacaPalavrasCreator.criarTabuleiroValido(null, c);
        return c;
    }

}
