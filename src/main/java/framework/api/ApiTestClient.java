package framework.api;

import framework.utils.config.PropertiesUtils;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/**
 * Config-driven HTTP client for API E2E. Base URI and auth come from {@code config.properties} or env-specific overrides.
 */
public final class ApiTestClient {

    private final RequestSpecification spec;

    private ApiTestClient(RequestSpecification spec) {
        this.spec = spec;
    }

    public static ApiTestClient fromConfig(PropertiesUtils config) {
        String baseUri = config.getProperty("api.baseUri", "https://jsonplaceholder.typicode.com");
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON);

        String authHeaderName = config.getProperty("api.authHeaderName", "");
        String authHeaderValue = config.getProperty("api.authHeaderValue", "");
        if (!authHeaderName.isBlank() && !authHeaderValue.isBlank()) {
            builder.addHeader(authHeaderName, authHeaderValue);
        }

        return new ApiTestClient(builder.build());
    }

    public ValidatableResponse get(String path) {
        return given().spec(spec).when().get(path).then();
    }

    public ValidatableResponse post(String path, Object body) {
        return given().spec(spec).body(body).when().post(path).then();
    }

    public ValidatableResponse put(String path, Object body) {
        return given().spec(spec).body(body).when().put(path).then();
    }

    public ValidatableResponse delete(String path) {
        return given().spec(spec).when().delete(path).then();
    }

    public RequestSpecification specification() {
        return spec;
    }
}
