import {useState} from "react";
import {useNavigate} from "react-router-dom";

export default function ProductForm() {
    const navigate = useNavigate();
    const [name, setName] = useState(localStorage.getItem('name') || '');
    const [description, setDescription] = useState(localStorage.getItem('description') ||'');
    const [price, setPrice] = useState(localStorage.getItem('price') || '');
    const token = sessionStorage.getItem('token');
    const product = { name, description, price }

    const handleNameChange = (e) => {
        setName(e.target.value);
        localStorage.setItem('name', e.target.value);
    }

    const handleDescriptionChange = (e) => {
        setDescription(e.target.value);
        localStorage.setItem('description', e.target.value);
    }

    const handlePriceChange = (e) => {
        setPrice(e.target.value);
        localStorage.setItem('price', e.target.value);
    }

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

        localStorage.removeItem('name');
        localStorage.removeItem('price');
        localStorage.removeItem('description');
    };

    return (
        <div>
            <h2>상품등록</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>상품이름: </label>
                    <input type="text" value={name} onChange={handleNameChange}/>
                </div>
                <div>
                    <label>상품설명: </label>
                    <input type="text" value={description} onChange={handleDescriptionChange}/>
                </div>
                <div>
                    <label>가격: </label>
                    <input type="text" value={price} onChange={handlePriceChange}/>
                </div>
                <button type="submit">등록하기</button>
            </form>
        </div>
    )
};