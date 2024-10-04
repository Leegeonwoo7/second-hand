import './App.css';
import React, {useEffect, useState} from "react";
import {BrowserRouter as Router, Link, Route, Routes, useNavigate} from "react-router-dom";
import JoinForm from "./components/user/JoinForm";
import KakaoPayButton from "./components/payment/KakaoPayButton";
import LoginForm from "./components/user/LoginForm";
import MainProductListView from "./components/product/MainProductListView";
import ProductForm from "./components/product/ProductForm";
import ProductDetailView from "./components/product/ProductDetailView";
import OrderView from "./components/order/OrderView";
import PrivateRoute from "./components/PrivateRoute";

function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        const token = sessionStorage.getItem('token');
        if (token) {
            setIsLoggedIn(true);
        } else {
            setIsLoggedIn(false);
        }
    }, []);

    return (
        <Router>
            <div>
                <nav>
                    <ul>
                        <li>
                            <Link to="/">메인화면</Link>
                        </li>
                        {!isLoggedIn ? (
                            <li>
                                <Link to="/join">회원가입</Link>
                            </li>
                        ) : (
                            <li>
                                <Link to="/login">로그아웃</Link>
                            </li>
                        )}

                        {!isLoggedIn ? (
                            <li>
                                <Link to="/login">로그인</Link>
                            </li>
                        ) : (
                            <li>
                                {/*TODO*/}
                                <Link to="/TODO">내 상점</Link>
                            </li>
                        )}

                        <li>
                            <Link to="/newProduct">상품등록</Link>
                        </li>
                        <li>
                            <Link to="/payment">결제</Link>
                        </li>
                    </ul>
                </nav>
            </div>
            <Routes>
                <Route path="/join" element={<JoinForm />} />
                <Route path="/payment" element={<KakaoPayButton />} />
                <Route path="/login" element={<LoginForm />}/>
                <Route path="/" element={<MainProductListView/>}/>
                <Route path="/newProduct" element={<PrivateRoute element={<ProductForm/>}/>} />
                <Route path="/products/:productId" element={<ProductDetailView/>}/>
                <Route path="/orders" element={<OrderView />}/>
            </Routes>
        </Router>
    )
}

export default App;
