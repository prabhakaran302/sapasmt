package com.sapient.teampositionapp;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sapient.teampositionap.model.base.PositionResponse;
import com.sapient.teampositionap.model.dto.CountryDTO;
import com.sapient.teampositionap.model.dto.LeagueDTO;
import com.sapient.teampositionapp.service.PositionService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PositionServiceTests {

	@Autowired
	private PositionService positionService;

	@Test
	public void testGetCountry() {
		CountryDTO country = positionService.getCountry("France");
		Assert.assertEquals(country.getId(), "46");
	}

	@Test
	public void testGetPositionFound() {
		LeagueDTO dto = new LeagueDTO();
		dto.setLeagueId("177");
		Assert.assertEquals(positionService.getPosition(dto, "Amiens").getPosition(), "10");
	}

	@Test
	public void testGetPositionLeagueNotFound() {
		LeagueDTO dto = null;
		Assert.assertEquals(positionService.getPosition(dto, "Amiens"), null);
	}

	@Test
	public void testGetLeagueCountryNotFound() {
		CountryDTO country = null;
		LeagueDTO dto = positionService.getLeague(country, "abc");
		Assert.assertEquals(dto, null);
	}

	@Test
	public void testGetPositionNotFound() {
		LeagueDTO dto = new LeagueDTO();
		dto.setLeagueId("177");
		Assert.assertEquals(positionService.getPosition(dto, "Amiens1"), null);
	}

	@Test
	public void testGetLeagueNotFound() {
		CountryDTO country = new CountryDTO();
		country.setName("France");
		country.setId("46");
		LeagueDTO dto = positionService.getLeague(country, "abc");
		Assert.assertEquals(dto, null);
	}

	@Test
	public void testGetLeagueFound() {
		CountryDTO country = new CountryDTO();
		country.setName("France");
		country.setId("46");
		LeagueDTO dto = positionService.getLeague(country, "Ligue 2");
		Assert.assertEquals(dto.getLeagueId(), "177");
	}

	@Test
	public void fetchPositionTest() {
		PositionResponse resp = positionService.fetchPosition("France", "Ligue 2", "Amiens");
		Assert.assertEquals(resp.getPosition(), "5");
	}

	@Test
	public void fetchPositionNotFoundTest() {
		PositionResponse resp = positionService.fetchPosition("France", "Ligue 2", "Amiens1");
		Assert.assertEquals(resp.getPosition(), "Not Found");
		Assert.assertEquals(resp.getTeamId(), "Not Found");
		Assert.assertEquals(resp.getTeamName(), "Not Found");
	}

	@Test
	public void fetchPositionCountryNotFoundTest() {
		PositionResponse resp = positionService.fetchPosition("USA", "Ligue 2", "Amiens1");
		Assert.assertEquals(resp.getCountryId(), "Not Found");
		Assert.assertEquals(resp.getCountryName(), "Not Found");
	}

	@Test
	public void fetchPositionLeagueNotFoundTest() {
		PositionResponse resp = positionService.fetchPosition("France", "Real Madrid", "Amiens1");
		Assert.assertEquals(resp.getTeamId(), "Not Found");
		Assert.assertEquals(resp.getTeamName(), "Not Found");
	}
}
