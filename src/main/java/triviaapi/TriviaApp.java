package triviaapi;

import java.util.List;
import java.util.Scanner;
import triviaapi.TriviaApiClient;
import triviaapi.Question;
import triviaapi.Response;


public class TriviaApp {
	public static void main(String[] args) {
		
		 // Δημιουργία του API Client
		TriviaApiClient apiClient = new TriviaApiClient();

	int amount = 5; // Αριθμος ερωτησεων
	String type = "any";  // Τύπος ερωτήσεων
    String category = "any";  // Κατηγορία 
    String difficulty = "any";  // Βαθμός δυσκολίας
    
    // Κλήση της μεθόδου για την ανάκτηση των ερωτήσεων
		try {
			Response response = apiClient.getTriviaQuestions(amount, type, category, difficulty);
			if (response.getResponseCode() == 0) {
				System.out.println("Trivia Questions Retrived Succesfully");
				System.out.println();
				
	// Εκτύπωση των ερωτήσεων και των απαντήσεων
				for (Question question : response.getResults()) {
					System.out.println("Category: " + question.getCategory());
					System.out.println("Difficulty: " + question.getDifficulty());
					System.out.println("Question: " + question.getQuestion());
					System.out.println("Answers: " + question.getShuffledAnswers());
					System.out.println("Correct Answer: " + question.getCorrectAnswer());
					System.out.println("Incorrect Answer: " + question.getIncorrectAnswers());
					System.out.println("-----");
					
				}
			} else {
				System.out.println(
						"Error: Failed to retrieve trivia questions. Response code: " + response.getResponseCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
