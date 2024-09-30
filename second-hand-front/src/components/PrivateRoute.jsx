import {Navigate} from "react-router-dom";

const isAuthenticated = () => {
    const token = sessionStorage.getItem('token');
    return token !== null;
};

export default function PrivateRoute({element: Component}) {
    return (
        isAuthenticated() ? Component : <Navigate to="/login" />
    );
};
