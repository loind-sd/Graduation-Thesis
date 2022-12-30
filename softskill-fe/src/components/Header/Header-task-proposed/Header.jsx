// import React, { useEffect } from "react";
// import "./Header.scss";
// import { useDispatch, useSelector } from "react-redux";
// import HeaderInfo from "./Header-info/HeaderInfo";
// import HeaderRoom from "./Header-room/HeaderRoom";
// import HeaderAction from "./Header-action/HeaderAction";

// import { checkEmpty } from "../../utils/input-validators";
// import { getUserInfo } from "../../redux/thunks/auth-thunks";
// import {
//   // add,
//   removeFromToastList,
// } from "../../redux/reducers/deliveredNotifs";
// import ToastShow from "../Common/Toasts/ToastShow";
// // import { environment } from "../../utils/constant/environment";

// const Header = () => {
//   const user = useSelector((state) => state.authData.user);
//   const dispatch = useDispatch();

//   useEffect(() => {
//     if (localStorage.getItem("token")) {
//       dispatch(getUserInfo());
//     }
//   }, [dispatch]);

//   const notifToastList = useSelector(
//     (state) => state.deliveredNotificationData.value.notifToastList
//   );

//   // useEffect(() => {
//   //   let url = environment.API_BASE_URL + "/notification/show";
//   //   const sse = new EventSource(url);

//   //   sse.addEventListener("user-list-event", (event) => {
//   //     const data = JSON.parse(event.data);
//   //     dispatch(add({ newNotifs: data }));
//   //   });

//   //   sse.onerror = () => {
//   //     sse.close();
//   //   };
//   //   return () => {
//   //     sse.close();
//   //   };
//   //   // eslint-disable-next-line react-hooks/exhaustive-deps
//   // }, []);

//   return (
//     <React.Fragment>
//       <header className="header separate-bottom">
//         <HeaderInfo />
//         {!checkEmpty(user) ? (
//           <React.Fragment>
//             <HeaderRoom />
//             <HeaderAction />
//           </React.Fragment>
//         ) : (
//           ""
//         )}
//       </header>
//       {!checkEmpty(user) ? (
//         <div
//           className="position-fixed"
//           style={{ top: "65px", right: "10px", zIndex: "100000" }}>
//           {notifToastList.map((item) => (
//             <ToastShow
//               detail={item.content}
//               key={item.id}
//               action={() => {
//                 dispatch(removeFromToastList({ notif: item }));
//               }}
//             />
//           ))}
//         </div>
//       ) : (
//         ""
//       )}
//     </React.Fragment>
//   );
// };

// export default Header;
