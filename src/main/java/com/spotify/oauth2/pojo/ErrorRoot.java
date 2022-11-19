package com.spotify.oauth2.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorRoot {

@JsonProperty("error")
private InnerError error;

@JsonProperty("error")
public InnerError getInnerError() {
return error;
}

@JsonProperty("error")
public void setInnerError(InnerError error) {
this.error = error;
}

}
