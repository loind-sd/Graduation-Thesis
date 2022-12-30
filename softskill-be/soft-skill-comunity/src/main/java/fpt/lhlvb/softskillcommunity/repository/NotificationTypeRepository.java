package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationTypeRepository extends JpaRepository<NotificationType, Long> {
    Optional<NotificationType> findByIdAndDeleteFlag(Long id, boolean deleteFlag);
}
