import { useState, React, useEffect } from 'react';
import { Chart } from 'primereact/chart';
import ReactApexChart from 'react-apexcharts';
import { UilSnowflake } from '@iconscout/react-unicons';
import { Calendar } from 'primereact';
import moment from 'moment';
import { useDispatch, useSelector } from 'react-redux';
import {
    getNumberJoin,
    getNumberRoom,
    getNumberUser,
    getRankInfo,
    getSoftSkill,
    getTotalRooms,
} from './../../../../redux/thunks/admin/statistic-thunks';
import './Dashboard.scss';
const PieChartDemo = () => {
    const dispatch = useDispatch();

    const [up1, setUp1] = useState('up');
    const [up2, setUp2] = useState('up');
    const [numberUser, setNumberUser] = useState(0);
    const [totalRoom, setTotalRoom] = useState(0);
    const [date, setDate] = useState(new Date());
    const [dateNumberJoin, setDateNumberJoin] = useState(new Date());
    const [time, setTime] = useState(moment(new Date()).format('YYYY-MM'));
    const [timeNumberJoin, setTimeNumberJoin] = useState(moment(new Date()).format('YYYY-MM-DD'));
    const [rankData, setRankData] = useState([]);
    const [softSkillData, setSoftSkillData] = useState([]);

    const useDateChange = (e) => {
        setTime(moment(e.value).format('YYYY-MM'));
        setDate(e.value);
        dispatch(
            getNumberRoom({
                time: moment(e.value).format('YYYY-MM'),
            }),
        );
    };

    const useDateChangeNumberJoin = (e) => {
        setTimeNumberJoin(moment(e.value).format('YYYY-MM-DD'));
        setDateNumberJoin(e.value);
        dispatch(
            getNumberJoin({
                time: moment(e.value).format('YYYY-MM-DD'),
            }),
        );
    };

    useEffect(() => {
        dispatch(getNumberUser());
        dispatch(getTotalRooms());
        dispatch(
            getNumberRoom({
                time: time,
            }),
        );
        dispatch(
            getNumberJoin({
                time: timeNumberJoin,
            }),
        );
        dispatch(getSoftSkill());
        dispatch(getRankInfo());
    }, []);

    let statistics = useSelector((state) => state.statisticAdmin);

    useEffect(() => {
        if (statistics) {
            setUp1(statistics.numberUser.isDevelop);
            setUp2(statistics.totalRoom.isDevelop);
            setNumberUser(statistics.numberUser.number);
            setTotalRoom(statistics.totalRoom.number);

            let _rankData = [];
            for (let index = 0; index < statistics.rankInfo.length; index++) {
                const element = statistics.rankInfo[index];
                _rankData.push(element.number);
            }
            setRankData(_rankData);

            let _softSkillData = [];
            for (let index = 0; index < statistics.softSkill.length; index++) {
                const element = statistics.softSkill[index];
                _softSkillData.push(element.countTaskCompleted);
            }
            setSoftSkillData(_softSkillData);
        }
    }, [statistics]);

    // console.log(statistics);

    let numberJoinLabel = [];
    let _numberJoin = [];
    for (let index = 0; index < statistics.numberJoin.length; index++) {
        const element = statistics.numberJoin[index];
        numberJoinLabel.push(element.date);
        _numberJoin.push(element.number);
    }

    let series = [
        {
            name: 'Số người truy cập',
            data: _numberJoin,
        },
    ];

    let numberRoomLabel = [];
    let _numberActiveRoom = [],
        _numberBookingRoom = [];
    for (let index = 0; index < statistics.numberRoom.length; index++) {
        const element = statistics.numberRoom[index];
        numberRoomLabel.push(element.week);
        _numberActiveRoom.push(element.numberActiveRoom);
        _numberBookingRoom.push(element.numberBookingRoom);
    }

    let roomSeries = [
        {
            name: 'Số phòng trực tiếp',
            data: _numberActiveRoom,
        },
        {
            name: 'Số phòng đặt lịch',
            data: _numberBookingRoom,
        },
    ];

    let rankLabel = [];
    for (let index = 0; index < statistics.rankInfo.length; index++) {
        const element = statistics.rankInfo[index];
        rankLabel.push(element.name);
    }

    let softSkillLabel = [];
    for (let index = 0; index < statistics.softSkill.length; index++) {
        const element = statistics.softSkill[index];
        softSkillLabel.push(element.softSkillName);
    }

    const getLightTheme = () => {
        // let basicOptions = {
        //     maintainAspectRatio: false,
        //     aspectRatio: 0.8,
        //     plugins: {
        //         legend: {
        //             labels: {
        //                 color: '#495057',
        //             },
        //         },
        //     },
        //     scales: {
        //         x: {
        //             ticks: {
        //                 color: '#495057',
        //             },
        //             grid: {
        //                 color: '#ebedef',
        //             },
        //         },
        //         y: {
        //             ticks: {
        //                 color: '#495057',
        //             },
        //             grid: {
        //                 color: '#ebedef',
        //             },
        //         },
        //     },
        // };

        let pieOptionRank = {
            title: {
                text: 'Biểu đồ phân cấp thứ hạng',
                align: 'center',
                margin: 10,
                offsetX: 0,
                offsetY: 0,
                floating: false,
                style: {
                    fontSize: '14px',
                    fontWeight: 'bold',
                    fontFamily: 'Roboto',
                    color: '#263238',
                },
            },
            chart: {
                width: '150%',
                type: 'pie',
            },
            labels: rankLabel,

            legend: {
                position: 'bottom',
            },
        };

        let pieOptionSS = {
            title: {
                text: 'Biểu đồ phân cấp Kỹ năng mềm',
                align: 'center',
                margin: 10,
                offsetX: 0,
                offsetY: 0,
                floating: false,
                style: {
                    fontSize: '14px',
                    fontWeight: 'bold',
                    fontFamily: 'Roboto',
                    color: '#263238',
                },
            },
            chart: {
                width: '1000px',
                height: '400px',
                type: 'pie',
            },
            labels: softSkillLabel,

            legend: {
                position: 'bottom',
            },
            responsive: [
                {
                    breakpoint: 480,
                    options: {
                        chart: {
                            width: 400,
                        },
                        legend: {
                            position: 'bottom',
                            offsetY: 0,
                        },
                    },
                },
            ],
        };

        let optionNumberJoin = {
            title: {
                text: 'Biểu đồ số người truy cập trang web',
                align: 'center',
                margin: 10,
                offsetX: 0,
                offsetY: 0,
                floating: false,
                style: {
                    fontSize: '14px',
                    fontWeight: 'bold',
                    fontFamily: 'Roboto',
                    color: '#263238',
                },
            },
            chart: {
                id: 'basic-bar',
            },
            fill: {
                colors: ['#87cc00', '#E91E63', '#9C27B0'],
            },
            xaxis: {
                categories: numberJoinLabel,
            },
        };

        let optionNumberRoom = {
            title: {
                text: 'Biểu đồ số phòng được tạo',
                align: 'center',
                margin: 10,
                offsetX: 0,
                offsetY: 0,
                floating: false,
                style: {
                    fontSize: '14px',
                    fontWeight: 'bold',
                    fontFamily: 'Roboto',
                    color: '#263238',
                },
            },
            chart: {
                id: 'basic-bar',
            },
            xaxis: {
                categories: numberRoomLabel,
            },
        };
        return {
            // basicOptions,
            pieOptionRank,
            optionNumberJoin,
            optionNumberRoom,
            pieOptionSS,
        };
    };

    const { optionNumberJoin, pieOptionRank, optionNumberRoom, pieOptionSS } = getLightTheme();

    return (
        <div>
            {/* <div style={{ width: '45%', margin: '0' }}>
                    <Chart type="pie" data={chartData} options={pieOption} style={{ position: 'relative' }} />
                </div>
            <div className="d-flex">
                <label htmlFor="monthpicker" className="font-weight-bold mt-2">
                    Thống kê theo tháng
                </label>
                <div className="col-lg-4 col-xl-2 col-md-4">
                    <Calendar
                        className="field"
                        id="monthpicker"
                        value={date}
                        onChange={useDateChange}
                        view="month"
                        dateFormat="mm/yy"
                    />
                </div>
            </div>
            <div className="d-flex justify-content-between">
                <ReactApexChart className="font-weight-light mt-5" options={pieOption} series={chartData} type="pie" />
                <ReactApexChart className="font-weight-light mt-5" options={pieOption} series={chartData} type="pie" />
            </div>
            <div className="dashboard-card">
                <Chart type="bar" data={basicData} options={basicOptions} />
                <ReactApexChart className="font-weight-light mt-5" options={option} series={barData} type="bar" />
            </div> */}
            <div className="pl-5 pb-5 mt-5 base-1">
                <table>
                    <tbody>
                        <tr>
                            <td>
                                <i className="pi pi-circle-fill" style={{ color: '#848484' }}></i>
                            </td>
                            <td>
                                <label className="ml-4" style={{ lineHeight: '29px', textDecorationLine: 'underline' }}>
                                    Số người sử dụng trang web
                                </label>
                            </td>
                            <td>
                                {up1 === 'up' && (
                                    <div
                                        className="ml-5"
                                        style={{
                                            background: 'rgb(219 249 159)',
                                            boxShadow: '0px 4px 4px rgba(0, 0, 0, 0.25)',
                                            borderRadius: '10px',
                                            width: '100px',
                                            height: '40px',
                                            justifyContent: 'center',
                                        }}
                                    >
                                        <button
                                            style={{
                                                background: 'transparent',
                                                marginLeft: '27px',
                                                marginTop: '6px',
                                                color: '#278A0E',
                                                fontSize: '20px',
                                                cursor: 'default',
                                            }}
                                        >
                                            {numberUser}
                                        </button>
                                        <i
                                            className="pi pi-arrow-up-right"
                                            style={{
                                                color: '#058E23',
                                                position: 'relative',
                                                top: '-7px',
                                                left: '10px',
                                            }}
                                        ></i>
                                    </div>
                                )}
                                {up1 === 'normal' && (
                                    <div
                                        className="ml-5"
                                        style={{
                                            background: 'rgb(255 255 226)',
                                            boxShadow: '0px 4px 4px rgba(0, 0, 0, 0.25)',
                                            borderRadius: '10px',
                                            width: '100px',
                                            height: '40px',
                                            justifyContent: 'center',
                                        }}
                                    >
                                        <button
                                            style={{
                                                background: 'transparent',
                                                marginLeft: '27px',
                                                marginTop: '6px',
                                                color: '#278A0E',
                                                fontSize: '20px',
                                                cursor: 'default',
                                            }}
                                        >
                                            {numberUser}
                                        </button>
                                    </div>
                                )}
                                {up1 === 'down' && (
                                    <div
                                        className="ml-5"
                                        style={{
                                            background: 'rgb(253 172 172)',
                                            boxShadow: '0px 4px 4px rgba(0, 0, 0, 0.25)',
                                            borderRadius: '10px',
                                            width: '100px',
                                            height: '40px',
                                            justifyContent: 'center',
                                        }}
                                    >
                                        <button
                                            style={{
                                                background: 'transparent',
                                                marginLeft: '27px',
                                                marginTop: '6px',
                                                color: '#278A0E',
                                                fontSize: '20px',
                                                cursor: 'default',
                                            }}
                                        >
                                            {numberUser}
                                        </button>
                                        <i
                                            className="pi pi-arrow-down-right"
                                            style={{
                                                color: '#058E23',
                                                position: 'relative',
                                                top: '-7px',
                                                left: '10px',
                                            }}
                                        ></i>
                                    </div>
                                )}
                            </td>
                        </tr>
                        <tr>
                            <td className="pt-4">
                                <i className="pi pi-circle-fill" style={{ color: '#848484' }}></i>
                            </td>
                            <td className="pt-4">
                                <label className="ml-4" style={{ lineHeight: '29px', textDecorationLine: 'underline' }}>
                                    Số phòng được tạo ra
                                </label>
                            </td>
                            <td className="pt-4">
                                {up2 === 'up' && (
                                    <div
                                        className="ml-5"
                                        style={{
                                            background: 'rgb(219 249 159)',
                                            boxShadow: '0px 4px 4px rgba(0, 0, 0, 0.25)',
                                            borderRadius: '10px',
                                            width: '100px',
                                            height: '40px',
                                            justifyContent: 'center',
                                        }}
                                    >
                                        <button
                                            style={{
                                                background: 'transparent',
                                                marginLeft: '27px',
                                                marginTop: '6px',
                                                color: '#278A0E',
                                                fontSize: '20px',
                                                cursor: 'default',
                                            }}
                                        >
                                            {totalRoom}
                                        </button>
                                        <i
                                            className="pi pi-arrow-up-right"
                                            style={{
                                                color: '#058E23',
                                                position: 'relative',
                                                top: '-7px',
                                                left: '10px',
                                            }}
                                        ></i>
                                    </div>
                                )}
                                {up2 === 'normal' && (
                                    <div
                                        className="ml-5"
                                        style={{
                                            background: 'rgb(255 255 226)',
                                            boxShadow: '0px 4px 4px rgba(0, 0, 0, 0.25)',
                                            borderRadius: '10px',
                                            width: '100px',
                                            height: '40px',
                                            justifyContent: 'center',
                                        }}
                                    >
                                        <button
                                            style={{
                                                background: 'transparent',
                                                marginLeft: '27px',
                                                marginTop: '6px',
                                                color: '#278A0E',
                                                fontSize: '20px',
                                                cursor: 'default',
                                            }}
                                        >
                                            {totalRoom}
                                        </button>
                                        <i
                                            className="pi pi-arrow-up-right"
                                            style={{
                                                color: '#058E23',
                                                position: 'relative',
                                                top: '-7px',
                                                left: '10px',
                                            }}
                                        ></i>
                                    </div>
                                )}
                                {up2 === 'down' && (
                                    <div
                                        className="ml-5"
                                        style={{
                                            background: 'rgb(253 172 172)',
                                            boxShadow: '0px 4px 4px rgba(0, 0, 0, 0.25)',
                                            borderRadius: '10px',
                                            width: '100px',
                                            height: '40px',
                                            justifyContent: 'center',
                                        }}
                                    >
                                        <button
                                            style={{
                                                background: 'transparent',
                                                marginLeft: '27px',
                                                marginTop: '6px',
                                                color: '#278A0E',
                                                fontSize: '20px',
                                                cursor: 'default',
                                            }}
                                        >
                                            {totalRoom}
                                        </button>
                                        <i
                                            className="pi pi-arrow-down-right"
                                            style={{
                                                color: '#058E23',
                                                position: 'relative',
                                                top: '-7px',
                                                left: '10px',
                                            }}
                                        ></i>
                                    </div>
                                )}
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div className="base-2 d-flex">
                <div className="number-join-card">
                    <ReactApexChart
                        className="font-weight-light mt-5"
                        options={optionNumberJoin}
                        series={series}
                        type="bar"
                    />
                    <div className="d-flex mb-4 ml-5">
                        <label htmlFor="monthpicker" className="font-weight-bold mt-2">
                            Thống kê trong 7 ngày
                        </label>
                        <div className="col-lg-4 col-xl-3 col-md-4">
                            <Calendar
                                className="field"
                                id="monthpicker"
                                value={dateNumberJoin}
                                onChange={useDateChangeNumberJoin}
                                dateFormat="dd/mm/yy"
                                style={{ width: '500px' }}
                            />
                        </div>
                    </div>
                </div>
                {/* <div className="" style={{ marginLeft: '-26px', marginTop: '43%' }}>
                    <button style={{ width: '50px', height: '50px', background: 'white' }}>
                        <UilSnowflake />
                    </button>
                </div> */}
                <div className="rank-card pl-5">
                    <ReactApexChart
                        className="font-weight-light mt-5"
                        options={pieOptionRank}
                        series={rankData}
                        type="pie"
                    />
                </div>
            </div>
            <div className="" style={{ position: 'absolute', left: '58%', marginTop: '-17px' }}>
                <button style={{ width: '50px', height: '50px', background: 'white', cursor: 'default' }}>
                    <UilSnowflake />
                </button>
            </div>
            <div className="base-3 d-flex">
                <div className="number-join-card mt-5">
                    <ReactApexChart
                        className="font-weight-light mt-5"
                        options={optionNumberRoom}
                        series={roomSeries}
                        type="bar"
                    />
                    <div className="d-flex mb-4 ml-5">
                        <label htmlFor="monthpicker" className="font-weight-bold mt-2">
                            Thống kê theo tháng
                        </label>
                        <div className="col-lg-4 col-xl-3 col-md-4">
                            <Calendar
                                className="field"
                                id="monthpicker"
                                value={date}
                                onChange={useDateChange}
                                view="month"
                                dateFormat="mm/yy"
                                style={{ width: '500px' }}
                            />
                        </div>
                    </div>
                </div>
                <div className="rank-card pl-5">
                    <ReactApexChart
                        className="font-weight-light mt-5"
                        options={pieOptionSS}
                        series={softSkillData}
                        type="pie"
                    />
                </div>
            </div>
        </div>
    );
};

export default PieChartDemo;
