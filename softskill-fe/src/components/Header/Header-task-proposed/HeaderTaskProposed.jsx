import React, { useEffect, useRef, useState } from 'react';
import './HeaderTaskProposed.scss';
import { useFormik } from 'formik';
import { UilTimes } from '@iconscout/react-unicons';
import { Button, Dialog, Dropdown, FileUpload, InputTextarea, Toast } from 'primereact';
import { useDispatch, useSelector } from 'react-redux';
import { getListSoftSkill } from '../../../redux/thunks/soft-skill-thunks';
import {
    addTaskProposed,
    formResetTaskProposed,
    getTimeCheckAddRecommendTask,
} from '../../../redux/thunks/task-proposed-thunks';
import { getRecommendTaskType } from '../../../redux/thunks/_dropdown-thunks';
import { useHistory } from 'react-router';
import { getTimeWithStringAndMsg } from '../../../utils/common-service';
import { HTTP_200 } from '../../../utils/constant/CommonConstant';

const HeaderTaskProposed = () => {
    const dispatch = useDispatch();
    const history = useHistory();
    const toast = useRef(null);

    const softSkillState = useSelector((state) => state.softSkillData.softSkills);
    const typeTaskState = useSelector((state) => state.dropdownData.recommendTaskType);
    const timeCheck = useSelector((state) => state.taskProposedData.time);
    const successSelector = useSelector((state) => state.taskProposedData.success);
    const errorSelector = useSelector((state) => state.taskProposedData.error);

    // State
    const [displayPopup, setDisplayPopup] = useState(false);
    const [softSkills, setSoftSkills] = useState([]);
    const [typeTask, setTypeTask] = useState([]);
    const [msgTime, setMsgTime] = useState('');
    const [fileTask, setFileTask] = useState('');
    const [fileGuideline, setFileGuideline] = useState('');

    useEffect(() => {
        setSoftSkills(softSkillState?.item);
    }, [softSkillState]);

    useEffect(() => {
        setTypeTask(typeTaskState?.item);
    }, [typeTaskState]);

    useEffect(() => {
        if (timeCheck?.item > 0) {
            let msg = getTimeWithStringAndMsg(timeCheck?.item, 'nữa để gửi nhiệm vụ tiếp theo.');
            setMsgTime(msg);
        }
    }, [timeCheck]);

    useEffect(() => {
        dispatch(getListSoftSkill());
        dispatch(getRecommendTaskType());
    }, [dispatch]);

    useEffect(() => {
        if (successSelector === HTTP_200) {
            toast.current.show({
                severity: 'success',
                summary: 'Thành công',
                detail: 'Gửi nhiệm vụ thành công!',
                life: 3000,
            });
        }
        if (errorSelector !== HTTP_200 && errorSelector !== '') {
            toast.current.show({
                severity: 'error',
                summary: 'Thất bại',
                detail: 'Gửi nhiệm vụ thất bại!',
                life: 3000,
            });
        }
    }, [errorSelector, successSelector]);

    const formik = useFormik({
        initialValues: {
            selectedSoftSkill: null,
            typeTask: null,
            taskDescription: '',
            guideline: '',
        },
        validate: (data) => {
            let errors = {};
            if (!data.taskDescription.trim()) {
                errors.taskDescription = 'Bạn vui lòng nhập nội dung của nhiệm vụ.';
            }
            if (data.selectedSoftSkill == null || data.selectedSoftSkill === undefined) {
                errors.selectedSoftSkill = 'Bạn cần lựa chọn một kỹ năng chính.';
            }
            if (data.typeTask == null || data.typeTask === undefined) {
                errors.typeTask = 'Bạn cần lựa chọn một loại nhiệm vụ.';
            }
            return errors;
        },
        onSubmit: (data) => {
            let count = 0;
            if (fileTask === null || fileTask === undefined || fileTask === '') {
                setErrorsFileTask('Bạn cần một bức ảnh cho nhiệm vụ này.');
                count++;
            }
            if (fileGuideline === null || fileGuideline === undefined || fileGuideline === '') {
                setErrorsFileGuideline('Bạn cần một bức ảnh để hướng dẫn cho nhiệm vụ này.');
                count++;
            }
            if (count !== 0) {
                return;
            }
            if (msgTime !== '') {
                return;
            }

            const formData = new FormData();
            formData.append(`fileTask`, fileTask);
            formData.append(`fileGuideline`, fileGuideline);
            dispatch(formResetTaskProposed());

            dispatch(
                addTaskProposed(
                    formData,
                    data.selectedSoftSkill?.id,
                    data.typeTask?.id,
                    data.taskDescription,
                    data.guideline,
                    history,
                ),
            );
            formik.resetForm();
            onHide('displayPopup');
            toast.current.show({
                severity: 'success',
                summary: 'Thành công',
                detail: 'Gửi đề xuất thành công!',
                life: 3000,
            });
        },
    });

    const isFormFieldValid = (name) => !!(formik.touched[name] && formik.errors[name]);

    const getFormErrorMessage = (name) => {
        return isFormFieldValid(name) && <small className="p-error">{formik.errors[name]}</small>;
    };

    const selectedTemplate = (option, props) => {
        if (option) {
            return <div>{option.name}</div>;
        }
        return <span>{props.placeholder}</span>;
    };

    const optionTemplate = (option) => {
        return <div>{option.name}</div>;
    };

    const dialogFuncMap = {
        displayPopup: setDisplayPopup,
    };

    const onClick = (name) => {
        dispatch(getTimeCheckAddRecommendTask());
        dialogFuncMap[`${name}`](true);
        formik.handleReset();
    };

    const onHide = (name) => {
        dialogFuncMap[`${name}`](false);
    };

    const [errorsFileTask, setErrorsFileTask] = useState('');
    const [errorsFileGuideline, setErrorsFileGuideline] = useState('');

    const uploadFileTask = (event) => {
        setErrorsFileTask('');
        setFileTask(event.files[0]);
    };

    const uploadFileGuideline = (event) => {
        setErrorsFileGuideline('');
        setFileGuideline(event.files[0]);
    };

    const layoutHeader = (
        <div className="task-header mt-1">
            <div className="title d-flex align-items-center">
                <span className="title-content mx-auto">
                    <span className="title-name">Gửi nhiệm vụ đề xuất</span>
                </span>
                <span
                    style={{ cursor: 'pointer' }}
                    className="icon p-2 rounded-circle bg-light"
                    onClick={() => onHide('displayPopup')}
                >
                    <UilTimes />{' '}
                </span>
            </div>
            <div className="content-header font-size-14">
                <div>
                    {' '}
                    Lưu ý: Nhiệm vụ của bạn sẽ được gửi đến Quản trị web. Bộ phận xử lý sẽ trả lời email của bạn khi
                    được duyệt.{' '}
                </div>
                <div>
                    Để hạn chế SPAM, sẽ giãn thời gian trả lời email có tính chất SPAM theo nguyên tắc: Khi người dùng
                    gửi N nhiệm vụ (N{'>'}1) thì thời gian trả lời trong vòng Nx48h.
                </div>
                <div>
                    Vì vậy người dùng cần cân nhắc trước khi gửi nhiệm vụ với cùng một nội dung để nhận được trả lời
                    nhanh nhất theo quy định.
                </div>
            </div>
        </div>
    );

    const layoutContent = (
        <div className="task-content mt-5 font-size-14">
            <div className="row mt-3 p-0">
                <div className="soft-skill col-sm-6">
                    <div className="d-flex align-items-center col-sm-12 p-0">
                        <p className="title font-weight-600" style={{ marginRight: '8px' }}>
                            Kỹ năng mềm <span style={{ color: 'red' }}>*</span>
                        </p>
                        <Dropdown
                            name="selectedSoftSkill"
                            style={{ width: '65%', marginLeft: '5px' }}
                            value={formik.values.selectedSoftSkill}
                            onChange={(e) => {
                                formik.handleChange(e);
                            }}
                            className={{
                                'p-invalid ': isFormFieldValid('selectedSoftSkill'),
                            }}
                            options={softSkills}
                            optionLabel="name"
                            filter
                            filterBy="name"
                            placeholder="Lựa chọn kỹ năng mềm"
                            valueTemplate={selectedTemplate}
                            itemTemplate={optionTemplate}
                        />
                    </div>
                    <div className="float-right" style={{ marginRight: '20px' }}>
                        {' '}
                        {getFormErrorMessage('selectedSoftSkill')}
                    </div>
                </div>
                <div className="type-task col-sm-6">
                    <div className="d-flex align-items-center col-sm-12 p-0">
                        <p className="title mb-1 font-weight-600">
                            Loại nhiệm vụ <span style={{ color: 'red' }}>*</span>
                        </p>
                        <Dropdown
                            name="typeTask"
                            value={formik.values.typeTask}
                            style={{ width: '65%', marginLeft: '5px' }}
                            defaultValue={0}
                            onChange={(e) => {
                                formik.handleChange(e);
                            }}
                            className={{
                                'p-invalid ': isFormFieldValid('typeTask'),
                            }}
                            options={typeTask}
                            optionLabel="name"
                            filter
                            filterBy="name"
                            placeholder="Lựa chọn loại nhiệm vụ"
                            valueTemplate={selectedTemplate}
                            itemTemplate={optionTemplate}
                        />
                    </div>
                    <div className="float-right" style={{ marginRight: '20px' }}>
                        {getFormErrorMessage('typeTask')}
                    </div>
                </div>
            </div>
            <div className="row task-img d-flex mt-3">
                <p className="font-weight-600 col-sm-2 p-0">
                    Ảnh nhiệm vụ <span style={{ color: 'red' }}>*</span>
                </p>
                <div className="w-100 col-sm-10 p-0">
                    <FileUpload
                        mode="basic"
                        name="taskImage"
                        accept="image/*"
                        onSelect={(e) => {
                            uploadFileTask(e);
                        }}
                        chooseLabel="Chọn Ảnh nhiệm vụ"
                        maxFileSize={1000000}
                        invalidFileSizeMessageSummary="File quá lớn "
                        invalidFileSizeMessageDetail=" (> 1MB)"
                    />
                    {errorsFileTask !== '' ? <small className="p-error">{errorsFileTask}</small> : null}
                </div>
            </div>
            <div className="row guideline-img d-flex  mt-1 mb-1">
                <p className="font-weight-600 col-sm-2 p-0">
                    Ảnh hướng dẫn <span style={{ color: 'red' }}>*</span>
                </p>
                <div className="w-100 col-sm-10 p-0">
                    <FileUpload
                        mode="basic"
                        name="guidelineImage"
                        accept="image/*"
                        onSelect={uploadFileGuideline}
                        chooseLabel="Chọn Ảnh Hướng Dẫn"
                        maxFileSize={1000000}
                        invalidFileSizeMessageSummary="File quá lớn "
                        invalidFileSizeMessageDetail=" (> 1MB)"
                    />
                    {errorsFileGuideline !== '' ? <small className="p-error">{errorsFileGuideline}</small> : null}
                </div>
            </div>
            <div className="row task-description d-flex justify-content-between mt-3">
                <p className="title mb-1 col-sm-2 font-weight-600 p-0">
                    Nhiệm vụ <span style={{ color: 'red' }}>*</span>
                </p>
                <div className="w-100 col-sm-10 p-0">
                    <InputTextarea
                        name="taskDescription"
                        value={formik.values.taskDescription}
                        minLength={1}
                        maxLength={200}
                        onChange={formik.handleChange}
                        className={{
                            'p-invalid ': isFormFieldValid('taskDescription'),
                        }}
                        style={{ width: '100%' }}
                        rows={3}
                    />
                    {getFormErrorMessage('taskDescription')}
                </div>
            </div>
            <div className="row guideline d-flex justify-content-between mt-3">
                <p className="title mb-1 col-sm-2 font-weight-600 p-0">Hướng dẫn </p>
                <InputTextarea
                    name="guideline"
                    value={formik.values.guideline}
                    onChange={formik.handleChange}
                    maxLength={255}
                    className="w-100 col-sm-10"
                    rows={3}
                />
            </div>
        </div>
    );

    const footerLayout = (
        <footer className="task-footer mt-3">
            <div className="d-flex justify-content-center">
                <Button
                    disabled={timeCheck?.item === undefined || timeCheck?.item < 0 ? false : true}
                    label="Gửi ngay"
                    type="submit"
                    style={{
                        width: '120px',
                        fontSize: '12px',
                        borderRadius: '7px',
                        backgroundColor: '#5993d9',
                        border: '#5993d9',
                    }}
                    className="ml-3"
                />
            </div>
            {msgTime !== '' ? <div className="error d-flex justify-content-center">{msgTime}</div> : null}
        </footer>
    );

    return (
        <React.Fragment>
            <Toast ref={toast} />
            <div className="header-task">
                <Button
                    style={{
                        backgroundColor: ' #71A3E0',
                        borderColor: ' #71A3E0',
                        fontSize: '14px',
                        borderRadius: '8px',
                    }}
                    className="w-100 pl-5 pr-5 pb-2 pt-2 text-white"
                    icon="pi pi-plus"
                    onClick={() => onClick('displayPopup')}
                >
                    <span className="pl-3">Tạo đề xuất</span>
                </Button>
                <Dialog className="dialog-task" closable={null} visible={displayPopup} style={{ width: '720px' }}>
                    <form onSubmit={formik.handleSubmit}>
                        <div className="w-100 p-1">
                            {layoutHeader}
                            {layoutContent}
                            {footerLayout}
                        </div>
                    </form>
                </Dialog>
            </div>
        </React.Fragment>
    );
};

export default HeaderTaskProposed;
