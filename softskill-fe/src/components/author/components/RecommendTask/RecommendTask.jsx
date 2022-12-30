import React, { useState, useEffect, useRef } from 'react';
import { classNames } from 'primereact/utils';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Toast } from 'primereact/toast';
import { Button } from 'primereact/button';
import { FilterMatchMode } from 'primereact/api';
import { Toolbar } from 'primereact/toolbar';
import { InputTextarea } from 'primereact/inputtextarea';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { Image } from 'primereact/image';
import { useDispatch, useSelector } from 'react-redux';
import {
    deleteRecommendTaskss,
    getRecommendTasks,
    updateRecommendTasks,
} from './../../../../redux/thunks/author/recommend-task-thunks';
import { sendThankMail } from './../../../../redux/thunks/mail-thunks';
import { Editor } from 'primereact/editor';
import './RecommendTask.scss';

function RecommendTask() {
    const dispatch = useDispatch();
    let emptyRecommendTask = {
        id: -1,
        no: -1,
        createdName: 0,
        taskContent: '',
        softSkillName: '',
        taskType: '',
        status: '0',
        createdDate: '',
        updateName: 0,
        taskImage: '',
        guideline: '',
        guidelineImage: null,
        mail: '',
    };

    const [recommendTasks, setRecommendTasks] = useState(null);
    const [mailContent, setMailContent] = useState('');
    const [mailSubject, setMailSubject] = useState('');
    const [recommendTaskDialog, setRecommendTaskDialog] = useState(false);
    const [recommendTaskViewDialog, setRecommendTaskViewDialog] = useState(false);
    const [deleteRecommendTaskDialog, setDeleteRecommendTaskDialog] = useState(false);
    const [deleteRecommendTasksDialog, setDeleteRecommendTasksDialog] = useState(false);
    const [recommendTask, setRecommendTask] = useState(emptyRecommendTask);
    const [selectedRecommendTasks, setSelectedRecommendTasks] = useState(null);
    const [submitted, setSubmitted] = useState(false);
    const [filters, setFilters] = useState({
        global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    });
    const [globalFilter, setGlobalFilter] = useState('');
    const toast = useRef(null);
    const dt = useRef(null);

    useEffect(() => {
        dispatch(getRecommendTasks());
    }, []);

    const rt = useSelector((state) => {
        return state.recommendTaskData.recommendTasks;
    });

    const rtType = useSelector((state) => {
        return state.recommendTaskData.rtType;
    });

    useEffect(() => {
        if (rtType === 'load') {
            setRecommendTasks(rt);
        }
    }, [rt, rtType]);

    const openNew = () => {
        setMailContent('');
        setMailSubject('');
        setSubmitted(false);
        setRecommendTaskDialog(true);
    };

    const hideDialog = () => {
        setSubmitted(false);
        setRecommendTaskDialog(false);
    };

    const hideViewDialog = () => {
        setSubmitted(false);
        setRecommendTaskViewDialog(false);
    };

    const hideDeleteRecommendTaskDialog = () => {
        setDeleteRecommendTaskDialog(false);
    };

    const hideDeleteRecommendTasksDialog = () => {
        setDeleteRecommendTasksDialog(false);
    };

    const saveRecommendTask = (e) => {
        setSubmitted(true);

        let _recommendTasks = [...recommendTasks];
        let _recommendTask = { ...recommendTask };
        if (_recommendTask.id) {
            _recommendTask.status = e;
            _recommendTask.updateName = 'Bạn';
            dispatch(
                updateRecommendTasks({
                    id: _recommendTask.id,
                    status: _recommendTask.status,
                }),
            );
            const index = findIndexById(recommendTask.id);

            _recommendTasks[index] = _recommendTask;
            toast.current.show({
                severity: 'success',
                summary: 'Thành công',
                detail: 'Cập nhật thành công!',
                life: 3000,
            });

            setRecommendTaskViewDialog(false);
            setRecommendTasks(_recommendTasks);
            setRecommendTask(emptyRecommendTask);
            hideDialog();
        }
    };

    const viewRecommendTask = (recommendTask) => {
        setRecommendTask({ ...recommendTask });
        setRecommendTaskViewDialog(true);
    };

    const confirmDeleteRecommendTask = (recommendTask) => {
        setRecommendTask(recommendTask);
        setDeleteRecommendTaskDialog(true);
    };

    const sendMail = () => {
        setSubmitted(true);
        if (mailSubject.trim() && mailContent.trim()) {
            // send mail - todo


            // let content = document.getElementsByClassName('p-inputtextarea')[2].innerHTML;
            // let r = content.replaceAll('\n', ' <br/>');
            dispatch(
                sendThankMail({
                    to: recommendTask.mail,
                    subject: mailSubject,
                    mailContent: mailContent,
                }),
            );
            // accept
            saveRecommendTask('2');
        }
    };

    // const formatMailContent = () => {
    //     let content = document.getElementsByClassName('p-inputtextarea')[2].innerHTML;
    //     let r = content.replace('\n', ' <br/>');
    //     setMailContent(r);
    // };

    const deleteRecommendTask = () => {
        let ids = [recommendTask.id];
        dispatch(deleteRecommendTaskss(ids));
        let _recommendTasks = recommendTasks.filter((val) => val.id !== recommendTask.id);
        setRecommendTasks(_recommendTasks);
        setDeleteRecommendTaskDialog(false);
        setRecommendTaskViewDialog(false);
        setRecommendTask(emptyRecommendTask);
        toast.current.show({ severity: 'success', summary: 'Thành công', detail: 'Xóa thành công!', life: 3000 });
    };

    const findIndexById = (id) => {
        let index = -1;
        for (let i = 0; i < recommendTasks.length; i++) {
            if (recommendTasks[i].id === id) {
                index = i;
                break;
            }
        }

        return index;
    };

    const confirmDeleteSelected = () => {
        if (selectedRecommendTasks.length === 1) {
            setRecommendTask(selectedRecommendTasks[0]);
            setDeleteRecommendTaskDialog(true);
        } else {
            setDeleteRecommendTasksDialog(true);
        }
    };

    const deleteSelectedRecommendTasks = () => {
        let ids = [];
        for (let index = 0; index < selectedRecommendTasks.length; index++) {
            const element = selectedRecommendTasks[index];
            ids.push(element.id);
        }
        dispatch(deleteRecommendTaskss(ids));
        let _recommendTasks = recommendTasks.filter((val) => !selectedRecommendTasks.includes(val));
        setRecommendTasks(_recommendTasks);
        setDeleteRecommendTasksDialog(false);
        setSelectedRecommendTasks(null);
        toast.current.show({ severity: 'success', summary: 'Thành công', detail: 'Xóa thành công!', life: 3000 });
    };

    const onInputChange = (e) => {
        const val = (e.target && e.target.value) || '';
        setMailSubject(val);
    };

    const onMailChange = (e) => {
        const val = (e.target && e.target.value) || '';
        setMailContent(val);
    };

    const initDemoMail = () => {
        const demo =
            'Chào bạn, <br>Lời đầu tiên, chúng tôi rất cảm ơn bạn vì đã gửi đề xuất nhiệm vụ cho <strong>Cộng đồng phát triển Kỹ năng mềm</strong>. Sự giúp đỡ của bạn sẽ giúp ích rất nhiều cho chúng tôi.<br><br>Đề xuất của bạn thực sự hữu ích và rất phù hợp với những tiêu chí mà <strong>Cộng đồng phát triển Kỹ năng mềm</strong> đang hướng tới. Vì vậy, chúng tôi chắc chắn sẽ thêm nhiệm vụ của bạn trong lần cập nhật sắp tới.<br><br>Một lần nữa, cảm ơn bạn rất nhiều. Nếu như bạn có những ý tưởng, đóng góp nào khác, vui lòng liên hệ với chúng tôi.<br><br>Thân gửi,<br>LoiND';
        setMailContent(demo);
    };

    const onGlobalFilterChange = (e) => {
        const value = e.target.value;
        let _filters = { ...filters };
        _filters['global'].value = value;

        setFilters(_filters);
        setGlobalFilter(value);
    };

    const leftToolbarTemplate = () => {
        return (
            <React.Fragment>
                <Button
                    label="Xóa"
                    icon="pi pi-trash"
                    className="p-button-danger"
                    onClick={confirmDeleteSelected}
                    disabled={!selectedRecommendTasks || !selectedRecommendTasks.length}
                />
            </React.Fragment>
        );
    };

    const statusBodyTemplate = (rowData) => {
        return (
            <span
                className={`recommendTask-badge status-${rowData.status.toLowerCase()} ${rowData.status === '2' ? 'text-success' : rowData.status === '1' ? 'text-primary' : 'text-danger'
                    }`}
            >
                {rowData.status === '1' ? 'Chờ duyệt' : rowData.status === '2' ? 'Duyệt' : 'Từ chối'}
            </span>
        );
    };

    const leftPagnitor = (
        <Button
            label="Xóa tất cả"
            className="p-button-text"
            style={{ margin: '0px', color: 'black', textDecoration: 'underline', cursor: 'pointer', fontSize: '12px' }}
            onClick={confirmDeleteSelected}
            disabled={!selectedRecommendTasks || !selectedRecommendTasks.length}
        />
    );

    const paginatorRight = <Button type="button" icon="pi pi-refresh" className="p-button-text" hidden />;

    const actionBodyTemplate = (rowData) => {
        return (
            <React.Fragment>
                <Button
                    icon="pi pi-eye"
                    className="p-button-rounded p-button-primary mr-2"
                    onClick={() => viewRecommendTask(rowData)}
                />
                <Button
                    icon="pi pi-trash"
                    className="p-button-rounded p-button-danger mr-2"
                    onClick={() => confirmDeleteRecommendTask(rowData)}
                />
            </React.Fragment>
        );
    };

    const header = (
        <div className="table-header">
            {/* <h5 className="mx-0 my-1">Quản lý nhiệm vụ đề xuất</h5> */}
            <span className="p-input-icon-left">
                <i className="pi pi-search" />
                <InputText value={globalFilter} onChange={onGlobalFilterChange} placeholder="Tìm kiếm" />
            </span>
        </div>
    );
    const recommendTaskViewDialogFooter = (
        <React.Fragment>
            {recommendTask.status === '1' && (
                <div>
                    <Button
                        label="Duyệt"
                        icon="pi pi-check"
                        className="p-button-raised p-button-rounded p-button-primary"
                        onClick={() => saveRecommendTask('2')}
                    />
                    <Button
                        label="Duyệt và Cảm ơn"
                        icon="pi pi-heart"
                        className="p-button-raised p-button-rounded p-button-primary"
                        onClick={() => openNew()}
                    />
                    <Button
                        label="Từ chối"
                        icon="pi pi-ban"
                        className="p-button-raised p-button-rounded p-button-danger"
                        onClick={() => saveRecommendTask('3')}
                    />
                </div>
            )}
            {recommendTask.status !== '1' && (
                <div>
                    <Button label="OK" icon="pi pi-check" className="p-button-text" onClick={hideViewDialog} />
                </div>
            )}
        </React.Fragment>
    );
    const recommendTaskDialogFooter = (
        <React.Fragment>
            <Button label="Hủy" icon="pi pi-times" className="p-button-text" onClick={hideDialog} />
            <Button label="Gửi" icon="pi pi-check" className="p-button-text" onClick={() => sendMail()} />
        </React.Fragment>
    );
    const deleteRecommendTaskDialogFooter = (
        <React.Fragment>
            <Button label="Hủy" icon="pi pi-times" className="p-button-text" onClick={hideDeleteRecommendTaskDialog} />
            <Button label="Xác nhận" icon="pi pi-check" className="p-button-text" onClick={deleteRecommendTask} />
        </React.Fragment>
    );
    const deleteRecommendTasksDialogFooter = (
        <React.Fragment>
            <Button label="Hủy" icon="pi pi-times" className="p-button-text" onClick={hideDeleteRecommendTasksDialog} />
            <Button
                label="Xác nhận"
                icon="pi pi-check"
                className="p-button-text"
                onClick={deleteSelectedRecommendTasks}
            />
        </React.Fragment>
    );

    const renderHeader = () => {
        return (
            <span className="ql-formats">
                <button className="ql-bold" aria-label="Bold"></button>
                <button className="ql-italic" aria-label="Italic"></button>
                <button className="ql-underline" aria-label="Underline"></button>
            </span>
        );
    };

    const replyMailHeader = renderHeader();

    return (
        <div className="recommend-task-manager">
            <div className="datatable-crud-demo">
                <Toast ref={toast} />

                <div className="recommend-task-card">
                    {/* <Toolbar className="mb-4" left={leftToolbarTemplate}></Toolbar> */}

                    <DataTable
                        ref={dt}
                        value={recommendTasks}
                        selection={selectedRecommendTasks}
                        onSelectionChange={(e) => setSelectedRecommendTasks(e.value)}
                        dataKey="id"
                        paginator
                        rows={10}
                        paginatorLeft={leftPagnitor}
                        paginatorRight={paginatorRight}
                        paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                        currentPageReportTemplate="Tổng số: {totalRecords} bản ghi"
                        rowsPerPageOptions={[10, 20, 50]}
                        filters={filters}
                        header={header}
                        responsiveLayout="scroll"
                        emptyMessage="Chưa có dữ liệu"
                        style={{ fontSize: '12px' }}
                    >
                        <Column selectionMode="multiple" headerStyle={{ width: '3rem' }} exportable={false}></Column>
                        <Column
                            field="no"
                            header="STT"
                            sortable
                            style={{ minWidth: '3rem', justifyContent: 'left' }}
                        ></Column>
                        <Column field="createdName" header="Người đề xuất" style={{ minWidth: '8rem' }}></Column>
                        <Column field="softSkillName" header="Kỹ năng" sortable style={{ minWidth: '8rem' }}></Column>
                        <Column field="taskType" header="Loại nhiệm vụ" sortable style={{ minWidth: '8rem' }}></Column>
                        <Column
                            field="status"
                            header="Trạng thái"
                            body={statusBodyTemplate}
                            sortable
                            style={{ minWidth: '8rem' }}
                        ></Column>
                        <Column field="createdDate" header="Ngày gửi" sortable style={{ minWidth: '8rem' }}></Column>
                        <Column
                            field="updateName"
                            header="Người phản hồi"
                            sortable
                            style={{ minWidth: '6rem' }}
                        ></Column>
                        <Column
                            header="Thao tác"
                            body={actionBodyTemplate}
                            exportable={false}
                            style={{ minWidth: '10rem' }}
                        ></Column>
                    </DataTable>
                </div>

                {/* dialog view */}
                <Dialog
                    visible={recommendTaskViewDialog}
                    style={{ width: '500px' }}
                    draggable={false}
                    header="Chi tiết"
                    modal
                    className="p-fluid"
                    dismissableMask
                    closeOnEscape
                    footer={recommendTaskViewDialogFooter}
                    onHide={hideViewDialog}
                >
                    <div className="field">
                        <span>
                            <label htmlFor="taskType" className="mr-3 mt-3">
                                Loại nhiệm vụ:{' '}
                            </label>
                            <label className="font-weight-bold"> {recommendTask.taskType}</label>
                        </span>
                    </div>

                    <div className="field">
                        <span>
                            <label htmlFor="softSkillName" className="mr-3 mt-3">
                                Kỹ năng:{' '}
                            </label>
                            <label className="font-weight-bold"> {recommendTask.softSkillName}</label>
                        </span>
                    </div>

                    <div className="field">
                        <label htmlFor="taskContent" className="mr-3 mt-3">
                            Nội dung nhiệm vụ
                        </label>
                        <InputTextarea
                            id="taskContent"
                            value={recommendTask.taskContent === null ? '' : recommendTask.taskContent}
                            readOnly
                            rows={3}
                            cols={20}
                        />
                    </div>

                    {recommendTask.taskImage === null && (
                        <label htmlFor="taskImage">
                            Ảnh nhiệm vụ<span className="font-weight-bold">: Không có</span>
                        </label>
                    )}
                    {recommendTask.taskImage && (
                        <div>
                            <label htmlFor="taskImage" className="d-block">
                                Ảnh nhiệm vụ
                            </label>
                            <Image
                                src={`data:image/jpeg;base64,${recommendTask.taskImage}`}
                                onError={(e) => (e.target.src = './../../../../assets/image/softskill.jpg')}
                                preview
                                downloadable
                                alt="Image"
                                imageClassName="recommendTask-image block m-auto pb-3"
                                style={{ width: '150px', height: '150px' }}
                                imageStyle={{ width: '-webkit-fill-available', height: '-webkit-fill-available' }}
                            />
                        </div>
                    )}

                    <div className="field">
                        <label htmlFor="guideline">Hướng dẫn</label>
                        <InputTextarea
                            id="guideline"
                            value={recommendTask.guideline === null ? '' : recommendTask.guideline}
                            readOnly
                            rows={3}
                            cols={20}
                        />
                    </div>

                    {recommendTask.guidelineImage === null && (
                        <label htmlFor="taskImage">
                            Ảnh nhiệm vụ<span className="font-weight-bold">: Không có</span>
                        </label>
                    )}
                    {recommendTask.guidelineImage && (
                        <div>
                            <label htmlFor="guidelineImage" className="d-block">
                                Ảnh hướng dẫn
                            </label>
                            <Image
                                src={
                                    `data:image/jpeg;base64,${recommendTask.guidelineImage}` ||
                                    require('./../../../../assets/image/softskill.jpg')
                                }
                                onError={(e) => (e.target.src = './../../../../assets/image/softskill.jpg')}
                                preview
                                downloadable
                                alt="Image"
                                imageClassName="recommendTask-image block m-auto pb-3"
                                style={{ width: '150px', height: '150px' }}
                                imageStyle={{ width: '-webkit-fill-available', height: '-webkit-fill-available' }}
                            />
                        </div>
                    )}
                </Dialog>

                {/* send mail */}
                <Dialog
                    visible={recommendTaskDialog}
                    style={{ width: '600px' }}
                    draggable={false}
                    header="Cảm ơn"
                    modal
                    className="p-fluid"
                    footer={recommendTaskDialogFooter}
                    onHide={hideDialog}
                >
                    <div className="field mb-3">
                        <label htmlFor="mail" className="font-weight-bold">
                            Người nhận
                        </label>
                        <InputText
                            id="mail"
                            value={recommendTask.mail}
                            required
                            readOnly
                            className={classNames({ 'p-invalid': submitted && !recommendTask.mail })}
                        />
                    </div>

                    <div className="field">
                        <label htmlFor="mailSubject" className="font-weight-bold">
                            Tiêu đề <span className="text-danger"> *</span>
                        </label>
                        <InputText
                            id="maiSubject"
                            value={mailSubject}
                            onChange={(e) => onInputChange(e)}
                            maxLength={100}
                            required
                            autoFocus
                            className={classNames({ 'p-invalid': submitted && !mailSubject })}
                        />
                        {submitted && !mailSubject.trim() && (
                            <small className="p-error">Tiêu đề không được để trống</small>
                        )}
                    </div>

                    <div className="field">
                        <label htmlFor="mailContent" className="font-weight-bold">
                            Nội dung email <span className="text-danger"> *</span>
                        </label>
                        {/* <InputTextarea
                            id="mailContent"
                            rows={8}
                            cols={20}
                            value={mailContent}
                            onChange={(e) => onMailChange(e)}
                            className={classNames({ 'p-invalid': submitted && !mailContent })}
                        /> */}
                        <Editor
                            headerTemplate={replyMailHeader}
                            style={{ height: '290px' }}
                            value={mailContent}
                            onTextChange={(e) => {
                                setMailContent(e.htmlValue);
                            }}
                        />
                        {submitted && !mailContent.trim() && (
                            <small className="p-error">Nội dung email không được để trống</small>
                        )}
                        <Button
                            label="Sử dụng mẫu có sẵn"
                            className="p-button-link"
                            style={{ width: '30%', display: 'block' }}
                            onClick={initDemoMail}
                        />
                    </div>
                </Dialog>

                <Dialog
                    visible={deleteRecommendTaskDialog}
                    style={{ width: '250px' }}
                    draggable={false}
                    header="Xóa?"
                    headerClassName="text-danger"
                    modal
                    closeOnEscape
                    dismissableMask
                    footer={deleteRecommendTaskDialogFooter}
                    onHide={hideDeleteRecommendTaskDialog}
                >
                    <div className="confirmation-content" style={{ fontFamily: 'Helvetica', fontSize: '1.2rem' }}>
                        <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                        {recommendTask && (
                            <span>
                                <label className="font-weight-bold">Bạn có chắc chắn?</label>
                            </span>
                        )}
                    </div>
                </Dialog>

                <Dialog
                    visible={deleteRecommendTasksDialog}
                    style={{ width: '300px' }}
                    draggable={false}
                    header="Xóa?"
                    modal
                    closeOnEscape
                    dismissableMask
                    footer={deleteRecommendTasksDialogFooter}
                    onHide={hideDeleteRecommendTasksDialog}
                >
                    <div className="confirmation-content" style={{ fontFamily: 'Helvetica', fontSize: '1.2rem' }}>
                        <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                        {recommendTask && (
                            <span>
                                <label className="font-weight-bold">Bạn có chắc chắn muốn xóa những mục đã chọn?</label>
                            </span>
                        )}
                    </div>
                </Dialog>
            </div>
        </div>
    );
}

export default RecommendTask;
