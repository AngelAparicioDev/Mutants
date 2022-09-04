package com.meli.mutants.domain.services;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.meli.mutants.domain.exceptions.DnaNotMutantException;
import com.meli.mutants.domain.exceptions.InvalidCharacterInSecuenceException;
import com.meli.mutants.domain.exceptions.InvalidMatrixException;
import com.meli.mutants.domain.repositories.AnalisysMutantRepository;

public class MutantService {

	private static final String DNA_NITROGEN_BASE_A = "AAAA";
	private static final String DNA_NITROGEN_BASE_T = "TTTT";
	private static final String DNA_NITROGEN_BASE_G = "GGGG";
	private static final String DNA_NITROGEN_BASE_C = "CCCC";
	private AnalisysMutantRepository userRepository;

	public MutantService(AnalisysMutantRepository userRepository) {
		this.userRepository = userRepository;
	}

	// Metodo que valida la cantidad de cadenas mutantes dentro de un array
	// si encuentra al menos 2 define que es un mutante y frena el proceso
	public boolean isMutant(String[] dna) {

		if (dna == null || !isValidMatrix(dna)) {
			throw new InvalidMatrixException("The matrix is not a square matrix !!");
		}

		String secuence = Arrays.stream(dna).collect(Collectors.joining());

		if (containsInvalidCharacters(secuence)) {
			throw new InvalidCharacterInSecuenceException("The matrix contains invalid characters !!");
		}

		int numberOfMutantSecuenceInDna = counterOfMutantStringsVertically(dna);

		if (containsMoreThanOneMutantSecuence(numberOfMutantSecuenceInDna)) {
			userRepository.saveAnalisys(secuence, true);
			return true;
		}

		numberOfMutantSecuenceInDna += counterOfMutantStringsHorizontally(dna);

		if (containsMoreThanOneMutantSecuence(numberOfMutantSecuenceInDna)) {
			userRepository.saveAnalisys(secuence, true);
			return true;
		}

		numberOfMutantSecuenceInDna += counterOfMutantStringsObliquely(dna);
		if (containsMoreThanOneMutantSecuence(numberOfMutantSecuenceInDna)) {
			userRepository.saveAnalisys(secuence, true);
			return true;
		}

		userRepository.saveAnalisys(secuence, false);
		throw new DnaNotMutantException("Dna validated is not mutant !!");

	}

	// Metodo para verificar si la secuencia es mutante
	private boolean containsMutantDna(String secuence) {
		return secuence.equalsIgnoreCase(DNA_NITROGEN_BASE_A) || secuence.equalsIgnoreCase(DNA_NITROGEN_BASE_T)
				|| secuence.equalsIgnoreCase(DNA_NITROGEN_BASE_G) || secuence.equalsIgnoreCase(DNA_NITROGEN_BASE_C);
	}

	// Metodo para verificar si la secuencia contiene caracteres no validos
	private boolean containsInvalidCharacters(String secuence) {
		int secuenceInitial = secuence.length();
		int secuenceFinal = secuence.replaceAll("[^ACTG]", "").length();

		return secuenceInitial != secuenceFinal;
	}

	// Metodo que permite contar el numero de cadenas que se identifican como
	// mutantes verticalmente si el numero de secuencias que
	// contienen adn mutante llega a 2 rompe el ciclo
	private int counterOfMutantStringsVertically(String[] dna) {
		int numberOfMutantSecuence = 0;
		String secuence = "";
		for (int i = 0; i < dna.length; i++) {
			for (int j = 0; j < dna.length; j++) {
				secuence = secuence + dna[j].substring(i, i + 1);
			}
			numberOfMutantSecuence += counterOfMutantStringsInString(secuence);
			if (containsMoreThanOneMutantSecuence(numberOfMutantSecuence)) {
				break;
			}

			secuence = "";
		}
		return numberOfMutantSecuence;
	}

	// Metodo que permite contar el numero de cadenas que se identifican como
	// mutantes horizontalmente si el numero de secuencias que
	// contienen adn mutante llega a 2 rompe el ciclo
	private int counterOfMutantStringsHorizontally(String[] dna) {
		int numberOfMutantSecuence = 0;

		for (String secuence : dna) {
			numberOfMutantSecuence += counterOfMutantStringsInString(secuence);
			if (containsMoreThanOneMutantSecuence(numberOfMutantSecuence)) {
				break;
			}
		}
		return numberOfMutantSecuence;
	}

	// Metodo que permite contar el numero de cadenas que se identifican como
	// mutantes oblicuamente si el numero de secuencias que
	// contienen adn mutante llega a 2 rompe el ciclo
	private int counterOfMutantStringsObliquely(String[] dna) {
		int numberOfMutantSecuence = 0;
		int n = dna.length;
		String secuence = "";

		for (int i = 1 - n; i < n; i++) {
			for (int x = -min(0, i), y = max(0, i); x < n && y < n; x++, y++) {
				secuence = secuence + dna[y].substring(x, x + 1);
			}

			numberOfMutantSecuence += counterOfMutantStringsInString(secuence);

			if (containsMoreThanOneMutantSecuence(numberOfMutantSecuence)) {
				break;
			}
			secuence = "";
		}

		return numberOfMutantSecuence;
	}

	// Metodo que me permite contar el numero de cadenas que se identifican como
	// mutantes en una cadena de texto sin traslapes si el numero de secuencias que
	// contienen adn mutante llega a 2 rompe el ciclo
	private int counterOfMutantStringsInString(String secuence) {
		int characterCounter = 0;
		int numberOfMutantSecuence = 0;

		while (characterCounter + 4 <= secuence.length()) {
			if (containsMutantDna(secuence.substring(characterCounter, characterCounter + 4))) {
				numberOfMutantSecuence++;
				characterCounter = characterCounter + 3;
			}
			if (containsMoreThanOneMutantSecuence(numberOfMutantSecuence)) {
				break;
			}
			characterCounter++;
		}
		return numberOfMutantSecuence;
	}

	// Metodo para verificar que se trata de una matriz cuadrada
	private boolean isValidMatrix(String[] array) {

		int columnsSize = array.length;
		for (String row : array) {
			if (columnsSize != row.length()) {
				return false;
			}
		}

		return true;
	}

	// Metodo que permite validar si ya contiene almenos 2 secuencias
	private boolean containsMoreThanOneMutantSecuence(int numberOfMutantSecuence) {
		return numberOfMutantSecuence > 1;
	}

}
