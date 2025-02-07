package triviaapi;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

	//Δήλωση ότι τα μη null πεδία περιλαμβάνονται στην JSON
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({
    "category",
    "type",
    "difficulty",
    "question",
    "correct_answer",
    "incorrect_answers"
})
		public class Question {
		//Κατηγορία ερώτησης
		@JsonProperty("category")
		private String category;
 
		//Τύπος ερώτησης 
		@JsonProperty("type")
		private String type;
		
		//Βαθμός δυσκολίας
		@JsonProperty("difficulty")
		private String difficulty;
    
		//Κείμενο ερώτησης   
		@JsonProperty("question")
		private String question;
    
		//Σωστή απάντηση
		@JsonProperty("correct_answer")
		private String correctAnswer;
    
		//Λίστα με λάθος απαντήσεις
	    @JsonProperty("incorrect_answers")
	    private List<String> incorrectAnswers;

	 //Ιδιότητες που μπορεί να υπάρχουν στο JSON και δεν εχουν οριστει ως πεδία
	    @JsonIgnore
	    private Map<String, Object> additionalProperties = new LinkedHashMap<>();

	 // Default constructor (σημαντικό αν χρησιμοποιείς Jackson για deserialization)
	    public Question() {}

	    // Parameterized constructor
	    public Question(String category, String type, String difficulty, String question, 
	                    String correctAnswer, List<String> incorrectAnswers) {
	        this.category = category;
	        this.type = type;
	        this.difficulty = difficulty;
	        this.question = question;
	        this.correctAnswer = correctAnswer;
	        this.incorrectAnswers = incorrectAnswers;
	    }
	    
	    // Getters and Setters
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
	
	    //Μέθοδος που επιστρέφει μια ανακατεμένη λίστα απαντήσεων
	    public List<String> getShuffledAnswers() {
	        List<String> allAnswers = new ArrayList<>(incorrectAnswers);
	        allAnswers.add(correctAnswer);
	        Collections.shuffle(allAnswers);
	        return allAnswers;
	    }
	 //Επιστρέφει τις επιπλέον ιδιότητες που ενδέχεται να υπαρχουν στο JSON
	    @JsonAnyGetter
	    public Map<String, Object> getAdditionalProperties() {
	        return this.additionalProperties;
	    }
	 //Αποθηκεύει μια επιπλέον ιδιότητα που δεν έχει οριστεί 
	    @JsonAnySetter
	    public void setAdditionalProperty(String name, Object value) {
	        this.additionalProperties.put(name, value);
	    }
	}
