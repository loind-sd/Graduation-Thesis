// import React, { useEffect, useState } from "react";
// import "./HeaderAction.scss";
// import UilTimes from "@iconscout/react-unicons/icons/uil-times";
// import UilCheck from "@iconscout/react-unicons/icons/uil-check";
// import UilPen from "@iconscout/react-unicons/icons/uil-pen";
// import UilBell from "@iconscout/react-unicons/icons/uil-bell";
// import { Password } from "primereact/password";
// import { InputText } from "primereact/inputtext";
// import {
//   changeName,
//   changeNickName,
//   changePassword,
//   forgotPassword,
// } from "../../../redux/thunks/user-thunks";
// import { useDispatch, useSelector } from "react-redux";
// import { useHistory } from "react-router-dom";
// import { checkPasswords } from "../../../utils/input-validators";
// import {
//   validatePassword,
// } from "./../../../utils/input-validators";
// import {
//   deleteNotification,
//   getListNotification,
// } from "../../../redux/thunks/notification-thunks";
// import { getTimeWithString } from "../../../utils/common-service";
// import { clear } from "../../../redux/reducers/deliveredNotifs";

// const objChangePassword = {
//   oldPassword: "",
//   newPassword: "",
// };

// const HeaderAction = () => {
//   let notifications = [];
//   const dispatch = useDispatch();
//   const history = useHistory();
//   // const success = useSelector((state) => state.userData.success);
//   const userAuth = useSelector((state) => state.authData.user);
//   const getNotifications = useSelector(
//     (state) => state.notificationData.notifications
//   );
//   const [page, setPage] = useState(1);
//   const allDeliveredNotifs = useSelector(
//     (state) => state.deliveredNotificationData.value.notifs
//   );

//   useEffect(() => {
//     window.addEventListener("scroll", handleScroll);

//     return () => {
//       window.removeEventListener("scroll", handleScroll);
//     };
//     // eslint-disable-next-line react-hooks/exhaustive-deps
//   }, []);

//   const [action, setAction] = useState({
//     changeName: "notChange",
//     changeNickName: "notChange",
//     changePassWord: "notChange",
//   });

//   const [nickName, setNickName] = useState("");
//   const [name, setName] = useState("");
//   const [password, setPassword] = useState({
//     oldPassword: "",
//     newPassword: "",
//     reNewPassword: "",
//   });

//   const [open, setOpen] = useState({
//     openNotify: false,
//     openUser: false,
//   });

//   const openNotify = () => {
//     open.openUser = false;
//     setOpen({ ...open, openNotify: !open.openNotify });
//     dispatch(getListNotification(page));

//     dispatch(clear());
//   };

//   const openUser = () => {
//     open.openNotify = false;
//     setOpen({ ...open, openUser: !open.openUser });
//   };

//   //  scroll get data notify
//   const handleScroll = (event) => {
//     let scrollTop = event.currentTarget.scrollTop;
//     let calculator = Math.floor(scrollTop / 1000) + 1;
//     if (calculator > page) {
//       dispatch(getListNotification(calculator));
//       setPage(calculator);
//     }
//   };

//   // action edit
//   const handleChangeName = () => {
//     dispatch(changeName({ name: name }));
//     clearFormName();
//   };

//   const handleChangeNickName = () => {
//     dispatch(changeNickName({ nickName: nickName }));
//     clearFormNickName();
//   };

//   const handleChangePassWord = () => {
//     let checkLengthNewPassword = validatePassword(password.newPassword);
//     if (checkLengthNewPassword.length > 0) {
//       // const messError = checkLengthNewPassword;
//       return;
//     }
//     let equalPassword = checkPasswords(
//       password.newPassword,
//       password.reNewPassword
//     );
//     if (!equalPassword) {
//       // const messError = "Mật khẩu nhập không trùng!";
//       return;
//     }
//     objChangePassword.oldPassword = password.oldPassword;
//     objChangePassword.newPassword = password.newPassword;
//     dispatch(changePassword(objChangePassword));
//     clearFormPassword();
//   };

//   const handleDeleteNotification = (data) => {
//     dispatch(deleteNotification(data));
//     dispatch(getListNotification(page));
//   };

//   const onClickForgot = () => {
//     dispatch(forgotPassword({ email: localStorage.getItem("email") }, history));
//   };

//   const cancelHandleChangeName = () => {
//     clearFormName();
//   };

//   const cancelHandleChangeNickName = () => {
//     clearFormNickName();
//   };

//   const cancelHandleChangePassWord = () => {
//     clearFormPassword();
//   };

//   const clearFormPassword = () => {
//     setAction({
//       ...action,
//       changePassWord: "notChange",
//       changeName: "notChange",
//       changeNickName: "notChange",
//     });
//     password.newPassword = "";
//     password.oldPassword = "";
//     password.reNewPassword = "";
//   };

