import React from 'react';
import FeedbackManager from '../../../components/admin/components/FeedbackManager/FeedbackManager';
import DefaultLayout from '../../../components/admin/layout/DefaultLayout';

function FeedbackManagement() {
    return (
        <DefaultLayout to="/admin-manage/feedback" text="Quản lý phản hồi">
            <FeedbackManager />
        </DefaultLayout>
    );
}

export default FeedbackManagement;