package triviaapi;

import org.apache.commons.text.StringEscapeUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TriviaApiClient {
	
	//URL για το API
	private static final String API_URL = "https://opentdb.com/api.php";
	
	//Ανάκτηση ερωτήσεων με παραμέτρους 
	public Response getTriviaQuestions(int amount, String type, String category,String difficulty) throws Exception {
		
	// Δημιουργία URL με παραμέτρους
	StringBuilder urlString = new StringBuilder (API_URL);
	urlString.append("?amount=").append(amount);
	
		if (type !=null && !type.isEmpty()) {
			urlString.append("&type=").append(type);
		}
		if (category != null && !category.isEmpty()) {
			urlString.append("&category=").append(category);
		}
		 if (difficulty != null && !difficulty.isEmpty()) {
			 urlString.append("&difficulty=").append(difficulty);
	        }
	
	//URL που περιλαμβάνει τις παραμετρους	
		URL url = new URL(urlString.toString());
		
	//Σύνδεση με το API
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		
	//Σε περιπτωση Σφάλματος με το APΙ ή απάντησης
		int responseCode = connection.getResponseCode();
		if (responseCode != 200) {
		    throw new RuntimeException("HTTP error code: " + responseCode);	
		}
	//Αναγνωση απο το API
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder response = new StringBuilder();
		String line;
		while ((line=reader.readLine()) != null) {
			response.append(line);
		}
		reader.close();
		
		//System.out.println("Raw JSON Response: " + response.toString());//
		
	//json-αντικειμενο//
		Gson gson = new Gson();
		try{
			Response triviaResponse = gson.fromJson(response.toString(), Response.class);
			
			for (Question q: triviaResponse.getResults() ) {
				 q.setCategory(StringEscapeUtils.unescapeHtml4(q.getCategory()));
				 q.setQuestion(StringEscapeUtils.unescapeHtml4(q.getQuestion()));
			        q.setCorrectAnswer(StringEscapeUtils.unescapeHtml4(q.getCorrectAnswer()));
			        
			        List<String> decodedIncorrectAnswers= new ArrayList<>();
			        for (String incorrect : q.getIncorrectAnswers()) {
			            decodedIncorrectAnswers.add(StringEscapeUtils.unescapeHtml4(incorrect));
			        }
			        q.setIncorrectAnswers(decodedIncorrectAnswers);
			    }

			    return triviaResponse;
		} 	catch (JsonSyntaxException e) {
			System.out.println("Error passing JSON: " + e.getMessage());
			return null;
			}
		}
	}	

