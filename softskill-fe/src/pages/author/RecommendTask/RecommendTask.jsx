import React from 'react';
import RecommendTask from '../../../components/author/components/RecommendTask/RecommendTask';
import DefaultLayout from '../../../components/author/layout/DefaultLayout';

function RecommendTaskManager() {
    return (
        <DefaultLayout to="/author-manage/recommend-task" text="Quản lý nhiệm vụ đề xuất">
            <RecommendTask />
        </DefaultLayout>
    );
}

export default RecommendTaskManager;
