package com.example.demo.services;

import java.io.IOException;
import java.util.List;

import com.example.demo.dto.ReviewDto;

public interface ReviewService {
	List<ReviewDto> getReviewsForBusiness(String name) throws IOException;
}
