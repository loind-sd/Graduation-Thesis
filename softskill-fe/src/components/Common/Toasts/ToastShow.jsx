import "./ToastShow.scss";
import React, { useState } from "react";
import Toast from "react-bootstrap/Toast";
import UilBell from "@iconscout/react-unicons/icons/uil-bell";
import UilTimes from "@iconscout/react-unicons/icons/uil-times";
import * as CommonConstant from "../../../utils/constant/CommonConstant";

const ToastShow = ({
    severity = CommonConstant.SEVERITY_SUCCESS,
    summary = "Thành công",
    detail = "Tác vụ thành công",
    action = () => { },
    life = 3000,
    keyToast = 1,
}) => {
    //   const { notification, action } = props;
    const [show, setShow] = useState(true);
    const toggleShow = () => setShow(!show);
    const none = () => { };

    return (
        <Toast
            style={{ fontSize: "1.4rem", zIndex: "1000000", backgroundColor: "#e4f8f0" }}
            show={show}
            position="top-end"
            key={keyToast}
            autohide={true}
            delay={life}
            onClose={action ? action : none()}>
            <div className="d-flex align-items-center justify-content-between separate-bottom">
                <span className="ml-2">
                    <UilBell size="20px" />
                    <strong className="me-auto">{summary}</strong>
                </span>
                <span className="float-right">
                    <small style={{ fontSize: "12px" }}>gần đây</small>
                    <span className="ml-1 custom-close" onClick={() => toggleShow()}>
                        {" "}
                        <UilTimes size="25px" />
                    </span>
                </span>
            </div>
            <Toast.Body>{detail}</Toast.Body>
        </Toast>
    );
};

export default ToastShow;