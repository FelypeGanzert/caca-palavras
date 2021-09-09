package com.felypeganzert.cacapalavras.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_localizacao_palavra_tabuleiro")
public class LocalizacaoPalavraNoTabuleiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_palavra")
    private Palavra palavra;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name =  "id_localizacao_palavra_tabuleiro")
    private List<LocalizacaoLetraNoTabuleiro> localizacoesLetrasNoTabuleiro = new ArrayList<LocalizacaoLetraNoTabuleiro>();
    
}
