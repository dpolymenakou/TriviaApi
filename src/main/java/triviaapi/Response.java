package triviaapi;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

	// Δηλώνουμε ότι τα μη-null πεδία περιλαμβάνονται στην JSON αναπαράσταση
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({
    "response_code",
    "results"
	})
	public class Response {

    // Κωδικός απόκρισης του API (0 = επιτυχία, 1+ = αποτυχία)
    @JsonProperty("response_code")
    private int responseCode;

    // Λίστα με τις ερωτήσεις που επιστρέφονται από το API
    @JsonProperty("results")
    private List<Question> results;

    // Ιδιότητες που μπορεί να υπάρχουν στο JSON και δεν έχουν οριστεί ως πεδία
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<>();

    // Getters and Setters
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

    // Επιστρέφει τις επιπλέον ιδιότητες που ενδέχεται να υπάρχουν στο JSON
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    // Αποθηκεύει μια επιπλέον ιδιότητα που δεν έχει οριστεί ρητά ως πεδίο
    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}