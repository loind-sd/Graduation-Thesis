package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.RoomTaskRegisterInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomTaskRegisterInfoRepository extends JpaRepository<RoomTaskRegisterInfo, Long> {
    List<RoomTaskRegisterInfo> findByRoomTaskIdAndDeleteFlag(Long roomTaskId, Boolean deleteFlag);

    Optional<RoomTaskRegisterInfo> findByRoomTaskIdAndCreatedByAndDeleteFlag(Long roomTaskId, Long createBy, Boolean deleteFlag);
}
