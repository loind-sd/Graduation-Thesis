package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.Notification;
import fpt.lhlvb.softskillcommunity.entity.NotificationType;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.NotificationMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdAndDeleteFlagOrderByCreatedAtDesc(Long id, Boolean deleteFlag);

    @Query(value = "select a.id, a.content, a.status, a.sender_id as senderId, a.delivered,a.created_date AS timeCreated, b.id as typeId, b.name as typeName, \n" +
            "       CASE\n" +
            "       WHEN a.type_id = 1 OR a.type_id = 2 OR a.type_id = 4\n" +
            "           THEN c.picture\n" +
            "       WHEN a.type_id = 3\n" +
            "           THEN null\n" +
            "       END as picture\n" +
            "            from notification a\n" +
            "            join notification_type b on a.type_id = b.id\n" +
            "            join users c on a.sender_id = c.id\n" +
            "            where a.user_id = :userId and a.delete_flag = :deleteFlag AND a.created_date < NOW()\n" +
            "            ORDER BY a.created_date DESC\n" +
            "       LIMIT :limit", nativeQuery = true)
    List<NotificationMapping> findNotification(@Param("userId") Long userId, @Param("deleteFlag") boolean deleteFlag, @Param("limit") int limit);

    List<Notification> findByUserIdAndDeliveredFalseAndDeleteFlagFalseAndCreatedAtBefore(Long id, Date date);

    @Query(value = "select *\n" +
            "from notification n \n" +
            "where n.user_id = :id and n.delivered = false and n.delete_flag = false and n.created_date <= now()", nativeQuery = true)
    List<Notification> findForFlux(Long id);

    Optional<Notification> findByIdAndDeleteFlag(Long id, boolean deleteFlag);

    Optional<Notification> findBySenderIdAndUserIdAndStatusAndDeleteFlagAndNotificationType(Long create, Long userId, boolean status, boolean deleteFlag, NotificationType notificationType);
    Optional<Notification> findBySenderIdAndUserIdAndDeleteFlagAndNotificationType(Long create, Long userId, boolean deleteFlag, NotificationType notificationType);
}
