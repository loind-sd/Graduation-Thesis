import React, { useEffect, useRef, useState } from "react";
import "./Header.scss";
import { useDispatch, useSelector } from "react-redux";
import HeaderInfo from "./Header-info/HeaderInfo";
import HeaderRoom from "./Header-room/HeaderRoom";
import HeaderAction from "./Header-action/HeaderAction";
import { Button, Toast } from "primereact";

import { checkEmpty } from "../../utils/input-validators";
import { getUserInfo } from "../../redux/thunks/auth-thunks";
import {
  add,
  removeFromToastList,
} from "../../redux/reducers/deliveredNotifs";
import HeaderTaskProposed from "./Header-task-proposed/HeaderTaskProposed";
import { socket } from "./../../pages/App/App";
import { Link } from "react-router-dom";
import { environment } from "../../utils/constant/environment";

const Header = ({ action }) => {
  const user = useSelector((state) => state.authData.user);
  const dispatch = useDispatch();
  const [checkAction, setAction] = useState("");

  useEffect(() => {
    if (user && user.userDetails !== undefined) {
      socket.emit("add-user", user.userDetails.id);
    }
  }, [user]);

  useEffect(() => {
    if (action === "home" || action === "") {
      setAction(
        "Xin chào " +
        (user?.userDetails?.name === undefined ? "" : user?.userDetails?.name)
      );
    } else if (action === "tasksProposed") {
      setAction("Nhiệm vụ đề xuất");
    } else if (action === "tasksForGroup") {
      setAction("Nhiệm vụ nhóm");
    } else if (action === "tasksForPersonal") {
      setAction("Nhiệm vụ cá nhân");
    } else if (action === "tasksForYou") {
      setAction("Dành cho bạn");
    }
  }, [action, user?.userDetails?.name]);

  useEffect(() => {
    if (localStorage.getItem("token")) {
      dispatch(getUserInfo());
    }
  }, [dispatch]);

  const notifToastList = useSelector(
    (state) => state.deliveredNotificationData.value.notifToastList
  );

  useEffect(() => {
    if (user?.userDetails?.id !== undefined) {
      let url = environment.API_BASE_URL + "/notification/show/" + user?.userDetails?.id;
      const sse = new EventSource(url);

      sse.addEventListener("user-list-event", (event) => {
        const data = JSON.parse(event.data);
        dispatch(add({ newNotifs: data }));
      });
      sse.onerror = () => {
        sse.close();
      }
      return () => {
        sse.close();
      };
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [user?.userDetails?.id]);
  const toast = useRef(null);
  return (
    <React.Fragment>
      <Toast ref={toast} />
      <header className="header separate-bottom">
        <div className="col-sm-9 d-flex justify-content-between p-0">
          {(action === "home" || action === "") &&
            user?.userDetails !== undefined ? (
            <React.Fragment>
              <HeaderInfo title={checkAction} />
              <HeaderRoom />
            </React.Fragment>
          ) : action === "tasksProposed" ? (
            <HeaderTaskProposed />
          ) : null}
        </div>
        {!checkEmpty(user) ? (
          <React.Fragment>
            <HeaderAction />
          </React.Fragment>
        ) : (
          <React.Fragment>
            {!window.location.href.endsWith("/login") && (
              <Link to="/login" className="btnLogin">
                <Button
                  style={{
                    backgroundColor: " #71A3E0",
                    borderColor: " #71A3E0",
                    fontSize: "14px",
                    borderRadius: "8px",
                    marginRight: "15px",
                  }}
                  className="w-100 pl-5 pr-5 pb-2 pt-2 text-white"
                  icon="pi pi-sign-in">
                  <span className="pl-3">Đăng nhập</span>
                </Button>
              </Link>
            )}
          </React.Fragment>
        )}
      </header>
      {!checkEmpty(user) ? (
        <div
          className="position-fixed"
          style={{ top: "65px", right: "10px", zIndex: "100000" }}>
          {notifToastList.map((item, index) => {
            toast.current.show({
              closable: true,
              severity: "info",
              summary: "Thông báo",
              detail: item.content,
              life: 3000,
            });
            setTimeout(function () {
              dispatch(removeFromToastList({ notif: item }));
            }, 500);
          })}
        </div>
      ) : (
        ""
      )}
    </React.Fragment>
  );
};

export default Header;
