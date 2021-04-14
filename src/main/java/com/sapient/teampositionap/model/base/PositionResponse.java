package com.sapient.teampositionap.model.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionResponse {
	private String countryName;
	private String countryId;

	private String teamName;
	private String teamId;

	private String leagueName;
	private String leagueId;

	private String position;
}
