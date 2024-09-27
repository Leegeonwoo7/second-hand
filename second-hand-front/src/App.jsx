import './App.css';
import React from "react";
import { BrowserRouter as Router, Link, Route, Routes } from "react-router-dom";
import JoinForm from "./components/user/JoinForm";
import KakaoPayButton from "./components/payment/KakaoPayButton";
import LoginForm from "./components/user/LoginForm";
import MainProductListView from "./components/product/MainProductListView";

function App() {
    return (
        <Router>
            <div>
                <nav>
                    <ul>
                        <li>
                            <Link to="/main">메인화면</Link>
                        </li>
                        <li>
                            <Link to="/join">회원가입</Link>
                        </li>
                        <li>
                            <Link to="/payment">결제</Link>
                        </li>
                        <li>
                            <Link to="/login">로그인</Link>
                        </li>
                    </ul>
                </nav>
            </div>
            <Routes>
                <Route path="/join" element={<JoinForm />} />
                <Route path="/payment" element={<KakaoPayButton />} />
                <Route path="/login" element={<LoginForm />}/>
                <Route path="/main" element={<MainProductListView/>}/>
            </Routes>
        </Router>


    )
}

export default App;
