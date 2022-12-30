package fpt.lhlvb.softskillcommunity.schedule;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.entity.Rooms;
import fpt.lhlvb.softskillcommunity.repository.RoomsRepository;
import fpt.lhlvb.softskillcommunity.repository.UsersRepository;
import fpt.lhlvb.softskillcommunity.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeleteRoomSchedule {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    @Autowired
    private RoomsRepository roomsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Scheduled(cron = CommonConstant.SCHEDULE_DELETE_ROOM)
    private void deleteRoom() {
        Long adminId = usersRepository.getAdminId(CommonConstant.ROLE_ADMIN);
        long now = CommonUtils.resultTimestamp().getTime();
        long timeBetween = 7 * 24 * 60 * 60 * 1000;

        List<Rooms> rooms = roomsRepository.findAllByDeleteFlag(CommonConstant.DELETE_FLAG_FALSE);
        List<Long> roomsIdDelete = new ArrayList<>();
        logger.info("***** Schedule Delete Room after 7 days not active *****");
        if (rooms != null) {
            for (Rooms room : rooms) {
                long updateDate = room.getUpdatedAt().getTime();
                if (now - updateDate > timeBetween) {
                    room.setCommonDelete(adminId);
                    roomsIdDelete.add(room.getId());
                    roomsRepository.save(room);
                    logger.info("delete Room: " + room.getId() + "- " + room.getName());
                }
            }
        }
        logger.info("All Room is Active");
    }
}
