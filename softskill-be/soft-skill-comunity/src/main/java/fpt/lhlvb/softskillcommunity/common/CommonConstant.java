package fpt.lhlvb.softskillcommunity.common;

public class CommonConstant {
    public static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d`~!@#$^&*()_+}|:\"<>?,.\\/;'\\\\\\]=-\\[]{8,50}$";
    public static final String REGEX_EMAIL = "^([\\w#!%$‘&+*/=?^_`{|}~-]+[\\w#!%$‘&+*/=?^_`.{|}~-]*[\\w#!%$‘&+*/=?^_`{|}~-]+){1,64}@[\\w]{2,63}[\\w-]*(\\.[\\w-]{2,63})*(\\.[a-zA-Z]{2,63})$";
    public static final String DATE_FORMAT_YYYYMMDD_HHMM = "yyyy-MM-dd HH:mm";

    public static final String COMMA = ",";

    public static final String DATE_FORMAT_DDMMYYYY = "dd/MM/yyyy";

    public static final String DATE_FORMAT_YYYYMMDD = "yyyy/MM/dd";

    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public static final String LOCAL_DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd hh:mm:ss";

    public static final String SCHEDULE_DELETE_ROOM = "0 0 0 ? * *";

    public static final String SCHEDULE_STATISTIC_USER_COUNT = "0 50 23 ? * *";

    public static final String TRUE_FLG = "1";

    public static final String FALSE_FLG = "0";

    public static final Integer STATUS_1 = 1;

    public static final Integer STATUS_0 = 0;

    public static final Integer STATUS_IS_FRIEND = 1;

    public static final Integer STATUS_IS_NOT_FRIEND = 0;

    public static final Integer STATUS_TASK_NOT_START = 1;

    public static final Integer STATUS_TASK_DOING = 2;

    public static final Integer ROOM_TYPE_ACTIVE = 1;

    public static final Integer ROOM_TYPE_BOOKING = 2;

    public static final Integer STATUS_TASK_COMPLETED = 3;

    public static final Integer NOTIFICATION_ADD_FRIEND = 1;

    public static final Integer NOTIFICATION_INVITE_ROOM = 2;

    public static final Boolean NOTIFICATION_STATUS_FALSE = false;

    public static final Boolean DELIVERED_FALSE = false;

    public static final Integer NOTIFICATION_REGISTER_ROOM_TASK = 3;

    public static final Integer NOTIFICATION_ADD_NEW_CONTACT = 4;

    public static final String SUCCESS_MESSAGE = "successMessage";
    public static final String WARNING_MESSAGE = "warningMessage";
    public static final String ERROR_MESSAGE = "errorMessage";

    public static final String ACTION_UPDATE = "update";

    public static final String ACTION_DELETE = "delete";

    public static final String ACTION_ACCEPT = "accept";
    public static final String ACTION_DENY = "deny";

    public static final Integer LIMIT_1 = 1;

    public static final Integer USERS_TASK_DONT_CARE = -1;
    public static final Integer NUMBER_1 = 1;
    public static final Integer NUMBER_2 = 2;
    public static final Integer NUMBER_3 = 3;
    public static final Integer NUMBER_4 = 4;

    public static final Integer ROLE_ADMIN = 1;

    public static final Integer ROLE_AUTHOR = 2;

    public static final Integer ROLE_USER = 3;

    public static final Integer MAX_ROOM_SIZE = 100;
    public static final Integer MAX_ROOM_NAME = 50;
    public static final Integer MAX_FEEDBACK_CONTENT = 50;

    public static final Integer MIN_ROOM_SIZE = 1;
    public static final Integer MIN_RATE_STAR = 1;
    public static final Integer MAX_RATE_STAR = 5;

    public static final Boolean DELETE_FLAG_FALSE = false;

    public static final Boolean DELETE_FLAG_TRUE = true;

    public static final Boolean JOIN_STATUS_FALSE = false;

    public static final Boolean JOIN_STATUS_TRUE = true;

    public static final Boolean IS_ONLINE_TRUE = true;

    public static final Boolean IS_ONLINE_FALSE = false;

    public static final Boolean IS_OLD_MEMBER_FALSE = false;

    public static final Boolean IS_OLD_MEMBER_TRUE = true;

    public static final Boolean IS_ACCEPT_DO_ROOM_TASK = true;

    public static final Boolean IS_CLICKED_TRUE = true;

    public static final Boolean IS_CLICKED_FALSE = false;

    public static final Boolean ROOM_LOCK_TRUE = true;

    public static final Boolean ROOM_LOCK_FALSE = false;
    public static final Boolean IS_JOINED_TRUE = true;

    public static final Boolean IS_JOINED_FALSE = false;

    public static final Integer MAX_NAVIGATION_RESULT = 20;

    // http code
    public static final String HTTP_CODE_200 = "200";
    public static final String HTTP_CODE_400 = "400";
    public static final String HTTP_CODE_401 = "401";
    public static final String HTTP_CODE_403 = "403";
    public static final String HTTP_CODE_404 = "404";
    public static final String HTTP_CODE_500 = "500";

    public static final String SECRET = "secret";
    public static final String SUCCESS = "Success get information record";
    public static final String ERROR = "Error get information record";
    public static final String DELETE_SUCCESS = "Delete record success";
    public static final String DELETE_FAIL = "Delete record fail";
    public static final String CREATE_SUCCESS = "Create or update record success";
    public static final String RECORD_DOES_NOT_EXIST = "The record does not exist";
    public static final String FIELD_INVALID = "Some field invalid";
    public static final String RECORD_ALREADY_EXISTS = "The record %s already exists";
    public static final String FRIEND_REQUEST_NOTIFICATION = " đã gửi cho bạn lời mời kết bạn";
    public static final String ROOM_INVITE_NOTIFICATION = " đã mời bạn tham gia phòng của ";
    public static final String NOTIFICATION_JOIN_ROOM_1 = "Còn 30 phút nữa là đến giờ bắt đầu công việc ở phòng ";
    public static final String NOTIFICATION_JOIN_ROOM_2 = "Đã đến giờ bắt đầu công việc tại phòng ";
    public static final String NOTIFICATION_JOIN_ROOM_3 = ", Hãy tham gia ngay nào.";
    public static final String NOTIFICATION_JOIN_ROOM_4 = " đã bị huỷ, mong bạn thông cảm!";
    public static final String USER_TASK_TIME_DAY_DOING = "Đã bắt đầu làm từ %s ngày trước";
    public static final String USER_TASK_TIME_DAY_DONE = "Đã hoàn thành từ %s ngày trước";
    public static final String USER_TASK_TIME_MONTH_DOING = "Đã bắt đầu làm khoảng %s tháng trước";
    public static final String USER_TASK_TIME_MONTH_DONE = "Đã hoàn thành khoảng %s tháng trước";
    public static final String USER_TASK_TIME_YEAR_DOING = "Đã bắt đầu làm khoảng %s năm trước";
    public static final String USER_TASK_TIME_YEAR_DONE = "Đã hoàn thành khoảng %s năm trước";

    public static final String RECORD_NOT_FOUND = "Record Not Found";
    public static final String PARSE_EXCEPTION = "Parse Exception";

    // complete code
    public static final Integer COMPLETED = 1;
    public static final Integer NONE_COMPLETE = 0;
    public static final String LOGGED_OUT_SUCCESS = "User has successfully logged out from the system!";

    public static final int SIZE_PER_PAGE = 20;
    public static final String RANDOM_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

}
