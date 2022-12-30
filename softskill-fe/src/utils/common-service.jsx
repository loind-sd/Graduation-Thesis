export const getTimeWithString = (params) => {
    let minutes = Math.floor(params / 60 / 1000);
    let hours = Math.floor(minutes / 60);
    let days = Math.floor(hours / 24);

    if (days < 1) {
        if (hours < 1) {
            return minutes + " phút trước";
        } else {
            return hours + " tiếng trước";
        }
    } else {
        return days + " ngày trước";
    }
};

export const getTimeWithStringAndMsg = (params, msg) => {
    let minutes = Math.floor(params / 60 / 1000);
    let hours = Math.floor(minutes / 60);
    let minutesRes = Math.floor(minutes % hours);
    let days = Math.floor(hours / 24);
    let hoursRes = Math.floor(hours % 24);

    if (days < 1) {
        if (hours < 1) {
            return minutes + " phút " + msg;
        } else {
            return hours + ":" + minutesRes + " phút " + msg;
        }
    } else {
        return days + " ngày " + hoursRes + ":" + minutesRes + " phút " + msg;
    }
};

export const convertDateToYYYYMMDD = (date) => {
    let mm = date.getMonth() + 1;
    let dd = date.getDate();

    return [
        date.getFullYear(),
        (mm > 9 ? "" : "0") + mm,
        (dd > 9 ? "" : "0") + dd,
    ].join("/");
};

export const convertDateToYYYYMMDD2 = (date) => {
    let MM = date.getMonth() + 1;
    let dd = date.getDate();
    let hh = date.getHours();
    let mm = date.getMinutes();
    let ss = date.getSeconds();

    return (
        [
            date.getFullYear(),
            (MM > 9 ? "" : "0") + MM,
            (dd > 9 ? "" : "0") + dd,
        ].join("-") +
        " " +
        [
            (hh > 9 ? "" : "0") + hh,
            (mm > 9 ? "" : "0") + mm,
            (ss > 9 ? "" : "0") + ss,
        ].join(":")
    );
};
