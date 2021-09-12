package com.felypeganzert.cacapalavras.rest.controller;

import com.felypeganzert.cacapalavras.exception.PalavraJaExisteNoCacaPalavrasException;
import com.felypeganzert.cacapalavras.exception.RecursoNaoEncontradoException;
import com.felypeganzert.cacapalavras.exception.RecursoNaoPertenceAException;
import com.felypeganzert.cacapalavras.exception.RegraNegocioException;
import com.felypeganzert.cacapalavras.rest.payload.ApiError;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiError resolveException(RegraNegocioException exc) {
        ApiError apiError = new ApiError(exc.getMessage());
        return apiError;
	}
    
    @ExceptionHandler(PalavraJaExisteNoCacaPalavrasException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiError resolveException(PalavraJaExisteNoCacaPalavrasException exc) {
        ApiError apiError = new ApiError(exc.getMessage());
        return apiError;
	}

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiError resolveException(RecursoNaoEncontradoException exc) {
        ApiError apiError = new ApiError(exc.getMessage());
        return apiError;
	}

    @ExceptionHandler(RecursoNaoPertenceAException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiError resolveException(RecursoNaoPertenceAException exc) {
        ApiError apiError = new ApiError(exc.getMessage());
        return apiError;
	}
    
}
