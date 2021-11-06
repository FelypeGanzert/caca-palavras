package com.felypeganzert.cacapalavras.entidades;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "tb_palavra")
public class Palavra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @NotBlank(message = "Palavra não pode ser vazia")
    private String palavra;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "id_caca_palavras", nullable = false)
    private CacaPalavras cacaPalavras;

    @OneToMany(mappedBy = "palavra", cascade = CascadeType.ALL, orphanRemoval=true)
    @Builder.Default
    private Set<LocalizacaoPalavra> localizacoes = new HashSet<LocalizacaoPalavra>();

    public Palavra(String palavra) {
        this.palavra = palavra;
    }

}
