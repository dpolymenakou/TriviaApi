package triviaapi;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TriviaApiClient {

private static final String API_URL = "https://opentdb.com/api.php?amount=10&type=multiple";
	
public Response getTriviaQuestions() throws Exception {

URL url = new URL(API_URL);
HttpURLConnection connection = (HttpURLConnection) url.openConnection();
connection.setRequestMethod("GET");

BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
StringBuilder response = new StringBuilder();
String line;

while ((line=reader.readLine()) != null) {
	response.append(line);
}

reader.close();

//json-αντικειμενο//
Gson gson = new Gson();
return gson.fromJson(response.toString(), Response.class);
}
} 
