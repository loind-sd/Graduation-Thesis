package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.repository.RankInfoRepository;
import fpt.lhlvb.softskillcommunity.service.RankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RankInfoServiceImpl implements RankInfoService {

    @Autowired
    private RankInfoRepository rankInfoRepository;

    @Override
    public Map<String, Object> getAllRankInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("rankInfo", rankInfoRepository.findAllByDeleteFlag(CommonConstant.DELETE_FLAG_FALSE));

        return response;
    }
}
