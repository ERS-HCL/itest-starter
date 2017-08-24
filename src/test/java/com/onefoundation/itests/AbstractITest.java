package com.onefoundation.itests;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import com.onefoundation.itests.common.RestUtil;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import static com.jayway.restassured.config.EncoderConfig.encoderConfig;
/**
 * The Class AbstractITest.
 */
@RunWith(value = Parameterized.class)
@Ignore
@SuppressWarnings({ "PMD.AbstractClassWithoutAnyMethod", "PMD.AbstractClassWithoutAbstractMethod",
		"PMD.DataflowAnomalyAnalysis", "PMD.DataflowAnomalyAnalysis" })
public abstract class AbstractITest {

	/** The runtime env variable for iTestEnv */
	public static final String ITEST_ENV = "ITEST_ENV";

	/** Test Environment profiles */
	public static final String[] PROFILES = { "dev", "ci", "test", "final" };

	/** Runtime Environment (Dev,Test,QA,Prod) */
	public String activeProfile;

	/** Load application configuration */
	public Config conf;

	/** The timeout. */
	public int timeout;

	/** The port. */
	public int port;

	/** The base uri. */
	public String baseURI;

	// Define the list of request specifications one for each endpoint
	// Reference:
	// https://github.com/rest-assured/rest-assured/wiki/Usage#specification-re-use

	/** The rspec. */
	public RequestSpecification rspec;

	/**
	 * Initial Setup
	 */
	@Before
	public void setUp() {

		this.activeProfile = RestUtil.getCurrentProfile();

		this.conf = ConfigFactory.load("application-" + this.activeProfile);
		this.baseURI = conf.getString("server.baseURI");
		this.port = conf.getInt("server.port");
		this.timeout = conf.getInt("service.api.timeout");

		final RequestSpecBuilder build = new RequestSpecBuilder().setBaseUri(baseURI).setPort(port);

		rspec = build.build();
		RestAssured.config = new RestAssuredConfig().encoderConfig(encoderConfig().defaultContentCharset("UTF-8")
				.encodeContentTypeAs("application-json", ContentType.JSON));
	}

}
