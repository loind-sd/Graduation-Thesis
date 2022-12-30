package fpt.lhlvb.softskillcommunity.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public interface RankInfoService {
    Map<String, Object> getAllRankInfo();
}
