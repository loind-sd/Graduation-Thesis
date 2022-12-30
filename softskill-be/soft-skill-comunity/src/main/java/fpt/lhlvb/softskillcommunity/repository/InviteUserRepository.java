package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.InviteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InviteUserRepository extends JpaRepository<InviteUser, Long> {

    @Query(value = "SELECT *\n" +
            "from invite_user a\n" +
            "where a.mail = :mail and a.delete_flag = false\n" +
            "and a.created_date between current_timestamp - interval '7 days' and current_timestamp LIMIT 1", nativeQuery = true)
    Optional<InviteUser> findByMailOver7Days(@Param("mail") String mail);
}
