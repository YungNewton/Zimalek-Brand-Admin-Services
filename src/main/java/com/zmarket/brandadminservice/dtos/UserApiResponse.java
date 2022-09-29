package com.zmarket.brandadminservice.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zmarket.brandadminservice.modules.security.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserApiResponse implements Serializable {
    private boolean requestSuccessful;
    private String responseMessage;
    private User responseBody;
    private List<ApiFieldError> errors;

    public UserApiResponse(boolean requestSuccessful, String responseMessage) {
        this.requestSuccessful = requestSuccessful;
        this.responseMessage = responseMessage;
    }

    public UserApiResponse(boolean requestSuccessful, List<ApiFieldError> errors) {
        this.requestSuccessful = requestSuccessful;
        this.errors = errors;
    }
}
