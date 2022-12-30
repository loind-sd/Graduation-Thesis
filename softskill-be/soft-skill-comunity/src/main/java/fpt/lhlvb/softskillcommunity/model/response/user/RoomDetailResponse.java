package fpt.lhlvb.softskillcommunity.model.response.user;

import fpt.lhlvb.softskillcommunity.model.response.user.mapping.RoomDetailMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.UserRoomDetailMapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDetailResponse {
    private RoomDetailMapping roomDetail;
    private List<UserRoomDetailMapping> userRoomDetails;
}
