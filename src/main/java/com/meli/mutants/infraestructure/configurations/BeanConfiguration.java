package com.meli.mutants.infraestructure.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.meli.mutants.domain.repositories.AnalisysMutantRepository;
import com.meli.mutants.domain.services.StatMutantService;
import com.meli.mutants.domain.services.MutantService;

@Configuration
public class BeanConfiguration {

	@Bean
	MutantService getMutantService(@Lazy AnalisysMutantRepository userRepository) {
		return new MutantService(userRepository);
	}

	@Bean
	StatMutantService getAnalisysMutantService(AnalisysMutantRepository userRepository) {
		return new StatMutantService(userRepository);
	}

}
