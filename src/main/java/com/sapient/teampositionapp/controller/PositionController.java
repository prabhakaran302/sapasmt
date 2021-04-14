package com.sapient.teampositionapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.teampositionap.model.base.PositionResponse;
import com.sapient.teampositionapp.service.PositionService;

@RestController
@RequestMapping("/api/v1/position")
public class PositionController {

	@Autowired
	public PositionService positionService;

	@GetMapping("/view/{countryName}/{leagueName}/{teamName}")
	public ResponseEntity<PositionResponse> viewPosition(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, @RequestParam(value = "Country Name") String countryName,
			@RequestParam(value = "League Name", required = true) String leagueName,
			@RequestParam(value = "Team Name", required = true) String teamName) throws Exception {
		PositionResponse response = positionService.fetchPosition(countryName, leagueName, teamName);
		
		if (response.getCountryName().equalsIgnoreCase("Not Found"))
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
