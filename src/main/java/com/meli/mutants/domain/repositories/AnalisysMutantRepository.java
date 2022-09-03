package com.meli.mutants.domain.repositories;

import java.util.Map;

public interface AnalisysMutantRepository {

	public boolean saveAnalisys(String dna, boolean isMutant);

	public Map<String, Integer> findMutants();

}
