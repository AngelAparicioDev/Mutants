package com.meli.mutants.application.mappers;

import com.meli.mutants.application.dtos.StatDto;

public class StatMapper {

	private static final StatMapper INSTANCE = new StatMapper();

	private StatMapper() {
	}

	public static StatMapper getInstance() {
		return INSTANCE;
	}

	public StatDto toApplication(com.meli.mutants.domain.dtos.StatDto dominio) {
		StatDto application = new StatDto();
		application.setCount_human_dna(dominio.getCount_human_dna());
		application.setCount_mutant_dna(dominio.getCount_mutant_dna());
		application.setRatio(dominio.getRatio());

		return application;
	}

}
