package com.example.demo.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.demo.dto.ReviewDto;
import com.example.demo.util.YelpUtility;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.Feature.Type;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired 
	private CloudVisionTemplate cloudVisionTemplate;
	
	@Autowired 
	private ResourceLoader resourceLoader;

	@Autowired
	private BusinessService businessService;

	@Override
	public List<ReviewDto> getReviewsForBusiness(String name) throws IOException {	
		List<ReviewDto> reviewList = new ArrayList<ReviewDto>();

		ObjectMapper mapper = new ObjectMapper();
		businessService.getBusiness(name)
			.stream()
			.forEach(b -> retrieveReviews(b.getId(), reviewList, mapper));
		
		return reviewList;
	}
	
	private void retrieveReviews(String id, List<ReviewDto> reviewList, ObjectMapper mapper) {
		id = id.replaceAll("^\"+|\"+$", ""); //remove double quotes from the start and end of id
		String url = YelpUtility.YEP_API_URL +"/"+ id +"/reviews";
		
		HttpHeaders headers =  new HttpHeaders() {{
			set( "Authorization","Bearer "+ YelpUtility.API_KEY);
		}};
		
		ResponseEntity<String> reviews = restTemplate
				.exchange(url, HttpMethod.GET, new HttpEntity<String>("parameters", headers), String.class);
		
		try {
			JsonNode reviewRoot = mapper.readTree(reviews.getBody());
			reviewRoot.get("reviews").forEach(r -> {
					try {
						ReviewDto review = mapper.treeToValue(r, ReviewDto.class);
						review.setEmotions(getEmotionsData(review.getUser().getImageUrl()));
						reviewList.add(review);
					} catch (Exception e) {
						throw new RuntimeException(e);
					} 
				}
			);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Map<String, String> getEmotionsData(String url) {
		return Optional.ofNullable(url)
			.map(u -> {
				AnnotateImageResponse response =
					    this.cloudVisionTemplate.analyzeImage(
					        this.resourceLoader.getResource(u), Type.FACE_DETECTION);

					Map<String, String> map = new HashMap<String, String>();
					response.getFaceAnnotationsList()
						.stream()
						.forEach(annotation -> {
							 map.put("joyLikeliHood", annotation.getJoyLikelihood().toString());
							 map.put("surpriseLikelihood", annotation.getSurpriseLikelihood().toString());
							 map.put("sorrowLikelihood", annotation.getSorrowLikelihood().toString());
							 map.put("angerLikeHood", annotation.getAngerLikelihood().toString());
						});
					
					return map;
				
			}).orElse(Collections.emptyMap());
	}

}
