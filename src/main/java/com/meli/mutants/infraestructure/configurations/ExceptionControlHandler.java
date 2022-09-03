package com.meli.mutants.infraestructure.configurations;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.meli.mutants.domain.exceptions.DnaNotMutantException;
import com.meli.mutants.domain.exceptions.InvalidCharacterInSecuenceException;
import com.meli.mutants.domain.exceptions.InvalidMatrixException;

@ControllerAdvice
public class ExceptionControlHandler {

	@ExceptionHandler(InvalidMatrixException.class)
	protected ResponseEntity<String> handleBadRequestError(final HttpServletRequest request,
			final InvalidMatrixException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}

	@ExceptionHandler(DnaNotMutantException.class)
	protected ResponseEntity<String> handleForbidden(final HttpServletRequest request,
			final DnaNotMutantException exception) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
	}

	@ExceptionHandler(InvalidCharacterInSecuenceException.class)
	protected ResponseEntity<String> handleBadRequestError(final HttpServletRequest request,
			final InvalidCharacterInSecuenceException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}
}
