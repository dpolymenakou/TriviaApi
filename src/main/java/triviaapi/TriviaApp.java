package triviaapi;

public class TriviaApp {
	public static void main(String[] args) {
		TriviaApiClient apiClient = new TriviaApiClient();

		try {
			Response response = apiClient.getTriviaQuestions();
			if (response.getResponseCode() == 0) {
				System.out.println("Trivia Questions Retrived Succesfully");
				System.out.println();

				for (Question question : response.getResults()) {
					System.out.println("Category: " + question.getCategory());
					System.out.println("Difficulty: " + question.getDifficulty());
					System.out.println("Question: " + question.getQuestion());
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
