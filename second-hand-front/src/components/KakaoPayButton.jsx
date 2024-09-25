export default function KakaoPayButton() {
    const handlePayment = () => {
        fetch('http://localhost:8080/payment', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                totalAmount: 10000,
                name: '테스트 상품',
            }),
        })
            .then((response) => response.json())
            .then((data) => {
                if (data.next_redirect_pc_url) {
                    window.location.href = data.next_redirect_pc_url;
                }
            });
    };

    return (
        <div>
            <h1>카카오페이 테스트</h1>
            <button onClick={handlePayment}>결제하기</button>
        </div>
    );
};