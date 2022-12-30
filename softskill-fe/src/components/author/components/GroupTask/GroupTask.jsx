import React, { useState, useEffect, useRef } from 'react';
import { classNames } from 'primereact/utils';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Toast } from 'primereact/toast';
import { Button } from 'primereact/button';
import { Dropdown } from 'primereact/dropdown';
import { FileUpload } from 'primereact/fileupload';
import { InputTextarea } from 'primereact/inputtextarea';
import { RadioButton } from 'primereact/radiobutton';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { Calendar } from 'primereact/calendar';
import { Image } from 'primereact/image';
import { FilterMatchMode, FilterOperator } from 'primereact/api';
import { useDispatch, useSelector } from 'react-redux';
import {
    addGroupTasks,
    deleteGroupTaskss,
    getGroupTasks,
    updateGroupTasks,
} from './../../../../redux/thunks/author/group-task-thunks';
import './GroupTask.scss';

function GroupTask() {
    const dispatch = useDispatch();
    let emptyGroupTask = {
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
        guidelineChange: false,
        guideline: [],
        doingNumber: 0,
        doneNumber: 0,
        favoriteNumber: 0,
        requiredTask: -1,
        taskImage: null,
        guidelineImage: null,
    };

    let emptyGuideline = {
        guideNo: 1,
        guideName: '',
    };

    const [groupTasks, setGroupTasks] = useState(null);
    const [groupTaskDialog, setGroupTaskDialog] = useState(false);
    const [groupTaskViewDialog, setGroupTaskViewDialog] = useState(false);
    const [deleteGroupTaskDialog, setDeleteGroupTaskDialog] = useState(false);
    const [deleteGroupTasksDialog, setDeleteGroupTasksDialog] = useState(false);
    const [groupTask, setGroupTask] = useState(emptyGroupTask);
    const [guideLine, setGuideline] = useState(emptyGuideline);
    const [selectedGroupTasks, setSelectedGroupTasks] = useState(null);
    const [submitted, setSubmitted] = useState(false);
    const [filters, setFilters] = useState({
        global: { value: null, matchMode: FilterMatchMode.CONTAINS },
        softSkillName: {
            operator: FilterOperator.AND,
            constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }],
        },
        taskName: { value: null, matchMode: FilterMatchMode.STARTS_WITH },
        status: { operator: FilterOperator.OR, constraints: [{ value: null, matchMode: FilterMatchMode.EQUALS }] },
    });
    const [globalFilter, setGlobalFilter] = useState('');
    const [personalTasks, setPersonalTasks] = useState([]);
    const [softSkills, setSoftSkills] = useState([]);
    const [fileTask, setFileTask] = useState('');
    const [fileGuideline, setFileGuideline] = useState('');
    const toast = useRef(null);
    const dt = useRef(null);

    // const [selectedFile, setSelectedFile] = useState();
    // const [preview, setPreview] = useState();

    // useEffect(() => {
    //     if (!selectedFile) {
    //         setPreview(undefined);
    //         return;
    //     }

    //     const objectUrl = URL.createObjectURL(selectedFile);
    //     setPreview(objectUrl);

    //     console.log(preview);

    //     return () => URL.revokeObjectURL(objectUrl);
    // }, [selectedFile]);

    // const onSelectFile = (e) => {
    //     if (!e.target.files || e.target.files.length === 0) {
    //         setSelectedFile(undefined);
    //         return;
    //     }

    //     setSelectedFile(e.target.files[0]);
    // };

    // const uploadImg = async () => {
    //     const formData = new FormData();
    //     formData.append('file', selectedFile);
    //     console.log(formData);
    //     const response = await RequestService.post('/manager/tasks/image', formData);
    //     // return http.post('http://localhost:8080/api/manager/tasks/image', formData, {
    //     //     headers: {
    //     //         'Content-Type': 'multipart/form-data',
    //     //     },
    //     // });
    //     // try {
    //     //     const response = await axios({
    //     //         method: 'post',
    //     //         url: `http://localhost:8080/api/manager/tasks/image`,
    //     //         file: formData,
    //     //         headers: { 'Content-Type': 'multipart/form-data' },
    //     //     });
    //     // } catch (error) {
    //     //     console.log(error);
    //     // }
    // };

    useEffect(() => {
        dispatch(getGroupTasks());
    }, []);

    const gt = useSelector((state) => {
        return state.groupTaskData.groupTasks;
    });

    const gtType = useSelector((state) => {
        return state.groupTaskData.gtType;
    });

    const personalTask = useSelector((state) => {
        return state.groupTaskData.personalTask;
    });

    const softSkill = useSelector((state) => {
        return state.groupTaskData.softSkill;
    });

    useEffect(() => {
        if (gtType === 'load') {
            setGroupTasks(gt);
        } else if (gtType === 'add') {
            let _groupTasks = [...groupTasks];
            let _groupTask = gt;
            _groupTask.no = _groupTasks.length + 1;
            _groupTasks.push(_groupTask);
            setGroupTasks(_groupTasks);
        } else if (gtType === 'update') {
            let _groupTasks = [...groupTasks];
            let _groupTask = gt;
            const index = findIndexById(gt.id);
            _groupTasks[index] = _groupTask;
            setGroupTasks(_groupTasks);
        }
    }, [gt, gtType]);

    useEffect(() => {
        if (personalTask) {
            setPersonalTasks(personalTask);
        }
        if (softSkill) {
            setSoftSkills(softSkill);
        }
    }, [personalTask, softSkill]);

    const openNew = () => {
        setGroupTask(emptyGroupTask);
        setSubmitted(false);
        setGroupTaskDialog(true);
        setFileTask('');
        setFileGuideline('');
    };

    const addNewGuideline = () => {
        setGuideline(emptyGuideline);
        setSubmitted(false);
        if (groupTask.guideline.length === 0 || groupTask.guideline[groupTask.guideline.length - 1].guideName.trim()) {
            guideLine.guideNo = groupTask.guideline.length + 1;
            groupTask.guideline.push(guideLine);
            groupTask.guidelineChange = true;
        }
    };

    const hideDialog = () => {
        setSubmitted(false);
        setGroupTaskDialog(false);
    };

    const hideViewDialog = () => {
        setSubmitted(false);
        setGroupTaskViewDialog(false);
    };

    const hideDeleteGroupTaskDialog = () => {
        setDeleteGroupTaskDialog(false);
    };

    const hideDeleteGroupTasksDialog = () => {
        setDeleteGroupTasksDialog(false);
    };

    const saveGroupTask = () => {
        setSubmitted(true);

        if (
            groupTask.taskName &&
            groupTask.taskName.trim() &&
            groupTask.softSkillId.toString() !== '-1' &&
            groupTask.taskContent &&
            groupTask.taskContent.trim() &&
            groupTask.startDate &&
            groupTask.endDate &&
            new Date(groupTask.endDate) >= new Date(groupTask.startDate) &&
            (fileTask !== '' || groupTask.taskImage) &&
            (fileGuideline !== '' ||
                groupTask.guidelineImage ||
                (!fileGuideline && !groupTask.guidelineImage && groupTask.guideline.length === 0))
        ) {
            let _groupTasks = [...groupTasks];
            let _groupTask = { ...groupTask };
            if (_groupTask.id !== -1) {
                console.log(fileTask);
                console.log(fileGuideline);
                const formData = new FormData();
                formData.append(`fileTask`, fileTask === '' ? null : fileTask);
                formData.append(`fileGuideline`, fileGuideline === '' ? null : fileGuideline);
                formData.append(`guideline`, tranferGuideline(_groupTask.guideline));
                dispatch(
                    // updateGroupTasks({
                    //     id: _groupTask.id,
                    //     taskName: _groupTask.taskName,
                    //     taskContent: _groupTask.taskContent,
                    //     taskImage: _groupTask.taskImage,
                    //     status: _groupTask.status,
                    //     requiredTask: _groupTask.requiredTask,
                    //     softSkillId: _groupTask.softSkillId,
                    //     startDate: _groupTask.startDate,
                    //     endDate: _groupTask.endDate,
                    //     guidelineChange: _groupTask.guidelineChange,
                    //     guideline: _groupTask.guideline,
                    //     guidelineImage: _groupTask.guidelineImage,
                    // }),
                    updateGroupTasks(
                        formData,
                        _groupTask.taskName,
                        _groupTask.taskContent,
                        _groupTask.requiredTask,
                        _groupTask.softSkillId,
                        _groupTask.startDate,
                        _groupTask.endDate,
                        _groupTask.status,
                        _groupTask.id,
                        _groupTask.guidelineChange,
                        _groupTask.no,
                    ),
                );

                toast.current.show({
                    severity: 'success',
                    summary: 'Thành công',
                    detail: 'Cập nhật thành công!',
                    life: 3000,
                });
            } else {
                // _groupTasks.push(_groupTask);
                const formData = new FormData();
                formData.append(`fileTask`, fileTask);
                formData.append(`fileGuideline`, fileGuideline);
                formData.append(`guideline`, tranferGuideline(_groupTask.guideline));
                // console.log(_groupTask.guideline);
                dispatch(
                    // addGroupTasks({
                    //     taskName: _groupTask.taskName,
                    //     taskContent: _groupTask.taskContent,
                    //     taskImage: _groupTask.taskImage,
                    //     requiredTask: _groupTask.requiredTask,
                    //     softSkillId: _groupTask.softSkillId,
                    //     startDate: _groupTask.startDate,
                    //     endDate: _groupTask.endDate,
                    //     guideline: _groupTask.guideline,
                    //     status: _groupTask.status,
                    //     guidelineImage: _groupTask.guidelineImage,
                    // }),
                    addGroupTasks(
                        formData,
                        _groupTask.taskName,
                        _groupTask.taskContent,
                        _groupTask.requiredTask,
                        _groupTask.softSkillId,
                        _groupTask.startDate,
                        _groupTask.endDate,
                        _groupTask.status,
                    ),
                );
                toast.current.show({
                    severity: 'success',
                    summary: 'Thành công',
                    detail: 'Thêm mới thành công!',
                    life: 3000,
                });
            }

            setGroupTasks(_groupTasks);
            setGroupTaskDialog(false);
            setGroupTask(emptyGroupTask);
        }
    };

    const tranferGuideline = (arr) => {
        let result = '';
        for (let index = 0; index < arr.length; index++) {
            const element = arr[index];
            result += element.guideName + '|||';
        }
        return result;
    };

    const viewGroupTask = (groupTask) => {
        setGroupTask({ ...groupTask });
        setGroupTaskViewDialog(true);
    };

    const editGroupTask = (groupTask) => {
        // groupTask.startDate = new Date(groupTask.startDate);
        setGroupTask({ ...groupTask });
        setGroupTaskDialog(true);
        setFileTask('');
        setFileGuideline('');
    };

    const confirmDeleteGroupTask = (groupTask) => {
        setGroupTask(groupTask);
        setDeleteGroupTaskDialog(true);
    };

    const emptyTemplate = () => {
        return (
            <div className="flex align-items-center flex-column">
                <i
                    className="pi pi-image mt-3 p-5"
                    style={{
                        fontSize: '3.3em',
                        borderRadius: '50%',
                        backgroundColor: '#f8f9fa',
                        color: '#dee2e6',
                    }}
                ></i>
                <span style={{ fontSize: '1.6em', color: '#dee2e6' }} className="my-5">
                    Kéo Và Thả Ảnh Tại Đây
                </span>
            </div>
        );
    };

    const deleteGroupTask = () => {
        let ids = [groupTask.id];
        dispatch(deleteGroupTaskss(ids));
        groupTask.status = 'Dừng';
        let _groupTasks = groupTasks.filter((val) => (val.id !== groupTask.id ? val : groupTask));
        setGroupTasks(_groupTasks);
        setDeleteGroupTaskDialog(false);
        setGroupTask(emptyGroupTask);
        toast.current.show({ severity: 'success', summary: 'Thành công', detail: 'Cập nhật thành công!', life: 3000 });
    };

    const findIndexById = (id) => {
        let index = -1;
        for (let i = 0; i < groupTasks.length; i++) {
            if (groupTasks[i].id === id) {
                index = i;
                break;
            }
        }

        return index;
    };

    const confirmDeleteSelected = () => {
        if (selectedGroupTasks.length === 1) {
            setGroupTask(selectedGroupTasks[0]);
            setDeleteGroupTaskDialog(true);
        } else {
            setDeleteGroupTasksDialog(true);
        }
    };

    const deleteSelectedGroupTasks = () => {
        let ids = [];
        for (let index = 0; index < selectedGroupTasks.length; index++) {
            const element = selectedGroupTasks[index];
            ids.push(element.id);
        }
        dispatch(deleteGroupTaskss(ids));
        let _groupTasks = [...groupTasks];
        for (let index = 0; index < selectedGroupTasks.length; index++) {
            const element = selectedGroupTasks[index];
            element.status = 'Dừng';
            // _groupTasks[ids[index]] = element;

            const i = findIndexById(ids[index]);
            _groupTasks[i] = element;
        }
        // let _groupTasks = groupTasks.filter((val) => !selectedGroupTasks.includes(val));
        setGroupTasks(_groupTasks);
        setDeleteGroupTasksDialog(false);
        setSelectedGroupTasks(null);
        toast.current.show({ severity: 'success', summary: 'Thành công', detail: 'Cập nhật thành công!', life: 3000 });
    };

    const onDropdownChange = (e, name) => {
        let _groupTask = { ...groupTask };
        _groupTask[`${name}`] = e.value;
        setGroupTask(_groupTask);
    };

    const onCalendarChange = (e, name) => {
        let _groupTask = { ...groupTask };
        if (e.value.getDate() < 10) {
            _groupTask[`${name}`] = e.value.getFullYear() + '-' + (e.value.getMonth() + 1) + '-0' + e.value.getDate();
        } else {
            _groupTask[`${name}`] = e.value.getFullYear() + '-' + (e.value.getMonth() + 1) + '-' + e.value.getDate();
        }
        setGroupTask(_groupTask);
    };

    const onStatusChange = (e) => {
        let _groupTask = { ...groupTask };
        _groupTask['status'] = e.value;
        setGroupTask(_groupTask);
    };

    const onInputChange = (e, name) => {
        const val = (e.target && e.target.value) || '';
        let _groupTask = { ...groupTask };
        _groupTask[`${name}`] = val;

        setGroupTask(_groupTask);
    };

    const onGlobalFilterChange = (e) => {
        const value = e.target.value;
        let _filters = { ...filters };
        _filters['global'].value = value;

        setFilters(_filters);
        setGlobalFilter(value);
    };

    const uploadFileTask = (event) => {
        setFileTask(event.files[0]);
    };

    const uploadFileGuideline = (event) => {
        setFileGuideline(event.files[0]);
        groupTask.guidelineChange = true;
    };

    const removeFileGuideline = () => {
        setFileGuideline('');
        groupTask.guidelineChange = false;
    };

    const removeFileTask = () => {
        setFileTask('');
    };

    const leftToolbarTemplate = () => {
        return (
            <React.Fragment>
                <Button
                    label="Xóa"
                    icon="pi pi-trash"
                    className="p-button-danger"
                    onClick={confirmDeleteSelected}
                    disabled={!selectedGroupTasks || !selectedGroupTasks.length}
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
                    icon="pi pi-eye"
                    className="p-button-rounded p-button-primary mr-2"
                    onClick={() => viewGroupTask(rowData)}
                />
                <Button
                    icon="pi pi-pencil"
                    className="p-button-rounded p-button-warning mr-2"
                    onClick={() => editGroupTask(rowData)}
                />
                <Button
                    icon="pi pi-trash"
                    className="p-button-rounded p-button-danger"
                    onClick={() => confirmDeleteGroupTask(rowData)}
                />
            </React.Fragment>
        );
    };

    const leftPagnitor = (
        <Button
            label="Xóa tất cả"
            className="p-button-text"
            style={{ margin: '0px', color: 'black', textDecoration: 'underline', cursor: 'pointer', fontSize: '12px' }}
            onClick={confirmDeleteSelected}
            disabled={!selectedGroupTasks || !selectedGroupTasks.length}
        />
    );

    const paginatorRight = <Button type="button" icon="pi pi-refresh" className="p-button-text" hidden />;

    const actionGuideTemplate = (rowData) => {
        return (
            <React.Fragment>
                <Button
                    icon="pi pi-trash"
                    className="p-button-rounded p-button-warning"
                    onClick={() => deleteGuideline(rowData)}
                />
            </React.Fragment>
        );
    };

    const deleteGuideline = (rowData) => {
        setGuideline(rowData);
        let _guidelines = groupTask.guideline.filter((val) => val.guideNo !== guideLine.guideNo);
        let i = 1;
        for (let index = 0; index < _guidelines.length; index++) {
            _guidelines[index].guideNo = i++;
        }
        groupTask.guideline = _guidelines;
        setGuideline(emptyGuideline);
    };

    const cellEditor = (options) => {
        return textEditor(options);
    };

    const textEditor = (options) => {
        return <InputText type="text" value={options.value} onChange={(e) => options.editorCallback(e.target.value)} />;
    };

    const onCellEditComplete = (e) => {
        let { rowData, newValue, field, originalEvent: event } = e;
        if (newValue.trim().length > 0) {
            rowData[field] = newValue;
            groupTask.guidelineChange = true;
        } else event.preventDefault();
    };

    const header = (
        <div className="table-header">
            {/* <h3 className="mx-0 my-1">Quản lý nhiệm vụ nhóm</h3> */}
            <span className="p-input-icon-left">
                <i className="pi pi-search" />
                <InputText value={globalFilter} onChange={onGlobalFilterChange} placeholder="Tìm kiếm" />
            </span>
            <Button
                label="Thêm nhiệm vụ nhóm"
                style={{ fontSize: '12px' }}
                icon="pi pi-plus"
                className="p-button-success mr-2"
                onClick={openNew}
            />
        </div>
    );

    const guideLineHeader = (
        <div className="table-header">
            <div className="mx-0 my-1">
                <Button
                    label="Thêm"
                    icon="pi pi-plus"
                    className="p-button-success mr-2"
                    style={{ width: 'auto' }}
                    onClick={addNewGuideline}
                />
            </div>
        </div>
    );
    const groupTaskViewDialogFooter = (
        <React.Fragment>
            <Button label="OK" icon="pi pi-check" className="p-button-text" onClick={hideViewDialog} />
        </React.Fragment>
    );
    const groupTaskDialogFooter = (
        <React.Fragment>
            <Button label="Hủy bỏ" icon="pi pi-times" className="p-button-text" onClick={hideDialog} />
            <Button label="Lưu" icon="pi pi-check" className="p-button-text" onClick={saveGroupTask} />
        </React.Fragment>
    );
    const deleteGroupTaskDialogFooter = (
        <React.Fragment>
            <Button label="Hủy" icon="pi pi-times" className="p-button-text" onClick={hideDeleteGroupTaskDialog} />
            <Button label="Xác nhận" icon="pi pi-check" className="p-button-text" onClick={deleteGroupTask} />
        </React.Fragment>
    );
    const deleteGroupTasksDialogFooter = (
        <React.Fragment>
            <Button label="Hủy" icon="pi pi-times" className="p-button-text" onClick={hideDeleteGroupTasksDialog} />
            <Button label="Xác nhận" icon="pi pi-check" className="p-button-text" onClick={deleteSelectedGroupTasks} />
        </React.Fragment>
    );

    return (
        <div className="group-task-manager">
            <div className="datatable-crud-demo">
                <Toast ref={toast} />

                <div className="group-task-card">
                    {/* <Toolbar className="mb-4" left={leftToolbarTemplate}></Toolbar> */}

                    <DataTable
                        ref={dt}
                        value={groupTasks}
                        selection={selectedGroupTasks}
                        onSelectionChange={(e) => setSelectedGroupTasks(e.value)}
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
                            style={{ minWidth: '3rem', alignItem: 'left' }}
                        ></Column>
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

                {/* dialog view */}
                <Dialog
                    visible={groupTaskViewDialog}
                    style={{ width: '450px' }}
                    draggable={false}
                    header="Chi tiết"
                    modal
                    className="p-fluid"
                    footer={groupTaskViewDialogFooter}
                    onHide={hideViewDialog}
                >
                    {groupTask.taskImage && (
                        <Image
                            src={`data:image/jpeg;base64,${groupTask.taskImage}`}
                            onError={(e) => (e.target.src = './../../../../assets/image/softskill.jpg')}
                            preview
                            alt={groupTask.taskImage}
                            imageClassName="groupTask-image block m-auto pb-3"
                            style={{ width: '150px', height: '150px' }}
                            imageStyle={{ width: '-webkit-fill-available', height: '-webkit-fill-available' }}
                        />
                    )}
                    <div className="field">
                        <label htmlFor="taskContent">Nội dung nhiệm vụ</label>
                        <InputTextarea id="taskContent" value={groupTask.taskContent} readOnly rows={3} cols={20} />
                    </div>
                    <div className="field">
                        <label htmlFor="guideline" className="mt-3">
                            Hướng dẫn
                        </label>
                        <DataTable
                            value={groupTask.guideline}
                            scrollable
                            rows={3}
                            emptyMessage="Không có hướng dẫn cho nhiệm vụ này"
                        >
                            <Column field="guideNo" header="Bước" style={{ minWidth: '4rem' }}></Column>
                            <Column field="guideName" header="Nội dung" style={{ minWidth: '20rem' }}></Column>
                        </DataTable>
                    </div>
                    <div className="field">
                        <span>
                            <label htmlFor="doingNumber" className="mr-3 mt-3">
                                Số người đang làm:{' '}
                            </label>
                            <label className="font-weight-bold"> {groupTask.doingNumber}</label>
                        </span>
                    </div>
                    <div className="field">
                        <span>
                            <label htmlFor="doneNumber" className="mr-3 mt-3">
                                Số người hoàn thành:{' '}
                            </label>
                            <label className="font-weight-bold"> {groupTask.doneNumber}</label>
                        </span>
                    </div>
                    <div className="field">
                        <span>
                            <label htmlFor="favoriteNumber" className="mr-3 mt-3">
                                Số người yêu thích:{' '}
                            </label>
                            <label className="font-weight-bold"> {groupTask.favoriteNumber}</label>
                        </span>
                    </div>
                </Dialog>

                {/* add, update */}
                <Dialog
                    visible={groupTaskDialog}
                    style={{ width: '600px' }}
                    draggable={false}
                    header="Thông tin nhiệm vụ nhóm"
                    modal
                    className="p-fluid"
                    footer={groupTaskDialogFooter}
                    onHide={hideDialog}
                >
                    <div className="field">
                        <label htmlFor="taskName" className="font-weight-bold">
                            Tên nhiệm vụ <span className="text-danger"> *</span>
                        </label>
                        <InputText
                            id="taskName"
                            value={groupTask.taskName}
                            onChange={(e) => onInputChange(e, 'taskName')}
                            required
                            maxLength={50}
                            autoFocus
                            className={classNames({ 'p-invalid': submitted && !groupTask.taskName })}
                        />
                        {submitted && !groupTask.taskName.trim() && (
                            <small className="p-error">Tên nhiệm vụ không được để trống</small>
                        )}
                    </div>

                    <div className="field">
                        <label htmlFor="softSkillId" className="mt-3 font-weight-bold">
                            Loại kỹ năng <span className="text-danger"> *</span>
                        </label>
                        <Dropdown
                            value={groupTask.softSkillId}
                            options={softSkills}
                            onChange={(e) => onDropdownChange(e, 'softSkillId')}
                            placeholder="Chọn 1 kỹ năng"
                        />
                        {submitted && groupTask.softSkillId.toString() === '-1' && (
                            <small className="p-error">Mục này không được để trống</small>
                        )}
                    </div>

                    <div className="field">
                        <label htmlFor="taskContent" className="mt-3 font-weight-bold">
                            Nội dung nhiệm vụ <span className="text-danger"> *</span>
                        </label>
                        <InputTextarea
                            id="taskContent"
                            value={groupTask.taskContent}
                            onChange={(e) => onInputChange(e, 'taskContent')}
                            required
                            maxLength={700}
                            rows={3}
                            cols={20}
                        />
                        {submitted && !groupTask.taskContent.trim() && (
                            <small className="p-error">Nội dung nhiệm vụ không được để trống</small>
                        )}
                    </div>

                    <div className="field">
                        <label htmlFor="requiredTask" className="mt-3 font-weight-bold">
                            Nhiệm vụ cá nhân cần hoàn thành
                        </label>
                        <Dropdown
                            value={groupTask.requiredTask}
                            options={personalTasks}
                            onChange={(e) => onDropdownChange(e, 'requiredTask')}
                            placeholder="Chọn 1 nhiệm vụ"
                        />
                    </div>

                    <div className="field">
                        <label htmlFor="taskImage" className="mt-3 font-weight-bold">
                            Ảnh nhiệm vụ<span className="text-danger mr-2"> *</span>
                        </label>
                        {groupTask.taskImage && (
                            <Image
                                src={`data:image/jpeg;base64,${groupTask.taskImage}`}
                                onError={(e) => (e.target.src = './../../../../assets/image/softskill.jpg')}
                                preview
                                downloadable
                                alt="Image"
                                imageClassName="group-image block m-auto pb-3 pt-2"
                                style={{ width: '150px', height: '150px' }}
                                imageStyle={{ width: '-webkit-fill-available', height: '-webkit-fill-available' }}
                            />
                        )}
                        <FileUpload
                            name="taskImage"
                            onSelect={uploadFileTask}
                            onRemove={removeFileTask}
                            accept="image/*"
                            emptyTemplate={emptyTemplate}
                            maxFileSize={1000000}
                            invalidFileSizeMessageSummary="File quá lớn "
                            invalidFileSizeMessageDetail=" (> 1MB)"
                            chooseLabel="Chọn Ảnh Nhiệm Vụ"
                            uploadOptions={{ style: { display: 'none' } }}
                            cancelOptions={{ style: { display: 'none' } }}
                        />
                        {submitted && !fileTask && !groupTask.taskImage && (
                            <small className="p-error">Mục này không được để trống</small>
                        )}
                        {/* <input type="file" onChange={onSelectFile} />
                        {selectedFile && <Image src={preview} preview alt="Image Text" />}

                        <button onClick={uploadImg}>UPload</button> */}
                    </div>

                    <div className="field">
                        <label htmlFor="guideline" className="mt-3 font-weight-bold">
                            Hướng dẫn
                        </label>
                        <DataTable
                            ref={dt}
                            dataKey="guideNo"
                            scrollable
                            rows={10}
                            value={groupTask.guideline}
                            header={guideLineHeader}
                            emptyMessage="Không có hướng dẫn cho nhiệm vụ này"
                        >
                            <Column field="guideNo" header="Bước" style={{ minWidth: '4rem' }}></Column>
                            <Column
                                field="guideName"
                                header="Nội dung"
                                style={{ minWidth: '20rem' }}
                                editor={(options) => cellEditor(options)}
                                onCellEditComplete={onCellEditComplete}
                            />
                            <Column body={actionGuideTemplate} exportable={false} style={{ minWidth: '3rem' }}></Column>
                        </DataTable>
                    </div>

                    <div className="field">
                        <label htmlFor="guidelineImage" className="mt-3 font-weight-bold">
                            Ảnh hướng dẫn <span className="text-danger mr-2"> *</span>
                        </label>
                        {groupTask.guidelineImage && (
                            <Image
                                src={`data:image/jpeg;base64,${groupTask.guidelineImage}`}
                                onError={(e) => (e.target.src = './../../../../assets/image/softskill.jpg')}
                                preview
                                downloadable
                                alt="Image"
                                imageClassName="group-image block m-auto pb-3 pt-2"
                                style={{ width: '150px', height: '150px' }}
                                imageStyle={{ width: '-webkit-fill-available', height: '-webkit-fill-available' }}
                            />
                        )}
                        <FileUpload
                            name="guidelineImage"
                            accept="image/*"
                            onSelect={uploadFileGuideline}
                            onRemove={removeFileGuideline}
                            chooseLabel="Chọn Ảnh Hướng Dẫn"
                            maxFileSize={1000000}
                            invalidFileSizeMessageSummary="File quá lớn "
                            invalidFileSizeMessageDetail=" (> 1MB)"
                            emptyTemplate={emptyTemplate}
                            uploadOptions={{ style: { display: 'none' } }}
                            cancelOptions={{ style: { display: 'none' } }}
                        />
                        {submitted && !fileGuideline && !groupTask.guidelineImage && groupTask.guideline.length > 0 && (
                            <small className="p-error">Mục này không được để trống</small>
                        )}
                    </div>
                    <div className="date-pick" style={{ display: 'flex' }}>
                        <div className="field" style={{ width: '87%' }}>
                            <label htmlFor="startDate" className="mt-3 font-weight-bold">
                                Ngày bắt đầu <span className="text-danger"> *</span>
                            </label>
                            <Calendar
                                dateFormat="dd/mm/yy"
                                value={new Date(groupTask.startDate)}
                                minDate={new Date()}
                                showIcon={true}
                                onChange={(e) => onCalendarChange(e, 'startDate')}
                            ></Calendar>
                            {submitted && !groupTask.startDate && (
                                <small className="p-error">Ngày bắt đầu không được để trống</small>
                            )}
                        </div>

                        <div className="field" style={{ marginLeft: '9%', width: '87%' }}>
                            <label htmlFor="endDate" className="mt-3 font-weight-bold">
                                Ngày kết thúc <span className="text-danger"> *</span>
                            </label>
                            <Calendar
                                dateFormat="dd/mm/yy"
                                value={new Date(groupTask.endDate)}
                                minDate={new Date()}
                                showIcon={true}
                                onChange={(e) => onCalendarChange(e, 'endDate')}
                            ></Calendar>
                            {submitted && !groupTask.endDate && (
                                <small className="p-error">Ngày kết thúc không được để trống</small>
                            )}
                            {submitted &&
                                groupTask.endDate &&
                                groupTask.startDate &&
                                new Date(groupTask.endDate) < new Date(groupTask.startDate) && (
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
                                    checked={groupTask.status === '1'}
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
                                    checked={groupTask.status === '0'}
                                />
                                <label htmlFor="category2" className="text-danger ml-2 mt-2">
                                    Dừng
                                </label>
                            </div>
                        </div>
                    </div>
                </Dialog>

                <Dialog
                    visible={deleteGroupTaskDialog}
                    style={{ width: '450px' }}
                    draggable={false}
                    header="Xóa?"
                    modal
                    footer={deleteGroupTaskDialogFooter}
                    onHide={hideDeleteGroupTaskDialog}
                >
                    <div className="confirmation-content" style={{ fontFamily: 'Helvetica', fontSize: '1.2rem' }}>
                        <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                        {groupTask && (
                            <span>
                                Việc xóa nhiệm vụ <b>{groupTask.taskName}</b> sẽ đồng nghĩa với việc chuyển trạng thái
                                sang <label className="text-danger font-weight-bold">Dừng </label>. <br />
                                <label className="ml-5">Bạn có chắc chắn?</label>
                            </span>
                        )}
                    </div>
                </Dialog>

                <Dialog
                    visible={deleteGroupTasksDialog}
                    style={{ width: '450px' }}
                    draggable={false}
                    header="Xóa?"
                    modal
                    footer={deleteGroupTasksDialogFooter}
                    onHide={hideDeleteGroupTasksDialog}
                >
                    <div className="confirmation-content" style={{ fontFamily: 'Helvetica', fontSize: '1.2rem' }}>
                        <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                        {groupTask && (
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

export default GroupTask;
