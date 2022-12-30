package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.RecommendTaskType;
import fpt.lhlvb.softskillcommunity.model.response.DropdownResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendTasTypeRepository extends JpaRepository<RecommendTaskType,Long> {

    @Query(value = "Select id as id,name as name from recomment_task_type where delete_flag =false ORDER BY id ",nativeQuery = true)
    List<DropdownResponse> dropdownRecommendTaskType();

    Optional<RecommendTaskType> findByIdAndDeleteFlag(Long id, boolean deleteFlag);
}
