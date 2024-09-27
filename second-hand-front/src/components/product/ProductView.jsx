import {useNavigate} from "react-router-dom";

export default function ProductView({ product }) {
    const navigate = useNavigate()

    const handleClick = () => {
        navigate(`/products/${product.id}`);
    }

    return (
        <div className="product-card" onClick={handleClick}>
            <img src={product.imageUrl} alt={product.name} className="product-image" />
            <div className="product-info">
                <h2>{product.name}</h2>
                <p>{product.description}</p>
                <p>{product.price}원</p>
            </div>
        </div>
    )
}