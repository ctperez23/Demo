package com.example.demo.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.example.demo.dto.ReviewDto;

public interface ReviewService {
	List<Map<String, List<ReviewDto>>> getReviewsForBusiness(String name) throws IOException;
}
