package com.meli.mutants.domain.services.unittest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.meli.mutants.domain.dtos.StatDto;
import com.meli.mutants.domain.repositories.AnalisysMutantRepository;
import com.meli.mutants.domain.services.StatMutantService;

@SpringBootTest
public class StatMutantServiceTest {

	private StatMutantService statMutantService;
	private static final String MUTANT_STRING = "mutants";
	private static final String HUMAN_STRING = "humans";

	@Mock
	private AnalisysMutantRepository mutantRepository;

	@Test
	public void getStatsWithMutantsTest() {
		// Arrange
		Map<String, Integer> stats = new HashMap<>();
		stats.put(MUTANT_STRING, 4);
		stats.put(HUMAN_STRING, 10);
		when(this.mutantRepository.findMutants()).thenReturn(stats);
		statMutantService = new StatMutantService(mutantRepository);

		// Act
		StatDto stat = statMutantService.getStats();

		// Assert
		assertNotNull(stat);
		assertEquals(4, stat.getCount_mutant_dna());
		assertEquals(10, stat.getCount_human_dna());
		assertEquals(0.4, stat.getRatio());

	}
	
	
	@Test
	public void getStatsWithoutMutantsTest() {
		// Arrange
		Map<String, Integer> stats = new HashMap<>();
		stats.put(MUTANT_STRING, 0);
		stats.put(HUMAN_STRING, 0);
		when(this.mutantRepository.findMutants()).thenReturn(stats);
		statMutantService = new StatMutantService(mutantRepository);

		// Act
		StatDto stat = statMutantService.getStats();

		// Assert
		assertNotNull(stat);
		assertEquals(0, stat.getCount_mutant_dna());
		assertEquals(0, stat.getCount_human_dna());
		assertEquals(0.0, stat.getRatio());

	}
}
