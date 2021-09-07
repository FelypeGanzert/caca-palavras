package com.felypeganzert.cacapalavras.repository;

import com.felypeganzert.cacapalavras.entidades.Palavra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PalavraRepository extends JpaRepository<Palavra, Integer>{

    @Modifying
    @Query("DELETE FROM Palavra p WHERE p.cacaPalavras.id = :idCacaPalavras")
    void deleteAllFromCacaPalavras(Integer idCacaPalavras);
    
}
