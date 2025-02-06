package src.main.java.triviaapi;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Question {
	private String category;
	private String type;
	private String difficulty;
	private String question;
	private String correct_answer;
	private List<String> incorrect_answers;

//Getters n Setters//
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getType() {
		return type;

	}

	public void setType(String type) {
		this.type = type;

	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getCorrectAnswer() {
		return correct_answer;
	}

	public void setCorrectAnswer(String correct_answer) {
		this.correct_answer = correct_answer;
	}

	public List<String> getIncorrectAnswers() {
		return incorrect_answers;
	}

	public void setIncorrectAnswers(List<String> incorrect_answers) {
		this.incorrect_answers = incorrect_answers;
	}
	public List<String> getShuffledAnswers() {
	    List<String> allAnswers = new ArrayList<>(incorrect_answers);
	    allAnswers.add(correct_answer); 
	    Collections.shuffle(allAnswers);
	    return allAnswers;
	}
	}

