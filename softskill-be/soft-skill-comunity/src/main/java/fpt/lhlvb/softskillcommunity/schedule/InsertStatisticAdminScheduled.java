package fpt.lhlvb.softskillcommunity.schedule;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.entity.StatisticAdmin;
import fpt.lhlvb.softskillcommunity.entity.Users;
import fpt.lhlvb.softskillcommunity.repository.StatisticAdminRepository;
import fpt.lhlvb.softskillcommunity.repository.UsersRepository;
import fpt.lhlvb.softskillcommunity.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class InsertStatisticAdminScheduled {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private StatisticAdminRepository statisticAdminRepository;

    @Scheduled(cron = CommonConstant.SCHEDULE_STATISTIC_USER_COUNT)
    private void addStatistic() {
        List<Users> usersList = usersRepository.findByDeleteFlag(CommonConstant.DELETE_FLAG_FALSE);
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT_YYYY_MM_DD);
        int number = CommonConstant.STATUS_0;

        String today = sdf.format(CommonUtils.resultTimestamp());
        for (Users u : usersList) {
            if (today.equals(sdf.format(u.getUpdatedAt()))) {
                number++;
            }
        }

        StatisticAdmin statisticAdmin = new StatisticAdmin();
        statisticAdmin.setDate(Date.valueOf(today));
        statisticAdmin.setNumber(number);
        statisticAdmin.setCommonRegister(1L);

        statisticAdminRepository.save(statisticAdmin);
        logger.info("***** Schedule insert new statistic for admin *****");
    }
}
