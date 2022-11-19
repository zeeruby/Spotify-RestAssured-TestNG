package com.spotify.oauth2.api;

import static com.spotify.oauth2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static com.spotify.oauth2.api.Route.API;
import static com.spotify.oauth2.api.Route.TOKEN;
import static com.spotify.oauth2.api.SpecBuilder.getAccountRequestSpec;
import static io.restassured.RestAssured.given;

import java.util.HashMap;

import io.restassured.response.Response;

public class RestResource {

	
	public static Response post(String path, Object requestObject, String token) {
		return given(getRequestSpec())
				.body(requestObject)
				.auth().oauth2(token)
				//.header("Authorization", "Bearer " + token)
			.when()
				.post(path)
			.then()
			    .spec(getResponseSpec())
			    .extract()
				.response();
	}

	public static Response postAccount(HashMap<String, String> formParamsMap) {
		return given(getAccountRequestSpec())
				.formParams(formParamsMap)
			.when()
				.post(API + TOKEN)
			.then()
			    .spec(getResponseSpec())
			    .extract()
				.response();
	}
	
	public static Response get(String path, String token) {
		return given(getRequestSpec())
				.auth().oauth2(token)
				//.header("Authorization", "Bearer " + token)
			.when()
				.get(path)
			.then()
			    .spec(getResponseSpec())
			    .extract()
			    .response();
	}

	public static Response put(String path, Object requestObject, String token) {
		return given(getRequestSpec())
				//.header("Authorization", "Bearer " + token)
				.auth().oauth2(token)
				.body(requestObject)
			.when()
				.put(path)
			.then().spec(getResponseSpec())
			    .extract()
			    .response();
	}


}
