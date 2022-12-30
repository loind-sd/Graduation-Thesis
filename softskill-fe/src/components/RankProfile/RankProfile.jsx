import React, { useEffect, useState } from 'react';
import './RankProfile.scss';
import * as Unicons from '@iconscout/react-unicons';
import { useDispatch, useSelector } from 'react-redux';
import { getRankInfo } from '../../redux/thunks/statistic-thunks';
import { ProgressBar } from 'react-bootstrap';
import { set } from 'lodash';

const RankProfile = () => {
    let name = localStorage.getItem('name');
    const [condition, setCondition] = useState(null);
    const [notiLvUp, setNotiLvUp] = useState('');
    const [rank, setRank] = useState('');
    const dispatch = useDispatch();
    useEffect(() => {
        dispatch(getRankInfo());
    }, [dispatch]);
    const statistic = useSelector((state) => state.rank);
    useEffect(() => {
        if (statistic.rankInfo.rankInfo?.rank === 'Đồng') {
            setNotiLvUp(
                'Làm thêm ' +
                    (statistic.rankInfo.rankInfo?.conditionRank - statistic.rankInfo.rankInfo?.completedTask) +
                    ' nhiệm vụ để tăng hạng Bạc',
            );
            setCondition(
                (statistic.rankInfo.rankInfo?.completedTask / statistic.rankInfo.rankInfo?.conditionRank) * 100,
            );
            document.getElementsByClassName('avatar-frame')[0].style.backgroundImage = "url('/image/dong.png')";
            setRank('Đồng');
        } else if (statistic.rankInfo.rankInfo?.rank === 'Bạc') {
            setNotiLvUp(
                'Làm thêm ' +
                    (statistic.rankInfo.rankInfo?.conditionRank - statistic.rankInfo.rankInfo?.completedTask) +
                    ' nhiệm vụ để tăng hạng Vàng',
            );
            setCondition(
                (statistic.rankInfo.rankInfo?.completedTask / statistic.rankInfo.rankInfo?.conditionRank) * 100,
            );
            document.getElementsByClassName('avatar-frame')[0].style.backgroundImage = "url('/image/bac.png')";
            setRank('Bạc');
        } else if (statistic.rankInfo.rankInfo?.rank === 'Vàng') {
            setNotiLvUp(
                'Làm thêm ' +
                    (statistic.rankInfo.rankInfo?.conditionRank - statistic.rankInfo.rankInfo?.completedTask) +
                    ' nhiệm vụ để tăng hạng Bạch Kim',
            );
            setCondition(
                (statistic.rankInfo.rankInfo?.completedTask / statistic.rankInfo.rankInfo?.conditionRank) * 100,
            );
            document.getElementsByClassName('avatar-frame')[0].style.backgroundImage = "url('=/image/vang.png')";
            setRank('Vàng');
        } else if (statistic.rankInfo.rankInfo?.rank === 'Bạch Kim') {
            setNotiLvUp(
                'Làm thêm ' +
                    (statistic.rankInfo.rankInfo?.conditionRank - statistic.rankInfo.rankInfo?.completedTask) +
                    ' nhiệm vụ để tăng hạng Kim Cương',
            );
            setCondition(
                (statistic.rankInfo.rankInfo?.completedTask / statistic.rankInfo.rankInfo?.conditionRank) * 100,
            );
            document.getElementsByClassName('avatar-frame')[0].style.backgroundImage = "url('/image/bachkim.png')";
            setRank('Bạch Kim');
        } else if (statistic.rankInfo.rankInfo?.rank === 'Kim Cương') {
            setCondition(100);
            document.getElementsByClassName('avatar-frame')[0].style.backgroundImage = "url('/image/kimcuong.png')";
            setRank('Kim Cương');
        }
    }, [statistic]);

    return (
        <div className="profile-rank col-3 px-5 pb-5">
            <div className="profile-container rounded row  justify-content-center">
                <div className="avatar-content p-avatar-image p-avatar-xl p-avatar-circle">
                    <div className="avatar-frame">
                        <div className="avatar-rank"></div>
                    </div>
                </div>
                <div className="rank-info justify-content-center row ">
                    <span className="name col-12 text-center font-weight-bold">Hạng {rank}</span>
                    <h4 className=" col-12 text-center ">
                        Tổng thời gian làm nhiệm vụ: {statistic.rankInfo.rankInfo?.completedTime} tiếng
                    </h4>
                    <div className="info col-6 text-center d-flex align-items-center pt-3">
                        <div className="progress progress-infinite col-11 px-0">
                            <ProgressBar now={condition} />
                        </div>
                        <div className="text-success mr-2">
                            <img src="/image/lv-up.png" className="lv-up" alt="" />
                        </div>
                    </div>
                    <h5 className="text-success pb-3 mt-3">{notiLvUp}</h5>
                    <div className="text-dark col-12 text-center"></div>
                    <div className="list-rank row mt-5  justify-content-center h-100 border p-5 rounded-lg">
                        <div className="col-lg-4 col-md-6 text-center rank">
                            <div className="bronze"></div>
                            <div>Đồng</div>
                        </div>
                        <div className="col-lg-4 col-md-6 text-center rank">
                            <div className="silver"></div>
                            <div>Bạc</div>
                        </div>
                        <div className="col-lg-4 col-md-6 text-center rank">
                            <div className="gold"></div>
                            <div>Vàng</div>
                        </div>
                        <div className="col-sm-12 col-lg-4 col-md-6 text-center rank">
                            <div className="platinum"></div>
                            <div>Bạch Kim</div>
                        </div>
                        <div className="col-sm-12 col-lg-4 col-md-6 text-center rank">
                            <div className="diamond"></div>
                            <div>Kim Cương</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default RankProfile;
