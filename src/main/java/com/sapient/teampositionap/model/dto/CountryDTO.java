package com.sapient.teampositionap.model.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CountryDTO {
	@JsonProperty(value = "country_id")
	private String id;

	@JsonProperty(value = "country_name")
	private String name;
}
