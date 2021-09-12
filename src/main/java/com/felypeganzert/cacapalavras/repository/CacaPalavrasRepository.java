package com.felypeganzert.cacapalavras.repository;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.dto.InformacoesBasicasCacaPalavrasDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CacaPalavrasRepository extends JpaRepository<CacaPalavras, Integer>{

    @Query("select new com.felypeganzert.cacapalavras.entidades.dto.InformacoesBasicasCacaPalavrasDTO"
            + "(c.id, c.dataCriacao, c.criador, c.titulo) from CacaPalavras c")
	List<InformacoesBasicasCacaPalavrasDTO> findAllComInformacoesBasicas();
    
}
