package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.RankInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankInfoRepository extends JpaRepository<RankInfo, Long> {
    List<RankInfo> findAllByDeleteFlag(Boolean deleteFlag);
}
