package com.meli.mutants.infraestructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meli.mutants.application.dtos.StatDto;
import com.meli.mutants.application.services.MutantServiceApplication;
import com.meli.mutants.application.services.StatMutantServiceApplication;
import com.meli.mutants.infraestructure.model.DnaSequence;

@RestController
@RequestMapping
public class MutantController {

	private MutantServiceApplication mutantServiceApplication;
	private StatMutantServiceApplication statServiceApplication;

	public MutantController(MutantServiceApplication mutantServiceApplication,
			StatMutantServiceApplication statServiceApplication) {
		this.mutantServiceApplication = mutantServiceApplication;
		this.statServiceApplication = statServiceApplication;
	}

	@PostMapping(value = "/mutant")
	public ResponseEntity<String> analyzeMutantCandidateSubject(@RequestBody DnaSequence dna) {
		return ResponseEntity.ok(mutantServiceApplication.isMutant(dna.getDna()));
	}

	@GetMapping(value = "/stats")
	public ResponseEntity<StatDto> analyzeMutantCandidateSubject() {
		return ResponseEntity.ok(statServiceApplication.getStats());
	}

}
