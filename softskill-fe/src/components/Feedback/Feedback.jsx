import './Feedback.scss';
import React, { useState, useEffect, useRef } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Dialog } from 'primereact/dialog';
import { Dropdown } from 'primereact/dropdown';
import { Button } from 'primereact/button';
import { createNewFeedback, getFeedbackType } from '../../redux/thunks/feedback-thunks';
import * as Yup from 'yup';
import { useFormik } from 'formik';
import { InputTextarea } from 'primereact/inputtextarea';
import ReactStars from 'react-rating-stars-component';
import { Toast } from 'primereact/toast';

const labels = {
    1: 'Vô ích',
    2: 'Bình thường',
    3: 'Tốt',
    4: 'Khá tốt',
    5: 'Rất tốt',
};

const Feedback = () => {
    const dispatch = useDispatch();

    const feedbackTypes = useSelector((state) => state.feedback.feedbackType);
    const fType = useSelector((state) => state.feedback.fType);
    const feedbackSuccess = useSelector((state) => state.feedback.success);

    const [displayBasic, setDisplayBasic] = useState(false);
    const [starName, setStarName] = useState('Tốt');
    const [isS, setIsS] = useState(false);
    const toast = useRef(null);

    useEffect(() => {
        dispatch(getFeedbackType());
    }, []);

    useEffect(() => {
        if (fType === 'send' && isS === true) {
            showSuccess();
            setIsS(false);
        }
    }, [isS, fType]);

    const ratingChanged = (newRating) => {
        formik.values.rate = newRating;
        setStarName(labels[newRating]);
    };

    const formik = useFormik({
        initialValues: {
            feedbackTitle: '',
            description: '',
            rate: 3,
        },
        validationSchema: Yup.object({
            description: Yup.string()
                .min(10, 'Nội dung phải chứa ít nhất 10 ký tự')
                .max(5000, 'Nội dung tối đa là 5000 ký tự')
                .required('Nội dung này không được trống'),

            feedbackTitle: Yup.object().required('Chọn ít nhất một vấn đề'),
        }),
        onSubmit: (values) => {
            let request = {
                content: values.description,
                titleId: values.feedbackTitle.id,
                rateStar: values.rate,
            };
            dispatch(createNewFeedback(request));
            setIsS(true);
            formik.resetForm();
            onHide();
        },
    });

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
        displayBasic: setDisplayBasic,
    };

    const onClick = (name, position) => {
        dialogFuncMap[`${name}`](true);
        document.getElementById('btn-feedback').style.visibility = 'hidden';
    };

    const onHide = (name) => {
        dialogFuncMap[`${name}`](false);
        document.getElementById('btn-feedback').style.visibility = 'visible';
    };

    const showSuccess = () => {
        toast.current.show({
            severity: 'success',
            summary: 'Thành công',
            detail: feedbackSuccess.data.item.successMessage,
            life: 3000,
        });
    };

    return (
        <form onSubmit={formik.handleSubmit}>
            <Toast ref={toast} position="bottom-left" />
            <div className="dialog-demo">
                <Button
                    id="btn-feedback"
                    label="Đánh giá"
                    icon="pi pi-external-link"
                    onClick={() => onClick('displayBasic')}
                    // display={clicked}
                />
                {/* <div className="feedback-container"> */}
                <Dialog
                    className="feedback-container"
                    draggable={false}
                    header="Bạn cảm thấy cộng đồng của chúng mình thế nào?"
                    headerStyle={{ color: 'white' }}
                    visible={displayBasic}
                    style={{ width: '30%', position: 'fixed', right: '1rem', bottom: '4rem' }}
                    onHide={() => onHide('displayBasic')}
                >
                    <div className="feedback-rate">
                        <h5>Đánh giá của bạn về chúng tôi</h5>
                        <div className="feedback-star">
                            <div className="feedback-star_rating">
                                <ReactStars
                                    name="rate"
                                    count={5}
                                    value={formik.values.rate}
                                    onChange={(e) => {
                                        // formik.handleChange(e);

                                        ratingChanged(e);
                                    }}
                                    size={24}
                                    isHalf={false}
                                    emptyIcon={<i className="far fa-star"></i>}
                                    fullIcon={<i className="fa fa-star"></i>}
                                    color1={'#ffffff'}
                                    color2={'#ffd700'}
                                />
                            </div>
                            <div className="feedback-star_label">
                                <span>{starName}</span>
                            </div>
                        </div>
                    </div>
                    <div className="feedback-question">
                        <h5>Vấn đề phản hồi</h5>
                        {formik.errors.feedbackTitle && formik.touched.feedbackTitle && (
                            <p
                                style={{
                                    color: '#000000',
                                    fontSize: '14px',
                                    fontStyle: 'italic',
                                    marginBottom: '1.5rem',
                                }}
                            >
                                {formik.errors.feedbackTitle}
                            </p>
                        )}
                        <Dropdown
                            name="feedbackTitle"
                            value={formik.values.feedbackTitle}
                            options={feedbackTypes.item}
                            onChange={(e) => {
                                formik.handleChange(e);
                            }}
                            optionLabel="name"
                            placeholder="Chọn vấn đề phản hồi"
                            valueTemplate={selectedTemplate}
                            itemTemplate={optionTemplate}
                        />
                    </div>

                    <div className="feedback-content">
                        <h5>Nội dung</h5>
                        {formik.errors.description && formik.touched.description && (
                            <p style={{ color: '#000000', fontSize: '14px', fontStyle: 'italic' }}>
                                {formik.errors.description}
                            </p>
                        )}
                        <InputTextarea
                            // value={formik.values.description}
                            // onChange={(e) => formik.handleChange(e)}
                            name="description"
                            rows={5}
                            cols={54}
                            autoResize
                            value={formik.values.description}
                            onChange={formik.handleChange}
                        />
                    </div>
                    <Button label="GỬI" onClick={formik.handleSubmit} type="submit" />
                </Dialog>
            </div>

            {/* </div> */}
        </form>
    );
};
export default Feedback;
