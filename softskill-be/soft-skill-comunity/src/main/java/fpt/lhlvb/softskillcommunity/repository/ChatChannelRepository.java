package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.ChatChannel;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.ChatChannelMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatChannelRepository extends JpaRepository<ChatChannel, Long> {

    @Query(value = "SELECT a.id, a.name, a.room_code as roomCode, b.is_master as isMaster\n" +
            "from chat_channel a\n" +
            "left join chat_channel_info b on a.id = b.chat_channel_id\n" +
            "where a.id in (1,2) OR b.user_id = :userId and b.delete_flag = false and a.delete_flag = false" +
            " order by a.id", nativeQuery = true)
    List<ChatChannelMapping> getAllContact(@Param("userId") Long userId);

    Optional<ChatChannel> findByRoomCode(String roomCode);
}
