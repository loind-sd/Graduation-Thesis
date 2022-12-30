import React, { Component, useEffect, useState } from 'react';
import Header from '../../../components/Header/Header';
import MenuBar from '../../../components/MenuBar/MenuBar';
import RankProfile from '../../../components/RankProfile/RankProfile';
import './Dashboard.scss';
import ReactApexChart from 'react-apexcharts';
import { Calendar } from 'primereact';
import { getCommunity, getPersonal, getRankInfo } from '../../../redux/thunks/statistic-thunks';
import { useDispatch, useSelector } from 'react-redux';
import _ from 'lodash';
import moment from 'moment';
import Feedback from '../../../components/Feedback/Feedback';

const Dashboard = () => {
    const statistic = useSelector((state) => state.statistic);
    const [time, setTime] = useState(moment(new Date()).format('YYYY-MM'));
    const [date, setDate] = useState(new Date());
    const dispatch = useDispatch();
    const useDateChange = (e) => {
        dispatch(getPersonal(moment(e.value).format('YYYY-MM')));
        setTime(moment(e.value).format('YYYY-MM'));
        setDate(e.value);
    };

    useEffect(() => {
        dispatch(getCommunity());
        dispatch(getPersonal(time));
    }, [dispatch]);

    let timeToCompleted = [],
        missionDoing = [],
        missionCompleted = [],
        dayRange = [];
    const [series, setSeries] = useState([]);
    const [option, setOption] = useState({});
    useEffect(() => {
        for (let i = 0; i < statistic.personal?.length; i++) {
            missionDoing.push(
                statistic.personal[i].statisticTaskMapping.totalTaskDoing === null
                    ? 0
                    : statistic.personal[i].statisticTaskMapping.totalTaskDoing.toFixed(1),
            );
            missionCompleted.push(
                statistic.personal[i].statisticTaskMapping.totalTaskCompleted === null
                    ? 0
                    : statistic.personal[i].statisticTaskMapping.totalTaskCompleted.toFixed(1),
            );
            timeToCompleted.push(
                statistic.personal[i].statisticTaskMapping.totalTimeCompleted === null
                    ? 0
                    : statistic.personal[i].statisticTaskMapping.totalTimeCompleted.toFixed(1),
            );
            dayRange.push(
                moment(statistic.personal[i].fromDate).format('DD/MM') +
                    '-' +
                    moment(statistic.personal[i].toDate).format('DD/MM'),
            );
        }
        setSeries([
            {
                name: 'Nhiệm vụ đang làm',
                type: 'column',
                data: missionDoing,
            },
            {
                name: 'Nhiệm vụ đã làm',
                type: 'column',
                data: missionCompleted,
            },
            {
                name: 'Giờ hoàn thành',
                type: 'line',
                data: timeToCompleted,
            },
        ]);
        setOption({
            chart: {
                height: '100%',
                type: 'line',
            },
            stroke: {
                width: [0, 0],
            },
            title: {
                text: 'Cá nhân',
                style: {
                    fontFamily:
                        'Helvetica,Segoe UI,Helvetica Neue,Arial,Noto Sans,sans-serif,Apple Color Emoji,Segoe UI Emoji,Segoe UI Symbol,Noto Color Emoji',
                    fontWeight: 800,
                },
            },
            dataLabels: {
                enabled: true,
                enabledOnSeries: [2],
            },
            labels: dayRange,
            xaxis: {
                type: 'text',
            },
            legend: {
                position: 'bottom',
                horizontalAlign: 'center',
                style: {
                    display: 'flex',
                    justifyContent: 'center',
                },
            },
            yaxis: [
                {
                    seriesName: 'Nhiệm vụ đang làm',
                    show: false,
                    title: {
                        text: 'Số nhiệm vụ',
                        style: {
                            fontFamily:
                                '-apple-system, BlinkMacSystemFont, Segoe UI, Roboto, Helvetica Neue, Arial, Noto Sans, Liberation Sans, sans-serif, Apple Color Emoji, Segoe UI Emoji, Segoe UI Symbol, Noto Color Emoji',
                            fontWeight: 500,
                            fontSize: '14px',
                        },
                    },
                },
                {
                    seriesName: 'Nhiệm vụ đang làm',
                    title: {
                        text: 'Số nhiệm vụ',
                        style: {
                            fontFamily:
                                '-apple-system, BlinkMacSystemFont, Segoe UI, Roboto, Helvetica Neue, Arial, Noto Sans, Liberation Sans, sans-serif, Apple Color Emoji, Segoe UI Emoji, Segoe UI Symbol, Noto Color Emoji',
                            fontWeight: 500,
                            fontSize: '14px',
                        },
                    },
                },
                {
                    opposite: true,
                    title: {
                        text: 'Giờ hoàn thành',
                        style: {
                            fontFamily:
                                '-apple-system, BlinkMacSystemFont, Segoe UI, Roboto, Helvetica Neue, Arial, Noto Sans, Liberation Sans, sans-serif, Apple Color Emoji, Segoe UI Emoji, Segoe UI Symbol, Noto Color Emoji',
                            fontWeight: 500,
                            fontSize: '14px',
                        },
                    },
                },
            ],
        });
    }, [statistic]);

    let listSkill = [],
        listCountSkill = [];
    const [series1, setSeries1] = useState([]);
    const [option1, setOption1] = useState({});
    useEffect(() => {
        for (let i = 0; i < statistic.community.length; i++) {
            listSkill.push(statistic.community[i].softSkillName);
            listCountSkill.push(statistic.community[i].countTaskCompleted);
        }
        setSeries1(listCountSkill);
        setOption1({
            title: {
                text: 'Cộng đồng',
            },
            fill: {
                colors: ['#66ad72', '#666fad', '#9466ad', '#a1ad66', '#62abbd'],
            },
            chart: {
                width: 380,
                type: 'pie',
                fontFamily:
                    'Helvetica,Segoe UI,Helvetica Neue,Arial,Noto Sans,sans-serif,Apple Color Emoji,Segoe UI Emoji,Segoe UI Symbol,Noto Color Emoji',
            },
            labels: listSkill,
            colors: ['#66ad72', '#666fad', '#9466ad', '#a1ad66', '#62abbd'],
            legend: {
                position: 'right',
                horizontalAlign: 'right',
                style: {
                    display: 'flex',
                    justifyContent: 'center',
                },
            },
            responsive: [
                {
                    breakpoint: 480,
                    options: {
                        chart: {
                            width: 200,
                            fontFamily:
                                'Helvetica,Segoe UI,Helvetica Neue,Arial,Noto Sans,sans-serif,Apple Color Emoji,Segoe UI Emoji,Segoe UI Symbol,Noto Color Emoji',
                        },
                    },
                },
            ],
        });
    }, [statistic]);

    return (
        <React.Fragment>
            <MenuBar activeMenu="dashboard-user" />
            <div className="home d-flex">
                <Header />
                <div className="content row">
                    <RankProfile />
                    <div className="row graph-container col-9 border-left">
                        <div id="chart" className="col-12 ">
                            <div className="border personal">
                                <ReactApexChart
                                    className="font-weight-light  p-2"
                                    options={option}
                                    series={series}
                                    type="line"
                                    height={'70%'}
                                    width={'100%'}
                                />
                                <div className="col-12 d-flex justify-content-center pt-3 font-italic ">
                                    Biểu đồ thống kê nhiệm vụ
                                </div>
                                <div className="monthpicker d-flex justify-content-start align-items-center pt-2 col-12 rounded">
                                    <label htmlFor="monthpicker" className="mt-2 ml-2">
                                        Chọn tháng
                                    </label>
                                    <div className="col-lg-4 col-xl-2 col-md-4">
                                        <Calendar
                                            className="field "
                                            id="monthpicker"
                                            value={date}
                                            onChange={useDateChange}
                                            view="month"
                                            dateFormat="mm/yy"
                                        />
                                    </div>
                                </div>
                            </div>
                            <div className="border p-2 mt-5 rounded community">
                                <ReactApexChart
                                    className="font-weight-light pie-chart d-flex justify-content-center"
                                    options={option1}
                                    series={series1}
                                    type="pie"
                                    height={'80%'}
                                    width={'100%'}
                                />
                                <div className="col-12 d-flex justify-content-center font-italic mt-4">
                                    Biểu đồ nhiệm vụ hoàn thành theo từng kỹ năng
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="graph"></div>
                </div>
            </div>
            <Feedback />
        </React.Fragment>
    );
};

export default Dashboard;
