package com.felypeganzert.cacapalavras.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegraNegocioException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;

	public RegraNegocioException(String message) {
		super(message);
	}

}