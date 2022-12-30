package fpt.lhlvb.softskillcommunity.model.request.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateFavoriteTaskRequest {
    @NotNull
    private Long taskId;

    @NotNull
    private Boolean isFavorite;

    @NotNull
    private Integer status;
}
