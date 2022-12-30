import React from 'react';
import DefaultLayout from '../../../components/admin/layout/DefaultLayout';
import RoomManager from './../../../components/admin/components/RoomManager/RoomManager';
function RoomManagement() {
    return (
        <DefaultLayout to="/admin-manage/room" text="Quản lý phòng">
            <RoomManager />
        </DefaultLayout>
    );
}

export default RoomManagement;