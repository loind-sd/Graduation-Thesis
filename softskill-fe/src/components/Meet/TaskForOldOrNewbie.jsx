
import React from 'react'
import { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { getTaskChooseById } from '../../redux/thunks/meeting-thunks'

const TaskForNewbie = ({ data }) => {
    const dispatch = useDispatch();
    useEffect(() => dispatch(getTaskChooseById(data.taskId)), [dispatch]);
    const currentTask = useSelector((state) => state.meetingData?.currentTask);
    return (
        <ul className="list-unstyled components mb-5 py-5 task-newbie">
            <li>

            </li>
        </ul>
    )
}

export default TaskForNewbie;