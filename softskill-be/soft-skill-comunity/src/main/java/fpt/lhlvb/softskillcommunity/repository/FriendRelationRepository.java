package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.FriendRelation;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.FriendMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRelationRepository extends JpaRepository<FriendRelation, Long> {

    List<FriendRelation> findByUserSentIdOrUserRecivedIdAndStatusAndDeleteFlag(Long userSentId, Long userReceiveId, int status, Boolean deleteFlag);

    Optional<FriendRelation> findByUserSentIdAndUserRecivedIdAndStatusAndDeleteFlag(Long userSentId, Long userReceiveId, int status, Boolean deleteFlag);

    Optional<FriendRelation> findByIdAndDeleteFlag(Long id, boolean deleteFlag);

    @Query(value = "SELECT u.\"id\", u.\"name\", u.nickname, u.picture\n" +
            "FROM friend_relation a JOIN users u ON a.user_recived_id = u.\"id\"\n" +
            "WHERE a.status = :status AND a.delete_flag = :deleteFlag and a.user_sent_id = :id\n" +
            "UNION\n" +
            "SELECT u.\"id\", u.\"name\", u.nickname, u.picture\n" +
            "FROM friend_relation a JOIN users u ON a.user_sent_id = u.\"id\"\n" +
            "WHERE a.status = :status AND a.delete_flag = :deleteFlag and a.user_recived_id = :id", nativeQuery = true)
    List<FriendMapping> getAllFriend(@Param("status") int status, @Param("deleteFlag") boolean deleteFlag, @Param("id") Long id);

    @Query(value = "SELECT u.id, u.name, u.nickname, u.picture\n" +
            "            FROM friend_relation a JOIN users u ON a.user_recived_id = u.id\n" +
            "            WHERE a.delete_flag = false and a.user_sent_id = :id\n" +
            "            UNION\n" +
            "            SELECT u.id, u.name, u.nickname, u.picture\n" +
            "            FROM friend_relation a JOIN users u ON a.user_sent_id = u.id\n" +
            "            WHERE a.delete_flag = false and a.user_recived_id = :id", nativeQuery = true)
    List<FriendMapping> getAllFriendEvenRequest(@Param("id") Long id);

    @Query(value = "SELECT u.\"id\", u.\"name\", u.nickname, u.picture, \n" +
            "CASE \n" +
            "WHEN u.id IN (SELECT f.user_recived_id FROM users t JOIN friend_relation f on t.id = f.user_recived_id WHERE f.delete_flag = :deleteFlag AND f.status = 0 AND f.user_sent_id = :id) THEN 'isSent'\n" +
            "WHEN u.id IN (SELECT f.user_sent_id FROM users t JOIN friend_relation f on t.id = f.user_sent_id WHERE f.delete_flag = :deleteFlag AND f.status = 0 AND f.user_recived_id = :id) THEN 'isReceived'\n" +
            "WHEN u.id IN (SELECT f.user_recived_id FROM users t JOIN friend_relation f on t.id = f.user_recived_id WHERE f.delete_flag = :deleteFlag AND f.status = 1 AND f.user_sent_id = :id\n" +
            "UNION \n" +
            "SELECT f.user_sent_id FROM users t JOIN friend_relation f on t.id = f.user_sent_id WHERE f.delete_flag = :deleteFlag AND f.status = 1 AND f.user_recived_id = :id) THEN 'isFriend'\n" +
            "ELSE 'nothing'\n" +
            "END status\n" +
            "FROM users u \n" +
            "WHERE u.mail LIKE :word AND delete_flag = :deleteFlag AND u.id <> :id\n" +
            "OR u.nickname LIKE :word AND delete_flag = :deleteFlag AND u.id <> :id\n" +
            "OR u.name LIKE :word AND delete_flag = :deleteFlag AND u.id <> :id\n" +
            "ORDER BY status", nativeQuery = true)
    List<FriendMapping> searchToAddFriend(@Param("deleteFlag") boolean deleteFlag, @Param("word") String word, @Param("id") Long myId);

    @Query(value = "SELECT EXISTS\n" +
            "( SELECT 1 FROM friend_relation A WHERE A.user_sent_id = :userSend AND A.user_recived_id = :userReceive AND A.delete_flag = FALSE OR A.user_sent_id = :userReceive AND A.user_recived_id = :userSend AND A.delete_flag = FALSE)", nativeQuery = true)
    Boolean checkExistAddFriend(@Param("userSend") Long userSend, @Param("userReceive") Long userReceive);

    @Query(value = "(SELECT * FROM friend_relation A WHERE A.user_sent_id = :userSend AND A.user_recived_id = :userReceive\n " +
            "AND A.delete_flag = FALSE OR A.user_sent_id = :userReceive AND A.user_recived_id = :userSend\n" +
            "AND A.delete_flag = FALSE)", nativeQuery = true)
    Optional<FriendRelation> getFriendRelationById(@Param("userSend") Long userSend, @Param("userReceive") Long userReceive);

}
