
export default function ProductView({ product }) {
    return (
        <div className="product-card">
            <img src={product.imageUrl} alt={product.name} className="product-image" />
            <div className="product-info">
                <h2>{product.name}</h2>
                <p>{product.description}</p>
                <p>{product.price}원</p>
            </div>
        </div>
    )
}