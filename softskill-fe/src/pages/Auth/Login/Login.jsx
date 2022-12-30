import './Login.scss';
import React, { useState } from 'react';
import { useHistory } from 'react-router-dom';
import * as Unicons from '@iconscout/react-unicons';
import { InputText } from 'primereact/inputtext';
import { useDispatch, useSelector } from 'react-redux';
import { beforeLogin, login } from '../../../redux/thunks/auth-thunks';
import { forgotPassword } from '../../../redux/thunks/user-thunks';

const Login = () => {
    const dispatch = useDispatch();
    const history = useHistory();

    dispatch(beforeLogin());
    const [condition, setCondition] = useState('');
    const [password, setPassword] = useState('');
    const [forgot, setForgot] = useState(false);
    const [email, setEmail] = useState('');
    const [viewPassword, setViewPassword] = useState('hide');

    let error = useSelector((state) => state.authData.error);

    const onClickForgot = () => {
        dispatch(forgotPassword({ email: email }, history));
    };

    const onClickSignIn = async (event) => {
        event.preventDefault();
        error = {};
        let userData = { condition: condition, password: password };
        dispatch(login(userData, history));
        // window.location.href = 'http://localhost:3000'
    };

    const formForgotPassword = (
        <React.Fragment>
            <div className="d-lg-flex half">
                <div
                    className="bg order-1 order-md-2"
                    style={{
                        backgroundImage: `url('https://app.studytogether.com/_next/static/media/singin1_left.0fdeb7c2.jpg')`,
                    }}
                ></div>
                <div className="contents order-2 order-md-1">
                    <div className="container">
                        <div className="row align-items-center justify-content-center">
                            <div className="col-md-7">
                                <div className="mb-4">
                                    <div
                                        style={{
                                            fontWeight: 'bold',
                                            maxWidth: '14ch',
                                            color: 'rgb(255 111 97)',
                                            fontSize: '2.875rem',
                                            lineHeight: '2.25rem',
                                            marginBottom: '2rem',
                                        }}
                                    >
                                        Quên mật khẩu
                                    </div>
                                </div>
                                <form onSubmit={onClickForgot} className="pb-5">
                                    <div className="form-group d-flex col-md-12 mt-4 pl-3 custom-prime">
                                        <InputText
                                            className="form-control p-4 rounded-pill"
                                            value={email}
                                            type="email"
                                            autoFocus
                                            required
                                            minLength={10}
                                            onChange={(e) => setEmail(e.target.value)}
                                            placeholder="Nhập Email"
                                        />
                                    </div>
                                    <div className="col-md-12 d-flex justify-content-around p-3">
                                        <small style={{ fontSize: '1.02rem' }}>
                                            Sau khi ấn Gửi, vui lòng kiểm tra trong hòm thư của bạn để lấy lại mật khẩu
                                        </small>
                                    </div>
                                    <div className="col-md-12 d-flex justify-content-around p-3">
                                        <button
                                            onClick={() => setForgot(!forgot)}
                                            className="col-md-5 btn btn-secondary p-3 w-50"
                                        >
                                            Trở về
                                        </button>
                                        <button type="submit" className="col-md-5 btn btn-primary p-3 ml-2 w-50">
                                            Gửi
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </React.Fragment>
    );

    const formLogin = (
        <React.Fragment>
            <div className="d-lg-flex half">
                <div
                    className="bg order-1 order-md-2"
                    style={{
                        backgroundImage: `url('https://app.studytogether.com/_next/static/media/singin1_left.0fdeb7c2.jpg')`,
                    }}
                ></div>
                <div className="contents order-2 order-md-1">
                    <div className="container">
                        <div className="row align-items-center justify-content-center">
                            <div className="col-md-7">
                                <div className="mb-4">
                                    <div
                                        style={{
                                            fontWeight: 'bold',
                                            maxWidth: '14ch',
                                            color: 'rgb(255 111 97)',
                                            fontSize: '2.875rem',
                                            lineHeight: '2.25rem',
                                            marginBottom: '2rem',
                                        }}
                                    >
                                        Đăng nhập
                                    </div>
                                    <p className="mb-4">
                                        Chào mừng bạn đến với
                                        <br />{' '}
                                        <strong style={{ fontWeight: 'bold' }}>Cộng đồng phát triển kỹ năng mềm</strong>
                                    </p>
                                </div>
                                <form onSubmit={onClickSignIn}>
                                    <div className="form-group first">
                                        <InputText
                                            className="form-control p-4 rounded-pill"
                                            required
                                            value={condition}
                                            autoFocus
                                            type="text"
                                            minLength={5}
                                            onChange={(e) => setCondition(e.target.value)}
                                            placeholder="Email"
                                        />
                                    </div>
                                    <div className="form-group d-flex custom-prime">
                                        <InputText
                                            className="form-control p-4 rounded-pill position-relative"
                                            required
                                            value={password}
                                            type={viewPassword === 'hide' ? 'password' : 'text'}
                                            minLength={5}
                                            onChange={(e) => setPassword(e.target.value)}
                                            placeholder="Mật khẩu"
                                        />
                                        {viewPassword === 'hide' ? (
                                            <span className="eye-custom" onClick={() => setViewPassword('show')}>
                                                <Unicons.UilEye size="20" />
                                            </span>
                                        ) : (
                                            <span className="eye-custom" onClick={() => setViewPassword('hide')}>
                                                <Unicons.UilEyeSlash size="20" />
                                            </span>
                                        )}
                                    </div>
                                    {error && error.status === '404' && (
                                        <small className="p-error">Tài khoản hoặc mật khẩu không chính xác</small>
                                    )}
                                    <div className="d-flex mb-5 align-items-center mt-2">
                                        <span className="ml-auto forgot-password" onClick={() => setForgot(!forgot)}>
                                            Quên mật khẩu?
                                        </span>
                                    </div>

                                    <input
                                        type="submit"
                                        value="Đăng nhập"
                                        className="btn btn-block btn-primary"
                                        style={{ borderRadius: '0.5rem' }}
                                    />

                                    <span className="d-block text-center my-4 text-muted">&mdash; hoặc &mdash;</span>

                                    <div className="social-login">
                                        <a
                                            href="http://localhost:8080/oauth2/authorize/google"
                                            className="google btn d-flex justify-content-center align-items-center"
                                        >
                                            <Unicons.UilGoogle /> <span className="ml-2">Tiếp tục với Google</span>
                                        </a>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </React.Fragment>
    );

    return (
        <React.Fragment>
            <div className="layout-login">
                <div className="row justify-content-center">
                    <div
                        // className="col-md-5 mr-5"
                        className="w-100"
                    >
                        {forgot ? formForgotPassword : formLogin}
                    </div>
                </div>
            </div>
        </React.Fragment>
    );
    // eslint-disable-next-line no-unreachable
};

export default Login;
