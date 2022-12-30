package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.FeedbackTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackTitleRepository extends JpaRepository<FeedbackTitle, Long> {

    Optional<FeedbackTitle> findByIdAndDeleteFlag(Long id, boolean deleteFlag);

    List<FeedbackTitle> findAllByDeleteFlag(boolean deleteFlag);
}
