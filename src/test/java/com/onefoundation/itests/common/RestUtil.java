package com.onefoundation.itests.common;

import static com.jayway.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.google.common.collect.Lists;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.onefoundation.itests.AbstractITest;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

// TODO: Auto-generated Javadoc
/**
 * The Class RestUtil.
 */
public final class RestUtil {

	/**
	 * Instantiates a new rest util.
	 */
	private RestUtil() {
	} // Prevent the class from being constructed

	/**
	 * Sets the content type.
	 *
	 * @param Type
	 *            the new content type
	 */
	/*
	 *** Sets ContentType*** We should set content type as JSON or XML before
	 * starting the test
	 */
	public static void setContentType(final ContentType Type) {
		given().contentType(Type);
	}

	/**
	 * Gets the json path.
	 *
	 * @param res
	 *            the res
	 * @return the json path
	 */
	/*
	 *** Returns JsonPath object*** First convert the API's response to String
	 * type with "asString()" method. Then, send this String formatted json
	 * response to the JsonPath class and return the JsonPath
	 */
	public static JsonPath getJsonPath(final Response res) {
		final String json = res.asString();
		return new JsonPath(json);
	}

	public static String getCurrentProfile() {
		final String currentEnv = System.getProperty(AbstractITest.ITEST_ENV);

		if (currentEnv != null && Arrays.asList(AbstractITest.PROFILES).contains(currentEnv)) {
			return currentEnv;
		} else {
			return "dev";
		}
	}
	/**
	 * Load Data from test yaml file
	 * 
	 * @param clazz
	 * @return List - Parameterized Data from yaml
	 * @throws FileNotFoundException
	 */
	public static <T> List<Object[]> getData(Class<T> clazz,String profile) throws FileNotFoundException {
		/* Load the file from the resources directory */
		final ClassLoader classloader = clazz.getClassLoader();
		InputStream input = classloader.getResourceAsStream("test-data-"+profile+".yaml");

		/* Parse the YAML */
		Yaml yaml = new Yaml();
		List<Map<String, Object>> object = (List<Map<String, Object>>) yaml.load(input);
		List<Object[]> parameters = Lists.newArrayList();

		for (Map<String, Object> o : object) {
			Object[] l = o.values().toArray();
			parameters.add(l);
		}

		return parameters;

	}

	public static boolean isJson(String str) {
	    try {
	    	JSONParser parser = new JSONParser();
	    	Object obj = parser.parse(str);
	    	if (obj instanceof JSONObject)
	    		return true;
	    	else 
	    		return false;
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	/**
	 * Takes the linked HashMap of params and builds the URI query param string
	 * 
	 * @param params
	 * @return query param String
	 */
	public static String buildQueryParams(List<Map<String, String>> params) {
		String buildParams = "";
		if (params != null) {

			final Iterator<Map<String, String>> paramsIT = params.iterator();

			while (paramsIT.hasNext()) {
				final Map<String, String> val = paramsIT.next();
				for (final Map.Entry<String, String> entry : val.entrySet()) {
					if (buildParams.length() > 0) {
						buildParams += "&";
					}
					buildParams += entry.getKey() + "=" + entry.getValue();
				}
			}
		}
		return buildParams;
	}
}
