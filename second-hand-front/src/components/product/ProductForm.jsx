import {useState} from "react";
import {useNavigate} from "react-router-dom";

export default function ProductForm() {
    const navigate = useNavigate();
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [price, setPrice] = useState('');
    const [quantity, setQuantity] = useState('');

    const token = sessionStorage.getItem('token');
    const product = { name, description, price, quantity }

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await fetch('http://localhost:8080/products/new', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`,
                },
                body: JSON.stringify(product)
            });
            if (response.ok) {
                navigate('/main');
            }
        } catch (error){
            console.log("Error: ", error);
        }
    };

    return (
        <div>
            <h2>상품등록</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>상품이름: </label>
                    <input type="text" value={name} onChange={(e) => setName(e.target.value)}/>
                </div>
                <div>
                    <label>상품설명: </label>
                    <input type="text" value={description} onChange={(e) => setDescription(e.target.value)}/>
                </div>
                <div>
                    <label>가격: </label>
                    <input type="text" value={price} onChange={(e) => setPrice(e.target.value)}/>
                </div>
                <div>
                    <label>개수: </label>
                    <input type="text" value={quantity} onChange={(e) => setQuantity(e.target.value)}/>
                </div>
                <button type="submit">등록하기</button>
            </form>
        </div>
    )
};