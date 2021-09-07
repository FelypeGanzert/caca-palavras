package com.felypeganzert.cacapalavras.repository;

import com.felypeganzert.cacapalavras.entidades.Letra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LetraRepository extends JpaRepository<Letra, Integer>{

    @Modifying
    @Query("DELETE FROM Letra l WHERE l.tabuleiro.id = :idTabuleiro")
    void deleteAllFromTabuleiro(Integer idTabuleiro);
    
}
