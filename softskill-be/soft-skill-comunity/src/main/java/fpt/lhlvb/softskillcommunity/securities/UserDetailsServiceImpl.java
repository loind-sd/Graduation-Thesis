package fpt.lhlvb.softskillcommunity.securities;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.entity.Account;
import fpt.lhlvb.softskillcommunity.entity.Users;
import fpt.lhlvb.softskillcommunity.repository.AccountRepository;
import fpt.lhlvb.softskillcommunity.repository.UsersRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String condition) throws UsernameNotFoundException {
        Optional<Users> usersOptional = usersRepository.findByMailAndDeleteFlag(condition, CommonConstant.DELETE_FLAG_FALSE);
        if (usersOptional.isEmpty()) {
            Optional<Account> accountOptional = accountRepository.findByUsernameAndDeleteFlag(condition, CommonConstant.DELETE_FLAG_FALSE);
            if (accountOptional.isEmpty()) {
                throw new UsernameNotFoundException("User Not Found with mail or nickname: " + condition);
            } else {
                return UserDetailsImpl.build(accountOptional.get().getUser());
            }
        } else {
            return UserDetailsImpl.build(usersOptional.get());
        }
    }
}
