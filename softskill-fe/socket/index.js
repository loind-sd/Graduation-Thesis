import { Server } from "socket.io";

const io = new Server({
    cors: {
        origin: "http://localhost:3000",
    },
});

let onlineUsers = [];
const addNewUser = (userId, socketId, arr) => {
    !arr.some((user) => user.userId === userId) && arr.push({ userId, socketId });
};

const removeUser = (socketId, arr) => {
    arr = arr.filter((user) => user.socketId !== socketId);
};

const getUser = (userId, arr) => {
    return arr.find((user) => user.userId === userId);
};

// const sendUserSocket = onlineUsers.get(data.to);

io.on("connection", (socket) => {
    process.setMaxListeners(0);
    global.chatSocket = socket;
    socket.on("add-user", (userId) => {
        addNewUser(userId, socket.id, onlineUsers);

        // if (getUser(22) && getUser(22) !== undefined) {
        //     console.log('bon t toi day');
        //     console.log(getUser(22));
        //     io.to(getUser(22).socketId).emit('hello', 'this is test thoi');
        // }
    });
    console.log("someone has connected");

    // room chat
    socket.on('join-room-chat', (data) => {
        let rooms = socket.rooms;
        let a = Array.from(rooms);
        for (let index = 0; index < a.length; index++) {
            const element = a[index];
            if (index !== 0) {
                socket.leave(element);
            }
        }
        socket.join(data.room);
    });

    socket.on("out-room", (data) => {
        socket.leave(data.roomCode);
    });

    socket.on("sendMessage", (data) => {
        console.log("someone has sended msg");
        socket.broadcast
            .to(data.currentChat.roomCode)
            .emit("msg-recieve", {
                chats: data.chats,
                currentChat: data.currentChat,
            });
        // socket.broadcast.emit('mini-noti', data.currentChat);
    });

    socket.on("send-mini-noti", (data) => {
        socket.broadcast.emit("mini-noti", data.currentChat);
    });

    socket.on("kick-user", (data) => {
        const sendUserSocket = getUser(data.userId, onlineUsers);
        io.to(sendUserSocket.socketId).emit("out-room-receive", {
            roomId: data.roomId,
            roomCode: data.roomCode,
        });
    });

    socket.on('addNewContact', async (data) => {
        console.log('someone has add new contact');
        let member = [...data.member];
        for (let index = 0; index < member.length; index++) {
            const element = member[index];
            const sendUserSocket = getUser(element, onlineUsers);
            if (sendUserSocket && sendUserSocket !== undefined) {
                // let sockets = await io.in(sendUserSocket.socketId).fetchSockets();
                // for (const sk of sockets) {
                //     sk.join(sendUserSocket.socketId);
                //     // sk.emit('cailgt');
                //     // console.log('e ra roi');
                // }
                // socket.join(sendUserSocket.socketId);
                // io.to(sendUserSocket.socketId).emit('contact-receive', data.newContact);
                // socket.leave(sendUserSocket.socketId);
                // for (const sk of sockets) {
                //     sk.leave(sendUserSocket.socketId);
                // }

                // io.in(sendUserSocket.socketId).socketsJoin('abc');
                // socket.join('abc');
                io.to(sendUserSocket.socketId).emit("contact-receive", data.newContact);
            }
        }
    });

    socket.on("add-members", (data) => {
        let member = [...data.member];
        for (let index = 0; index < member.length; index++) {
            const element = member[index];
            const sendUserSocket = getUser(element, onlineUsers);
            if (sendUserSocket && sendUserSocket !== undefined) {
                io.to(sendUserSocket.socketId).emit("add-member-receive", data.contact);
            }
        }
    });

    socket.on("deleteMessage", (data) => {
        console.log("someone has delete msg");
        socket.to(data.currentChat.roomCode).emit("msg-recieve-delete", data.id);
    });

    socket.on("updateMessage", (data) => {
        console.log("someone has update msg");
        socket.broadcast
            .to(data.currentChat.roomCode)
            .emit("msg-recieve-update", data.chats);
    });

    socket.on("disconnect", () => {
        console.log("someone has disconnected");
        removeUser(socket.id, onlineUsers);
    });

    //  room meeting
    socket.on("join-room-meeting", (dataJoin) => {
        console.log("join meeting");
        let index = 1;
        let checkUserIsNew = false;
        let roomBefore = io.sockets.adapter.rooms.get(dataJoin.room);

        if (roomBefore !== undefined && !roomBefore.has(socket.id)) {
            checkUserIsNew = true;
        }
        let totalVote = 0;
        socket.join(dataJoin.room);
        let roomAfter = io.sockets.adapter.rooms.get(dataJoin.room);
        // let userInRoom = io.sockets.adapter.users.get(dataJoin.userId);
        if (dataJoin.isMaster == true) {
            io.sockets.adapter.rooms[dataJoin.room + 'roomMaster'] = socket.id;
        }

        if (checkUserIsNew === true) {
            console.log("join-after", socket.id);
            io.to(socket.id).emit("join-after", {
                actionVote: io.sockets.adapter.rooms[dataJoin.room + "actionEvent"],
                task: io.sockets.adapter.rooms[dataJoin.room + "tasks"],
            });
        }

        socket.on("update-task-choose", (data) => {
            io.sockets.adapter.rooms[dataJoin.room + "taskIdChoose"] = data.taskChooseId;
        });

        // console.log(roomAfter.has(socket.id));
        if (dataJoin?.members?.length != 0) {
            io.sockets.adapter.rooms[dataJoin.room + "members"] = dataJoin.members;
            let countComplete = 0,
                countCancel = 0;
            dataJoin?.members?.forEach((member) => {
                if (member.isClickCompleted == true) {
                    countComplete++;
                }
                if (member.isClickCancel == true) {
                    countCancel++;
                }
            });
            io.sockets.adapter.rooms[dataJoin.room + "countComplete"] =
                countComplete == 0
                    ? 0
                    : (countComplete / dataJoin?.members?.length) * 100;
            io.sockets.adapter.rooms[dataJoin.room + "countCancel"] =
                countCancel == 0 ? 0 : (countCancel / dataJoin?.members?.length) * 100;

            socket.to(dataJoin.room).emit("count-complete-init", {
                countComplete: io.sockets.adapter.rooms[dataJoin.room + "countCancel"],
                countCancel: io.sockets.adapter.rooms[dataJoin.room + "countCancel"],
            });
        }
        socket.on("reset-count-complete-init", () => {
            io.sockets.adapter.rooms[dataJoin.room + "countCancel"] = 0;
            io.sockets.adapter.rooms[dataJoin.room + "countCancel"] = 0;
            io.sockets.adapter.rooms[dataJoin.room + "actionEvent"] = "none";
        });
        socket.on("action-event-room", (data) => {
            process.setMaxListeners(0);
            console.log("action-event-room");
            totalVote = 0;
            io.sockets.adapter.rooms[dataJoin.room + "tasks"] = data.tasksToChoose;
            io.sockets.adapter.rooms[dataJoin.room + "taskIdChoose"] = null;
            if (data.actionEvent === "vote") {
                var counter = 30;
                var countdown = setInterval(function () {
                    counter--;
                    if (counter === 0 || totalVote === roomAfter?.size) {
                        io.in(dataJoin.room).emit("counter", {
                            counterTime: counter,
                            userCount: roomAfter?.size,
                            voteStatus: "done",
                        });
                        clearInterval(countdown);
                    } else {
                        io.in(dataJoin.room).emit("counter", {
                            counterTime: counter,
                            userCount: roomAfter?.size,
                            voteStatus: "doing",
                        });
                    }
                }, 1000);
            }

            socket.broadcast.to(dataJoin.room).emit("action-event-room-change", {
                actionEvent: data.actionEvent,
                tasksToChoose: data.tasksToChoose,
            });
        });

        socket.on("sent-rq-mission", (data) => {
            index++;
            process.setMaxListeners(0);
            console.log("sent-rq-mission");
            socket
                .to(io.sockets.adapter.rooms[dataJoin.room + "roomMaster"])
                .emit("mission-rq-receive", {
                    roomId: data.roomId,
                    taskId: data.taskId,
                    softSkillId: data.softSkillId,
                    socketId: socket.id,
                    name: data.name,
                });
        });
        socket.on("result-sent-rq", (data) => {
            process.setMaxListeners(0);
            console.log("result-sent-rq");
            socket.to(data.socketId).emit("receive-rq-result", {
                result: data.result,
            });
        });

        socket.on("vote-task", (data) => {
            io.sockets.adapter.rooms[dataJoin.room + "tasks"] = data.tasksUpdate;
            io.to(dataJoin.room).emit("update-vote", {
                tasksUpdate: data.tasksUpdate,
            });
        });

        socket.on("update-total-user", (data) => {
            totalVote = data.total;
        });

        socket.on("change-action-event", (data) => {
            io.sockets.adapter.rooms[dataJoin.room + "actionEvent"] =
                data.actionEvent;
            io.sockets.adapter.rooms[dataJoin.room + "taskIdChoose"] =
                data.taskIdChoose;

            io.to(socket.id).emit("update-action-event", {
                taskIdChoose: io.sockets.adapter.rooms[dataJoin.room + "taskIdChoose"],
                actionEvent: data.actionEvent,
            });
        });

        socket.on("change-lock-room-in-active-room", (data) => {
            socket.broadcast.emit("update-lock-room-receive", {
                changeLock: data.changeLock,
                roomId: data.roomId,
            });
        });

        socket.on("change-lock-room", (data) => {
            io.to(dataJoin.room).emit("update-lock-room", {
                changeLock: data.changeLock,
            });
        });

        socket.on("members-in-room", (data) => {
            console.log("members-in-room");
            let countComplete = 0,
                countCancel = 0;
            data?.forEach((member) => {
                io.sockets.adapter.rooms[dataJoin.room + "countComplete"] = 0;
                io.sockets.adapter.rooms[dataJoin.room + "countCancel"] = 0;
                if (member.isClickCompleted == true) {
                    countComplete++;
                }
                if (member.isClickCancel == true) {
                    countCancel++;
                }
            });
            io.sockets.adapter.rooms[dataJoin.room + "countComplete"] =
                countComplete == 0 ? 0 : (countComplete / data?.length) * 100;
            io.sockets.adapter.rooms[dataJoin.room + "countCancel"] =
                countCancel == 0 ? 0 : (countCancel / data?.length) * 100;
            io.sockets.adapter.rooms[dataJoin.room + "members"] = data;
            io.to(dataJoin.room).emit("count-complete-init", {
                countComplete:
                    io.sockets.adapter.rooms[dataJoin.room + "countComplete"] == undefined
                        ? 0
                        : io.sockets.adapter.rooms[dataJoin.room + "countComplete"],
                countCancel:
                    io.sockets.adapter.rooms[dataJoin.room + "countCancel"] == undefined
                        ? 0
                        : io.sockets.adapter.rooms[dataJoin.room + "countCancel"],
                taskIdChoose: io.sockets.adapter.rooms[dataJoin.room + "taskIdChoose"],
            });
        });
    });
});
io.listen(5000);
