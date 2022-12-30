package fpt.lhlvb.softskillcommunity.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface AdministratorsService extends UserDetailsService {
}
