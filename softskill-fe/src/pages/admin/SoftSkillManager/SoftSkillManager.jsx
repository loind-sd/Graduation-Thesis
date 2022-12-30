import React from 'react';
import DefaultLayout from '../../../components/admin/layout/DefaultLayout';
import SSManager from '../../../components/admin/components/SSManager/SSManager';

function SoftSkillManager() {
    return (
        <DefaultLayout to="/admin-manage/softskills" text="Quản lý kỹ năng">
            <SSManager />
        </DefaultLayout>
    );
}

export default SoftSkillManager;
