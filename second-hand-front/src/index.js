import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    // 개발모드에서 React.StrcitMode를 사용할 경우 버그의 발생을 방지하기위해 useEffect를 두번씩 호출함 프로덕션에서는 한번씩 호출되기때문에 배포시에는 주석을 해제해주어야함.
  // <React.StrictMode>
    <App />
  // </React.StrictMode>
);

reportWebVitals();
