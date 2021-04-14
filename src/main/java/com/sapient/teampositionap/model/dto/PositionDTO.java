package com.sapient.teampositionap.model.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class PositionDTO {
	@JsonProperty(value = "team_name")
	private String teamName;
	
	@JsonProperty(value = "team_id")
	private String teamId;
	
	@JsonProperty(value = "overall_league_position")
	private String position;
}
