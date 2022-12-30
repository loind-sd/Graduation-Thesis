import RequestService from '../../utils/request-service';
import { sendInviteFailure, sendInviteSuccess, sendThankFailure, sendThankSuccess } from '../actions/mail-action';

export const sendInviteMail = (body) => async (disatch) => {
    try {
        const response = await RequestService.post('/mail/invite', body, true);
        sendInviteSuccess(response.data.item);
    } catch (error) {
        sendInviteFailure(error.response);
    }
};

export const sendThankMail = (body) => async () => {
    try {
        const response = await RequestService.post('/mail/thank', body, true);
        sendThankSuccess(response.data.item);
    } catch (error) {
        sendThankFailure(error.response);
    }
};
