import React, { useState, useEffect, useRef } from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { SelectButton } from 'primereact/selectbutton';
import { Toast } from 'primereact/toast';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { Checkbox } from 'primereact/checkbox';
import { InputTextarea } from 'primereact/inputtextarea';
import { ConfirmPopup, confirmPopup } from 'primereact/confirmpopup';
import './RoomManager.scss';
import { useDispatch, useSelector } from 'react-redux';
import { deleteRoom, getRooms } from '../../../../redux/thunks/admin/room-thunk';
import { event } from 'jquery';
function RoomManager() {
    const dispatch = useDispatch();

    const [txtSearch, setTxtSearch] = useState('');
    const [roomData, setRoomData] = useState([]);
    const [initRoomDataFilter, setInitRoomDataFilter] = useState([]);

    const [headerDetail, setHeaderDetail] = useState('');
    const [isShowDetails, setIsShowDetails] = useState(false);
    const [isDeleteRoom, setDeleteRoom] = useState(false);
    const [room, setRoom] = useState(null);
    const [roomDelete, setRoomDelete] = useState(null);
    const [deleteRoomMaster, setDeleteRoomMaster] = useState(false);
    const [visible, setVisible] = useState(false);
    const toast = useRef(null);

    const rooms = useSelector((state) => state.adminRoomData.rooms);

    useEffect(() => {
        dispatch(getRooms());
    }, [dispatch]);

    useEffect(() => {
        setDataInit(rooms);
    }, [rooms]);

    const setDataInit = (data) => {
        if (data?.item !== undefined && data?.item !== null) {
            let dataMapping = data.item.rooms;
            setInitRoomDataFilter(dataMapping);
            setRoomData(dataMapping);
            setTxtSearch('');
        } else {
            setRoomData([]);
        }
    };

    const onTxtSearchChange = (value) => {
        if (value === '') {
            setRoomData(initRoomDataFilter);
        } else {
            setRoomData(
                initRoomDataFilter.filter(
                    (e) =>
                        e.roomName.toLowerCase().includes(value.toLowerCase()) ||
                        e.softSkill.toLowerCase().includes(value.toLowerCase()) ||
                        e.taskName.toLowerCase().includes(value.toLowerCase()),
                ),
            );
        }

        // } else if (isBookingRoom) {
        //     setRoomData(initRoomData.filter((e) =>
        //         (e.roomName.toLowerCase().includes(value.toLowerCase())
        //             || e.softSkill.toLowerCase().includes(value.toLowerCase())
        //             || e.taskName.toLowerCase().includes(value.toLowerCase()))
        //         && e.startTime !== null
        //     ));
        // } else if ((!isActiveRoom && !isBookingRoom) || selectRoomType === null) {
        //     setRoomData(initRoomData.filter((e) =>
        //         e.roomName.toLowerCase().includes(value.toLowerCase())
        //         || e.softSkill.toLowerCase().includes(value.toLowerCase())
        //         || e.taskName.toLowerCase().includes(value.toLowerCase())
        //     ));
        // }

        // setInitRoomDataFilter(roomData);
        setTxtSearch(value);
    };
    // console.log(isActiveRoom)
    // console.log(isBookingRoom)

    // useEffect(() => {
    //         if (isActiveRoom) {
    //             setRoomData(roomData.filter((e) =>
    //                 e.startTime === null))
    //             setIsActiveRoom(true);
    //             setIsBookingRoom(false);
    //         } else {
    //             setRoomData(roomData.filter((e) =>
    //                 e.startTime !== null
    //             ))
    //             setIsActiveRoom(false);
    //             setIsBookingRoom(true);
    //         }
    //     } else {
    //         setIsActiveRoom(false);
    //         setIsBookingRoom(false);
    //         setTxtSearch('');
    //     }
    //     // setRoomData(initRoomDataFilter)
    // }, [selectRoomType]);

    // const onRoomTypeSelected = (e) => {
    //     let value = e.value;
    //     console.log(value)

    //     if (value === null) {
    //         setRoomData(initRoomData);
    //         setSelectRoomType(null);
    //         setTxtSearch('');
    //         setInitRoomDataFilter(initRoomData);
    //     } else {
    //         setSelectRoomType(value);
    //         if (value === 'Phòng trực tiếp') {
    //             setInitRoomDataFilter(roomData.filter((e) =>
    //                 e.startTime !== null))
    //             setIsActiveRoom(true);
    //             setIsBookingRoom(false);
    //         } else if (value === 'Phòng đặt lịch') {
    //             setInitRoomDataFilter(roomData.filter((e) =>
    //                 e.startTime === null
    //             ))
    //             setIsActiveRoom(false);
    //             setIsBookingRoom(true);
    //         }
    //         setRoomData(initRoomDataFilter);
    //     }
    // };

    const header = (
        <div className="table-header">
            <div style={{ width: '80%' }} className="flex justify-content-end">
                <span style={{ width: 'inherit' }} className="p-input-icon-left">
                    <i className="pi pi-search" />
                    <InputText
                        value={txtSearch}
                        style={{ width: 'inherit' }}
                        onChange={(e) => onTxtSearchChange(e.target.value)}
                        placeholder="Tên phòng, kỹ năng, nhiệm vụ"
                    />
                </span>
            </div>
        </div>
    );

    const onHide = () => {
        setIsShowDetails(false);
        setDeleteRoom(false);
    };

    const roomType = (rowData) => {
        return (
            <span
                className={`product-badge status-${rowData.startTime} ${
                    rowData.startTime === null ? 'text-success' : 'text-danger'
                }`}
            >
                {rowData.startTime === null ? 'Phòng hoạt động' : 'Phòng đặt lịch'}
            </span>
        );
    };

    const roomAction = (rowData) => {
        return (
            <React.Fragment>
                <Button
                    icon="pi pi-eye"
                    className="p-button-rounded p-button-primary mr-2"
                    data-toggle="tooltip"
                    data-placement="top"
                    title="Xem chi tiết"
                    onClick={() => handleRoomDetail(rowData)}
                />

                <Button
                    id={`delete-room-${rowData.roomId}`}
                    icon="pi pi-trash"
                    className="p-button-rounded p-button-danger"
                    data-toggle="tooltip"
                    data-placement="top"
                    title="Xoá phòng"
                    onClick={() => {
                        // handleConfirmDelete(event);
                        beforeDeleteRoom(rowData);
                    }}
                />

                {/* <ConfirmPopup
                    // target={document.getElementById('delete-room-' + rowData.roomId)}
                    visible={visible}
                    onHide={() => setVisible(false)}
                    message="Bạn có muốn xoá chủ phòng?"
                    icon="pi pi-exclamation-triangle" accept={accept} reject={reject} /> */}
            </React.Fragment>
        );
    };
    const accept = () => {
        let roomDeleteRequest = {
            roomId: roomDelete.roomId,
            deleteRoomMaster: deleteRoomMaster,
        };
        dispatch(deleteRoom(roomDeleteRequest));
        // toast.current.show({ severity: 'success', summary: 'Thành công', detail: 'Xoá phòng và chủ phòng thành công', life: 3000 });
    };

    const deleteRoomPopup = (
        <form>
            <React.Fragment>
                {/* <Button label="Hủy" icon="pi pi-times" className="p-button-text" onClick={onHide} /> */}
                <Button label="Xác nhận" icon="pi pi-check" className="p-button-text" onClick={accept} />
            </React.Fragment>
        </form>
    );

    const handleRoomDetail = (data) => {
        console.log(data);
        setIsShowDetails(true);
        setRoom(data);
        data.startTime === null
            ? setHeaderDetail('Chi tiết phòng trực tiếp')
            : setHeaderDetail('Chi tiết phòng đặt lịch');
    };

    // const reject = () => {
    //     let roomDeleteRequest = {
    //         roomId: roomDelete.roomId,
    //         deleteRoomMaster: false
    //     }
    //     dispatch(deleteRoom(roomDeleteRequest));
    //     // toast.current.show({ severity: 'warn', summary: 'Rejected', detail: 'Xoá phòng thành công', life: 3000 });
    // };

    const beforeDeleteRoom = (data) => {
        setRoomDelete(data);
        setDeleteRoom(true);
    };

    const onDeleteRoomMaster = (check) => {
        setDeleteRoomMaster(check);
    };

    return (
        <div className="softskill-manager">
            <div className="datatable-crud-demo">
                {/* <Toast ref={toast} /> */}

                <div className="softskill-card">
                    <DataTable
                        // ref={dt}
                        value={roomData}
                        dataKey="roomId"
                        paginator
                        rows={8}
                        // paginatorRight={paginatorRight}
                        paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                        currentPageReportTemplate="Tổng số: {totalRecords} bản ghi"
                        rowsPerPageOptions={[10, 20, 50]}
                        header={header}
                        // filters={filters}
                        responsiveLayout="scroll"
                        emptyMessage="Chưa có dữ liệu"
                        style={{ fontSize: '12px' }}
                    >
                        {/* <Column selectionMode="multiple" headerStyle={{ width: '3rem' }} exportable={false}></Column> */}
                        <Column field="roomId" header="ID" hidden></Column>
                        <Column field="numRow" header="STT" sortable style={{ maxWidth: '4rem' }}></Column>
                        <Column field="roomName" header="Tên phòng" sortable style={{ minWidth: '12rem' }}></Column>
                        <Column
                            field="softSkill"
                            header="Kỹ năng đang hoạt động"
                            sortable
                            style={{ minWidth: '6rem' }}
                        ></Column>
                        <Column
                            field="taskName"
                            header="Nhiệm vụ gần nhất"
                            sortable
                            style={{ minWidth: '6rem' }}
                        ></Column>
                        <Column
                            field="startTime"
                            header="Loại phòng"
                            body={roomType}
                            sortable
                            style={{ minWidth: '8rem' }}
                        ></Column>
                        <Column
                            header="Thao tác"
                            body={roomAction}
                            exportable={false}
                            style={{ minWidth: '8rem' }}
                        ></Column>
                    </DataTable>
                </div>

                {room !== null ? (
                    <>
                        <div className="form-detail">
                            <Dialog
                                visible={isShowDetails}
                                style={{ width: '400px' }}
                                header={headerDetail}
                                modal
                                className="p-fluid"
                                draggable={false}
                                // footer={softskillDialogFooter}
                                onHide={() => onHide()}
                            >
                                <div className="field-info d-flex flex-row justify-content-between">
                                    <div style={{ width: '80%', margin: 'auto 0' }}>
                                        <label style={{ fontSize: 'larger', margin: '0' }} className="font-weight-bold">
                                            Số người đang hoạt động
                                        </label>
                                    </div>

                                    <InputText
                                        id="userOnline"
                                        value={room.userOnline}
                                        style={{ width: '20%', textAlign: 'center' }}
                                        autoFocus
                                        readOnly
                                    />
                                </div>
                                <div className="field-info d-flex flex-row justify-content-between">
                                    <div style={{ width: '80%', margin: 'auto 0' }}>
                                        <label style={{ fontSize: 'larger', margin: '0' }} className="font-weight-bold">
                                            Số người tối đa
                                        </label>
                                    </div>

                                    <InputText
                                        id="roomSize"
                                        value={room.roomSize}
                                        style={{ width: '20%', textAlign: 'center' }}
                                        readOnly
                                    />
                                </div>

                                {room.startTime === null ? (
                                    <>
                                        <div className="field-info d-flex flex-row justify-content-between">
                                            <div style={{ width: '80%', margin: 'auto 0' }}>
                                                <label
                                                    style={{ fontSize: 'larger', margin: '0' }}
                                                    className="font-weight-bold"
                                                >
                                                    Phòng đã khóa
                                                </label>
                                            </div>
                                            <div style={{ margin: '0 auto' }}>
                                                <Checkbox inputId="binary" checked={room.isLock} readOnly />
                                            </div>
                                        </div>
                                    </>
                                ) : (
                                    <>
                                        <div className="field-info d-flex flex-row justify-content-between">
                                            <div style={{ width: '80%', margin: 'auto 0' }}>
                                                <label
                                                    style={{ fontSize: 'larger', margin: '0' }}
                                                    className="font-weight-bold"
                                                >
                                                    Thời gian đặt lịch
                                                </label>
                                            </div>
                                            <InputText
                                                id="userOnline"
                                                value={room.startTime}
                                                style={{ width: '40%', textAlign: 'center' }}
                                                readOnly
                                            />
                                        </div>
                                    </>
                                )}

                                <div className="field-info d-flex flex-row justify-content-between">
                                    <div style={{ width: '80%', margin: 'auto 0' }}>
                                        <label style={{ fontSize: 'larger', margin: '0' }} className="font-weight-bold">
                                            Mô tả
                                        </label>
                                    </div>
                                    <InputTextarea
                                        style={{ height: '60px' }}
                                        value={room.description}
                                        rows={5}
                                        cols={30}
                                        autoResize
                                        readOnly
                                    />
                                </div>
                            </Dialog>
                        </div>
                    </>
                ) : (
                    <></>
                )}

                <div className="delete-room">
                    <Dialog
                        visible={isDeleteRoom}
                        style={{ width: '450px' }}
                        header="Xác nhận xoá phòng"
                        draggable={false}
                        modal
                        footer={deleteRoomPopup}
                        onHide={onHide}
                    >
                        <div className="confirmation-content">
                            <div
                                style={{ fontSize: '1.4rem', paddingTop: '1.5rem', borderRadius: 'none' }}
                                className="field-checkbox"
                            >
                                <span>
                                    <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                                    Việc này sẽ khiến thành viên không thể tiếp tục làm việc và trao đổi cùng nhau.{' '}
                                    <br />
                                </span>
                                <br></br>
                                <Checkbox checked={deleteRoomMaster} onChange={(e) => onDeleteRoomMaster(e.checked)} />
                                <label
                                    style={{ marginLeft: '1.5rem', color: 'red', marginBottom: 'auto' }}
                                    htmlFor="binary"
                                >
                                    Xoá chủ phòng
                                </label>
                            </div>
                        </div>
                    </Dialog>
                </div>
            </div>
        </div>
    );
}
export default RoomManager;
