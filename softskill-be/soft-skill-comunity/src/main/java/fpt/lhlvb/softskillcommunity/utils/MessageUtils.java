package fpt.lhlvb.softskillcommunity.utils;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;

public class MessageUtils {
    //Common
    public static final String ERROR_DUPLICATE = "Trùng %s";
    public static final String ERROR_NULL = "Không để trống %s";
    public static final String ERROR_STRING_LENGTH = "%s không để trống và không được phép vượt quá %s kí tự";
    public static final String ERROR_NOT_EXIST = "Không tồn tại %s";
    public static final String ERROR_NOT_VALID = "%s không hợp lệ";
    public static final String ERROR_DATE = "Ngày tháng %s không hợp lệ";

    // TaskManager
    public static final String ADD_TASK_SUCCESS = "Thêm nhiệm vụ mới thành công";
    public static final String UPDATE_TASK_SUCCESS = "Cập nhật nhiệm vụ thành công";
    public static final String DELETE_TASK_SUCCESS = "Xoá nhiệm vụ thành công";
    public static final String UPLOAD_IMAGE_ERROR = "File không đúng định dạng";

    // USER
    public static final String UPDATE_USER_PROFILE_NICKNAME_ERROR_1 = "Đã trùng nickname với người khác";
    public static final String UPDATE_USER_PROFILE_NICKNAME_ERROR_2 = "Nickname của bạn phải bắt đầu bằng dấu @";
    public static final String UPDATE_USER_PROFILE_NICKNAME_ERROR_3 = "Nickname mới không trùng với nickname cũ";
    public static final String UPDATE_USER_PROFILE_NAME_ERROR_1 = "Tên mới không trùng với tên cũ";
    public static final String UPDATE_USER_PROFILE_SUCCESS = "Cập nhật thông tin cá nhân thành công!";
    public static final String ERROR_PASSWORD = "Mật khẩu của bạn phải chứa ít nhất 8 kí tự bao gồm cả chữ, số và 1 chữ cái viết hoa";
    public static final String ERROR_OLD_PASSWORD_DUPLICATE = "Mật khẩu cũ của bạn không đúng";
    public static final String ERROR_OLD_AND_NEW_PASSWORD = "Mật khẩu cũ và mật khẩu mới không được trùng nhau";
    public static final String ERROR_RENEW_PASSWORD = "Xác nhận mật khẩu mới không đúng";
    public static final String CHANGE_PASSWORD_SUCCESS = "Đổi mật khẩu thành công";
    public static final String FORGOT_PASSWORD_SUCCESS = "Đặt lại mật khẩu thành công";

    // ROOM
    public static final String SEARCH_AND_FILTER_ROOM_ERROR = "Không tìm thấy dữ liệu";
    public static final String SEARCH_AND_FILTER_ROOM_SUCCESS_1 = "Tìm thấy ";
    public static final String SEARCH_AND_FILTER_ROOM_SUCCESS_2 = " dữ liệu";
    public static final String NOT_JOINED_ROOM = "Bạn chưa tham gia phòng nào!";
    public static final String NOT_REGISTER_ROOM = "Bạn chưa đăng ký tham gia phòng nào!";
    public static final String DELETE_ROOM = "Xoá phòng thành công!";
    public static final String DELETE_BOOKING_ROOM = "Xoá phòng đặt lịch thành công!";
    public static final String NOT_CREATED_ROOM = "Bạn chưa tạo phòng nào!";
    public static final String NOT_CREATED_BOOKING_ROOM = "Bạn chưa tạo phòng đặt lịch nào!";
    public static final String CREATE_ROOM_SUCCESS = "Tạo phòng thành công!";
    public static final String UPDATE_ROOM_SUCCESS = "Cập nhật thông tin phòng thành công!";
    public static final String ERROR_ROOM_SIZE = "Số lượng người trong phòng không vượt quá "
            + CommonConstant.MIN_ROOM_SIZE + " - " + CommonConstant.MAX_ROOM_SIZE + " người";
    public static final String NOT_ROUND_ROOM_BY_SOFT_SKILL_ID = "Not found Rooms by SoftSkillId = ";

    // ROOM_TASK
    public static final String REGISTER_ROOM_TASK_SUCCESS = "Tham gia phòng thành công!";
    public static final String ACCEPT_USER_ROOM_TASK_SUCCESS = "Xác nhận người mới cùng làm việc thành công!";
    public static final String ADD_ROOM_TASK_SUCCESS = "Thêm nhiệm vụ nhóm thành công!";
    public static final String ADD_ROOM_TASK_WAITING = "Mọi thành viên trong nhóm của bạn cần chọn nhiệm vụ nhóm giống nhau!";
    public static final String LOCK_ROOM = "Khoá phòng thành công!";
    public static final String UNLOCK_ROOM = "Mở khoá phòng thành công!";
    public static final String CHOOSE_STATUS_ROOM_TASK = "Mọi thành viên trong nhóm của bạn cần xác nhận giống nhau!";
    public static final String COMPLETED_ROOM_TASK = "Bạn đã hoàn thành nhiệm vụ này!";
    public static final String CANCEL_ROOM_TASK = "Bạn đã huỷ bỏ nhiệm vụ này!";
    public static final String OUT_ROOM_TASK = "Rời phòng thành công.";

    // USER_TASK
    public static final String COMPLETED_USER_ROOM_TASK = "Bạn đã hoàn thành nhiệm vụ này!";

    // TASK
    public static final String ADD_TASK_FAVOURITE = "Thích nhiệm vụ thành công";
    public static final String REMOVE_TASK_FAVOURITE = "Bỏ thích nhiệm vụ thành công";

    // SOFT SKILL
    public static final String ADD_SOFT_SKILL = "Thêm kỹ năng mềm thành công!";
    public static final String UPDATE_SOFT_SKILL = "Cập nhật thông tin kỹ năng mềm thành công!";
    public static final String DELETE_SOFT_SKILL = "Xoá kỹ năng mềm thành công!";

    //FEEDBACK
    public static final String ADD_FEEDBACK_SUCCESS = "Gửi đánh giá thành công!";
    public static final String ERROR_RATE_STAR = "Đánh giá sao không vượt quá "
            + CommonConstant.MIN_RATE_STAR + " - " + CommonConstant.MAX_RATE_STAR + " sao";

    //ADMIN FEEDBACK
    public static final String CONFIRM_FEEDBACK_SUBJECT = "Trả lời phản hồi.";

    public static final String ACCEPT_FRIEND = " đã trở thành bạn bè!";
    public static final String DENY_FRIEND = "Đã xóa lời mời!";
}