//   const clearFormNickName = () => {
//     setAction({
//       ...action,
//       changePassWord: "notChange",
//       changeName: "notChange",
//       changeNickName: "notChange",
//     });
//     setNickName("");
//   };

//   const clearFormName = () => {
//     setAction({
//       ...action,
//       changePassWord: "notChange",
//       changeName: "notChange",
//       changeNickName: "notChange",
//     });
//     setName("");
//   };

//   notifications = getNotifications.item ? getNotifications.item : [];
//   // layout children
//   const layoutChangeName = (
//     <li className="user-item">
//       <div className="link d-flex justify-content-between">
//         <div
//           className="custom-btn ml-1 bg-dark"
//           onClick={() => cancelHandleChangeName()}>
//           <UilTimes />
//         </div>

//         <div className="custom-btn mr-3" onClick={() => handleChangeName()}>
//           <UilCheck />
//         </div>
//       </div>

//       <div className="change-nickName">
//         <div className="form-group d-flex col-md-12 pl-3 pt-1 mb-4 custom-prime p-float-label mt-3">
//           <InputText
//             className="form-control p-4 rounded-pill"
//             value={name}
//             type="text"
//             required
//             minLength={5}
//             onChange={(e) => setName(e.target.value)}
//           />
//           <label htmlFor="inputtext" className="pl-3 small">
//             Nhập tên
//           </label>
//         </div>
//       </div>
//     </li>
//   );

//   const layoutChangeNickname = (
//     <li className="user-item">
//       <div className="link d-flex justify-content-between">
//         <div
//           className="custom-btn ml-1 bg-dark"
//           onClick={() => cancelHandleChangeNickName()}>
//           <UilTimes />
//         </div>

//         <div className="custom-btn mr-3" onClick={() => handleChangeNickName()}>
//           <UilCheck />
//         </div>
//       </div>

//       <div className="change-nickName">
//         <div className="form-group d-flex col-md-12 pl-3 pt-1 mb-4 custom-prime p-float-label mt-3">
//           <InputText
//             className="form-control p-4 rounded-pill"
//             value={nickName}
//             type="text"
//             required
//             minLength={5}
//             onChange={(e) => setNickName(e.target.value)}
//           />
//           <label htmlFor="inputtext" className="pl-3 small">
//             Nhập nickName
//           </label>
//         </div>
//       </div>
//     </li>
//   );

//   const layoutChangePassword = (
//     <li className="user-item">
//       <div className="link d-flex justify-content-between">
//         <div
//           className="custom-btn ml-1 bg-dark"
//           onClick={() => cancelHandleChangePassWord()}>
//           <UilTimes />
//         </div>
//         <div className="custom-btn mr-3" onClick={() => handleChangePassWord()}>
//           <UilCheck />
//         </div>
//       </div>
//       <div className="change-password">
//         <div className="form-group d-flex col-md-12 pl-3 pt-1 custom-prime p-float-label">
//           <Password
//             className="form-control pb-5 rounded-pill"
//             required
//             value={password.oldPassword}
//             minLength={1}
//             onChange={(e) =>
//               setPassword({
//                 ...password,
//                 oldPassword: e.target.value,
//               })
//             }
//             toggleMask
//             feedback={false}
//           />
//           <label
//             htmlFor="inputtext"
//             className="small"
//             style={{ padding: "7px 25px" }}>
//             Nhập mật khẩu
//           </label>
//         </div>
//         <div className="form-group d-flex align-items-center col-md-12 pl-3 pt-1 custom-prime p-float-label mt-4">
//           <Password
//             className="form-control pb-5 rounded-pill"
//             required
//             value={password.newPassword}
//             minLength={6}
//             onChange={(e) =>
//               setPassword({
//                 ...password,
//                 newPassword: e.target.value,
//               })
//             }
//             toggleMask
//             feedback={false}
//           />
//           <label
//             htmlFor="inputtext"
//             className="small"
//             style={{ padding: "7px 25px" }}>
//             Nhập mật khẩu mới
//           </label>
//         </div>
//         <div className="form-group d-flex align-items-center col-md-12 pl-3 pt-1 custom-prime p-float-label mt-4 mb-5">
//           <Password
//             className="form-control pb-5 rounded-pill"
//             required
//             value={password.reNewPassword}
//             minLength={6}
//             onChange={(e) =>
//               setPassword({
//                 ...password,
//                 reNewPassword: e.target.value,
//               })
//             }
//             toggleMask
//             feedback={false}
//           />
//           <label
//             htmlFor="inputtext"
//             className="small"
//             style={{ padding: "7px 25px" }}>
//             Xác nhận mật khẩu mới
//           </label>
//         </div>
//         <div className="form-group d-flex align-items-center col-md-12 pl-3 pt-1 mt-4 mb-5">
//           <span className="pl-3">
//             Quên mật khẩu?{" "}
//             <span
//               style={{
//                 color: "blue",
//                 textDecoration: "underline",
//                 cursor: "pointer",
//               }}
//               onClick={() => onClickForgot}>
//               Lấy lại ngay!
//             </span>
//           </span>
//         </div>
//       </div>
//     </li>
//   );

