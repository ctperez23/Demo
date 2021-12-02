package com.example.demo.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ReviewDto;
import com.example.demo.services.ReviewService;

@RestController
@RequestMapping("/business")
public class BusinessController {
	
	@Autowired
	ReviewService reviewService;
	
	@RequestMapping("/reviews")
	public List<Map<String, List<ReviewDto>>> getReviewsForBusiness(@RequestParam String search) throws IOException {
		return reviewService.getReviewsForBusiness(search);
	}

}
