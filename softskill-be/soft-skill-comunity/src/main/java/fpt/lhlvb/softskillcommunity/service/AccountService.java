package fpt.lhlvb.softskillcommunity.service;

import fpt.lhlvb.softskillcommunity.entity.Account;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcomunity.model.request.admin.AccountRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public interface AccountService {
    Account getAccountByUsername(String username);

    ApiResponse getAdminAcc(int pageNo);

    ApiResponse updateRoleAdminAcc(AccountRequest acc);

    ApiResponse blockUser(Long[] ids);

    ApiResponse getUserAcc(int pageNo);

    ApiResponse getAccInfo();

    ApiResponse updateAccInfo(Map<String, Object> req);
}
