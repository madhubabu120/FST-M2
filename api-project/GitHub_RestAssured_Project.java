package Examples;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GitHub_RestAssured_Project {
    //Request  and Response specifications
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    String sshKey;
    int id;

    @BeforeClass
    public void setUp()
    {
        //Request specification
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://api.github.com")
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "token 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")
                .build();

        //Response Specification
        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    @Test(priority = 1)
    public void postRequest()
    {
        //Request body
        Map<String,Object> reqBody = new HashMap<>();
        reqBody.put("title","TestAPIKey");
        reqBody.put("key","ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCZBtl3vYDt8zjFcdXKsCGpcaK1D2kBYz+BeYn+0sznzFg9SacRVB8TF2Qt2KXTZNfHeNDoarsUavQ6dFFuh1gSRY3BZB5S6frFCGUqBGgiBUcnwqQuAHmayZFy6l1IYE5aBscBN4Qj+Z+qpFJnOANULAQq96uJ+qWXDios+tvf/QBWYJGQ/tFBEcTRu/qpxHNrso4C6T2RowfTQwcenbxyGeCiRQxU23n5PkS6HspQWcUbkwBHjhjT6B8Z2vZPC858A3ACeOPMHRiHIuHpF5tHGMqQBVKK7yVy+E2tHZpuyLqxcnKbywF5rnQ9eHqbEPQvo3+FoBSChawmW3BK+8hR");

        Response response = given().log().all().spec(requestSpec).body(reqBody)
                .when().post(" /user/keys");
        id = response.then().extract().body().path("key");
        response.then().spec(responseSpec).log().all();
        response.then().statusCode(201);

    }

    @Test(priority = 2)
    public void getRequest()
    {
        given().spec(requestSpec).log().all().pathParam("keyId",id)
                .when().get("/user/keys/{keyId}")
                .then().spec(responseSpec).log().all()
                .statusCode(200);
    }

    @Test(priority = 3)
    public void deleteRequest()
    {
        given().spec(requestSpec).log().all().pathParam("keyId",id)
                .when().delete("/user/keys/{keyId}")
                .then().log().all().statusCode(204).body("message",equalTo(""+id));

    }


}
