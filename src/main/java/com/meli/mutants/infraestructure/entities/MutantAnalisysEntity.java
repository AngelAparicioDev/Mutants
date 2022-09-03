package com.meli.mutants.infraestructure.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "mutantAnalysis")
public class MutantAnalisysEntity {

	@DynamoDBHashKey
	private String secuenceDna;

	@DynamoDBAttribute
	private boolean isMutant;

	public String getSecuenceDna() {
		return secuenceDna;
	}

	public void setSecuenceDna(String secuenceDna) {
		this.secuenceDna = secuenceDna;
	}

	public boolean isMutant() {
		return isMutant;
	}

	public void setMutant(boolean isMutant) {
		this.isMutant = isMutant;
	}

}
