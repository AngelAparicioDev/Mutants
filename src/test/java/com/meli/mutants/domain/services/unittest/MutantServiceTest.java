package com.meli.mutants.domain.services.unittest;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.boot.test.context.SpringBootTest;

import com.meli.mutants.domain.exceptions.DnaNotMutantException;
import com.meli.mutants.domain.exceptions.InvalidCharacterInSecuenceException;
import com.meli.mutants.domain.exceptions.InvalidMatrixException;
import com.meli.mutants.domain.repositories.AnalisysMutantRepository;
import com.meli.mutants.domain.services.MutantService;

@SpringBootTest
public class MutantServiceTest {

	private MutantService mutantService;

	@Mock
	private AnalisysMutantRepository mutantRepository;

	private static final String SECUENCE_MUTANT_TWO_INTERN_HORIZONTALLY = "ATGCAACAGTGCTTTTGTAGAGGGCCCCTATCACTT";
	private static final String JSON_MUTANT_TWO_INTERN_HORIZONTALLY = "ATGCAA,CAGTGC,TTTTGT,AGAGGG,CCCCTA,TCACTT";
	private static final String SECUENCE_MUTANT_TWO_INTERN_VERTICALLY = "ATGCGACAGCGCTTACGTAGACGGCCTCTATCACTT";
	private static final String JSON_MUTANT_TWO_INTERN_VERTICALLY = "ATGCGA,CAGCGC,TTACGT,AGACGG,CCTCTA,TCACTT";
	private static final String SECUENCE_MUTANT_TWO_INTERN_OBLIQUELY = "ATGCGAATCAGTGCAGTTATATAGAGAAGGCCGGTACAACTGACTGACCCGCTAGTTCAGTGAC";
	private static final String JSON_MUTANT_TWO_INTERN_OBLIQUELY = "ATGCGAAT,CAGTGCAG,TTATATAG,AGAAGGCC,GGTACAAC,TGACTGAC,CCGCTAGT,TCAGTGAC";
	private static final String SECUENCE_NOT_MUTANT = "ATGCGACAGTGCTTATGTAGAGAGCCTCTATCACTG";
	private static final String JSON_NOT_MUTANT = "ATGCGA,CAGTGC,TTATGT,AGAGAG,CCTCTA,TCACTG";
	private static final String SECUENCE_MUTANT_COMBINATED_SECUENCE = "ATGCGACAGTGCTTATGTAGAAGGCCCCTATCACTG";
	private static final String JSON_MUTANT_COMBINATED_SECUENCE = "ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG";
	private static final String SECUENCE_INVALID_MATRIX = "ATGCGACAGTGCTTATGTAGAAGGCCCCTATCACT";
	private static final String JSON_INVALID_MATRIX = "ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACT";
	private static final String SECUENCE_MUTANT_INVALID_CHARACTERS = "ATGCAACAGTGCTTTTGTAGAGGGCCCCTATCACTH";
	private static final String JSON_MUTANT_INVALID_CHARACTERS = "ATGCAA,CAGTGC,TTTTGT,AGAGGG,CCCCTA,TCACTH";

	@Test
	public void isMutantHorizontalSecuenceTest() {
		// Arrange
		when(this.mutantRepository.saveAnalisys(SECUENCE_MUTANT_TWO_INTERN_HORIZONTALLY, true)).thenReturn(true);
		mutantService = new MutantService(mutantRepository);

		// Act
		boolean isMutant = mutantService.isMutant(dnaBuild(JSON_MUTANT_TWO_INTERN_HORIZONTALLY));

		// Assert
		assertEquals(true, isMutant);

	}

	@Test
	public void isMutantVerticalSecuenceTest() {
		// Arrange
		when(this.mutantRepository.saveAnalisys(SECUENCE_MUTANT_TWO_INTERN_VERTICALLY, true)).thenReturn(true);
		mutantService = new MutantService(mutantRepository);

		// Act
		boolean isMutant = mutantService.isMutant(dnaBuild(JSON_MUTANT_TWO_INTERN_VERTICALLY));

		// Assert
		assertEquals(true, isMutant);

	}

	@Test
	public void isMutantObliqualSecuenceTest() {
		// Arrange
		when(this.mutantRepository.saveAnalisys(SECUENCE_MUTANT_TWO_INTERN_OBLIQUELY, true)).thenReturn(true);
		mutantService = new MutantService(mutantRepository);

		// Act
		boolean isMutant = mutantService.isMutant(dnaBuild(JSON_MUTANT_TWO_INTERN_OBLIQUELY));

		// Assert
		assertEquals(true, isMutant);

	}

	@Test
	public void isMutantCombinatedSecuenceTest() {
		// Arrange
		when(this.mutantRepository.saveAnalisys(SECUENCE_MUTANT_COMBINATED_SECUENCE, true)).thenReturn(true);
		mutantService = new MutantService(mutantRepository);

		// Act
		boolean isMutant = mutantService.isMutant(dnaBuild(JSON_MUTANT_COMBINATED_SECUENCE));

		// Assert
		assertEquals(true, isMutant);

	}

	@Test
	public void isNotMutantTest() {
		// Arrange
		when(this.mutantRepository.saveAnalisys(SECUENCE_INVALID_MATRIX, false)).thenReturn(false);
		mutantService = new MutantService(mutantRepository);

		try {
			// Act
			mutantService.isMutant(dnaBuild(JSON_NOT_MUTANT));
			fail();
		} catch (DnaNotMutantException e) {
			// assert
			assertEquals("Dna validated is not mutant !!", e.getMessage());
		}

	}

	@Test
	public void invalidSquareMatrixTest() {
		// Arrange
		when(this.mutantRepository.saveAnalisys(SECUENCE_NOT_MUTANT, false)).thenReturn(false);
		mutantService = new MutantService(mutantRepository);

		try {
			// Act
			mutantService.isMutant(dnaBuild(JSON_INVALID_MATRIX));
			fail();
		} catch (InvalidMatrixException e) {
			// assert
			assertEquals("The matrix is not a square matrix !!", e.getMessage());
		}

	}

	@Test
	public void invalidNullMatrixTest() {
		// Arrange
		when(this.mutantRepository.saveAnalisys(null, false)).thenReturn(false);
		mutantService = new MutantService(mutantRepository);

		try {
			// Act
			mutantService.isMutant(null);
			fail();
		} catch (InvalidMatrixException e) {
			// assert
			assertEquals("The matrix is not a square matrix !!", e.getMessage());
		}

	}

	@Test
	public void matrixContainsInvalidCharactersTest() {
		// Arrange
		when(this.mutantRepository.saveAnalisys(SECUENCE_MUTANT_INVALID_CHARACTERS, false)).thenReturn(false);
		mutantService = new MutantService(mutantRepository);

		try {
			// Act
			mutantService.isMutant(dnaBuild(JSON_MUTANT_INVALID_CHARACTERS));
			fail();
		} catch (InvalidCharacterInSecuenceException e) {
			// assert
			assertEquals("The matrix contains invalid characters !!", e.getMessage());
		}

	}

	private String[] dnaBuild(String dna) {
		String[] dnaString = dna.split(",");
		return dnaString;
	}
}
