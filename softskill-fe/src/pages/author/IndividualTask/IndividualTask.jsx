import React from 'react';
import IndividualTask from '../../../components/author/components/IndividualTask/IndividualTask';
import DefaultLayout from '../../../components/author/layout/DefaultLayout';

function IndividualTaskManager() {
    return (
        <DefaultLayout to="/author-manage/individual-task" text="Quản lý nhiệm vụ cá nhân">
            <IndividualTask />
        </DefaultLayout>
    );
}

export default IndividualTaskManager;
