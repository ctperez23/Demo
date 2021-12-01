package com.example.demo.dto;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReviewDto {
	
	private String id;
	private String url;
	private String text;
	private String rating;
    @JsonProperty("time_created") private String timeCreated;
	private UserDto user;
	private Map<String, String> Emotions;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(String timeCreated) {
		this.timeCreated = timeCreated;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public Map<String, String> getEmotions() {
		return Emotions;
	}

	public void setEmotions(Map<String, String> emotions) {
		Emotions = emotions;
	}

	public String toString() {
		return "[id="+id +", text="+text+"]";
	}
}
