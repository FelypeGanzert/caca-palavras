package com.felypeganzert.cacapalavras.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecursoNaoPertenceAException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;

	private String nomeRecursoFilho;
	private String nomeRecursoPai;

	public RecursoNaoPertenceAException(String nomeRecursoFilho, String nomeRecursoPai) {
		super();
		this.nomeRecursoFilho = nomeRecursoFilho;
		this.nomeRecursoPai = nomeRecursoPai;
	}

	public String getMessage(){
		return String.format("%s não pertence à %s'", nomeRecursoFilho, nomeRecursoPai);
	}

}