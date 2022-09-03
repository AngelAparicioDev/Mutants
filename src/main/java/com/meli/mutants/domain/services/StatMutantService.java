package com.meli.mutants.domain.services;

import java.util.Map;

import com.meli.mutants.domain.dtos.StatDto;
import com.meli.mutants.domain.repositories.AnalisysMutantRepository;

public class StatMutantService {

	private static final String MUTANT_STRING = "mutants";
	private static final String HUMAN_STRING = "humans";

	private AnalisysMutantRepository userRepository;

	public StatMutantService(AnalisysMutantRepository userRepository) {
		this.userRepository = userRepository;
	}

	// Metodo que devuelve las estadisticas de humanos y mutantes guardados en la bd
	public StatDto getStats() {
		Map<String, Integer> stats = userRepository.findMutants();
		double numberOfMutants = stats.get(MUTANT_STRING);
		double numberOfHumans = stats.get(HUMAN_STRING);
		double ratio = numberOfHumans == 0 ? 0 : numberOfMutants / numberOfHumans;

		StatDto statDto = new StatDto();
		statDto.setCount_human_dna((int) numberOfHumans);
		statDto.setCount_mutant_dna((int) numberOfMutants);
		statDto.setRatio(ratio);

		return statDto;
	}

}
