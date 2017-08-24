package com.onefoundation.itests.demo;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.cedarsoftware.util.io.JsonWriter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.onefoundation.itests.AbstractITest;
import com.onefoundation.itests.common.RestUtil;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Automated Integration Tests based on the data parameters provided by the
 * test-data.yml file
 * 
 *
 */
public class APITestIT extends AbstractITest {
	/* These are the parameters from the YAML */
	/** Test Case Name */
	@Parameter(value = 0)
	public String fName;

	/** API operation GET, POST, DELETE, PUT */
	@Parameter(value = 1)
	public String fOperation;

	/** API URL */
	@Parameter(value = 2)
	public String fUri;

	/** API Response Body */
	@Parameter(value = 3)
	public String fResponseBodyJson;

	/** Expected Response Code */
	@Parameter(value = 4)
	public int fExpectedStatusCode;

	/** List of Query Parameters */
	@Parameter(value = 5)
	public List<Map<String, String>> fQueryParams;

	/** JSON POST Body */
	@Parameter(value = 6)
	public String fRequestBodyJson;
	
	/** Example of Sample Data */
	@Parameter(value = 7)
	public List<Map<String, Object>> fSampleData;

	/** Parameterized Data */
	@Parameters(name = "ApiTest{index}:{0}")
	public static Collection<Object[]> data() throws FileNotFoundException {
		return RestUtil.getData(APITestIT.class,RestUtil.getCurrentProfile());
	}

	/**
	 * Setup.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() {

		super.setUp();

	}

	/**
	 * Automated Test Case
	 */
	@Test
	public void testCase() {
		System.out.println("Running " + fName + "...\n");
		// Build the Query Params
		String queryParams = RestUtil.buildQueryParams(fQueryParams);
		String finalURI = fUri;
		if (queryParams.length() > 0) {
			finalURI += "?" + queryParams;
		}
		// when:
		Response response = null;

		/* Send the right request according to operation parameter */
		switch (fOperation) {
		case "GET":
			response = given().spec(rspec).get(finalURI);
			break;

		case "POST":
			assertNotNull(fRequestBodyJson);
			response = given().spec(rspec).header("Content-Type", "application/json").contentType(ContentType.JSON)
					.body(fRequestBodyJson).when().post(finalURI);
			break;
		case "PUT":
			assertNotNull(fRequestBodyJson);
			response = given().spec(rspec).header("Content-Type", "application/json").contentType(ContentType.JSON)
					.body(fRequestBodyJson).when().put(finalURI);
			break;
		case "DELETE":
			response = given().spec(rspec).header("Content-Type", "application/json").when().delete(finalURI);
			break;
		default:
			fail("Unsupported operation");
		}

		// then:
		assertNotNull(response);
		response.then().log().all(); // log result
		if (fResponseBodyJson != null) {
			String body = response.then().assertThat().contentType(JSON).and().extract().body().asString();
			// Format both JSON strings to using the same formatter
			String actualJson = JsonWriter.formatJson(body);
			String expectedJson = JsonWriter.formatJson(fResponseBodyJson);
			assertEquals(actualJson, expectedJson);
		}
		
		SoftAssertions softly = new SoftAssertions();
		softly.assertThat(response.statusCode()).isEqualTo(fExpectedStatusCode);
		softly.assertThat(response.getTimeIn(TimeUnit.MILLISECONDS)).isLessThanOrEqualTo(timeout);
		softly.assertAll();
		
		/* Custom result verification code */
		// ADD API RESPONSE VERIFICATION HERE
		if (fSampleData != null) {
			Iterator<Map<String, Object>> dataIterator = fSampleData.iterator();
			while (dataIterator.hasNext()) {
				Map<String, Object> val = dataIterator.next();
				for (final Map.Entry<String, Object> entry : val.entrySet()) {
					System.out.println("---");
					System.out.println("Key = "+entry.getKey());
					System.out.println("Value (Type) = "+entry.getValue().getClass());
					System.out.println("Value (isJSON)= "+RestUtil.isJson(entry.getValue().toString()));
					System.out.println("Value  = "+entry.getValue().toString());
					
				}
			}
		}

	}

}
