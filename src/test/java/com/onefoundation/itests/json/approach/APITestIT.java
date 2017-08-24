package com.onefoundation.itests.json.approach;

import static com.jayway.restassured.RestAssured.given;
import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.response.Response;
import com.onefoundation.itests.AbstractITest;
import com.onefoundation.itests.common.RestUtil;
import com.onefoundation.itests.model.HelloTestData;

// TODO: Auto-generated Javadoc
/**
 * Automated Integration Tests based on the data parameters provided by the
 * test-data.yml file
 * 
 * @author tsukhu
 *
 */
public class APITestIT extends AbstractITest {
	
	/**  Test Data. */
	@Parameter(value = 0)
	public HelloTestData testData;

	/**
	 *  Parameterized Data .
	 *
	 * @return the list
	 * @throws InstantiationException the instantiation exception
	 * @throws Exception the exception
	 */
	@Parameters(name = "ITest #{index}")
	public static List<HelloTestData> data() throws InstantiationException, Exception {
		return RestUtil.getJsonData("hello",HelloTestData.class);
	}


	/**
	 * Setup.
	 */
	@Before
	public void setUp() {

		super.setUp();
	}

	/**
	 * Test quick hello with rest assured.
	 */
	@Test
	public void testQuickHelloWithRestAssured() {
		System.out.println("Test Data = " + testData.toString());
		final String name = this.testData.getInput();
		final String expected = this.testData.getExpected();
		// when:
		final Response response = given().spec(rspec).parameters("name", name).get("/idp/cart/v1/hello");
		// then:
		response.then().log().all(); // log result
		response.then().body("message", equalTo(expected)); // Auto JSON
															// serialization
		// Hard Assert
		assertThat(response.statusCode()).isEqualTo(200);
		assertThat(response.header("Content-Type")).isEqualTo("application/json");
		assertThat(response.getTimeIn(TimeUnit.MILLISECONDS)).isLessThanOrEqualTo(timeout);

		// Soft Assert

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(response.statusCode()).isEqualTo(200);
		softly.assertThat(response.header("Content-Type")).isEqualTo("application/json");
		softly.assertThat(response.getTimeIn(TimeUnit.MILLISECONDS)).isLessThanOrEqualTo(timeout);
		softly.assertAll();
		// and:
		final DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
		assertThatJson(parsedJson).field("message").isEqualTo(expected);

		/*
		 */
	}
	
	@Test
	public void testOneMore() {
	}
	

}
