package com.felypeganzert.cacapalavras.entidades;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_letra")
public class Letra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @NotBlank(message = "Letra não pode ser vazia")
    private char letra;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "id_tabuleiro", nullable = false)
    private Tabuleiro tabuleiro;

    @Embedded
    private Posicao posicao;

    public Letra(char letra, Posicao posicao){
        this.letra = letra;
        this.posicao = posicao;
    }

    public Letra(Tabuleiro tabuleiro, char letra, Posicao posicao){
        this.tabuleiro = tabuleiro;
        this.letra = letra;
        this.posicao = posicao;
    }

}
