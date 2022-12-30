package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByRoleNameAndDeleteFlag(String roleName, boolean deleteFlag);

    Optional<Roles> findByIdAndDeleteFlag(Long roleId, Boolean deleteFlagFalse);
}
