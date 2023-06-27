package com.mealmaker.babiyo.review.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class ReviewDto {

	private int no;
	private int productNo;
	private String productName;
	private String memberId;
	private String categoryName;
	private double starRating;
	private String content;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date createDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date modifyDate;
	private String name;
	private String nickname;
	private List<String> reviewImg;
	private String productImg;
	
}
