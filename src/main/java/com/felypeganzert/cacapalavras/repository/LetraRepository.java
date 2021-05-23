package com.felypeganzert.cacapalavras.repository;

import com.felypeganzert.cacapalavras.entidades.Letra;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LetraRepository extends JpaRepository<Letra, Long>{
    
}
