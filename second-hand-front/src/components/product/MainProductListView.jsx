import {useEffect, useState} from "react";
import './ProductList.css';
import ProductView from "./ProductView";

export default function MainProductListView() {
    const [products, setProducts] = useState([]);

    useEffect(() => {
        async function fetchProducts() {
            try {
                const response = await fetch('http://localhost:8080/products');
                if (!response.ok) {
                    throw new Error('상품을 불러오는데 실패했습니다.');
                }
                const data = await response.json();
                setProducts(data);
            } catch (error){
                console.error('Error fetching products', error);
            }
        }

        fetchProducts();
    }, []);

    return (
        <div>
            <h1>상품목록</h1>
            <div className="product-list">
                {products.map((product) => (
                    <ProductView key={product.id} product={product}/>
                ))}
            </div>
        </div>
    )
};