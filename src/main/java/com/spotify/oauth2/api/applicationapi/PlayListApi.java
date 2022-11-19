package com.spotify.oauth2.api.applicationapi;

import com.spotify.oauth2.api.RestResource;
import static com.spotify.oauth2.api.TokenManager.getToken;
import com.spotify.oauth2.pojo.PlayList;
import com.spotify.oauth2.utils.ConfigLoader;

import static com.spotify.oauth2.api.Route.*;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class PlayListApi {
	
	@Step
	public static Response post(PlayList requestPlayList) {
		return RestResource.post(USERS +"/"+ConfigLoader.getInstance().getUser()+ PLAYLISTS, requestPlayList, getToken());
	}
	
	@Step
	public static Response post(PlayList requestPlayList, String invalidToken) {
		return RestResource.post(USERS+"/"+ConfigLoader.getInstance().getUser()+ PLAYLISTS , requestPlayList, invalidToken);
	}
	
	@Step
	public static Response get(String playListId) {
		return RestResource.get(PLAYLISTS + "/"+ playListId, getToken());
	}
	
	@Step
	public static Response put(PlayList requestPlayList, String playListId) {
		return RestResource.put(PLAYLISTS + "/"+ playListId, requestPlayList, getToken());
	}

}
