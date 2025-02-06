package src.main.java.triviaapi;

import java.util.List;

public class Response {
	private int responseCode;
	private List<Question> results;

//Getters and Setters//
	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public List<Question> getResults() {
		return results;
	}

	public void setResults(List<Question> results) {
		this.results = results;
	}
}