//   const layoutAction = (
//     <React.Fragment>
//       <li className="user-item">
//         <div className="link">
//           Tên: {userAuth?.userDetails.name}
//           <span className="wrap-action">
//             <span
//               className="border-left pl-3 pb-2"
//               style={{ color: "#407fbe", cursor: "pointer" }}
//               onClick={() =>
//                 setAction({
//                   ...action,
//                   changeName: "change",
//                 })
//               }>
//               <UilPen size={20} />
//             </span>
//           </span>
//         </div>
//       </li>
//       <li className="user-item">
//         <div className="link">
//           NickName: {userAuth?.userDetails.nickName}
//           <span className="wrap-action">
//             <span
//               className="border-left pl-3 pb-2"
//               style={{ color: "#407fbe", cursor: "pointer" }}
//               onClick={() =>
//                 setAction({
//                   ...action,
//                   changeNickName: "change",
//                 })
//               }>
//               <UilPen size={20} />
//             </span>
//           </span>
//         </div>
//       </li>

//       <li className="user-item">
//         <div className="link">
//           Mật Khẩu:
//           <span className="mt-2 ml-0">**************</span>
//           <span className="wrap-action">
//             <span
//               className="border-left pl-3 pb-2"
//               style={{ color: "#407fbe", cursor: "pointer" }}
//               onClick={() =>
//                 setAction({
//                   ...action,
//                   changePassWord: "change",
//                 })
//               }>
//               <UilPen size={20} />
//             </span>
//           </span>
//         </div>
//       </li>
//     </React.Fragment>
//   );
//   const layoutNotification = (
//     <React.Fragment>
//       {notifications.map((data, index) => (
//         <li className="content-item" key={index}>
//           <div className="item-info">
//             <div className="item-image">
//               <img
//                 src={require("../../../assets/image/user.png")}
//                 alt="Hình ảnh"
//               />
//             </div>
//             <div className="item-info-detail">
//               <span className="item-description">{data.content}</span>
//               <span className="item-time">
//                 {getTimeWithString(
//                   new Date().getTime() - new Date(data.timeCreated)
//                 )}
//               </span>
//             </div>
//           </div>
//           <div className="item-invite d-flex">
//             <button className="btn btn-primary mr-2 btn-custom">
//               Xác nhận
//             </button>
//             <button
//               className="btn btn-secondary btn-custom"
//               onClick={() => handleDeleteNotification(data.id)}>
//               Xoá
//             </button>
//           </div>
//         </li>
//       ))}
//     </React.Fragment>
//   );

//   return (
//     <React.Fragment>
//       {/* {success.status === CommonConstant.HTTP_200 ? (
//         <ToastShow detail={CommonConstant.ACTION_SUCCESS} />
//       ) : (
//         ""
//       )}
//       {checkString(success.status) &&
//         success.status !== CommonConstant.HTTP_200 ? (
//         <ToastShow
//           detail={CommonConstant.ACTION_FAILURE}
//           severity={CommonConstant.SEVERITY_ERROR}
//         />
//       ) : (
//         ""
//       )} */}
//       <div className="header__action">
//         <div className="notify">
//           <span className="logo rounded-circle" onClick={() => openNotify()}>
//             <UilBell />
//             <span className="badge rounded-circle">
//               {allDeliveredNotifs.length}
//             </span>
//           </span>
//           <div
//             style={
//               open.openNotify === false
//                 ? { display: "none" }
//                 : { display: "block" }
//             }
//             className="notify__content"
//             onScroll={handleScroll}>
//             <header className="content-header separate-bottom">
//               Thông báo
//             </header>
//             <ul className="content-list-option h-50">{layoutNotification}</ul>
//           </div>
//         </div>
//         <div className="user d-flex align-items-center">
//           <div className="logo item-image" onClick={() => openUser()}>
//             <img
//               src={require("../../../assets/image/user.png")}
//               alt="Ảnh đại diện"
//             />
//           </div>
//           <ul
//             style={
//               open.openUser === false
//                 ? { display: "none" }
//                 : { display: "block" }
//             }
//             className="user__option-list">
//             {action.changePassWord === "change"
//               ? layoutChangePassword
//               : action.changeNickName === "change"
//                 ? layoutChangeNickname
//                 : action.changeName === "change"
//                   ? layoutChangeName
//                   : layoutAction}
//           </ul>
//         </div>
//       </div>
//     </React.Fragment>
//   );
// };

// export default HeaderAction;
