package triviaapi;

import java.util.List;

public class Question {
	private String category;
	private String type;
	private String difficulty;
	private String Question;
	private String correctAnswer;
	private List<String> incorrectAnswers;

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
		return Question;
	}

	public void setQuestion(String question) {
		this.Question = question;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public List<String> getIncorrectAnswers() {
		return incorrectAnswers;
	}

	public void setIncorrectAnswers(List<String> incorrectAnswers) {
		this.incorrectAnswers = incorrectAnswers;
	}
}

