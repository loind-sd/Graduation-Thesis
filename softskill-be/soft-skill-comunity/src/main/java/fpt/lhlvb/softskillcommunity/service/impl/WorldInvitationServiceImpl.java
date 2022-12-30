package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.WorldInvitation;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.WorldInvitationResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.WorldInvitationMapping;
import fpt.lhlvb.softskillcommunity.repository.WorldInvitationRepository;
import fpt.lhlvb.softskillcommunity.service.WorldInvitationService;
import fpt.lhlvb.softskillcommunity.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorldInvitationServiceImpl implements WorldInvitationService {
    @Autowired
    private WorldInvitationRepository worldInvitationRepository;

    @Autowired
    private CommonService commonService;

    @Override
    public ApiResponse loadInvitation(Map<String, Object> req) {
        Long msgId = req.get("msgId") == null ? Long.MAX_VALUE : Long.parseLong(String.valueOf(req.get("msgId")));
        int offset = req.get("offset") == null ? 0 : Integer.parseInt(String.valueOf(req.get("offset")));
        List<WorldInvitationMapping> worldInvitationMappings = worldInvitationRepository.getWithLimit(Integer.parseInt(String.valueOf(req.get("limit"))), CommonConstant.DELETE_FLAG_FALSE, commonService.idUserAccountLogin(), msgId, offset, Long.parseLong(String.valueOf(req.get("channelId"))));
        List<WorldInvitationResponse> response = new ArrayList<>();
        Timestamp now = CommonUtils.resultTimestamp();
        for (WorldInvitationMapping w : worldInvitationMappings) {
            response.add(new WorldInvitationResponse(
                    w.getWorldInvitationId(),
                    w.getRoomId(),
                    w.getCreatedBy(),
                    w.getCreatedName(),
                    w.getCreatedMail(),
                    w.getCreatedNickName(),
                    CommonUtils.calculateTimeStamp(now, w.getCreatedTime()),
                    w.getStatus()));
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, response);
    }

    @Override
    public ApiResponse addInvitation(Long roomId) {
        Map<String, Object> response = new HashMap<>();
        if (worldInvitationRepository.checkExistInvitation(CommonConstant.DELETE_FLAG_FALSE, commonService.idUserAccountLogin(), roomId).get() == CommonConstant.DELETE_FLAG_FALSE) {
            WorldInvitation worldInvitation = new WorldInvitation();
            worldInvitation.setRoomId(roomId);
            worldInvitation.setCommonRegister(commonService.idUserAccountLogin());

            Long id = worldInvitationRepository.save(worldInvitation).getId();
            response.put("id", id);
            response.put("add", "done");
        } else {
            response.put("info", "already sent invite today");
        }

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, response);
    }

    @Override
    public ApiResponse deleteMessage(Long id) {
        WorldInvitation worldInvitation = worldInvitationRepository.findByIdAndDeleteFlag(id, CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        worldInvitation.setCommonDelete(commonService.idUserAccountLogin());
        Map<String, Object> response = new HashMap<>();
        response.put("deleteMessage", "done");
        response.put("id", worldInvitationRepository.save(worldInvitation));

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, response);
    }
}
