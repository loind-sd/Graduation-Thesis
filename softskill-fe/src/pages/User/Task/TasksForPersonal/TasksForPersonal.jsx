import React, { useEffect, useState } from "react";
import { InputText, TabPanel, TabView } from "primereact";
import "./TasksForPersonal.scss"
import { useDispatch, useSelector } from "react-redux";
import { Button } from "primereact";
import {
    UilCrosshair,
    UilExclamationCircle,
    UilSearchAlt
} from "@iconscout/react-unicons";
import { getListTaskDoingPersonal, getListTaskDonePersonal, updateTaskPersonal } from "../../../../redux/thunks/task-thunks";
import Highlighter from "react-highlight-words";
import { colorListSoftSkill } from "../../../../utils/constant/CommonConstant";

const TasksForPersonal = () => {
    const dispatch = useDispatch();
    const colorList = colorListSoftSkill;

    const tasksDataDoing = useSelector((state) => state.taskData.tasksPersonalDoing);
    const tasksDataDone = useSelector((state) => state.taskData.tasksPersonalDone);
    const [changeTabViewTask, setChangeTabViewTask] = useState(0);
    const [initDataFilterTask, setInitDataFilterTask] = useState([]);
    const [dataFilterTask, setDataFilterTask] = useState([]);
    const [showBtn, setShowBtn] = useState(true);
    const [txtSearch, setTxtSearch] = useState("");

    useEffect(() => {
        dispatch(getListTaskDoingPersonal(2));
    }, [dispatch]);

    useEffect(() => {
        setDataInit(tasksDataDoing);
    }, [tasksDataDoing]);

    useEffect(() => {
        setDataInit(tasksDataDone);
    }, [tasksDataDone]);

    const setDataInit = (data) => {

        if (data?.item !== undefined && data?.item !== null) {

            let dataMapping = data.item;
            let dataRes = dataMapping.map((item) => ({
                ...item,
                viewAll: false,
                userTasks: item.userTasks.filter((e) =>
                    e.taskName.toLowerCase().includes("")
                ),
            }));
            setInitDataFilterTask(dataRes);
            setDataFilterTask(dataRes);
        } else {
            setDataFilterTask([]);
        }
    };

    const handleChangeTabViewTask = (data) => {
        setChangeTabViewTask(data.index);
        if (data.index === 0) {
            setDataInit(tasksDataDoing);
            dispatch(getListTaskDoingPersonal(2));
            setTxtSearch("");
            setShowBtn(true);
        } else if (data.index === 1) {
            setDataInit(tasksDataDone);
            dispatch(getListTaskDonePersonal(3));
            setTxtSearch("");
            setShowBtn(false);
            // document.getElementById("btn-completed").style.visibility = "hidden";
        }
    };

    const handleCompleted = (taskId) => {

        dispatch(updateTaskPersonal(taskId));
    }

    const handleViewAll = (sofSkillId, viewAll) => {
        let dataSearch = dataFilterTask.map((item) =>
            item.softSkill.id === sofSkillId
                ? {
                    ...item,
                    viewAll: viewAll,
                }
                : {
                    ...item,
                }
        );
        setDataFilterTask(dataSearch);
    };

    const searchTasks = (input) => {
        setDataFilterTask(initDataFilterTask.map((item) => ({
            ...item,
            userTasks: item.userTasks.filter((e) =>
                e.taskName.toLowerCase().includes(input.toLowerCase())
                || e.taskContent.toLowerCase().includes(input.toLowerCase())
            ),
        })));
        setTxtSearch(input);
    };

    return (
        <form>
            <React.Fragment>
                <div className="invidual_task-header">
                    {/* <HeaderInfo></HeaderInfo> */}
                    <div className="">
                        <TabView
                            onTabChange={(e) => handleChangeTabViewTask(e)}
                            activeIndex={changeTabViewTask}>
                            <TabPanel header="Nhiệm vụ chưa làm" className="col-6 py-3 d-flex justify-content-center font-weight-bold">
                            </TabPanel>
                            <TabPanel header="Nhiệm vụ hoàn thành" className="col-6 py-3 d-flex justify-content-center  font-weight-bold">
                            </TabPanel>
                        </TabView>
                    </div>
                    <span className="d-flex align-items-center search-input">
                        <span className="icon-search">
                            <UilSearchAlt size="20" />
                        </span>
                        <InputText
                            className="animated-search-filter"
                            value={txtSearch}
                            onChange={(e) => searchTasks(e.target.value)}
                            placeholder="Tìm kiếm nhiệm vụ"
                        />
                    </span>
                    {/* <SearchBar placeholder={"Tìm kiếm tên nhiệm vụ"} status={true} /> */}
                </div>

                <div className="invidual-task_content">
                    {dataFilterTask?.map((element, index) => (
                        element.userTasks.length > 0 ? (
                            <div className="invidual-task_softskills">
                                <div class="task-open d-flex justify-content-between p-1">
                                    <div>
                                        <h5>{element.softSkill.name}</h5>
                                    </div>
                                    <div>
                                        {element.userTasks.length < 4 ? ("") : (
                                            <div class="view-all" onClick={() =>
                                                handleViewAll(element?.softSkill.id, !element?.viewAll)
                                            }>
                                                {element?.viewAll ? "Thu gọn" : "Xem tất cả"}
                                            </div>
                                        )}
                                    </div>
                                </div>

                                <div className="invidual_task-info">
                                    {element.userTasks.map((task, index) => (
                                        element.viewAll ? (
                                            <div className="task-item p-3" style={{ backgroundColor: colorList.filter(i => i.id === element?.softSkill?.id)[0]?.color }} key={index}>
                                                <div class="task-item-header">
                                                    <header className="d-flex align-items-center justify-content-between">
                                                        <div className="d-flex align-items-center">
                                                            <UilCrosshair className="crosshair" style={{ color: "#5594E0", }} size="28" />
                                                            <Highlighter
                                                                highlightClassName="hightlight-search"
                                                                searchWords={[txtSearch]}
                                                                autoEscape={true}
                                                                textToHighlight={task.taskName}
                                                            />
                                                            {/* <span className="task-name">{task.taskName}</span> */}
                                                        </div>
                                                        <span data-toggle="tooltip" data-placement="top" title={task.taskContent}>
                                                            <UilExclamationCircle
                                                                size="20" />
                                                        </span>
                                                    </header>
                                                </div>


                                                <div className="task-content">
                                                    <Highlighter
                                                        highlightClassName="hightlight-search"
                                                        searchWords={[txtSearch]}
                                                        autoEscape={true}
                                                        textToHighlight={task.taskContent}
                                                    />
                                                    {/* <span>
                                                {task.taskContent}
                                            </span> */}
                                                </div>

                                                {showBtn ? (
                                                    <>
                                                        <Button id="btn-completed" label="Hoàn thành" onClick={() => handleCompleted(task.taskId)} type="submit" />
                                                    </>)

                                                    : (
                                                        <></>
                                                    )
                                                }
                                                {/* <Button id="btn-completed" label="Hoàn thành" onClick={() => handleCompleted(task.taskId)} type="submit" /> */}
                                            </div>
                                        ) : (element.viewAll === false && index < 3) ? (
                                            <div className="task-item p-3" style={{ backgroundColor: colorList.filter(i => i.id === element?.softSkill?.id)[0]?.color }} key={index}>
                                                <div class="task-item-header">
                                                    <header className="d-flex align-items-center justify-content-between">
                                                        <div className="d-flex align-items-center">
                                                            <UilCrosshair className="crosshair" style={{ color: "#5594E0", }} size="28" />
                                                            <Highlighter
                                                                highlightClassName="hightlight-search"
                                                                searchWords={[txtSearch]}
                                                                autoEscape={true}
                                                                textToHighlight={task.taskName}
                                                            />
                                                            {/* <span className="task-name">{task.taskName}</span> */}
                                                        </div>
                                                        <span data-toggle="tooltip" data-placement="top" title={task.taskContent}>
                                                            <UilExclamationCircle
                                                                size="20" />
                                                        </span>
                                                    </header>
                                                </div>


                                                <div className="task-content">
                                                    <Highlighter
                                                        highlightClassName="hightlight-search"
                                                        searchWords={[txtSearch]}
                                                        autoEscape={true}
                                                        textToHighlight={task.taskContent}
                                                    />
                                                    {/* <span>
                                                {task.taskContent}
                                            </span> */}
                                                </div>

                                                {showBtn ? (
                                                    <>
                                                        <Button id="btn-completed" label="Hoàn thành" onClick={() => handleCompleted(task.taskId)} type="submit" />
                                                    </>)

                                                    : (
                                                        <></>
                                                    )
                                                }
                                                {/* <Button id="btn-completed" label="Hoàn thành" onClick={() => handleCompleted(task.taskId)} type="submit" /> */}
                                            </div>
                                        ) : (
                                            ""
                                        )

                                    ))}
                                </div>
                            </div>
                        ) : (
                            ""
                        )

                    ))}
                    {/* <div>
                        {changeTabViewTask === 1  ?
                        ( <span>Bạn chưa hoàn thành task nào</span>)
                        : ("")
                        } 
                    </div> */}
                </div>
            </React.Fragment>

        </form>
    );
};

export default TasksForPersonal;
