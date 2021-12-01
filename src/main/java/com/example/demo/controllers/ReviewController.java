package com.example.demo.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ReviewDto;
import com.example.demo.services.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;
	
	@RequestMapping("/{name}")
	public List<ReviewDto> getReviewsForBusiness(@PathVariable String name) throws IOException {
		return reviewService.getReviewsForBusiness(name);
	}

}
