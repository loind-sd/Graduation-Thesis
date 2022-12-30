package fpt.lhlvb.softskillcommunity.model.response.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccInfoResponse {
    private Long id;
    private int no;
    private String name;
    private String mail;
    private String roleId;
    private String role;
    private String status;
    private String createdDate;
}
