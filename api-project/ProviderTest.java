package liveProject;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)
public class ProviderTest {
    Map<String, String> headers = new HashMap<>();
    String resourcePath = "/api/users";

    @Pact(consumer = "UserConsumer", provider = "UserProvider")
    public RequestResponsePact consumerTest(PactDslWithProvider builder) {
        headers.put("Content-Type", "application/json");


        DslPart requestResponseBody = new PactDslJsonBody().
                numberType("id", 4176).
                stringType("firstName", "maninder").
                stringType("lastName", "reddy").
                stringType("email", "maninder@mail.com");
        return builder.given("A request to create a user").
                uponReceiving("A request to create a user").
                method("POST").
                path(resourcePath).
                headers(headers).
                body(requestResponseBody).
                willRespondWith().
                status(201).
                body(requestResponseBody).
                toPact();
    }

    @Test
    @PactTestFor(providerName = "UserProvider", port = "8282")
    public void consumerTest() {
        String baseUri = "http://localhost:8282";

        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("id", 4176);
        reqBody.put("firstName", "maninder");
        reqBody.put("lastName", "reddy");
        reqBody.put("email", "maninder@mail.com");

        given().headers(headers).body(reqBody).log().all().
                when().post(baseUri + resourcePath).
                then().statusCode(201).log().all();
    }
}