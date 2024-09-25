import './App.css';
import React from "react";
import { BrowserRouter as Router, Link, Route, Routes } from "react-router-dom";
import JoinForm from "./components/JoinForm";
import KakaoPayButton from "./components/KakaoPayButton";

function App() {
    return (
        <Router>
            <div>
                <nav>
                    <ul>
                        <li>
                            <Link to="/join">회원가입</Link>
                        </li>
                        <li>
                            <Link to="/payment">결제하기</Link>
                        </li>
                    </ul>
                </nav>
            </div>
            <Routes>
                <Route path="/join" element={<JoinForm />} />
                <Route path="/payment" element={<KakaoPayButton />} />
            </Routes>
        </Router>


    )
}

export default App;
