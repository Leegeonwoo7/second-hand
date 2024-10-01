import {useState} from "react";
import {useNavigate} from "react-router-dom";

export default function LoginForm() {
    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const isToken = sessionStorage.getItem('token');

    const handleLogout = () => {
        sessionStorage.removeItem('token');
        localStorage.clear();
        setIsLoggedIn(false);
        navigate("/");
    }

    const handleLoginSubmit = async (e) => {
        e.preventDefault();

        const loginForm = {username, password}

        try {
            const response = await fetch('http://localhost:8080/users/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(loginForm),
            });
            if (response.ok) {
                const data = await response.json();
                sessionStorage.setItem('token', data.token);
                setIsLoggedIn(true);
            }
        } catch (error){
            console.log('ERROR: ', error);
        }
    };

    return (
        <div>
            {isLoggedIn || isToken ? (
                <div>
                    <h1>{username}님 환영합니다 !</h1>
                    <button onClick={handleLogout}>로그아웃</button>
                </div>
            ) : (
                <div>
                    <h1>로그인</h1>
                    <form onSubmit={handleLoginSubmit}>
                        <div>
                            <label>아이디: </label>
                            <input type="text" value={username} onChange={(e) => setUsername(e.target.value)}/>
                        </div>
                        <div>
                            <label>비밀번호: </label>
                            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)}/>
                        </div>
                        <button type="submit">로그인</button>
                    </form>
                </div>
            )
            }
        </div>
    )
};