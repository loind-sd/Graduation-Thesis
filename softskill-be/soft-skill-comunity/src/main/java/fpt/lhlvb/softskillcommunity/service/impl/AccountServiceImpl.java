package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.Account;
import fpt.lhlvb.softskillcommunity.entity.Users;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.admin.AccInfoResponse;
import fpt.lhlvb.softskillcommunity.model.response.admin.mapping.AccInfoMapping;
import fpt.lhlvb.softskillcommunity.repository.AccountRepository;
import fpt.lhlvb.softskillcommunity.repository.RolesRepository;
import fpt.lhlvb.softskillcommunity.repository.UsersRepository;
import fpt.lhlvb.softskillcommunity.service.AccountService;
import fpt.lhlvb.softskillcomunity.model.request.admin.AccountRequest;
import fpt.lhlvb.softskillcomunity.model.response.user.mapping.AccountMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private CommonService commonService;
    private List<AccInfoResponse> list;

    @Override
    public Account getAccountByUsername(String username) {
        Optional<Account> accountsOptional = accountRepository.findByUsernameAndDeleteFlag(username.trim(), CommonConstant.DELETE_FLAG_FALSE);
        if (accountsOptional.isEmpty()) {
            throw new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND);
        }
        return accountsOptional.get().getUser().getAccount();
    }

    @Override
    public ApiResponse getAdminAcc(int pageNo) {
        int size = accountRepository.getSizeAdminAcc(CommonConstant.DELETE_FLAG_FALSE, CommonConstant.NUMBER_1, CommonConstant.NUMBER_2);
        int totalPage = size % CommonConstant.SIZE_PER_PAGE == 0 ? size / CommonConstant.SIZE_PER_PAGE : size / CommonConstant.SIZE_PER_PAGE + 1;
        if (pageNo < CommonConstant.NUMBER_1 || pageNo > totalPage) {
            pageNo = CommonConstant.NUMBER_1;
        }
        List<AccountMapping> mapping = accountRepository.getAdminAcc(CommonConstant.DELETE_FLAG_FALSE, CommonConstant.NUMBER_1, CommonConstant.NUMBER_2, CommonConstant.SIZE_PER_PAGE, (pageNo - 1) * CommonConstant.SIZE_PER_PAGE);
        Map<String, Object> response = new HashMap<>();
        response.put("totalPage", totalPage);
        response.put("pageNo", pageNo);
        response.put("data", mapping);
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, response);
    }

    @Override
    public ApiResponse updateRoleAdminAcc(AccountRequest acc) {
        Account account = accountRepository.findByIdAndDeleteFlag(acc.getId(), CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        account.setRoles(rolesRepository.findByIdAndDeleteFlag(acc.getRoleId(), CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND)));
        account.setCommonUpdate(commonService.idUserAccountLogin());
        Map<String, Object> response = new HashMap<>();
        response.put("update", "done");
        response.put("id", accountRepository.save(account).getId());
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, response);
    }

    @Override
    public ApiResponse blockUser(Long[] ids) {
        Map<String, Object> response = new HashMap<>();
        for (Long id : ids) {
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

            account.setCommonDelete(commonService.idUserAccountLogin());
            accountRepository.save(account);

            Users users = account.getUser();
            users.setCommonDelete(commonService.idUserAccountLogin());
            usersRepository.save(users);
        }
        response.put("deleteUser", "done");
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, response);
    }

    @Override
    public ApiResponse getUserAcc(int pageNo) {
        Map<String, Object> response = new HashMap<>();
        int size = accountRepository.getSizeUserAcc(CommonConstant.DELETE_FLAG_FALSE, CommonConstant.NUMBER_3);
        int totalPage = size % CommonConstant.SIZE_PER_PAGE == 0 ? size / CommonConstant.SIZE_PER_PAGE : size / CommonConstant.SIZE_PER_PAGE + 1;
        if (pageNo < CommonConstant.NUMBER_1 || pageNo > totalPage) {
            pageNo = CommonConstant.NUMBER_1;
        }
        List<AccountMapping> mapping = accountRepository.getUserAcc(CommonConstant.DELETE_FLAG_FALSE, CommonConstant.NUMBER_3, CommonConstant.SIZE_PER_PAGE, (pageNo - 1) * CommonConstant.SIZE_PER_PAGE);
        response.put("totalPage", totalPage);
        response.put("pageNo", pageNo);
        response.put("data", mapping);
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, response);
    }

    @Override
    public ApiResponse getAccInfo() {
        List<AccInfoMapping> mappings = accountRepository.getAccInfo();
        List<AccInfoResponse> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT_DDMMYYYY);
        int i = CommonConstant.NUMBER_1;
        for (AccInfoMapping m : mappings) {
            list.add(new AccInfoResponse(m.getId(), i++, m.getName(),m.getMail(), String.valueOf(m.getRoleId()), m.getRoleName(), m.getStatus(), sdf.format(m.getCreatedDate())));
        }

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, list);
    }

    @Override
    public ApiResponse updateAccInfo(Map<String, Object> req) {
        Map<String, Object> response = new HashMap<>();
        Account account = accountRepository.findById(Long.parseLong(String.valueOf(req.get("id"))))
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        if (!account.getRoles().getId().equals(Long.parseLong(String.valueOf(req.get("roleId"))))) {
            account.setRoles(rolesRepository.findByIdAndDeleteFlag(Long.parseLong(String.valueOf(req.get("roleId"))), CommonConstant.DELETE_FLAG_FALSE)
                    .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND)));
            account.setCommonUpdate(commonService.idUserAccountLogin());
        }

        Users users = account.getUser();
        if ("Dừng".equals(String.valueOf(req.get("status"))) && !account.isDeleteFlag() ||
                "Dừng".equals(String.valueOf(req.get("status"))) && !users.isDeleteFlag()) {
            account.setCommonDelete(commonService.idUserAccountLogin());
            users.setCommonDelete(commonService.idUserAccountLogin());
        } else if ("Hoạt động".equals(String.valueOf(req.get("status"))) && account.isDeleteFlag() ||
                "Hoạt động".equals(String.valueOf(req.get("status"))) && users.isDeleteFlag()) {
            account.setDeleteFlag(CommonConstant.DELETE_FLAG_FALSE);
            users.setDeleteFlag(CommonConstant.DELETE_FLAG_FALSE);
            users.setCommonUpdate(commonService.idUserAccountLogin());
            account.setCommonUpdate(commonService.idUserAccountLogin());
        }
        usersRepository.save(users);
        accountRepository.save(account);
        response.put("updateAccInfo", "done");
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, response);
    }
}
