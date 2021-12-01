package com.example.demo.services;

import java.util.List;

import com.example.demo.dto.BusinessDto;

public interface BusinessService {
	List<BusinessDto> getBusiness(String name);
}
