import React from 'react';
import DefaultLayout from './../../../components/admin/layout/DefaultLayout';
import Dashboard from './../../../components/admin/components/Dashboard/Dashboard';

function DashboardManager() {
    return (
        <DefaultLayout to="/admin-manage/dashboard" text="Thống kê">
            <Dashboard />
        </DefaultLayout>
    );
}

export default DashboardManager;
