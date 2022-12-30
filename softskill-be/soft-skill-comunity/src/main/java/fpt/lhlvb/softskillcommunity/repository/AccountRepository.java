package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.Account;
import fpt.lhlvb.softskillcommunity.entity.Users;
import fpt.lhlvb.softskillcommunity.model.response.admin.mapping.AccInfoMapping;
import fpt.lhlvb.softskillcomunity.model.response.user.mapping.AccountMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsernameAndDeleteFlag(String username, Boolean deleteFlag);

    @Query(value = "SELECT count(1) AS \"size\" FROM \"accounts\" a JOIN users b on a.user_id = b.\"id\"\n" +
            "WHERE a.delete_flag = :deleteFlag AND b.delete_flag = :deleteFlag and a.role_id in (:adminRole,:authorRole)", nativeQuery = true)
    int getSizeAdminAcc(@Param("deleteFlag") boolean deleteFlag, @Param("adminRole") int admin, @Param("authorRole") int author);

    @Query(value = "SELECT a.id, b.mail, b.\"name\", a.role_id AS \"roleId\", b.nickname FROM \"accounts\" a JOIN users b on a.user_id = b.\"id\"\n" +
            "WHERE a.delete_flag = :deleteFlag AND b.delete_flag = :deleteFlag and a.role_id in (:adminRole,:authorRole)\n" +
            "ORDER BY a.role_id\n" +
            "LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<AccountMapping> getAdminAcc(@Param("deleteFlag") boolean deleteFlag, @Param("adminRole") int admin, @Param("authorRole") int author, @Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT count(1) AS \"size\" FROM \"accounts\" a JOIN users b on a.user_id = b.\"id\"\n" +
            "WHERE a.delete_flag = :deleteFlag AND b.delete_flag = :deleteFlag and a.role_id = :userRole", nativeQuery = true)
    int getSizeUserAcc(@Param("deleteFlag") boolean deleteFlag, @Param("userRole") int user);

    @Query(value = "SELECT a.id, b.mail, b.\"name\", a.role_id AS \"roleId\", b.nickname FROM \"accounts\" a JOIN users b on a.user_id = b.\"id\"\n" +
            "WHERE a.delete_flag = :deleteFlag AND b.delete_flag = :deleteFlag and a.role_id = :userRole\n" +
            "ORDER BY a.role_id\n" +
            "LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<AccountMapping> getUserAcc(@Param("deleteFlag") boolean deleteFlag, @Param("userRole") int user, @Param("limit") int limit, @Param("offset") int offset);

    Optional<Account> findByIdAndDeleteFlag(Long id, boolean deleteFlag);

    Optional<Account> findByUserAndDeleteFlagFalse (Users user);

    Optional<Account> findByUserIdAndDeleteFlag(Long id, boolean deleteFlag);

    @Query(value = "SELECT a.\"id\", b.\"name\", b.mail, c.\"id\" as roleId, c.role_name as roleName, \n" +
            "CASE \n" +
            "WHEN a.delete_flag = TRUE OR b.delete_flag = TRUE THEN\n" +
            "'Dừng'\n" +
            "ELSE\n" +
            "'Hoạt động'\n" +
            "END  as status,\n" +
            "a.created_date as createdDate\n" +
            "FROM \"accounts\" a \n" +
            "JOIN users b on a.user_id = b.\"id\"\n" +
            "join roles c on a.role_id = c.\"id\"" +
            " ORDER BY a.\"id\"", nativeQuery = true)
    List<AccInfoMapping> getAccInfo();

}
