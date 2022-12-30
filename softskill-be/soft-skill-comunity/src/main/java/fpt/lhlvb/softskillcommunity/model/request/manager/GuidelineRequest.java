package fpt.lhlvb.softskillcommunity.model.request.manager;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GuidelineRequest {
    @NotNull
    private String title;
    @NotNull
    private String content;
    private Byte[] file;
}
