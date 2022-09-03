package com.meli.mutants.domain.exceptions;

public class DnaNotMutantException extends RuntimeException {

	private static final long serialVersionUID = 54671L;

	public DnaNotMutantException(String msg) {
		super(msg);
	}
}
