package com.sapient.teampositionapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sapient.teampositionap.model.base.PositionResponse;
import com.sapient.teampositionap.model.dto.CountryDTO;
import com.sapient.teampositionap.model.dto.LeagueDTO;
import com.sapient.teampositionap.model.dto.PositionDTO;
import com.sapient.teampositionap.model.util.Constants;

@Service
public class PositionService {

	@Value("${search.server.baseUrl}")
	private String searchServerURL;

	@Value("${search.server.APIKey}")
	private String apiKey;

	@Autowired
	private RestTemplate restTemplate;

	public PositionResponse fetchPosition(String countryName, String leagueName, String teamName) {
		CountryDTO country = getCountry(countryName);
		LeagueDTO league = getLeague(country, leagueName);
		PositionDTO position = getPosition(league, teamName);
		return createResponse(country, league, position);
	}

	private PositionResponse createResponse(CountryDTO curCountry, LeagueDTO curLeague, PositionDTO position) {
		PositionResponse positionResponse = new PositionResponse();
		positionResponse.setCountryId(curCountry != null ? curCountry.getId() : "Not Found");
		positionResponse.setCountryName(curCountry != null ? curCountry.getName() : "Not Found");
		positionResponse.setLeagueId(curLeague != null ? curLeague.getLeagueId() : "Not Found");
		positionResponse.setLeagueName(curLeague != null ? curLeague.getLeagueName() : "Not Found");
		positionResponse.setTeamId(position != null ? position.getTeamId() : "Not Found");
		positionResponse.setTeamName(position != null ? position.getTeamName() : "Not Found");
		positionResponse.setPosition(position != null ? position.getPosition() : "Not Found");
		return positionResponse;
	}

	public PositionDTO getPosition(LeagueDTO curLeague, String teamName) {
		if (curLeague == null)
			return null;
		String url = getURL(Constants.getStandingsAPI, new String[] { Constants.LEAGUE_ID, curLeague.getLeagueId() });
		ResponseEntity<List<PositionDTO>> listPositions = restTemplate.exchange(url.toString(), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PositionDTO>>() {
				});
		return listPositions.getBody().stream()
				.filter(position -> teamName.toUpperCase().equalsIgnoreCase(position.getTeamName().toUpperCase()))
				.findAny().orElse(null);
	}

	public LeagueDTO getLeague(CountryDTO curCountry, String leagueName) {
		if (curCountry == null)
			return null;
		String url = getURL(Constants.getLeaguesAPI, new String[] { Constants.COUNTRY_ID, curCountry.getId() });
		ResponseEntity<List<LeagueDTO>> listLeagues = restTemplate.exchange(url.toString(), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<LeagueDTO>>() {
				});
		return listLeagues.getBody().stream()
				.filter(league -> leagueName.toUpperCase().equalsIgnoreCase(league.getLeagueName().toUpperCase()))
				.findAny().orElse(null);
	}

	public CountryDTO getCountry(String countryName) {
		String url = getURL(Constants.getCountryAPI, new String[] {});
		ResponseEntity<List<CountryDTO>> listCountry = restTemplate.exchange(url.toString(), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<CountryDTO>>() {
				});
		return listCountry.getBody().stream()
				.filter(country -> countryName.toUpperCase().equalsIgnoreCase(country.getName().toUpperCase()))
				.findAny().orElse(null);
	}

	private String getURL(String api, String... param) {
		StringBuilder url = new StringBuilder(searchServerURL);
		url.append(Constants.SLASH).append(Constants.QUESTION_MARK).append(Constants.ACTION).append(api)
				.append(Constants.AND).append(Constants.APIKEY).append(apiKey);
		if (param.length != 0) {
			url.append(Constants.AND).append(param[0]).append(param[1]);
		}
		return url.toString();
	}
}
