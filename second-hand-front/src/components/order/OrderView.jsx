import {useLocation} from "react-router-dom";
import {useEffect} from "react";

export default function OrderView() {
    const handlePaymentClick = null;
    const routeLocation = useLocation();
    const {product} = routeLocation.state || {};
    console.log(product.name);

    // useEffect(() => {
    //     async function getUserInfo() {
    //         try{
    //             // const response await fetch('http://localhost:8080/users')
    //         }
    //     }
    // })

    return (
        <div>
            <form onSubmit={handlePaymentClick}>
                <div id="order-header">
                    <h3>결제하기</h3>
                    <ul>
                        <li>{product.name}</li>
                        <li>{product.price}</li>
                    </ul>
                </div>

                <div id="order-delivery">
                    <h3>배송지</h3>

                </div>

                <div id="order-payment">

                </div>

                <div id="order-total-amount">

                </div>

                <div>
                    <button type="submit">결제하기</button>
                </div>
            </form>
        </div>
    )
};