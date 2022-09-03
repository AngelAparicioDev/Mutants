package com.meli.mutants.infraestructure.repositories;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.meli.mutants.infraestructure.entities.MutantAnalisysEntity;

@EnableScan
@Repository
public interface AnalisysMutantRepositoryDynamo extends CrudRepository<MutantAnalisysEntity, String> {

}
