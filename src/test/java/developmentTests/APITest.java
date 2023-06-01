package developmentTests;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;

public class APITest {

    @Test
    public void testAPIEndpoint() {
        // Make an HTTP GET request using Rest Assured
        Response response = RestAssured.get("https://pokeapi.co/api/v2/pokemon/ditto");

        // Validate the response
        response.then()
                .statusCode(200)
                .contentType("application/json");

        // Extract data from the response
        String responseBody = response.getBody().asString();
        // Process and assert on the response data
        // ...
        System.out.println(responseBody);
    }
}
