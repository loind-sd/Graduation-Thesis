import React, { useState, useEffect, useRef } from 'react';
import { classNames } from 'primereact/utils';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Toast } from 'primereact/toast';
import { Button } from 'primereact/button';
import { Dropdown } from 'primereact/dropdown';
import { FilterMatchMode } from 'primereact/api';
import { Toolbar } from 'primereact/toolbar';
import { InputTextarea } from 'primereact/inputtextarea';
import { RadioButton } from 'primereact/radiobutton';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { Calendar } from 'primereact/calendar';
import { useDispatch, useSelector } from 'react-redux';
import {
    addIndividualTasks,
    deleteIndividualTaskss,
    getIndividualTasks,
    updateIndividualTasks,
} from './../../../../redux/thunks/author/individual-task-thunks';
import './IndividualTask.scss';

function IndividualTask() {
    const dispatch = useDispatch();
    let emptyIndividualTask = {
        id: -1,
        no: -1,
        taskName: '',
        softSkillId: -1,
        softSkillName: '',
        status: '1',
        startDate: '',
        endDate: '',
        createdDate: '',
        createdName: 0,
        taskContent: '',
        doneNumber: 0,
    };

    const [individualTasks, setIndividualTasks] = useState(null);
    const [individualTaskDialog, setIndividualTaskDialog] = useState(false);
    const [deleteIndividualTaskDialog, setDeleteIndividualTaskDialog] = useState(false);
    const [deleteIndividualTasksDialog, setDeleteIndividualTasksDialog] = useState(false);
    const [individualTask, setIndividualTask] = useState(emptyIndividualTask);
    const [selectedIndividualTasks, setSelectedIndividualTasks] = useState(null);
    const [submitted, setSubmitted] = useState(false);
    const [filters, setFilters] = useState({
        global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    });
    const [globalFilter, setGlobalFilter] = useState('');
    const [softSkills, setSoftSkills] = useState([]);
    const toast = useRef(null);
    const dt = useRef(null);

    useEffect(() => {
        dispatch(getIndividualTasks());
    }, []);

    const it = useSelector((state) => {
        return state.individualTaskData.individualTasks;
    });

    const itType = useSelector((state) => {
        return state.individualTaskData.itType;
    });

    const softSkill = useSelector((state) => {
        return state.individualTaskData.softSkill;
    });

    useEffect(() => {
        if (itType === 'load') {
            setIndividualTasks(it);
        } else if (itType === 'add') {
            let _individualTasks = [...individualTasks];
            let _individualTask = it;
            _individualTask.no = _individualTasks.length + 1;
            _individualTasks.push(_individualTask);
            setIndividualTasks(_individualTasks);
        }
    }, [it, itType]);

    useEffect(() => {
        if (softSkill) {
            setSoftSkills(softSkill);
        }
    }, [softSkill]);

    const openNew = () => {
        setIndividualTask(emptyIndividualTask);
        setSubmitted(false);
        setIndividualTaskDialog(true);
    };

    const hideDialog = () => {
        setSubmitted(false);
        setIndividualTaskDialog(false);
    };

    const hideDeleteIndividualTaskDialog = () => {
        setDeleteIndividualTaskDialog(false);
    };

    const hideDeleteIndividualTasksDialog = () => {
        setDeleteIndividualTasksDialog(false);
    };

    const editIndividualTask = (individualTask) => {
        // individualTask.startDate = new Date(individualTask.startDate);
        setIndividualTask({ ...individualTask });
        setIndividualTaskDialog(true);
    };

    const confirmDeleteIndividualTask = (individualTask) => {
        setIndividualTask(individualTask);
        setDeleteIndividualTaskDialog(true);
    };

    const saveGroupTask = () => {
        setSubmitted(true);

        if (
            individualTask.taskName &&
            individualTask.taskName.trim() &&
            individualTask.softSkillId.toString() !== '-1' &&
            individualTask.taskContent &&
            individualTask.taskContent.trim() &&
            individualTask.startDate &&
            individualTask.endDate &&
            new Date(individualTask.endDate) >= new Date(individualTask.startDate)
        ) {
            let _individualTasks = [...individualTasks];
            let _individualTask = { ...individualTask };
            if (_individualTask.id !== -1) {
                dispatch(
                    updateIndividualTasks({
                        id: _individualTask.id,
                        taskName: _individualTask.taskName,
                        taskContent: _individualTask.taskContent,
                        status: _individualTask.status,
                        softSkillId: _individualTask.softSkillId,
                        startDate: _individualTask.startDate,
                        endDate: _individualTask.endDate,
                    }),
                );
                const index = findIndexById(individualTask.id);

                _individualTasks[index] = _individualTask;
                toast.current.show({
                    severity: 'success',
                    summary: 'Thành công',
                    detail: 'Cập nhật thành công!',
                    life: 3000,
                });
            } else {
                // _individualTasks.push(_individualTask);
                dispatch(
                    addIndividualTasks({
                        taskName: _individualTask.taskName,
                        taskContent: _individualTask.taskContent,
                        softSkillId: _individualTask.softSkillId,
                        startDate: _individualTask.startDate,
                        endDate: _individualTask.endDate,
                        status: _individualTask.status,
                    }),
                );
                toast.current.show({
                    severity: 'success',
                    summary: 'Thành công',
                    detail: 'Thêm mới thành công!',
                    life: 3000,
                });
            }

            setIndividualTasks(_individualTasks);
            setIndividualTaskDialog(false);
            setIndividualTask(emptyIndividualTask);
        }
    };

    const deleteIndividualTask = () => {
        let ids = [individualTask.id];
        dispatch(deleteIndividualTaskss(ids));
        individualTask.status = 'Dừng';
        let _individualTasks = individualTasks.filter((val) => (val.id !== individualTask.id ? val : individualTask));
        setIndividualTasks(_individualTasks);
        setDeleteIndividualTaskDialog(false);
        setIndividualTask(emptyIndividualTask);
        toast.current.show({ severity: 'success', summary: 'Thành công', detail: 'Cập nhật thành công!', life: 3000 });
    };

    const findIndexById = (id) => {
        let index = -1;
        for (let i = 0; i < individualTasks.length; i++) {
            if (individualTasks[i].id === id) {
                index = i;
                break;
            }
        }

        return index;
    };

    const confirmDeleteSelected = () => {
        if (selectedIndividualTasks.length === 1) {
            setIndividualTask(selectedIndividualTasks[0]);
            setDeleteIndividualTaskDialog(true);
        } else {
            setDeleteIndividualTasksDialog(true);
        }
    };

    const deleteSelectedIndividualTasks = () => {
        let ids = [];
        for (let index = 0; index < selectedIndividualTasks.length; index++) {
            const element = selectedIndividualTasks[index];
            ids.push(element.id);
        }
        dispatch(deleteIndividualTaskss(ids));
        let _individualTasks = [...individualTasks];
        for (let index = 0; index < selectedIndividualTasks.length; index++) {
            const element = selectedIndividualTasks[index];
            element.status = 'Dừng';
            // _individualTasks[ids[index]] = element;

            const i = findIndexById(ids[index]);
            _individualTasks[i] = element;
        }
        // let _individualTasks = individualTasks.filter((val) => !selectedIndividualTasks.includes(val));
        setIndividualTasks(_individualTasks);
        setDeleteIndividualTasksDialog(false);
        setSelectedIndividualTasks(null);
        toast.current.show({ severity: 'success', summary: 'Thành công', detail: 'Cập nhật thành công!', life: 3000 });
    };

    const onDropdownChange = (e, name) => {
        let _individualTask = { ...individualTask };
        _individualTask[`${name}`] = e.value;
        setIndividualTask(_individualTask);
    };

    const onCalendarChange = (e, name) => {
        let _individualTask = { ...individualTask };
        if (e.value.getDate() < 10) {
            _individualTask[`${name}`] =
                e.value.getFullYear() + '-' + (e.value.getMonth() + 1) + '-0' + e.value.getDate();
        } else {
            _individualTask[`${name}`] =
                e.value.getFullYear() + '-' + (e.value.getMonth() + 1) + '-' + e.value.getDate();
        }
        setIndividualTask(_individualTask);
    };

    const onStatusChange = (e) => {
        let _individualTask = { ...individualTask };
        _individualTask['status'] = e.value;
        setIndividualTask(_individualTask);
    };

    const onInputChange = (e, name) => {
        const val = (e.target && e.target.value) || '';
        let _individualTask = { ...individualTask };
        _individualTask[`${name}`] = val;

        setIndividualTask(_individualTask);
    };

    const onGlobalFilterChange = (e) => {
        const value = e.target.value;
        let _filters = { ...filters };
        _filters['global'].value = value;

        setFilters(_filters);
        setGlobalFilter(value);
    };

    const leftPagnitor = (
        <Button
            label="Xóa tất cả"
            className="p-button-text"
            style={{ margin: '0px', color: 'black', textDecoration: 'underline', cursor: 'pointer', fontSize: '12px' }}
            onClick={confirmDeleteSelected}
            disabled={!selectedIndividualTasks || !selectedIndividualTasks.length}
        />
    );

    const paginatorRight = <Button type="button" icon="pi pi-refresh" className="p-button-text" hidden />;

    const leftToolbarTemplate = () => {
        return (
            <React.Fragment>
                {/* <Button label="Thêm nhiệm vụ cá nhân" icon="pi pi-plus" className="p-button-success mr-2" onClick={openNew} /> */}
                <Button
                    label="Xóa"
                    icon="pi pi-trash"
                    className="p-button-danger"
                    onClick={confirmDeleteSelected}
                    disabled={!selectedIndividualTasks || !selectedIndividualTasks.length}
                />
            </React.Fragment>
        );
    };

    const statusBodyTemplate = (rowData) => {
        return (
            <span
                className={`product-badge status-${rowData.status.toLowerCase()} ${
                    rowData.status === '1' ? 'text-success' : 'text-danger'
                }`}
            >
                {rowData.status === '1' ? 'Hoạt động' : 'Dừng'}
            </span>
        );
    };

    const actionBodyTemplate = (rowData) => {
        return (
            <React.Fragment>
                <Button
                    icon="pi pi-pencil"
                    className="p-button-rounded p-button-warning mr-2"
                    onClick={() => editIndividualTask(rowData)}
                />
                <Button
                    icon="pi pi-trash"
                    className="p-button-rounded p-button-danger"
                    onClick={() => confirmDeleteIndividualTask(rowData)}
                />
            </React.Fragment>
        );
    };

    const header = (
        <div className="table-header">
            {/* <h3 className="mx-0 my-1">Quản lý nhiệm vụ cá nhân</h3> */}
            <span className="p-input-icon-left">
                <i className="pi pi-search" />
                <InputText value={globalFilter} onChange={onGlobalFilterChange} placeholder="Tìm kiếm" />
            </span>
            <Button
                label="Thêm nhiệm vụ cá nhân"
                style={{ fontSize: '12px' }}
                icon="pi pi-plus"
                className="p-button-success mr-2"
                onClick={openNew}
            />
        </div>
    );

    const individualTaskDialogFooter = (
        <React.Fragment>
            <Button label="Hủy bỏ" icon="pi pi-times" className="p-button-text" onClick={hideDialog} />
            <Button label="Lưu" icon="pi pi-check" className="p-button-text" onClick={saveGroupTask} />
        </React.Fragment>
    );
    const deleteIndividualTaskDialogFooter = (
        <React.Fragment>
            <Button label="Hủy" icon="pi pi-times" className="p-button-text" onClick={hideDeleteIndividualTaskDialog} />
            <Button label="Xác nhận" icon="pi pi-check" className="p-button-text" onClick={deleteIndividualTask} />
        </React.Fragment>
    );
    const deleteIndividualTasksDialogFooter = (
        <React.Fragment>
            <Button
                label="Hủy"
                icon="pi pi-times"
                className="p-button-text"
                onClick={hideDeleteIndividualTasksDialog}
            />
            <Button
                label="Xác nhận"
                icon="pi pi-check"
                className="p-button-text"
                onClick={deleteSelectedIndividualTasks}
            />
        </React.Fragment>
    );

    return (
        <div className="individual-task-manager">
            <div className="datatable-crud-demo">
                <Toast ref={toast} />

                <div className="individual-task-card">
                    {/* <Toolbar className="mb-4" left={leftToolbarTemplate}></Toolbar> */}

                    <DataTable
                        ref={dt}
                        value={individualTasks}
                        selection={selectedIndividualTasks}
                        onSelectionChange={(e) => setSelectedIndividualTasks(e.value)}
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
                        <Column field="no" header="STT" sortable style={{ minWidth: '3rem' }}></Column>
                        <Column field="taskName" header="Tên nhiệm vụ" sortable style={{ minWidth: '16rem' }}></Column>
                        <Column
                            field="softSkillName"
                            header="Loại kỹ năng"
                            sortable
                            style={{ minWidth: '8rem' }}
                        ></Column>
                        <Column
                            field="status"
                            header="Trạng thái"
                            body={statusBodyTemplate}
                            sortable
                            style={{ minWidth: '8rem' }}
                        ></Column>
                        <Column
                            field="doneNumber"
                            header="Số người hoàn thành"
                            sortable
                            style={{ minWidth: '4rem' }}
                        ></Column>
                        <Column field="startDate" header="Ngày bắt đầu" sortable style={{ minWidth: '8rem' }}></Column>
                        <Column field="endDate" header="Ngày kết thúc" sortable style={{ minWidth: '8rem' }}></Column>
                        <Column field="createdDate" header="Ngày tạo" sortable style={{ minWidth: '8rem' }}></Column>
                        <Column field="createdName" header="Người tạo" sortable style={{ minWidth: '8rem' }}></Column>
                        <Column
                            header="Thao tác"
                            body={actionBodyTemplate}
                            exportable={false}
                            style={{ minWidth: '10rem' }}
                        ></Column>
                    </DataTable>
                </div>

                {/* add, update */}
                <Dialog
                    visible={individualTaskDialog}
                    style={{ width: '600px' }}
                    draggable={false}
                    header="Thông tin nhiệm vụ cá nhân"
                    modal
                    className="p-fluid"
                    footer={individualTaskDialogFooter}
                    onHide={hideDialog}
                >
                    <div className="field">
                        <label htmlFor="taskName" className="font-weight-bold">
                            Tên nhiệm vụ <span className="text-danger"> *</span>
                        </label>
                        <InputText
                            id="taskName"
                            value={individualTask.taskName}
                            onChange={(e) => onInputChange(e, 'taskName')}
                            required
                            maxLength={50}
                            autoFocus
                            className={classNames({ 'p-invalid': submitted && !individualTask.taskName })}
                        />
                        {submitted && !individualTask.taskName.trim() && (
                            <small className="p-error">Tên nhiệm vụ không được để trống</small>
                        )}
                    </div>

                    <div className="field">
                        <label htmlFor="softSkillId" className="mt-3 font-weight-bold">
                            Loại kỹ năng <span className="text-danger"> *</span>
                        </label>
                        <Dropdown
                            value={individualTask.softSkillId}
                            options={softSkills}
                            onChange={(e) => onDropdownChange(e, 'softSkillId')}
                            placeholder="Chọn 1 kỹ năng"
                        />
                        {submitted && individualTask.softSkillId.toString() === '-1' && (
                            <small className="p-error">Mục này không được để trống</small>
                        )}
                    </div>

                    <div className="field">
                        <label htmlFor="taskContent" className="mt-3 font-weight-bold">
                            Nội dung nhiệm vụ <span className="text-danger"> *</span>
                        </label>
                        <InputTextarea
                            id="taskContent"
                            value={individualTask.taskContent}
                            onChange={(e) => onInputChange(e, 'taskContent')}
                            required
                            maxLength={255}
                            rows={3}
                            cols={20}
                        />
                        {submitted && !individualTask.taskContent.trim() && (
                            <small className="p-error">Nội dung nhiệm vụ không được để trống</small>
                        )}
                    </div>

                    <div className="date-pick" style={{ display: 'flex' }}>
                        <div className="field" style={{ width: '87%' }}>
                            <label htmlFor="startDate" className="mt-3 font-weight-bold">
                                Ngày bắt đầu <span className="text-danger"> *</span>
                            </label>
                            <Calendar
                                dateFormat="dd/mm/yy"
                                value={new Date(individualTask.startDate)}
                                minDate={new Date()}
                                showIcon={true}
                                onChange={(e) => onCalendarChange(e, 'startDate')}
                            ></Calendar>
                            {submitted && !individualTask.startDate && (
                                <small className="p-error">Ngày bắt đầu không được để trống</small>
                            )}
                        </div>

                        <div className="field" style={{ marginLeft: '9%', width: '87%' }}>
                            <label htmlFor="endDate" className="mt-3 font-weight-bold">
                                Ngày kết thúc <span className="text-danger"> *</span>
                            </label>
                            <Calendar
                                dateFormat="dd/mm/yy"
                                value={new Date(individualTask.endDate)}
                                minDate={new Date()}
                                showIcon={true}
                                onChange={(e) => onCalendarChange(e, 'endDate')}
                            ></Calendar>
                            {submitted && !individualTask.endDate && (
                                <small className="p-error">Ngày kết thúc không được để trống</small>
                            )}
                            {submitted &&
                                individualTask.endDate &&
                                individualTask.startDate &&
                                new Date(individualTask.startDate) > new Date(individualTask.endDate) && (
                                    <small className="p-error">Ngày bắt đầu không được lớn hơn ngày kết thúc</small>
                                )}
                        </div>
                    </div>

                    <div className="field">
                        <label className="mb-3 mt-3 font-weight-bold">Trạng thái</label>
                        <div className="formgrid grid">
                            <div className="field-radiobutton col-6 mb-2">
                                <RadioButton
                                    inputId="status1"
                                    name="status"
                                    value="1"
                                    onChange={onStatusChange}
                                    checked={individualTask.status === '1'}
                                />
                                <label htmlFor="category1" className="text-success ml-2">
                                    Hoạt động
                                </label>
                            </div>
                            <div className="field-radiobutton col-6">
                                <RadioButton
                                    inputId="status2"
                                    name="status"
                                    value="0"
                                    onChange={onStatusChange}
                                    checked={individualTask.status === '0'}
                                />
                                <label htmlFor="category2" className="text-danger ml-2 mt-2">
                                    Dừng
                                </label>
                            </div>
                        </div>
                    </div>
                </Dialog>

                <Dialog
                    visible={deleteIndividualTaskDialog}
                    style={{ width: '450px' }}
                    draggable={false}
                    header="Xóa?"
                    modal
                    footer={deleteIndividualTaskDialogFooter}
                    onHide={hideDeleteIndividualTaskDialog}
                >
                    <div className="confirmation-content" style={{ fontFamily: 'Helvetica', fontSize: '1.2rem' }}>
                        <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                        {individualTask && (
                            <span>
                                Việc xóa nhiệm vụ <b>{individualTask.taskName}</b> sẽ đồng nghĩa với việc chuyển trạng
                                thái sang <label className="text-danger font-weight-bold">Dừng </label>. <br />
                                <label className="ml-5">Bạn có chắc chắn?</label>
                            </span>
                        )}
                    </div>
                </Dialog>

                <Dialog
                    visible={deleteIndividualTasksDialog}
                    style={{ width: '450px' }}
                    draggable={false}
                    header="Xóa?"
                    modal
                    footer={deleteIndividualTasksDialogFooter}
                    onHide={hideDeleteIndividualTasksDialog}
                >
                    <div className="confirmation-content" style={{ fontFamily: 'Helvetica', fontSize: '1.2rem' }}>
                        <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                        {individualTask && (
                            <span>
                                Việc xóa các nhiệm vụ trên sẽ đồng nghĩa với việc chuyển trạng thái sang{' '}
                                <label className="text-danger font-weight-bold">Dừng </label>. <br />
                                <label className="ml-5">Bạn có chắc chắn?</label>
                            </span>
                        )}
                    </div>
                </Dialog>
            </div>
        </div>
    );
}

export default IndividualTask;
