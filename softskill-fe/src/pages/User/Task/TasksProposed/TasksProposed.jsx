import React, { useState, useEffect } from "react";
import { FilterMatchMode, FilterOperator } from "primereact/api";
import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";
import { InputText } from "primereact/inputtext";
import "./TasksProposed.scss";
import { useDispatch, useSelector } from "react-redux";
import { getTasksProposed } from "../../../../redux/thunks/task-proposed-thunks";
import { Dialog } from "primereact";
import { UilTimes } from "@iconscout/react-unicons";

const TasksProposed = () => {
    const dispatch = useDispatch();
    const tasksProposedState = useSelector((state) => state.taskProposedData.tasks);
    const type = useSelector((state) => state.taskProposedData.type);

    // State
    const [displayPopup, setDisplayPopup] = useState(false);
    const [taskProposeById, setTaskProposeById] = useState([]);
    const [globalFilterValue, setGlobalFilterValue] = useState("");
    const [tasksProposed, setTasksProposed] = useState([]);
    const [selectedItem, setSelectedItem] = useState(0)
    const [filters, setFilters] = useState({
        "global": { value: null, matchMode: FilterMatchMode.CONTAINS },
        'softSkillName': { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
        "content.dateCreate": { value: null, matchMode: FilterMatchMode.STARTS_WITH },
        'status': { operator: FilterOperator.OR, constraints: [{ value: null, matchMode: FilterMatchMode.EQUALS }] }
    });
    // useEffect
    useEffect(() => {
        if (type === 'load') {
            setTasksProposed(tasksProposedState?.item)
        } else if (type === 'add') {
            let _tasksProposed = [...tasksProposed];
            _tasksProposed.push(tasksProposedState?.item);

            setTasksProposed(_tasksProposed);
        }
    }, [tasksProposedState, type]);

    useEffect(() => {
        dispatch(getTasksProposed())
    }, [dispatch]);
    

    // filter
    const onGlobalFilterChange = (e) => {
        const value = e.target.value;
        let _filters = { ...filters };
        _filters["global"].value = value;

        setFilters(_filters);
        setGlobalFilterValue(value);
    };

    // popup
    const dialogFuncMap = {
        displayPopup: setDisplayPopup,
    };

    const onClick = (name) => {
        dialogFuncMap[`${name}`](true);
    };

    const onHide = (name) => {
        dialogFuncMap[`${name}`](false);
    };

    // action
    const showTaskProposedById = (id) => {
        setTaskProposeById(tasksProposed.filter(item => item?.id === id))
        onClick("displayPopup")
        setSelectedItem(id)
    }

    const renderHeader = () => {
        return (
            <div className="flex justify-content-between align-items-center">
                <span className="p-input-icon-left">
                    <i className="pi pi-search" />
                    <InputText
                        maxLength={256}
                        value={globalFilterValue}
                        onChange={onGlobalFilterChange}
                        placeholder="Tìm kiếm"
                    />
                </span>
            </div>
        );
    };

    const statusBodyTemplate = (rowData) => {
        return (
            <span
                className={`product-badge status-${rowData?.status?.toLowerCase()} ${rowData?.status === "Đã duyệt"
                    ? "text-success"
                    : rowData?.status === "Chờ duyệt"
                        ? "text-primary"
                        : rowData?.status === "Đã huỷ" ? "text-danger" : ""
                    }`}>
                {rowData?.status}
            </span>
        );
    };
    const header = renderHeader();

    const countryBodyTemplate = (rowData) => {
        return (
            <React.Fragment>
                <div>
                    {rowData.content.content}
                    <small className="float-right" style={{ fontSize: "1.2rem" }}>{rowData.content.dateCreate}</small>
                </div>
            </React.Fragment>
        );
    };

    // layout
    const layoutPopup = (
        <Dialog
            className="layout-popup"
            draggable={false}
            dismissableMask={true}
            closeOnEscape={true}
            visible={displayPopup}
            style={{ width: "700px", height: "500px" }}
            onHide={() => onHide("displayPopup")}>
            <div className="popup-content">
                {taskProposeById?.map((item, index) => (
                    <div key={index}>
                        <div className="title d-flex align-items-center">
                            <span className="title-content mx-auto">
                                <span className="title-name ">Nhiệm vụ đề xuất</span>
                            </span>
                            <span
                                style={{ cursor: "pointer" }}
                                className="icon p-2 pr-0 rounded-circle bg-light"
                                onClick={() => onHide("displayPopup")}>
                                <UilTimes />{" "}
                            </span>
                        </div>
                        <div className="row task-content">
                            <div>
                                <span className="col-sm-3">
                                    Kỹ năng :
                                </span>
                                <span className="col-sm-9" style={{paddingLeft: '13px'}}>
                                    {item?.softSkillName}
                                </span>
                            </div>
                        </div>
                        <div className="row content-guideline mt-2 mb-3">
                            <div>
                                <span className="col-sm-3">
                                    Nhiệm vụ :
                                </span>
                                <span className="col-sm-9 p-0"> {item?.content?.content}</span>

                            </div>
                        </div>
                        {item.taskImage !== null ?
                        <div className="img-task mt-3">
                            <div className="d-flex justify-content-center align-content-center">
                                <img
                                    style={{ height: "300px", width: "400px", borderRadius: "6px" }}
                                    src={`data:image/jpeg;base64,${item?.taskImage}` || require("../../../../assets/image/softskill.jpg")}
                                    alt="ảnh nhiệm vụ"
                                    about="Ảnh nhiệm vụ"
                                />
                            </div>
                        </div> : <div className="img-task mt-3"><small>Không có ảnh nhiệm vụ</small></div>}
                        {item.guideline !== "" ?
                            <div className="row task-content mt-3">
                                <div>
                                    <span className="col-sm-3">
                                        Hướng dẫn :
                                    </span>
                                    <span className="col-sm-9 p-0">
                                        {item?.guideline}
                                    </span>
                                </div>
                            </div> : null}
                        {item.guidelineImage !== null ?
                            <div className="img-task mt-3">
                                <div className="d-flex justify-content-center align-content-center">
                                    <img
                                        style={{ height: "300px", width: "400px", borderRadius: "6px" }}
                                        src={`data:image/jpeg;base64,${item?.guidelineImage}` || require("../../../../assets/image/softskill.jpg")}
                                        alt="ảnh hướng dẫn"
                                        about="Ảnh hướng dẫn"
                                    />
                                </div>
                            </div> : <div className="img-task mt-3"><small>Không có ảnh hướng dẫn</small></div>}
                    </div>
                ))}
            </div>
        </Dialog>
    );

    return (
        <React.Fragment>
            {layoutPopup}
            <DataTable
                value={tasksProposed}
                paginator
                header={header}
                rows={9}
                paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink"
                dataKey="id"
                rowHover
                filters={filters}
                responsiveLayout="scroll"
                selection={selectedItem} onSelectionChange={e => showTaskProposedById(e.value?.id)}
                globalFilterFields={['name', 'country.name', 'country.date', 'status']}
                emptyMessage="Bạn chưa đề xuất nhiệm vụ nào"
            >
                <Column selectionMode="single" hidden={true} headerStyle={{ width: '3em' }}></Column>
                <Column
                    className="font-size-14"
                    headerStyle={{ width: "15rem" }}
                    field="softSkillName"
                    header="Kỹ năng mềm"
                    sortable
                    style={{ minWidth: "14rem" }}
                />
                <Column
                    className="font-size-14"
                    headerStyle={{ width: "70rem" }}
                    sortField="content.dateCreate"
                    body={countryBodyTemplate}
                    header="Nhiệm vụ"
                    sortable
                    style={{ minWidth: "14rem" }}
                />
                <Column
                    className="font-size-14"
                    style={{paddingLeft: '13px' }}
                    headerStyle={{ width: "15rem" }}
                    field="status"
                    header="Trạng thái"
                    sortable
                    body={statusBodyTemplate}
                />
            </DataTable>
        </React.Fragment>
    );
};

export default TasksProposed;
