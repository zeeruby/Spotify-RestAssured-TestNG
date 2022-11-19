package com.spotify.oauth2.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.applicationapi.PlayListApi;
import com.spotify.oauth2.pojo.ErrorRoot;
import com.spotify.oauth2.pojo.PlayList;
import com.spotify.oauth2.utils.DataLoader;
import static com.spotify.oauth2.utils.FakerUtils.*;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
@Epic("Spotify Oauth 2.0")
@Feature("PlayList API")
public class PlayListTests extends BaseTest{
	@Story("Create a Playlist story")
	@Link("https://example.org")
	@Issue("1234567")
	@TmsLink("678989")
     @Description("This is the allure description: Should be able to create a playlist")
	@Test
	public void shouldBeAbleToCreateAPlayList() {
        PlayList requestPlayList = playListBuider(generateName(),generateDescription(), false, false);
		Response response = PlayListApi.post(requestPlayList);
		PlayList responsePlayList = response.as(PlayList.class);
		assertStatusCode(response.statusCode(), StatusCode.CODE_201);
		assertEqualPlayList(requestPlayList, responsePlayList);
	}
	
	@Link("https://example.org")
	@Issue("12345")
	@TmsLink("6789")
     @Description("This is the allure description: Should pull a playlist using play list id")
	 @Test(description="Should be able to get a playlist with id")
	public void shouldBeAbleToGetAPlayList() {
		PlayList requestPlayList = playListBuider("Updated PlayList Name", "Updated PlayList description", false, false);
		Response response = PlayListApi.get(DataLoader.getInstance().getPlayListId());
		PlayList responsePlayList = response.as(PlayList.class);
		assertStatusCode(response.statusCode(), StatusCode.CODE_200);
		assertEqualPlayList(requestPlayList, responsePlayList);
	}

	@Test
	public void shouldBeAbleToUpdateAPlayList() {
		PlayList requestPlayList = playListBuider(generateName(),generateDescription(), false, false);
		Response response = PlayListApi.put(requestPlayList, DataLoader.getInstance().updatePlayListId());
		assertStatusCode(response.statusCode(), StatusCode.CODE_200);
	}
     
	@Story("Create a Playlist story")
	 @Test
	public void shouldNotBeAbleToCreateAPlayListWithoutName() {
		 PlayList requestPlayList = playListBuider("",generateDescription(), false, false);
		Response response = PlayListApi.post(requestPlayList);
		assertStatusCode(response.statusCode(), StatusCode.CODE_400);
		assertError(response, StatusCode.CODE_400);
	}
    
	
	@Story("Create a Playlist story")
	@Test
	public void shouldNotBeAbleToCreateAPlayListWithExpiredToken() {
		String invalidToken = "BQCmXJVHo2x2RmcjArKPikQ2ljMRYSV34hJ_3PFvz6qz";
		
		PlayList requestPlayList = playListBuider(generateName(),generateDescription(), false, false);
		Response response = PlayListApi.post(requestPlayList, invalidToken);
		assertStatusCode(response.statusCode(), StatusCode.CODE_401);
		assertError(response, StatusCode.CODE_401);
	}
	
	 @Step
	 public PlayList playListBuider(String name, String description, boolean _public, Boolean isCollaborative){
	        return PlayList.builder().
	                name(name).
	                _public(_public).
	                description(description).
	                collaborative(isCollaborative).
	                build();
	    }
	 @Step
	public void assertEqualPlayList(PlayList reqPlayList, PlayList resPlayList) {
		assertThat(resPlayList.getName(), equalTo(reqPlayList.getName()));
		assertThat(resPlayList.getDescription(), equalTo(reqPlayList.getDescription()));
		assertThat(resPlayList.get_public(), equalTo(reqPlayList.get_public()));
		assertThat(resPlayList.getCollaborative(), equalTo(reqPlayList.getCollaborative()));
	}
	
	 @Step
	public void assertStatusCode(int actualStatusCode, StatusCode expectedStatusCode) {
		assertThat(actualStatusCode, equalTo(expectedStatusCode.code));
	}
	
	 @Step
	public void assertError(Response res, StatusCode expectedStatusCode) {
		ErrorRoot responseError = res.as(ErrorRoot.class);
		assertThat(responseError.getInnerError().getStatus(), equalTo(expectedStatusCode.code));
		assertThat(responseError.getInnerError().getMessage(), equalTo(expectedStatusCode.msg));
	}

	/*
	 * ErrorRoot errorRoot= given() . baseUri("https://api.spotify.com").
	 * basePath("/v1"). contentType(ContentType.JSON).
	 * header("Authorization","Bearer "+ ""). body(requestPlayList). log().all().
	 * when(). post("/users/31npezfq67bimmv5rnfzah57p2lm/playlists"). then().
	 * spec(getResponseSpec()). assertThat(). statusCode(401). extract().
	 * response(). as(ErrorRoot.class);
	 * 
	 * assertThat(errorRoot.getInnerError().getStatus(), equalTo(401));
	 * assertThat(errorRoot.getInnerError().getMessage(),
	 * equalTo("Invalid access token")); // body("error.status",
	 * equalTo(401),"error.message", equalTo("Invalid access token"), "public",
	 * equalTo(false));
	 */
}
