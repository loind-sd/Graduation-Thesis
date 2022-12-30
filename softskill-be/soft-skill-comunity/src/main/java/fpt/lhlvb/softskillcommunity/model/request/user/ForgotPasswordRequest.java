package fpt.lhlvb.softskillcommunity.model.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
//@AllArgsConstructor
public class ForgotPasswordRequest {
    @NotBlank
    @Email
    private String email;

    public ForgotPasswordRequest(){

    }

    public ForgotPasswordRequest(String email) {
        this.email = email;
    }
}
