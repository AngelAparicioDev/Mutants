package com.meli.mutants.infraestructure.repositories.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.meli.mutants.domain.repositories.AnalisysMutantRepository;
import com.meli.mutants.infraestructure.entities.MutantAnalisysEntity;
import com.meli.mutants.infraestructure.repositories.AnalisysMutantRepositoryDynamo;

@Service
public class AnalisysMutantDynamoRepositoryImpl implements AnalisysMutantRepository {

	@Autowired
	private @Lazy AnalisysMutantRepositoryDynamo userRepository;

	private static final String MUTANT_STRING = "mutants";
	private static final String HUMAN_STRING = "humans";

	@Override
	public boolean saveAnalisys(String dna, boolean isMutant) {
		MutantAnalisysEntity analisysMutant = new MutantAnalisysEntity();
		analisysMutant.setMutant(isMutant);
		analisysMutant.setSecuenceDna(dna);
		return userRepository.save(analisysMutant) != null;
	}

	@Override
	public Map<String, Integer> findMutants() {
		Iterator<MutantAnalisysEntity> users = userRepository.findAll().iterator();

		List<MutantAnalisysEntity> usersList = StreamSupport
				.stream(Spliterators.spliteratorUnknownSize(users, 0), false).collect(Collectors.toList());

		List<MutantAnalisysEntity> mutantsList = usersList.stream().filter(user -> user.isMutant())
				.collect(Collectors.toList());

		Map<String, Integer> stats = new HashMap<>();
		stats.put(MUTANT_STRING, mutantsList.size());
		stats.put(HUMAN_STRING, usersList.size());

		return stats;
	}

}
