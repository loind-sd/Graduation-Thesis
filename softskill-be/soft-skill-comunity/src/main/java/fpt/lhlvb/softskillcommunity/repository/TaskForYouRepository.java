package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.TaskForYou;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.TaskForYouMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskForYouRepository extends JpaRepository<TaskForYou, Long> {
    List<TaskForYou> findByUserIdAndDeleteFlagOrderById(Long userId, boolean deleteFlag);

    Optional<TaskForYou> findByIdAndDeleteFlag(Long aLong, boolean deleteFlag);

    @Query(value = "WITH t as(\n" +
            "SELECT \"count\"(1) as numberNotCompleted FROM task_for_you\n" +
            "WHERE delete_flag = :deleteFlag and user_id = :userId and status = :statusNot),\n" +
            "u as (SELECT \"count\"(1) as numberCompleted FROM task_for_you\n" +
            "WHERE delete_flag = :deleteFlag and user_id = :userId and status = :statusDone)\n" +
            "\n" +
            "SELECT * from t,u", nativeQuery = true)
    Optional<TaskForYouMapping> countTask(@Param("userId") Long userId, @Param("statusDone") int statusDone, @Param("statusNot") int statusNot, @Param("deleteFlag") boolean deleteFlag);
}
