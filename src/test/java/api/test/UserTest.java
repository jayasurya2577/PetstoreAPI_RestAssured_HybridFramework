package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import api.endpoints.UserEndpoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTest {
	Faker faker;
	User userPayload;
	@BeforeClass
	public void setUp() {
		faker = new Faker();
		userPayload = new User();
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 8));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
	}
	@Test(priority = 1)
	public void testPostUser() {
		Response res =UserEndpoints.createUser(userPayload);
		res.then().log().body();
		Assert.assertEquals(res.statusCode(), 200);
	}
	@Test(priority = 2)
	public void testgetReqByUsername() {
		Response res =UserEndpoints.readUser(this.userPayload.getUsername()).then().extract().response();
		res.then().log().body();
		Assert.assertEquals(res.statusCode(), 200);
	}
	@Test(priority = 3)
	public void testputReqByUsername() {
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setPhone(faker.phoneNumber().phoneNumber());
		Response res =UserEndpoints.UpdateUser(this.userPayload.getUsername(), userPayload);
		res.then().log().body();
		Assert.assertEquals(res.statusCode(), 200);
	}
}
