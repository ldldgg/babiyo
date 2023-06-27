package com.mealmaker.babiyo.review.model;

import com.mealmaker.babiyo.util.ImageDto;

import lombok.Data;

@Data
public class BuyProductDto {

	private int productNo;
	private String productName;
	private int count;
	private ImageDto imgDto;
	
}
