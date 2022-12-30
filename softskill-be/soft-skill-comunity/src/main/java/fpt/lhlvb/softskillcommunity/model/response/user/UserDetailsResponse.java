package fpt.lhlvb.softskillcommunity.model.response.user;

import fpt.lhlvb.softskillcommunity.model.response.user.mapping.UserDetailsMapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsResponse {
    private UserDetailsMapping userDetails;
    private String passwordDecrypt;
}
