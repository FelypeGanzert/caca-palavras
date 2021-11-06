package com.felypeganzert.cacapalavras.repository;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.LocalizacaoPalavra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LocalizacaoPalavraRepository extends JpaRepository<LocalizacaoPalavra, Integer>{

    @Modifying
    @Query(" DELETE FROM LocalizacaoPalavra l "
        + " WHERE l.id IN ( "
        + "      SELECT l2.id FROM LocalizacaoPalavra l2 "
        + "      WHERE l2.palavra.cacaPalavras.id = :idCacaPalavras "
        + " )")
    void deleteAllFromCacaPalavrasId(Integer idCacaPalavras);
    
    @Modifying
    @Query(" DELETE FROM LocalizacaoPalavra l "
        + " WHERE l.id IN ( "
        + "      SELECT l2.id FROM LocalizacaoPalavra l2 "
        + "      WHERE l2.palavra.cacaPalavras.tabuleiro.id = :idTabuleiro "
        + " )")
    void deleteAllFromTabuleiroId(Integer idTabuleiro);

    @Modifying
    @Query(" DELETE FROM LocalizacaoPalavra l "
        + " WHERE l.id IN ( "
        + "      SELECT l2.id FROM LocalizacaoPalavra l2 "
        + "      WHERE l2.palavra.id = :idPalavra "
        + " )")
    void deleteAllFromPalavraId(Integer idPalavra);

    @Modifying
    @Query(" DELETE FROM LocalizacaoPalavra locPalavra "
        + " WHERE locPalavra.id IN ( "
        + "      SELECT locPalavra2.id FROM LocalizacaoPalavra locPalavra2 "
        + "      INNER JOIN locPalavra2.localizacoesLetras locLetra2 "
        + "      WHERE locLetra2.letra.id = :idLetra "
        + " )")
    void deleteAllUsingLetraId(Integer idLetra);

    @Modifying
    @Query(" DELETE FROM LocalizacaoPalavra locPalavra "
        + " WHERE locPalavra.id IN ( "
        + "      SELECT locPalavra2.id FROM LocalizacaoPalavra locPalavra2 "
        + "      INNER JOIN locPalavra2.localizacoesLetras locLetra2 "
        + "      WHERE locLetra2.letra.id in (:idsLetras) "
        + " )")
    void deleteAllUsingLetrasId(List<Integer> idsLetras);

}
