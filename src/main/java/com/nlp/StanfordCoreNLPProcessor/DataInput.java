package com.nlp.StanfordCoreNLPProcessor;

public class DataInput {

	String brand;
	String sentiment;
	String category;
	String issue;
	String model;

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSentiment() {
		return sentiment;
	}

	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}

	public String getCategory() {
		return category;
	}

	@Override
	public String toString() {
		return brand + "," + category + "," + issue + "," + model + "," + issue + "," + sentiment;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

}
