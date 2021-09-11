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
import javax.persistence.OrderBy;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_localizacao_palavra_tabuleiro")
public class LocalizacaoPalavraNoTabuleiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "id_palavra", nullable = false)
    private Palavra palavra;

    @OneToMany(mappedBy = "localizacaoPalavraNoTabuleiro", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ordems ASC")
    @Builder.Default
    private List<LocalizacaoLetraNoTabuleiro> localizacoesLetrasNoTabuleiro = new ArrayList<LocalizacaoLetraNoTabuleiro>();
    
    public void removerVinculoComLocalizacaoLetra(LocalizacaoLetraNoTabuleiro localizacaoLetra){
        localizacoesLetrasNoTabuleiro.remove(localizacaoLetra);
    }

    public void removeVinculoComLocalizacaoLetras() {
        localizacoesLetrasNoTabuleiro.forEach(l -> l.removerVinculoComLocalizacaoPalavra());
        localizacoesLetrasNoTabuleiro.clear();
    }

    @PreRemove
    public void removerVinculos() {
        removeVinculoComLocalizacaoLetras();
    }
}
