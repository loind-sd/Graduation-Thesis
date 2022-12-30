import React from "react";
import { ADMIN_ROLE, AUTHOR_ROLE, USER_ROLE } from "../../utils/constant/CommonConstant";
import { Route, Redirect } from "react-router-dom";

const PrivateRoute = ({ component: Component, path, ...rest }) => {
  let authData = localStorage.getItem("static");
  let authenticatedUser = authData === USER_ROLE && localStorage.getItem("token");
  let authenticatedAuthor = authData === AUTHOR_ROLE && localStorage.getItem("token");
  let authenticatedAdmin = authData === ADMIN_ROLE && localStorage.getItem("token");

  let authenticated = false;
  if (path.startsWith("/admin-manage/") && authenticatedAdmin) {
    authenticated = true;
  } else if (path.startsWith("/author-manage/") && authenticatedAuthor) {
    authenticated = true;
  } else if (authenticatedUser && !path.startsWith("/admin-manage/") && !path.startsWith("/author-manage/")) {
    authenticated = true;
  }
  return (
    <Route
      {...rest}
      render={(props) => (authenticated ? <Component {...rest} {...props} /> : <Redirect to={"/login"} />)}
    />
  );
};

export default PrivateRoute;
