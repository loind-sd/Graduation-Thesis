import React from 'react';
import DefaultLayout from './../../../components/admin/layout/DefaultLayout';
import AccManager from './../../../components/admin/components/AccManager/AccManager';

function AccountManager() {
    return (
        <DefaultLayout to="/admin-manage/acc" text="Quản lý tài khoản">
            <AccManager />
        </DefaultLayout>
    );
}

export default AccountManager;
