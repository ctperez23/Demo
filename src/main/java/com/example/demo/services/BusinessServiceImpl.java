package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.demo.dto.BusinessDto;
import com.example.demo.util.YelpUtility;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BusinessServiceImpl implements BusinessService{
	
	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public List<BusinessDto> getBusiness(String name) {
		String searchUrl = YelpUtility.YEP_API_URL + "/search?location=\"wisconsin\"&limit=1&term=\""+name+"\"";
		
		HttpHeaders headers =  new HttpHeaders() {{
			set( "Authorization","Bearer "+ YelpUtility.API_KEY);
		}};
		ResponseEntity<String> response = restTemplate
				.exchange(searchUrl, HttpMethod.GET, new HttpEntity<String>("parameters", headers), String.class);
		
		List<BusinessDto> businessList = new ArrayList<BusinessDto>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode root = mapper.readTree(response.getBody());
			root.get("businesses").forEach(b -> {
				try {
					BusinessDto business = mapper.treeToValue(b, BusinessDto.class);
					businessList.add(business);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});	
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		
		return businessList;
	}
}
