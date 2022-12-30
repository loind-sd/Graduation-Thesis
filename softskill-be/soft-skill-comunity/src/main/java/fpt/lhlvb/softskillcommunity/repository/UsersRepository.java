package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.Users;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.FriendMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.UserDetailsMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByMail(String mail);
    Optional<Users> findByMailAndDeleteFlag(String mail, Boolean deleteFlag);

    @Query(value = "select u.id, u.name, a.password, u.nickname as nickName, u.mail, u.gender, u.birthdate,\n" +
            "u.created_date as createdDate, u.picture, r.role_name AS roleName \n" +
            "from Users u\n" +
            "join accounts a on a.user_id = u.id and a.delete_flag = false\n" +
            "JOIN roles r ON A.role_id = r.ID AND r.delete_flag = FALSE\n" +
            "where u.id = :id and u.delete_flag = false;", nativeQuery = true)
    UserDetailsMapping getUserDetails(Long id);

    Optional<Users> findByIdAndDeleteFlag(Long id, Boolean deleteFlag);

    @Query(value = "select u.id, u.\"name\", u.nickname, u.picture, \n" +
            "CASE \n" +
            "WHEN u.id IN (SELECT f.user_recived_id FROM users s JOIN friend_relation f on s.id = f.user_recived_id WHERE f.delete_flag = false AND f.user_sent_id = :myId\n" +
            "UNION \n" +
            "SELECT f.user_sent_id FROM users s JOIN friend_relation f on s.id = f.user_sent_id WHERE f.delete_flag = false AND f.user_recived_id = :myId) THEN 'isFriend'\n" +
            "ELSE 'nothing' END status\n" +
            "from users u\n" +
            "where u.id = :theirId", nativeQuery = true)
    Optional<FriendMapping> findOtherUserInfo(@Param("theirId") Long theirId, @Param("myId") Long myId);

    @Query(value = "SELECT * from users WHERE mail LIKE %:mail% OR nickname LIKE %:nickname% AND delete_flag = :deleteFlag", nativeQuery = true)
    List<Users> searchByMailOrNickName(@Param("mail") String mail, @Param("nickname") String nickName, @Param("deleteFlag") Boolean deleteFlag);

    List<Users> findByMailContainsAndDeleteFlag(String mail, Boolean deleteFlag);
    List<Users> findByNicknameContainsAndDeleteFlag(String nickname, Boolean deleteFlag);
    Optional<Users> findByNicknameAndDeleteFlag(String nickName, Boolean deleteFlag);

    @Query(value = "SELECT\n" +
            "\t u.id AS adminId\n" +
            "FROM\n" +
            "\tusers u\n" +
            "JOIN accounts a ON\n" +
            "\tu.id = a.user_id\n" +
            "\tAND a.role_id = :roleAdmin\n" +
            "\tAND a.delete_flag = FALSE\n" +
            "WHERE\n" +
            "\tu.delete_flag = FALSE\n" +
            "LIMIT 1", nativeQuery = true)
    Long getAdminId(Integer roleAdmin);

    @Query(value = "select u.id\n" +
            "from Users u \n" +
            "where u.id <> :userId and u.delete_flag = false;", nativeQuery = true)
    List<Long> othersUserId(Long userId);

    List<Users> findByDeleteFlag(Boolean deleteFlag);
}
