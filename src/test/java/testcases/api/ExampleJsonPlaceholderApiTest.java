package testcases.api;

import framework.api.ApiTestClient;
import framework.utils.config.PropertiesUtils;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

/**
 * API E2E example against a public test API (no auth). Base URI is {@code api.baseUri} in config.
 */
public class ExampleJsonPlaceholderApiTest {

    private ApiTestClient client;

    @Before
    public void setUp() {
        client = ApiTestClient.fromConfig(new PropertiesUtils());
    }

    @Test
    public void getPostById_returnsJsonWithMatchingId() {
        client.get("/posts/1")
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", Matchers.equalTo(1))
                .body("userId", Matchers.notNullValue());
    }
}
