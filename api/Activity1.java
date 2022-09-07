package Activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity1 {

    final static String Root_URI = "https://petstore.swagger.io/v2/pet";

    @Test(priority=1)
    public void addPet()
    {
        String reqBody = "{"
                + "\"id\": 134555,"
                + "\"name\": \"Henry\","
                + " \"status\": \"alive\""
                + "}";
        Response response =
                given().contentType(ContentType.JSON)
                .body(reqBody)
                .when().post(Root_URI);

        response.then().body("id",equalTo(134555));
        response.then().body("name",equalTo("Henry"));
        response.then().body("status",equalTo("alive"));
    }
    @Test(priority=2)
    public void getData()
    {
        Response response =
            given().contentType(ContentType.JSON) // Set headers
                    .when().pathParam("petId", "134555") // Set path parameter
                    .get(Root_URI + "/{petId}"); // Send GET request

        // Assertion
        response.then().body("id", equalTo(134555));
        response.then().body("name", equalTo("Henry"));
        response.then().body("status", equalTo("alive"));
    }
    @Test(priority=3)
    public void deletePetId()
    {
        Response response =
                given().contentType(ContentType.JSON) // Set headers
                        .when().pathParam("petId", "134555") // Set path parameter
                        .delete(Root_URI + "/{petId}"); // Send DELETE request

        // Assertion
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("134555"));
    }
}
