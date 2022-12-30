package fpt.lhlvb.softskillcommunity.User;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.UserTask;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.model.request.LoginRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.DeleteRoomUserRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.FeedbackRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.RoomRequest;
import fpt.lhlvb.softskillcommunity.repository.UserTaskRepository;
import fpt.lhlvb.softskillcommunity.securities.UserDetailsServiceImpl;
import fpt.lhlvb.softskillcommunity.securities.jwt.JwtTokenUtils;
import fpt.lhlvb.softskillcommunity.service.*;
import fpt.lhlvb.softskillcommunity.utils.CommonUtils;
import fpt.lhlvb.softskillcommunity.utils.MessageUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserTest {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    @Autowired
    private JwtTokenUtils jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private RoomsService roomsService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserTaskService userTaskService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserTaskRepository userTaskRepository;

    @Autowired
    private TasksService tasksService;

    @Autowired
    private UserTaskFavoriteService userTaskFavoriteService;

    @BeforeEach
    public void loginRequire() {
        String jwt = authenticationService.login(new LoginRequest("abc.vinh", "123456")).getToken();
        try {
            if (jwt != null && jwtTokenUtil.validateJwtToken(jwt)) {
                String username = jwtTokenUtil.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception e) {
            logger.error("Cannot set user authentication: " + e);
        }
        logger.info("Login success " + "\n");
//        logger.info("id Login " + commonService.idUserAccountLogin());
    }

    @AfterEach
    public void afterMethod() {
        logger.info("Function has been implemented\n");
    }

    //    String roomName, String description, Long softSkillId, Integer roomSize, String startTime, Long taskId
    @Test
    @DisplayName("Function create room")
    public void createRoom() {
//        assumingThat(true, () -> assertNotNull(commonService.idUserAccountLogin()));

        String errorMessage = CommonConstant.ERROR_MESSAGE;
        String successMessage = CommonConstant.SUCCESS_MESSAGE;

        List<RoomRequest> roomRequests = new ArrayList<>() {
            {
                add(new RoomRequest("Test_NameMax", "Phòng dành cho giới trẻ", Long.parseLong("1"), 10, null, null));
                add(new RoomRequest("Group1_tw", "Phòng dành cho giới trẻ", Long.parseLong("1"), 10, null, null));
                add(new RoomRequest("Groupnew1", "Phòng dành cho giới trẻ", Long.parseLong("1"), 101, null, null));
                add(new RoomRequest("Groupnew2", "Phòng dành cho giới trẻ", null, 10, null, null));
                add(new RoomRequest("Groupne3", "Phòng dành cho giới trẻ", Long.parseLong("1"), 10, "2022-13-10", Long.parseLong("1")));
                add(new RoomRequest("Groupnew4", "Phòng dành cho giới trẻ", Long.parseLong("1"), 10, "2022-10-10", null));
                add(new RoomRequest("Groupnew5", null, Long.parseLong("1"), 10, "2022-10-10", Long.parseLong("1")));
                add(new RoomRequest("Groupnew6", "Phòng dành cho giới trẻ", Long.parseLong("-1"), 10, null, null));
                add(new RoomRequest("Groupnew7", "Phòng dành cho giới trẻ", Long.parseLong("1"), 10, "2022-09-09", Long.parseLong("-1")));
                add(new RoomRequest(CommonUtils.randomString(6), "Phòng dành cho giới trẻ", Long.parseLong("1"), 10, "2022-10-10", Long.parseLong("1")));
                add(new RoomRequest(CommonUtils.randomString(6), "Phòng dành cho giới trẻ", Long.parseLong("1"), 10, null, null));
            }
        };

        Object errorRoomName = roomsService.addRoom(roomRequests.get(0)).get(errorMessage);
        assertNotNull(errorRoomName);
        logger.info(errorMessage + " - " + errorRoomName);

        Object errorRoomNameDuplicate = roomsService.addRoom(roomRequests.get(1)).get(errorMessage);
        assertNotNull(errorRoomNameDuplicate);
        logger.info(errorMessage + " - " + errorRoomNameDuplicate);

        Object errorRoomSize = roomsService.addRoom(roomRequests.get(2)).get(errorMessage);
        assertNotNull(errorRoomSize);
        logger.info(errorMessage + " - " + errorRoomSize);

        Object errorSoftSkill = roomsService.addRoom(roomRequests.get(3)).get(errorMessage);
        assertNotNull(errorSoftSkill);
        logger.info(errorMessage + " - " + errorSoftSkill);

        assertThrows(RecordNotFoundException.class, () -> {
            roomsService.addRoom(roomRequests.get(7));
        });
        logger.info(errorMessage + " - " + String.format(MessageUtils.ERROR_NOT_VALID, "Kỹ năng mềm"));

//        ParseException errorStartTime = assertThrows(ParseException.class, () -> {
//            roomsService.addRoom(roomRequests.get(4));
//        });
//
        Object errorStartTime = roomsService.addRoom(roomRequests.get(4)).get(errorMessage);
        assertNotNull(errorStartTime);
        logger.info(errorMessage + " - " + errorStartTime);

        Object errorTask = roomsService.addRoom(roomRequests.get(5)).get(errorMessage);
        assertNotNull(errorTask);
        logger.info(errorMessage + " - " + errorTask);

        assertThrows(RecordNotFoundException.class, () -> {
            roomsService.addRoom(roomRequests.get(8));
        });
        logger.info(errorMessage + " - " + String.format(MessageUtils.ERROR_NOT_VALID, "Nhiệm vụ"));

        Object errorDescription = roomsService.addRoom(roomRequests.get(6)).get(errorMessage);
        assertNotNull(errorDescription);
        logger.info(errorMessage + " - " + errorDescription);

        Object successBooking = roomsService.addRoom(roomRequests.get(9)).get(successMessage);
        assertNotNull(successBooking);
        logger.info(successMessage + " - " + successBooking);

        Object successActive = roomsService.addRoom(roomRequests.get(10)).get(successMessage);
        assertNotNull(successActive);
        logger.info(successMessage + " - " + successActive);
    }

    @Test
    @DisplayName("Function delete booking room")
    void joinRoom() {

    }

//    @ParameterizedTest
//    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
//    public void createRoom1(String roomName, String description, String softSkillId, Integer roomSize, String startTime, String taskId, String message) {
//        RoomRequest roomRequest = new RoomRequest(roomName, description, Long.parseLong(softSkillId), roomSize, startTime, Long.parseLong(taskId));
//
//        String messageResponse = roomsService.addRoom(roomRequest).get(CommonConstant.ERROR_MESSAGE).toString();
//        assertEquals(messageResponse, message);
//        logger.info(message + " - " + messageResponse);
//    }

    @Test
    @DisplayName("Function send feedback")
    public void sendFeedback() {
        List<FeedbackRequest> feedbacks = new ArrayList<>() {
            {
                add(new FeedbackRequest(null, "1", "Trang web quá chậm , cần phải code tốt hơn\n" +
                        "nâng cấp trang web trước khi mọi người rời đi"));
                add(new FeedbackRequest("0", "1", "Trang web quá chậm , cần phải code tốt hơn\n" +
                        "nâng cấp trang web trước khi mọi người rời đi"));
                add(new FeedbackRequest("3", null, "Trang web quá chậm , cần phải code tốt hơn\n" +
                        "nâng cấp trang web trước khi mọi người rời đi"));
                add(new FeedbackRequest("3", "-1", "Trang web quá chậm , cần phải code tốt hơn\n" +
                        "nâng cấp trang web trước khi mọi người rời đi"));
                add(new FeedbackRequest("3", "1", "Trang web quá chậm , cần phải code tốt hơn\n" +
                        "nâng cấp trang web trước khi mọi người rời đi"));
                add(new FeedbackRequest("3", "1", "Trang web phản hồi quá chậm"));
            }
        };

        String errorMessageConst = CommonConstant.ERROR_MESSAGE;
        String successMessageConst = CommonConstant.SUCCESS_MESSAGE;

        Object errorRate = feedbackService.sentFeedback(feedbacks.get(0)).get(errorMessageConst);
        assertNotNull(errorRate);
        logger.info(errorMessageConst + " - " + errorRate);

        Object errorRate1 = feedbackService.sentFeedback(feedbacks.get(1)).get(errorMessageConst);
        assertNotNull(errorRate1);
        logger.info(errorMessageConst + " - " + errorRate1);

        Object errorTitle = feedbackService.sentFeedback(feedbacks.get(3)).get(errorMessageConst);
        assertNotNull(errorTitle);
        logger.info(errorMessageConst + " - " + errorTitle);

        Object errorTitle1 = feedbackService.sentFeedback(feedbacks.get(4)).get(errorMessageConst);
        assertNotNull(errorTitle1);
        logger.info(errorMessageConst + " - " + errorTitle1);

        Object success = feedbackService.sentFeedback(feedbacks.get(5)).get(successMessageConst);
        assertNotNull(success);
        logger.info(successMessageConst + " - " + success);
    }

    @Test
    @DisplayName("Function completed individual task")
    void completedIndividualTask() {
        String errorMessageConst = CommonConstant.ERROR_MESSAGE;
        String successMessageConst = CommonConstant.SUCCESS_MESSAGE;

        Long individualTaskId = userTaskService.addNewIndividualTask();

        List<Long> taskIds = new ArrayList<>() {
            {
                add(null);
                add(Long.parseLong("-1"));
                add(Long.parseLong("11"));
            }
        };

        assertThrows(RecordNotFoundException.class, () -> {
            userTaskService.updateStatusUserTask(taskIds.get(0));
        });
        logger.info(errorMessageConst + " " + String.format(MessageUtils.ERROR_NOT_VALID, "Nhiệm vụ"));

        assertThrows(RecordNotFoundException.class, () -> {
            userTaskService.updateStatusUserTask(taskIds.get(1));
        });
        logger.info(errorMessageConst + " " + String.format(MessageUtils.ERROR_NOT_VALID, "Nhiệm vụ"));

        assertThrows(RecordNotFoundException.class, () -> {
            userTaskService.updateStatusUserTask(taskIds.get(2));
        });
        logger.info(errorMessageConst + " " + String.format(MessageUtils.ERROR_NOT_VALID, "Nhiệm vụ"));

        Object success = userTaskService.updateStatusUserTask(individualTaskId).get(successMessageConst);
        logger.info(successMessageConst + " " + success);

        userTaskService.deleteIndividualTask(individualTaskId);
    }

    @Test
    @DisplayName("Function delete room booking")
    void deleteRoomBooking() {
        String errorMessageConst = CommonConstant.ERROR_MESSAGE;
        String successMessageConst = CommonConstant.SUCCESS_MESSAGE;

        Long roomId = roomsService.addNewRoomBooking().get(0);
        Long taskId = roomsService.addNewRoomBooking().get(1);

        assertThrows(RecordNotFoundException.class, () -> {
            roomsService.userDeleteRoomBooking(new DeleteRoomUserRequest(Long.parseLong("11"), Long.parseLong("0")));
        });
        logger.info(errorMessageConst + " - " + String.format(MessageUtils.ERROR_NOT_VALID, "Phòng đặt lịch"));

        Object success = roomsService.userDeleteRoomBooking(new DeleteRoomUserRequest(roomId, taskId)).get(successMessageConst);
        assertNotNull(success);
        logger.info(successMessageConst + " - " + success);
    }

    @Test
    @DisplayName("Function mark favourite task")
    void markFavouriteTask() {
        String errorMessageConst = CommonConstant.ERROR_MESSAGE;
        String successMessageConst = CommonConstant.SUCCESS_MESSAGE;

        assertThrows(RecordNotFoundException.class, () -> {
            userTaskFavoriteService.addTaskFavorite(Long.parseLong("-1"), CommonConstant.STATUS_TASK_DOING);
        });
        logger.info(errorMessageConst + " - " + String.format(MessageUtils.ERROR_NOT_VALID, "Nhiệm vụ"));

        assertThrows(RecordNotFoundException.class, () -> {
            userTaskFavoriteService.addTaskFavorite(null, CommonConstant.STATUS_TASK_DOING);
        });
        logger.info(errorMessageConst + " - " + String.format(MessageUtils.ERROR_NOT_VALID, "Nhiệm vụ"));

        Object success = favouriteTaskCommon(true).get(successMessageConst);
        assertNotNull(success);
        logger.info(successMessageConst + " - " + success);
    }

    private Map<String, Object> favouriteTaskCommon(Boolean isMark){
        Long taskId = Long.parseLong(tasksService.addTask().get("taskId").toString());

        if(isMark){
            return userTaskFavoriteService.addTaskFavorite(taskId, CommonConstant.STATUS_TASK_DOING);
        }
        userTaskFavoriteService.addTaskFavorite(taskId, CommonConstant.STATUS_TASK_DOING);
        return userTaskFavoriteService.addTaskFavorite(taskId, CommonConstant.STATUS_TASK_DOING);
    }

    @Test
    @DisplayName("Function unmark favourite task")
    void unmarkFavouriteTask() {
        String errorMessageConst = CommonConstant.ERROR_MESSAGE;
        String successMessageConst = CommonConstant.SUCCESS_MESSAGE;

        assertThrows(RecordNotFoundException.class, () -> {
            userTaskFavoriteService.addTaskFavorite(Long.parseLong("-1"), CommonConstant.STATUS_TASK_DOING);
        });
        logger.info(errorMessageConst + " - " + String.format(MessageUtils.ERROR_NOT_VALID, "Nhiệm vụ"));

        assertThrows(RecordNotFoundException.class, () -> {
            userTaskFavoriteService.addTaskFavorite(null, CommonConstant.STATUS_TASK_DOING);
        });
        logger.info(errorMessageConst + " - " + String.format(MessageUtils.ERROR_NOT_VALID, "Nhiệm vụ"));

        Object success =favouriteTaskCommon(false).get(successMessageConst);
        assertNotNull(success);
        logger.info(successMessageConst + " - " + success);
    }
}
