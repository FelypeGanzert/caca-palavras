package com.felypeganzert.cacapalavras.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_localizacao_letra_tabuleiro")
public class LocalizacaoLetraNoTabuleiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    private int ordem;

    @ManyToOne
    @JoinColumn(name = "id_letra")
    private Letra letra;

    public LocalizacaoLetraNoTabuleiro(int ordem, Letra letra){
        this.ordem = ordem;
        this.letra = letra;
    }

    public LocalizacaoLetraNoTabuleiro(LocalizacaoLetraNoTabuleiro outro){
        this.ordem = outro.getOrdem();
        this.letra = outro.getLetra();
    }

}
