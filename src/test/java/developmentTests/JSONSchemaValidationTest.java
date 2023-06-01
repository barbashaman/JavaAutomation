package developmentTests;

import io.restassured.path.json.JsonPath;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


public class JSONSchemaValidationTest {

    @Test
    public void testPokemonSchema() {
        // Get the response from the endpoint as a string
        String response = given()
                .baseUri("https://pokeapi.co/api/v2")
                .when()
                .get("/pokemon/ditto")
                .then()
                .assertThat()
                // Validate the schema of the response
                .body(matchesJsonSchemaInClasspath("schemas/pokemon-schema.json"))
                .extract()
                .asString();

        // Parse the response using JsonPath
        JsonPath jsonPath = new JsonPath(response);

        // Verify some values using JsonPath
        assertThat(jsonPath.getInt("id"), equalTo(132));
        assertThat(jsonPath.getString("name"), equalTo("ditto"));
        assertThat(jsonPath.getInt("height"), equalTo(3));
    }
}
