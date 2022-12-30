import React, { useEffect, useState } from "react";
import "./HeaderRoom.scss";
import { useFormik } from "formik";
import { UilTimes } from "@iconscout/react-unicons";

import {
  Button,
  Dialog,
  Dropdown,
  InputNumber,
  InputTextarea,
  InputText,
  Calendar,
  addLocale,
  InputSwitch,
  Toast,
} from "primereact";
import { useDispatch, useSelector } from "react-redux";
import { getListSoftSkill } from "./../../../redux/thunks/soft-skill-thunks";
import {
  createNewRoom,
  resetFormRoom,
} from "../../../redux/thunks/room-active-thunks";
import { getTasksBySoftSkillId } from "../../../redux/thunks/_dropdown-thunks";
import { getTaskById } from "../../../redux/thunks/task-thunks";
import { convertDateToYYYYMMDD2 } from "../../../utils/common-service";
import { HTTP_200 } from "../../../utils/constant/CommonConstant";
import { useRef } from "react";
import { useHistory } from 'react-router';

const HeaderRoom = () => {
  const dispatch = useDispatch();
  const toast = useRef(null);

  const successSelector = useSelector((state) => state.roomData.success);
  const errorSelector = useSelector((state) => state.roomData.error);

  const softSkills = useSelector((state) => state.softSkillData.softSkills);
  const tasks = useSelector((state) => state.dropdownData.tasksBySoftSkillId);
  const taskDetail = useSelector((state) => state.taskData.task);

  // State
  const [displayPopup, setDisplayPopup] = useState(false);
  let today = new Date();
  let month = today.getMonth();
  let year = today.getFullYear();
  let prevMonth = month === 0 ? 11 : month - 1;
  let prevYear = prevMonth === 11 ? year - 1 : year;
  let nextMonth = month === 11 ? 0 : month + 1;
  let nextYear = nextMonth === 0 ? year + 1 : year;
  const [checkBooking, setCheckBooking] = useState(false);
  const [checkTaskDetail, setCheckTaskDetail] = useState(false);

  let minDate = new Date();
  minDate.setMonth(prevMonth);
  minDate.setFullYear(prevYear);

  let maxDate = new Date();
  maxDate.setMonth(nextMonth);
  maxDate.setFullYear(nextYear);

  addLocale("es", {
    firstDayOfWeek: 1,
    dayNames: [
      "domingo",
      "lunes",
      "martes",
      "miércoles",
      "jueves",
      "viernes",
      "sábado",
    ],
    dayNamesShort: ["dom", "lun", "mar", "mié", "jue", "vie", "sáb"],
    dayNamesMin: ["D", "L", "M", "X", "J", "V", "S"],
    monthNames: [
      "enero",
      "febrero",
      "marzo",
      "abril",
      "mayo",
      "junio",
      "julio",
      "agosto",
      "septiembre",
      "octubre",
      "noviembre",
      "diciembre",
    ],
    monthNamesShort: [
      "ene",
      "feb",
      "mar",
      "abr",
      "may",
      "jun",
      "jul",
      "ago",
      "sep",
      "oct",
      "nov",
      "dic",
    ],
    today: "Hoy",
    clear: "Limpiar",
  });

  useEffect(() => {
    dispatch(getListSoftSkill());
    dispatch(resetFormRoom());
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [dispatch]);

  useEffect(() => {
    if (successSelector === HTTP_200) {
      toast.current.show({
        severity: "success",
        summary: "Thành công",
        detail: "Thêm phòng thành công!",
        life: 3000,
      });
    }
    if (errorSelector !== HTTP_200 && errorSelector !== "") {
      toast.current.show({
        severity: "error",
        summary: "Thất bại",
        detail: "Thêm phòng thất bại!",
        life: 3000,
      });
    }
  }, [errorSelector, successSelector]);

  const history = useHistory();
  const formik = useFormik({
    initialValues: {
      roomName: "",
      roomSize: 5,
      selectedSoftSkill: null,
      description: "",
      timeRequest: "",
      task: null,
    },
    validate: (data) => {
      let errors = {};
      if (!data.roomName) {
        errors.roomName = "Bạn vui lòng nhập tên phòng.";
      }
      if (
        data.selectedSoftSkill == null ||
        data.selectedSoftSkill === undefined
      ) {
        errors.selectedSoftSkill = "Bạn cần lựa chọn một kỹ năng chính.";
      }
      if (checkBooking) {
        if (!data.timeRequest) {
          errors.timeRequest = "Bạn cần chọn thời gian.";
        }
        if (data.selectedSoftSkill) {
          if (!data.task) {
            errors.task = "Bạn cần lựa chọn một nhiệm vụ.";
          }
        }
      }
      return errors;
    },
    onSubmit: (data) => {
      let dataRq = {
        roomName: data.roomName,
        description: data.description,
        softSkillId: data.selectedSoftSkill?.id,
        roomSize: data.roomSize,
        startTime: data.timeRequest
          ? convertDateToYYYYMMDD2(data?.timeRequest)
          : null,
        taskId: data.task?.id,
      };
      // setFormData(dataRq);
      dispatch(resetFormRoom());
      dispatch(createNewRoom(dataRq, history));
      formik.resetForm();
      onHide("displayPopup");
    },
  });

  const isFormFieldValid = (name) =>
    !!(formik.touched[name] && formik.errors[name]);

  const getFormErrorMessage = (name) => {
    return (
      isFormFieldValid(name) && (
        <small className="p-error">{formik.errors[name]}</small>
      )
    );
  };

  const selectedTemplate = (option, props) => {
    if (option) {
      return <div>{option.name}</div>;
    }
    return <span>{props.placeholder}</span>;
  };

  const optionTemplate = (option) => {
    return <div>{option.name}</div>;
  };

  const dialogFuncMap = {
    displayPopup: setDisplayPopup,
  };

  const onClick = (name) => {
    dialogFuncMap[`${name}`](true);
  };

  const onHide = (name) => {
    dialogFuncMap[`${name}`](false);
  };

  function checkChooseSoftSkill(data) {
    if (data.value.id) {
      formik.setFieldValue("task", null);
      dispatch(getTasksBySoftSkillId(data.value.id));
    }
  }

  function getDataTask(data) {
    if (data.value.id) {
      dispatch(getTaskById(data.value.id));
    }
  }

  const layoutHeader = (
    <div className="room-header mt-1">
      <div className="title d-flex align-items-center">
        <span className="title-content mx-auto">
          <span style={{ color: "#B45714", marginLeft: "34px" }}>
            Tạo phòng mới
          </span>
        </span>
        <span
          style={{ cursor: "pointer" }}
          className="icon p-2 rounded-circle bg-light"
          onClick={() => onHide("displayPopup")}>
          <UilTimes />{" "}
        </span>
      </div>
    </div>
  );

  const layoutContent = (
    <div className="room-content mt-4">
      <div className="room-name ">
        <p className="title mb-0 font-weight-bold small">
          Tên phòng <span style={{ color: "red" }}>*</span>
        </p>
        <InputText
          name="roomName"
          value={formik.values.roomName}
          onChange={formik.handleChange}
          autoFocus
          className={{ "p-invalid": isFormFieldValid("roomName") }}
          minLength={4}
          maxLength={50}
          type="text"
          placeholder="Nhập tên phòng của bạn"
        />
        {getFormErrorMessage("roomName")}
      </div>
      <div className="row mt-3">
        <div className="room-size col-sm-6 p-0">
          <p className="title mb-1 font-weight-bold small">
            Số lượng người trong phòng <span style={{ color: "red" }}>*</span>
          </p>
          <InputNumber
            name="roomSize"
            value={formik.values.roomSize}
            onValueChange={formik.handleChange}
            required={true}
            inputId="horizontal"
            showButtons
            buttonLayout="horizontal"
            step={1}
            decrementButtonClassName="p-button-danger"
            incrementButtonClassName="p-button-success"
            incrementButtonIcon="pi pi-plus"
            decrementButtonIcon="pi pi-minus"
            min={1}
            max={50}
          />
        </div>
        <div className="room-soft-skill col-sm-6 p-0">
          <p className="title mb-1 font-weight-bold small">
            Chọn kỹ năng mềm <span style={{ color: "red" }}>*</span>
          </p>
          <Dropdown
            name="selectedSoftSkill"
            value={formik.values.selectedSoftSkill}
            onChange={(e) => {
              formik.handleChange(e);
              checkChooseSoftSkill(e);
            }}
            className={{
              "p-invalid ": isFormFieldValid("selectedSoftSkill"),
            }}
            options={softSkills.item}
            optionLabel="name"
            filter
            filterBy="name"
            placeholder="Lựa chọn kỹ năng mềm"
            valueTemplate={selectedTemplate}
            itemTemplate={optionTemplate}
          />
          {getFormErrorMessage("selectedSoftSkill")}
        </div>
      </div>

      <div className="room-description mt-3">
        <p className="title mb-1 font-weight-bold small">Mô tả về phòng</p>
        <InputTextarea
          name="description"
          value={formik.values.description}
          onChange={formik.handleChange}
          className="ml-3 mr-2"
          cols={3}
          placeholder="Chào mừng bạn vào phòng cùng luyện tập"
        />
      </div>
      <div className="room-check-booking mt-4 w-50 d-flex align-items-center">
        <span className="title mb-1 font-weight-bold small mr-3">
          Bạn muốn đặt lịch cho phòng?
        </span>
        <InputSwitch
          checked={checkBooking}
          onChange={(e) => setCheckBooking(e.value)}
        />
      </div>
      {checkBooking === true ? (
        <>
          <div className="room-date mt-3 col-sm-6 p-0">
            <p className="title mb-1 font-weight-bold small">
              Chọn thời gian <span style={{ color: "red" }}>*</span>
            </p>
            <Calendar
              name="timeRequest"
              value={formik.values.timeRequest}
              onChange={formik.handleChange}
              showTime
              className={{
                "p-invalid ": isFormFieldValid("timeRequest"),
              }}
              stepMinute={15}
              minDate={new Date()}
              maxDate={new Date(new Date().setDate(new Date().getDate() + 6))}
            />
            {getFormErrorMessage("timeRequest")}
          </div>
          <div className="room-task col-sm-12 mt-3 p-0">
            <p className="title mb-1 font-weight-bold small">
              Chọn nhiệm vụ <span style={{ color: "red" }}>*</span>
            </p>
            <div className="d-flex" style={{ alignItems: "flex-end" }}>
              <div className="col-sm-6 p-0">
                <Dropdown
                  name="task"
                  value={formik.values.task}
                  onChange={(e) => {
                    formik.handleChange(e);
                    getDataTask(e);
                  }}
                  className={{
                    "p-invalid ": isFormFieldValid("task"),
                  }}
                  options={tasks?.item}
                  optionLabel="name"
                  filter
                  filterBy="name"
                  placeholder="Lựa chọn nhiệm vụ"
                  valueTemplate={selectedTemplate}
                  itemTemplate={optionTemplate}
                />
              </div>
              {formik.values.task ? (
                <div
                  className="ml-3 p-0 task-detail"
                  onClick={() => setCheckTaskDetail(!checkTaskDetail)}>
                  {!checkTaskDetail ? (
                    <span className="ml-3">Chi tiết</span>
                  ) : (
                    <span className="ml-3">Thu gọn</span>
                  )}
                </div>
              ) : (
                ""
              )}
            </div>
            {getFormErrorMessage("task")}
          </div>
          {checkTaskDetail && taskDetail?.item ? (
            <div className="task-description">
              <InputTextarea
                value={taskDetail?.item.content}
                className="w-100"
                cols={3}
                readOnly={true}
              />
            </div>
          ) : (
            ""
          )}
        </>
      ) : (
        ""
      )}
    </div>
  );

  const footerLayout = (
    <footer className="room-footer mt-3 d-flex justify-content-center">
      <Button
        label="Tạo phòng ngay"
        type="submit"
        style={{
          width: "120px",
          fontSize: "12px",
          borderRadius: "7px",
          backgroundColor: "#5993d9",
          border: "#5993d9",
        }}
        className="ml-3"
        aria-label="Search"
      />
    </footer>
  );

  return (
    <React.Fragment>
      <Toast ref={toast} />
      <div className="header-room">
        <Button
          style={{
            backgroundColor: "#71A3E0",
            borderColor: " #71A3E0",
            fontSize: "14px",
            borderRadius: "8px",
          }}
          className="w-100 pl-5 pr-5 pb-2 pt-2 text-white"
          icon="pi pi-plus"
          onClick={() => onClick("displayPopup")}>
          <span className="pl-3">Tạo phòng</span>
        </Button>
        <Dialog
          className="create-room"
          closable={null}
          visible={displayPopup}
          style={{ width: "600px" }}>
          <form onSubmit={formik.handleSubmit}>
            <div className="w-100 p-1">
              {layoutHeader}
              {layoutContent}
              {footerLayout}
            </div>
          </form>
        </Dialog>
      </div>
    </React.Fragment>
  );
};

export default HeaderRoom;
