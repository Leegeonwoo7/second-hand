import {useState} from "react";

export default function JoinForm() {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const [emailSend, setEmailSend] = useState(false);
    const [emailCertification, setEmailCertification] = useState(false);
    const [certificationCode, setCertificationCode] = useState('');
    const [codeCheckMessage, setCodeCheckMessage] = useState('');


    const handleSubmit = (e) => {
        e.preventDefault();

        if (!emailCertification){
            setMessage('이메일 인증을 완료해주세요.');
            return;
        }

        const user = { username, email, password }

        fetch('http://localhost:8080/users/join', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(user),
        })
            .then(response => {
                console.log('응답: ', response)
            })
            .catch((error) => {
                console.log('ERROR: ', error);
            })
    }

    const handleEmailCertification = () => {
        fetch('http://localhost:8080/users/email-certification', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email }),
        })
            .then((response) => response.status)
            .then((data) => {
                if (data === 200) {
                    setEmailSend(true);
                }
            })
            .catch((error) => {
            console.log('ERROR: ', error)
        })
    }

    const handleCheckCode = () => {
        fetch('http://localhost:8080/users/code-check', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email, certificationCode
            })
        })
            .then((response) => response.status)
            .then((data) => {
                if (data === 200) {
                    setEmailCertification(true)
                    setCodeCheckMessage('이메일 인증이 확인되었습니다.');
                    setCertificationCode('');
                }
            })
            .catch((error) => {
                console.log('ERROR: ', error)
            })
    }

    return (
        <div>
            <h2>회원가입</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>이름: </label>
                    <input type="text" value={username} onChange={(e) => setUsername(e.target.value)}/>
                </div>
                <div>
                    <label>이메일: </label>
                    <input type="email" value={email} onChange={(e) => setEmail(e.target.value)}/>
                    <button type="button" onClick={handleEmailCertification}>이메일 인증</button>
                </div>
                {emailSend && (
                    <div>
                        <label>인증 코드: </label>
                        <input type="text" value={certificationCode} onChange={(e) => setCertificationCode(e.target.value)}/>
                        <button type="button" onClick={handleCheckCode}>인증하기</button>
                    </div>
                )}
                {codeCheckMessage && (
                    <div>
                        <p>이메일 인증이 확인되었습니다.</p>
                    </div>
                )}
                <div>
                    <label>비밀번호: </label>
                    <input type="password" value={password} onChange={(e) => setPassword(e.target.value)}/>
                </div>
                <button type="submit">가입하기</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
};