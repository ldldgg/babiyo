package com.mealmaker.babiyo.review.model;

import lombok.Data;

@Data
public class ReviewListDto {
	
	private int no;
	private String categoryName;
	private String productName;
	private double ratingAvg;
	private int reviewCount;
	private int rowNum;

}
