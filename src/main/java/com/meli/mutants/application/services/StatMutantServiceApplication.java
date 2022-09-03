package com.meli.mutants.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meli.mutants.application.dtos.StatDto;
import com.meli.mutants.application.mappers.StatMapper;
import com.meli.mutants.domain.services.StatMutantService;

@Service
public class StatMutantServiceApplication {

	@Autowired
	private StatMutantService statService;
	private static final StatMapper statMapper = StatMapper.getInstance();

	public StatDto getStats() {
		return statMapper.toApplication(statService.getStats());
	}

}
