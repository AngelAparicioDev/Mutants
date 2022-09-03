package com.meli.mutants.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meli.mutants.domain.services.MutantService;

@Service
public class MutantServiceApplication {

	@Autowired
	private MutantService mutanService;

	private static final String IS_MUTANT = "Congratulations is a mutant !!";

	public String isMutant(String[] dna) {
		return mutanService.isMutant(dna) ? IS_MUTANT : "";
	}

}
