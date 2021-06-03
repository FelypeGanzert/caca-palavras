package com.felypeganzert.cacapalavras.repository;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.rest.dto.InformacoesBasicasCacaPalavrasDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CacaPalavrasRepository extends JpaRepository<CacaPalavras, Long>{

    @Query("select new com.felypeganzert.cacapalavras.rest.dto.InformacoesBasicasCacaPalavrasDTO"
            + "(c.id, c.dataCriacao, c.criador, c.titulo) from CacaPalavras c")
	List<InformacoesBasicasCacaPalavrasDTO> findAllComInformacoesBasicas();
    
}
