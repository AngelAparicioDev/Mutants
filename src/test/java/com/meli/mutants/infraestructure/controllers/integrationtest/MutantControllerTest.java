package com.meli.mutants.infraestructure.controllers.integrationtest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.mutants.MutantesApplication;
import com.meli.mutants.infraestructure.model.DnaSequence;

@SpringBootTest(classes = MutantesApplication.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MutantControllerTest {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private MockMvc mockMvc;

	private static final String DNA_MUTANT = "ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG";
	private static final String DNA_HUMAN = "ATGCGA,CAGTGC,TTATAT,AGATGG,CCTCTA,TCACTG";
	private static final String DNA_INVALID_MATRIX = "ATGCGA,CAGTGC,TTATAT,AGATG,CCTCTA,TCACTG";
	private static final String DNA_INVALID_CHARACTERS = "XTGCGA,CAGTGC,TTAAAT,AGATGG,CCTCTA,TCACTG";

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeAll
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void getStatTest() throws Exception {
		this.mockMvc.perform(get("/api/stats")).andExpect(status().isOk());
	}

	@Test
	public void isMutantTest() throws Exception {
		DnaSequence dna = new DnaSequence();
		dna.setDna(DNA_MUTANT.split(","));

		mockMvc.perform(MockMvcRequestBuilders.post("/api/mutant").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dna)))
				.andExpect(status().isOk()).andExpect(content().string("Congratulations is a mutant !!"));
	}

	@Test
	public void isNotMutantTest() throws Exception {
		DnaSequence dna = new DnaSequence();
		dna.setDna(DNA_HUMAN.split(","));

		mockMvc.perform(MockMvcRequestBuilders.post("/api/mutant").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dna)))
				.andExpect(status().isForbidden()).andExpect(content().string("Dna validated is not mutant !!"));
	}

	@Test
	public void isMutantInvalidMatrixTest() throws Exception {
		DnaSequence dna = new DnaSequence();
		dna.setDna(DNA_INVALID_MATRIX.split(","));

		mockMvc.perform(MockMvcRequestBuilders.post("/api/mutant").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dna)))
				.andExpect(status().isBadRequest()).andExpect(content().string("The matrix is not a square matrix !!"));
	}

	@Test
	public void isMutantContainInvalidCharactersTest() throws Exception {
		DnaSequence dna = new DnaSequence();
		dna.setDna(DNA_INVALID_CHARACTERS.split(","));

		mockMvc.perform(MockMvcRequestBuilders.post("/api/mutant").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dna)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("The matrix contains invalid characters !!"));
	}
}
