export const validatePassword = (password) => {
    if (password.length < 6) {
        return "Password needs to be at least 6 characters long";
    }
    return "";
};

export const checkPasswords = (password_1, password_2) => {
    if (password_1 !== password_2) {
        return false;
    }
    return true;
};

export const validateEmail = (email) => {
    if (!email || !email.match(/^(\D)+(\w)*((\.(\w)+)?)+@(\D)+(\w)*((\.(\D)+(\w)*)+)?(\.)[a-z]{2,}$/)) {
        return "Not a valid Email";
    }
    return "";
};

export const checkEmpty = (params) => {
    if (params && Object.keys(params).length === 0
        && Object.getPrototypeOf(params) === Object.prototype) {
        return true;
    }
}

export const checkString = (params) => {
    return params ? true : false;
}