package com.sapient.teampositionap.model.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeagueDTO {
	@JsonProperty(value = "country_id")
	private String countryId;
	
	@JsonProperty(value = "league_id")
	private String leagueId;
	
	@JsonProperty(value = "league_name")
	private String leagueName;
}
