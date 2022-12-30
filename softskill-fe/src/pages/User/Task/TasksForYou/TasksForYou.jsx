import React, { useEffect, useRef, useState } from "react";
import "./TasksForYou.scss";
import {
    UilPlus,
    UilCircle,
    UilEllipsisV,
    UilEdit,
    UilTrashAlt,
} from "@iconscout/react-unicons";
import { Column, DataTable, FilterMatchMode, InputText } from "primereact";
import { UisCheckCircle } from "@iconscout/react-unicons-solid";
import { useDispatch, useSelector } from "react-redux";
import {
    addTask,
    deleteTask,
    editTask,
    formReset,
    getTasks,
} from "../../../../redux/thunks/task-for-you-thunks";

let countId = 0;
let idMapping = 0;
let nameMapping = "";
let nameMappingBefore = "";

const TasksForYou = () => {
    const dispatch = useDispatch();
    const isLoaded = useSelector((state) => state.taskForYouData.isLoaded);
    const tasks = useSelector((state) => state.taskForYouData.tasks);

    const [checkOpen, setCheckOpen] = useState("doing");
    const [inputTask, setInputTask] = useState("");
    const [focusTask, setFocusTask] = useState(false);
    const [listTaskDoing, setListTaskDoing] = useState([]);
    const [listTaskDone, setListTaskDone] = useState([]);
    const [taskIdEdit, setEditIdEdit] = useState(0);
    const [showIdEdit, setShowIdEdit] = useState(0);
    const [taskIdFocus, setEditIdFocus] = useState(0);

    useEffect(() => {
        dispatch(getTasks());
    }, [dispatch]);

    useEffect(() => {
        setListTaskDoing(tasks?.item?.NotCompleted);
        setListTaskDone(tasks?.item?.Completed);
        countId = tasks?.item?.idMax;
    }, [tasks]);

    const handleSubmit = (e) => {
        if (
            e.which === 13 &&
            !e.shiftKey &&
            inputTask !== "" &&
            isLoaded === false
        ) {
            addTaskToState();
        }
    };

    const handleSubmitUpDate = (e) => {
        if (
            e.which === 13 &&
            !e.shiftKey &&
            e.target.value !== "" &&
            isLoaded === false
        ) {
            dispatch(editTask({ id: taskIdEdit, name: e.target.value, status: 4 }));
            setEditIdEdit(0);
        }
    };

    const handleMouse = (id) => {
        setFocusTask(true);
        setEditIdFocus(id);
    };

    const handleActionEdit = (id) => {
        setShowIdEdit(0);
        nameMappingBefore = listTaskDoing.find((a) => a.id === id)?.name;
        setEditIdEdit(id);
    };

    const handleActionDelete = (id) => {
        setShowIdEdit(0);
        dispatch(formReset());
        dispatch(deleteTask(id));
        let data = listTaskDoing.filter((item) => item.id !== id);
        setListTaskDoing(data);
    };

    const onchangeUpdate = (id, name) => {
        idMapping = id;
        nameMapping = name;
        let data = [...listTaskDoing];
        let artwork = data.find((a) => a.id === id);
        artwork.name = name;
        setListTaskDoing(data);
    };

    const addTaskToState = () => {
        if (
            inputTask === "" ||
            inputTask === undefined ||
            inputTask === null ||
            isLoaded
        ) {
            return;
        } else {
            let taskAdd = [
                ...listTaskDoing,
                {
                    id: ++countId,
                    name: inputTask,
                },
            ];
            dispatch(formReset());
            dispatch(addTask({ name: inputTask }));
            setListTaskDoing(taskAdd);
            setInputTask("");
        }
    };

    const addToDone = (id) => {
        let data = {
            id: id,
            name: listTaskDoing[listTaskDoing.findIndex((item) => item.id === id)]
                ?.name,
            status: 3,
        };
        dispatch(formReset());
        dispatch(editTask(data));
        let dataDoing = listTaskDoing;
        let dataDone = [...listTaskDone, data];
        setListTaskDoing(dataDoing.filter((item) => item.id !== id));
        setListTaskDone(dataDone);
    };

    // Click outside input
    const inputRef = useRef(null);
    useOutsideInput(inputRef);
    function useOutsideInput(ref) {
        useEffect(() => {
            const handleClickOutside = (event) => {
                if (ref.current && !ref.current.contains(event.target)) {
                    if (nameMappingBefore !== nameMapping) {
                        let dataReq = {
                            id: idMapping,
                            name: nameMapping,
                            status: 4,
                        };
                        dispatch(editTask(dataReq));
                    }
                    setEditIdEdit(0);
                }
            };
            document.addEventListener("mousedown", handleClickOutside);
            return () => {
                document.removeEventListener("mousedown", handleClickOutside);
            };
        }, [ref]);
    }

    // Click outside action
    const actionRef = useRef(null);
    useOutsideAction(actionRef);
    function useOutsideAction(ref) {
        useEffect(() => {
            const handleClickOutside = (event) => {
                if (ref.current && !ref.current.contains(event.target)) {
                    setShowIdEdit(0);
                }
            };
            document.addEventListener("mousedown", handleClickOutside);
            return () => {
                document.removeEventListener("mousedown", handleClickOutside);
            };
        }, [ref]);
    }

    const layoutInput = (
        <span className="d-flex align-items-center mt-4">
            <InputText
                value={inputTask}
                required={true}
                minLength={1}
                maxLength={256}
                autoFocus={true}
                onChange={(e) => setInputTask(e.target.value)}
                onKeyPress={handleSubmit}
                placeholder="Nhập tên nhiệm vụ của bạn"
            />
            <span
                className="icon-add rounded-circle"
                onClick={() => addTaskToState()}>
                <UilPlus />
            </span>
        </span>
    );

    const showEdit = (id) => {
        setShowIdEdit(id);
    };

    const layoutDoing = listTaskDoing?.map((data, index) => (
        <div className="task-item d-flex justify-content-between w-100" key={index}>
            {taskIdEdit !== data?.id ? (
                <span
                    className="icon-tick"
                    onClick={() => addToDone(data?.id)}
                    onMouseOver={() => handleMouse(data?.id)}
                    onMouseLeave={() => setFocusTask(false)}>
                    {" "}
                    {focusTask === true && taskIdFocus === data?.id ? (
                        <span style={{ color: "#55BC7D" }}>
                            <UisCheckCircle size={20} />
                        </span>
                    ) : (
                        <UilCircle size={20} />
                    )}
                </span>
            ) : null}
            <div className="content-item w-100">
                {taskIdEdit === data?.id ? (
                    <InputText
                        style={{ backgroundColor: "#F3F9FF", paddingLeft: "10px" }}
                        value={
                            listTaskDoing[
                                listTaskDoing.findIndex((item) => item.id === taskIdEdit)
                            ]?.name
                        }
                        onChange={(e) => onchangeUpdate(taskIdEdit, e.target.value)}
                        ref={inputRef}
                        onKeyPress={(e) => handleSubmitUpDate(e)}
                        autoFocus={true}
                        required={true}
                        minLength={1}
                        maxLength={256}
                    />
                ) : (
                    <div className="p-inputtext" style={{ fontSize: "1.4rem" }}>
                        {data?.name}
                    </div>
                )}
            </div>
            <span
                onClick={() => showEdit(data?.id)}
                className="icon-edit position-relative">
                <UilEllipsisV size={20} />
            </span>
            {showIdEdit === data?.id ? (
                <div className="edit-content" ref={actionRef}>
                    <div
                        className="item-action d-flex justify-content-between align-items-center separate-bottom"
                        onClick={() => handleActionEdit(data?.id)}>
                        <span>
                            <UilEdit size={18} />
                        </span>
                        <span>Sửa nhiệm vụ</span>
                    </div>
                    <div
                        className="item-action d-flex d-flex justify-content-between align-items-center"
                        onClick={() => handleActionDelete(data?.id)}>
                        <div>
                            <span>
                                {" "}
                                <UilTrashAlt size={18} />
                            </span>
                            <span> Xoá nhiệm vụ</span>
                        </div>
                    </div>
                </div>
            ) : null}
        </div>
    ));

    // const layoutDone = listTaskDone?.map((data, index) => (
    //     <div className="task-item d-flex justify-content-between w-100" key={index}>
    //         <div className="content-item w-100">
    //             <div
    //                 className="p-inputtext"
    //                 style={{ fontSize: "1.4rem", paddingLeft: "10px" }}>
    //                 {data?.name}
    //             </div>
    //         </div>
    //     </div>
    // ));
    const [globalFilterValue, setGlobalFilterValue] = useState("");
    const [filters, setFilters] = useState({
        global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    });

    // filter
    const onGlobalFilterChange = (e) => {
        const value = e.target.value;
        let _filters = { ...filters };
        _filters["global"].value = value;

        setFilters(_filters);
        setGlobalFilterValue(value);
    };
    const renderHeader = () => {
        return (
            <div className="flex justify-content-between align-items-center">
                <span className="p-input-icon-left">
                    <i className="pi pi-search" />
                    <InputText
                        value={globalFilterValue}
                        onChange={onGlobalFilterChange}
                        placeholder="Tìm kiếm"
                    />
                </span>
            </div>
        );
    };
    const header = renderHeader();

    const lDone = (
        <DataTable
            value={listTaskDone}
            paginator
            header={header}
            rows={10}
            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink"
            dataKey="id"
            rowHover
            filters={filters}
            responsiveLayout="scroll"
            globalFilterFields={["name,id"]}
            emptyMessage="Chưa có bản ghi nào.">
            <Column
                headerStyle={{ width: "10rem" }}
                className="font-size-14"
                header="ID"
                field="id"></Column>
            <Column
                className="font-size-14"
                field="name"
                header="Tên nhiệm vụ"
                headerStyle={{ width: "90rem" }}
                sortable
            />
        </DataTable>
    );

    return (
        <div className="task-for-you mt-5">
            <header className="task-header d-flex">
                <div
                    className={
                        checkOpen === "doing"
                            ? "count-task-doing active"
                            : "count-task-doing"
                    }>
                    <div className="rounded-circle" onClick={() => setCheckOpen("doing")}>
                        {" "}
                        <UilCircle size={20} />
                    </div>
                    <div className="count-content">
                        <div className="type">Đang làm</div>
                        <div className="count">{listTaskDoing?.length}</div>
                    </div>
                </div>
                <div
                    className={
                        checkOpen === "done" ? "count-task-done active" : "count-task-done"
                    }>
                    <div className="rounded-circle" onClick={() => setCheckOpen("done")}>
                        {" "}
                        <UisCheckCircle size={20} />
                    </div>
                    <div className="count-content">
                        <div className="type">Hoàn thành</div>
                        <div className="count">{listTaskDone?.length}</div>
                    </div>
                </div>
            </header>
            {checkOpen === "doing" ? (
                <React.Fragment>
                    <div className="add-task">{layoutInput}</div>
                    <div className="task-content">{layoutDoing}</div>
                </React.Fragment>
            ) : checkOpen === "done" ? (
                <div className="task-content m-0 mt-4">{lDone}</div>
            ) : null}
        </div>
    );
};

export default TasksForYou;
