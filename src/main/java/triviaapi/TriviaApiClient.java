package triviaapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class TriviaApiClient {
    private static final Logger logger = LogManager.getLogger(TriviaApiClient.class);
    private static final String API_URL = "https://opentdb.com/api.php";

    /**
     * Ανάκτηση ερωτήσεων από το Trivia API.
     *
     * @param amount     Αριθμός ερωτήσεων.
     * @param type       Τύπος ερωτήσεων (π.χ. "multiple", "boolean").
     * @param category   Κατηγορία ερωτήσεων.
     * @param difficulty Δυσκολία ερωτήσεων ("easy", "medium", "hard").
     * @return Αντικείμενο Response που περιέχει τις ερωτήσεις.
     * @throws TriviaAPIException Αν αποτύχει η κλήση στο API.
     */
    public triviaapi.Response getTriviaQuestions(int amount, String type, String category, String difficulty) throws TriviaAPIException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            
        	// Δημιουργία URI με StringBuilder
            StringBuilder uriBuilder = new StringBuilder(API_URL)
                    .append("?amount=").append(amount);

            // Αν η τιμή είναι "any", δεν προσθέτουμε την παράμετρο
            if (type != null && !type.equals("any")) uriBuilder.append("&type=").append(type);
            if (category != null && !category.equals("any")) uriBuilder.append("&category=").append(category);
            if (difficulty != null && !difficulty.equals("any")) uriBuilder.append("&difficulty=").append(difficulty);

            URI uri = new URI(uriBuilder.toString());
            HttpGet request = new HttpGet(uri);
            request.setHeader("Accept", "application/json");

            logger.info("Fetching trivia questions from API: {}", uri);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                HttpEntity entity = response.getEntity();

                if (statusCode != HttpStatus.SC_OK) {
                    logger.error("API returned error code: {}", statusCode);
                    throw new TriviaAPIException("Error from API: HTTP " + statusCode);
                }

                if (entity == null) {
                    logger.error("Empty response from API");
                    throw new TriviaAPIException("Empty response from API");
                }

                ObjectMapper mapper = new ObjectMapper();
                Response triviaResponse = mapper.readValue(entity.getContent(), Response.class);

                // Αποκωδικοποίηση HTML χαρακτήρων στις ερωτήσεις
                decodeHtmlEntities(triviaResponse);

                return triviaResponse;
            }
        } catch (URISyntaxException | IOException e) {
            logger.error("Error processing API request: {}", e.getMessage());
            throw new TriviaAPIException("Error processing API request", e);
        }
    } 
    public void getTriviaQuestionsWithRetry() throws TriviaAPIException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Δημιουργία του αιτήματος και εκτέλεση
            CloseableHttpResponse response = httpClient.execute(new HttpGet("https://opentdb.com/api.php"));
            
            // Έλεγχος για κωδικό 429 (Rate Limit Exceeded)
            if (response.getStatusLine().getStatusCode() == 429) {
                Header retryAfterHeader = response.getFirstHeader("Retry-After");
                if (retryAfterHeader != null) {
                    // Αν η επικεφαλίδα Retry-After υπάρχει, περιμένουμε για το καθορισμένο χρονικό διάστημα
                    long retryAfter = Long.parseLong(retryAfterHeader.getValue());
                    System.out.println("API rate limit exceeded. Retrying after " + retryAfter + " seconds.");
                    Thread.sleep(retryAfter * 1000); // Περιμένουμε το χρόνο που επιστρέφει η επικεφαλίδα
                } else {
                    throw new TriviaAPIException("API rate limit exceeded, but no Retry-After header provided.");
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new TriviaAPIException("Error processing API request", e);
        }
    }
    public Response getTriviaQuestionsWithBackoff(int retries) throws TriviaAPIException {
        int attempts = 0;
        while (attempts < retries) {
            try {
                // Προσπάθεια να καλέσεις το API
                return getTriviaQuestions(5, "multiple", "9", "easy");
            } catch (TriviaAPIException e) {
                if (e.getMessage().contains("429")) {
                    attempts++;
                    long waitTime = (long) Math.pow(2, attempts) * 1000; // Exponential backoff
                  logger.info("Rate limit exceeded. Retrying in " + waitTime / 1000 + " seconds...");
                    try {
                        Thread.sleep(waitTime);
                    } catch (InterruptedException ex) {
                        throw new TriviaAPIException("Error during retry", ex);
                    }
                } else {
                    throw e; // Αν το λάθος δεν είναι από το API, ξαναπετάμε την εξαίρεση
                }
            }
        }
        throw new TriviaAPIException("Exceeded max retries.");
    }
    /**
     * Αποκωδικοποιεί HTML χαρακτήρες στις ερωτήσεις και απαντήσεις.
     *
     * @param response Το Response αντικείμενο από το API.
     */
    private void decodeHtmlEntities(Response response) {
        for (Question q : response.getResults()) {
            q.setCategory(StringEscapeUtils.unescapeHtml4(q.getCategory()));
            q.setQuestion(StringEscapeUtils.unescapeHtml4(q.getQuestion()));
            q.setCorrectAnswer(StringEscapeUtils.unescapeHtml4(q.getCorrectAnswer()));

            List<String> decodedIncorrectAnswers = new ArrayList<>();
            for (String incorrect : q.getIncorrectAnswers()) {
                decodedIncorrectAnswers.add(StringEscapeUtils.unescapeHtml4(incorrect));
            }
            q.setIncorrectAnswers(decodedIncorrectAnswers);
        }
    }
}