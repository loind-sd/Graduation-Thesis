import React from 'react';
import GroupTask from '../../../components/author/components/GroupTask/GroupTask';
import DefaultLayout from '../../../components/author/layout/DefaultLayout';

function GroupTaskManager() {
    return (
        <DefaultLayout to="/author-manage/group-task" text="Quản lý nhiệm vụ nhóm">
            <GroupTask />
        </DefaultLayout>
    );
}

export default GroupTaskManager;
