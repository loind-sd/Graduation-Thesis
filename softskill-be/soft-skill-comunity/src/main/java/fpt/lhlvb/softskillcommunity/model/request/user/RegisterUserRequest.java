package fpt.lhlvb.softskillcommunity.model.request.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
public class RegisterUserRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String mail;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotNull
    private Integer gender;

    @NotNull
    private Date birthdate;

    @NotNull
    private Long roleId;
}
