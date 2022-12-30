package fpt.lhlvb.softskillcommunity.model.response.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticRoomResponse {
    private String week;
    private int numberActiveRoom;
    private int numberBookingRoom;
}
