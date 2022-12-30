import { FORM_RESET, SHOW_LOADER } from "../action-types/_common-action-types";

export const showLoader = () => ({
    type: SHOW_LOADER,
});

export const reset = () => ({
    type: FORM_RESET,
});
