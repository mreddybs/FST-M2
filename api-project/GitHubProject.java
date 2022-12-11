package liveProject;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;


public class GitHubProject {
    String sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAAAgQDLfHvEy7sGKalC7EWpQqUI4LNNLV9cGrB6KUVQIQmVsI1VAwEjYEMP1WvFFLke4G2YnXt9bR/6uwoAL2GR64SieiZAay7PW8aZezP5U89pEv5xV75sKb5dGJh+bxsZmDmaKYFA7Kthq4cFUAjYoWVtz/L7yXnY1Hwwq0hcbYBt9w==";
    int sshKeyId;
    //Request specification
    RequestSpecification requestspec = new RequestSpecBuilder().
            setBaseUri("https://api.github.com/user/keys").
            addHeader("Authorization", "token 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").
            addHeader("Content-Type", "application/json").
            build();
    //Response specification

    ResponseSpecification responsespec = new ResponseSpecBuilder().
            expectResponseTime(lessThan(4000L)).
            expectBody("key", equalTo(sshKey)).
            expectBody("title", equalTo("TestAPIKey")).
            build();

    @Test(priority = 1)
    public void postRequestTest() {
        //path: https:
        Map<String, String> reqBody = new HashMap<>();
        reqBody.put("title", "TestAPIKey");
        reqBody.put("key", sshKey);


        Response response = given().spec(requestspec).body(reqBody).
                when().post();
        sshKeyId = response.then().extract().path("id");
        response.then().statusCode(201).spec(responsespec);

    }

    @Test(priority = 2)
    public void getRequestTest() {
        given().spec(requestspec).pathParam("keyId", sshKeyId).
                when().get("/{keyId}").
                then().statusCode(200).spec(responsespec);
    }

    @Test(priority = 3)
    public void deleteRequestTest() {
        given().spec(requestspec).pathParam("keyId", sshKeyId).
                when().delete("/{keyId}").
                then().statusCode(204).time(lessThan(3000L));
    }
}