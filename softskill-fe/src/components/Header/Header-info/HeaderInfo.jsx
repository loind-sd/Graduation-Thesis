import React from "react";
import "./HeaderInfo.scss";

const HeaderInfo = ({
  title
}) => {

  return (
    <React.Fragment>
      <div className="header__info">
        <span>{title}</span>
      </div>
    </React.Fragment>
  );
};

export default HeaderInfo;
