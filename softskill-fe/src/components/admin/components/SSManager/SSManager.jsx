import React, { useState, useEffect, useRef } from 'react';
import { FilterMatchMode } from 'primereact/api';
import { classNames } from 'primereact/utils';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Toast } from 'primereact/toast';
import { Button } from 'primereact/button';
import { Toolbar } from 'primereact/toolbar';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { RadioButton } from 'primereact/radiobutton';
import './SSManager.scss';
import { useDispatch, useSelector } from 'react-redux';
import {
    addSoftSkill,
    getSoftSkill,
    updateSoftSkill,
    deleteSoftSkills,
} from '../../../../redux/thunks/admin/softskill-thunks';
function SSManager() {
    const dispatch = useDispatch();
    let emptySoftSkill = {
        id: -1,
        no: -1,
        name: '',
        numberRoom: 0,
        numberTask: 0,
        status: 'Hoạt động',
        createdDate: '',
        createdName: '',
    };

    const [softskills, setSoftSkills] = useState(null);
    const [softskillDialog, setSoftSkillDialog] = useState(false);
    const [deleteSoftSkillDialog, setDeleteSoftSkillDialog] = useState(false);
    const [deleteSoftSkillsDialog, setDeleteSoftSkillsDialog] = useState(false);
    const [softSkill, setSoftSkill] = useState(emptySoftSkill);
    const [selectedSoftSkill, setSelectedSoftSkill] = useState(null);
    const [submitted, setSubmitted] = useState(false);
    const [globalFilter, setGlobalFilter] = useState('');
    const toast = useRef(null);
    const dt = useRef(null);
    const [filters, setFilters] = useState({
        global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    });

    useEffect(() => {
        dispatch(getSoftSkill());
    }, []);

    const ss = useSelector((state) => {
        return state.softskillData.softskills;
    });

    const ssType = useSelector((state) => {
        return state.softskillData.ssType;
    });

    useEffect(() => {
        if (ssType === 'load') {
            setSoftSkills(ss);
        } else if (ssType === 'add') {
            let _softskills = [...softskills];
            let _softskill = ss;
            _softskill.no = _softskills.length + 1;
            _softskills.push(_softskill);
            setSoftSkills(_softskills);
        }
    }, [ss, ssType]);

    const openNew = () => {
        setSoftSkill(emptySoftSkill);
        setSubmitted(false);
        setSoftSkillDialog(true);
    };

    const hideDialog = () => {
        setSubmitted(false);
        setSoftSkillDialog(false);
    };

    const hideDeleteSoftSkillDialog = () => {
        setDeleteSoftSkillDialog(false);
    };

    const hideDeleteSoftSkillsDialog = () => {
        setDeleteSoftSkillsDialog(false);
    };

    const statusBodyTemplate = (rowData) => {
        return (
            <span
                className={`product-badge status-${rowData.status.toLowerCase()} ${
                    rowData.status === 'Hoạt động' ? 'text-success' : 'text-danger'
                }`}
            >
                {rowData.status}
            </span>
        );
    };

    const saveSoftSkill = () => {
        setSubmitted(true);
        if (softSkill.name.trim()) {
            let _softskills = [...softskills];
            let _softskill = { ...softSkill };
            if (_softskill.id !== -1) {
                dispatch(
                    updateSoftSkill({
                        softSkillId: _softskill.id,
                        softSkillName: _softskill.name,
                        status: _softskill.status,
                    }),
                );

                const index = findIndexById(_softskill.id);
                _softskills[index] = _softskill;
                toast.current.show({
                    severity: 'success',
                    summary: 'Thành công',
                    detail: 'Cập nhật thành công!',
                    life: 3000,
                });
            } else {
                // _softskills.push(_softskill);
                dispatch(
                    addSoftSkill({
                        softSkillName: _softskill.name,
                        status: _softskill.status,
                    }),
                );
                toast.current.show({
                    severity: 'success',
                    summary: 'Thành công',
                    detail: 'Thêm mới kỹ năng thành công!',
                    life: 3000,
                });
            }

            setSoftSkills(_softskills);
            setSoftSkillDialog(false);
            setSoftSkill(emptySoftSkill);
        }
    };

    const editSoftSkill = (softskill) => {
        setSoftSkill({ ...softskill });
        setSoftSkillDialog(true);
    };

    const confirmDeleteSoftSkill = (softskill) => {
        setSoftSkill(softskill);
        setDeleteSoftSkillDialog(true);
    };

    const deleteSoftSkill = () => {
        let ids = [softSkill.id];
        dispatch(deleteSoftSkills(ids));
        softSkill.status = 'Dừng';
        let _softskills = softskills.filter((val) => (val.id !== softSkill.id ? val : softSkill));

        setSoftSkills(_softskills);
        setDeleteSoftSkillDialog(false);
        setSoftSkill(emptySoftSkill);
        toast.current.show({ severity: 'success', summary: 'Thành công', detail: 'Cập nhật thành công!', life: 3000 });
    };

    const findIndexById = (id) => {
        let index = -1;
        for (let i = 0; i < softskills.length; i++) {
            if (softskills[i].id === id) {
                index = i;
                break;
            }
        }

        return index;
    };

    const confirmDeleteSelected = () => {
        if (selectedSoftSkill.length === 1) {
            setSoftSkill(selectedSoftSkill[0]);
            setDeleteSoftSkillDialog(true);
        } else {
            setDeleteSoftSkillsDialog(true);
        }
    };

    const onInputChange = (e, name) => {
        const val = (e.target && e.target.value) || '';
        let _softskill = { ...softSkill };
        _softskill[`${name}`] = val;

        setSoftSkill(_softskill);
    };

    const onStatusChange = (e) => {
        let _softskill = { ...softSkill };
        _softskill['status'] = e.value;
        setSoftSkill(_softskill);
    };

    const deleteSelectedSoftSkills = () => {
        let ids = [];
        for (let index = 0; index < selectedSoftSkill.length; index++) {
            const element = selectedSoftSkill[index];
            ids.push(element.id);
        }
        dispatch(deleteSoftSkills(ids));
        let _softskills = [...softskills];
        for (let index = 0; index < selectedSoftSkill.length; index++) {
            const element = selectedSoftSkill[index];
            element.status = 'Dừng';

            const i = findIndexById(ids[index]);
            _softskills[i] = element;
        }
        // let _softskills = softskills.filter((val) => !selectedSoftSkill.includes(val));
        setSoftSkills(_softskills);
        setDeleteSoftSkillsDialog(false);
        setSelectedSoftSkill(null);
        toast.current.show({ severity: 'success', summary: 'Thành công', detail: 'Cập nhật thành công!', life: 3000 });
    };

    const leftToolbarTemplate = () => {
        return (
            <React.Fragment>
                <Button
                    label="Xóa"
                    icon="pi pi-trash"
                    className="p-button-danger"
                    onClick={confirmDeleteSelected}
                    disabled={!selectedSoftSkill || !selectedSoftSkill.length}
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
            disabled={!selectedSoftSkill || !selectedSoftSkill.length}
        />
    );

    const paginatorRight = <Button type="button" icon="pi pi-refresh" className="p-button-text" hidden />;

    const actionBodyTemplate = (rowData) => {
        return (
            <React.Fragment>
                <Button
                    icon="pi pi-pencil"
                    className="p-button-rounded p-button-warning mr-2"
                    onClick={() => editSoftSkill(rowData)}
                />
                {/* <Button
                    icon="pi pi-trash"
                    className="p-button-rounded p-button-danger"
                    onClick={() => confirmDeleteSoftSkill(rowData)}
                /> */}
            </React.Fragment>
        );
    };

    const onGlobalFilterChange = (e) => {
        const value = e.target.value;
        let _filters = { ...filters };
        _filters['global'].value = value;

        setFilters(_filters);
        setGlobalFilter(value);
    };

    const header = (
        <div className="table-header">
            {/* <h5 className="mx-0 my-1">Quản lý kỹ năng</h5> */}
            <div className="flex justify-content-end">
                <span className="p-input-icon-left">
                    <i className="pi pi-search" />
                    <InputText value={globalFilter} onChange={onGlobalFilterChange} placeholder="Tìm kiếm" />
                </span>
            </div>
            <Button
                label="Thêm kỹ năng"
                icon="pi pi-plus"
                style={{ fontSize: '12px' }}
                className="p-button-success mr-2"
                onClick={openNew}
            />
        </div>
    );

    const softskillDialogFooter = (
        <React.Fragment>
            <Button label="Hủy" icon="pi pi-times" className="p-button-text" onClick={hideDialog} />
            <Button label="Lưu" icon="pi pi-check" className="p-button-text" onClick={saveSoftSkill} />
        </React.Fragment>
    );
    const deleteSoftSkillDialogFooter = (
        <React.Fragment>
            <Button label="Hủy" icon="pi pi-times" className="p-button-text" onClick={hideDeleteSoftSkillDialog} />
            <Button label="Xác nhận" icon="pi pi-check" className="p-button-text" onClick={deleteSoftSkill} />
        </React.Fragment>
    );
    const deleteSoftSkillsDialogFooter = (
        <React.Fragment>
            <Button label="Hủy" icon="pi pi-times" className="p-button-text" onClick={hideDeleteSoftSkillsDialog} />
            <Button label="Xác nhận" icon="pi pi-check" className="p-button-text" onClick={deleteSelectedSoftSkills} />
        </React.Fragment>
    );

    return (
        <div className="softskill-manager">
            <div className="datatable-crud-demo">
                <Toast ref={toast} />

                <div className="softskill-card">
                    {/* <Toolbar className="mb-4" left={leftToolbarTemplate}></Toolbar> */}

                    <DataTable
                        ref={dt}
                        value={softskills}
                        selection={selectedSoftSkill}
                        onSelectionChange={(e) => setSelectedSoftSkill(e.value)}
                        dataKey="id"
                        paginator
                        rows={8}
                        paginatorLeft={leftPagnitor}
                        paginatorRight={paginatorRight}
                        paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                        currentPageReportTemplate="Tổng số: {totalRecords} bản ghi"
                        rowsPerPageOptions={[10, 20, 50]}
                        header={header}
                        filters={filters}
                        responsiveLayout="scroll"
                        emptyMessage="Chưa có dữ liệu"
                        style={{ fontSize: '12px' }}
                    >
                        <Column selectionMode="multiple" headerStyle={{ width: '3rem' }} exportable={false}></Column>
                        <Column field="id" header="ID" hidden></Column>
                        <Column field="no" header="STT" sortable style={{ minWidth: '3rem' }}></Column>
                        <Column field="name" header="Tên kỹ năng" sortable style={{ minWidth: '12rem' }}></Column>
                        <Column
                            field="numberRoom"
                            header="Số phòng hoạt động"
                            sortable
                            style={{ minWidth: '6rem' }}
                        ></Column>
                        <Column
                            field="numberTask"
                            header="Số nhiệm vụ hoàn thành"
                            sortable
                            style={{ minWidth: '6rem' }}
                        ></Column>
                        <Column
                            field="status"
                            header="Trạng thái"
                            body={statusBodyTemplate}
                            sortable
                            style={{ minWidth: '8rem' }}
                        ></Column>
                        <Column field="createdDate" header="Ngày tạo" sortable style={{ minWidth: '8rem' }}></Column>
                        <Column field="createdName" header="Người tạo" sortable style={{ minWidth: '8rem' }}></Column>
                        <Column
                            header="Thao tác"
                            body={actionBodyTemplate}
                            exportable={false}
                            style={{ minWidth: '8rem' }}
                        ></Column>
                    </DataTable>
                </div>

                <Dialog
                    visible={softskillDialog}
                    style={{ width: '450px' }}
                    draggable={false}
                    header="Thông tin kỹ năng mềm"
                    modal
                    className="p-fluid"
                    footer={softskillDialogFooter}
                    onHide={hideDialog}
                >
                    <div className="field">
                        <label htmlFor="name" className="font-weight-bold">
                            Tên kỹ năng
                        </label>
                        <InputText
                            id="name"
                            value={softSkill.name}
                            onChange={(e) => onInputChange(e, 'name')}
                            required
                            maxLength={50}
                            autoFocus
                            className={classNames({ 'p-invalid': submitted && !softSkill.name })}
                        />
                        {submitted && !softSkill.name.trim() && (
                            <small className="p-error">Tên không được để trống</small>
                        )}
                    </div>

                    <div className="field">
                        <label className="mb-3 mt-3 font-weight-bold">Trạng thái</label>
                        <div className="formgrid grid">
                            <div className="field-radiobutton col-6 mb-2">
                                <RadioButton
                                    inputId="status1"
                                    name="Status"
                                    value="Hoạt động"
                                    onChange={onStatusChange}
                                    checked={softSkill.status === 'Hoạt động'}
                                />
                                <label htmlFor="category1" className="text-success ml-2">
                                    Hoạt động
                                </label>
                            </div>
                            <div className="field-radiobutton col-6">
                                <RadioButton
                                    inputId="status2"
                                    name="Status"
                                    value="Dừng"
                                    onChange={onStatusChange}
                                    checked={softSkill.status === 'Dừng'}
                                />
                                <label htmlFor="category2" className="text-danger ml-2 mt-2">
                                    Dừng
                                </label>
                                {softSkill.status === 'Dừng' && (
                                    <small className="p-error">
                                        <br />
                                        Tất cả các Nhiệm vụ, Phòng họp mặt liên quan đến kỹ năng <b>
                                            {softSkill.name}
                                        </b>{' '}
                                        đều sẽ bị vô hiệu hóa. Bạn nên chắc chắn về điều đó
                                    </small>
                                )}
                            </div>
                        </div>
                    </div>
                </Dialog>

                <Dialog
                    visible={deleteSoftSkillDialog}
                    style={{ width: '450px' }}
                    draggable={false}
                    header="Xóa?"
                    modal
                    footer={deleteSoftSkillDialogFooter}
                    onHide={hideDeleteSoftSkillDialog}
                >
                    <div className="confirmation-content" style={{ fontFamily: 'Helvetica', fontSize: '1.2rem' }}>
                        <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                        {softSkill && (
                            <span>
                                Việc xóa kỹ năng <b>{softSkill.name}</b> sẽ đồng nghĩa với việc chuyển trạng thái sang{' '}
                                <label className="text-danger font-weight-bold">Dừng </label>. <br />
                                <label className="ml-5">Bạn có chắc chắn?</label>
                            </span>
                        )}
                    </div>
                </Dialog>

                <Dialog
                    visible={deleteSoftSkillsDialog}
                    style={{ width: '450px' }}
                    draggable={false}
                    header="Xoá?"
                    modal
                    footer={deleteSoftSkillsDialogFooter}
                    onHide={hideDeleteSoftSkillsDialog}
                >
                    <div className="confirmation-content" style={{ fontFamily: 'Helvetica', fontSize: '1.2rem' }}>
                        <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                        {softSkill && (
                            <span>
                                Việc xóa các kỹ năng trên sẽ đồng nghĩa với việc chuyển trạng thái sang{' '}
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

export default SSManager;
