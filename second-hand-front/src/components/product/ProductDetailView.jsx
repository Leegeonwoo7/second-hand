import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";

export default function ProductDetailView(props) {
    const {productId} = useParams();
    const [product, setProduct] = useState(null);
    const token = sessionStorage.getItem('token');

    useEffect(() => {
        async function fetchProductDetails() {
            try{
                const response = await fetch(`http://localhost:8080/products/${productId}`, {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                    },
                });
                if (!response.ok) {
                    throw new Error('상품 상세정보를 불러오는데 실패했습니다.');
                }
                const data = await response.json();
                setProduct(data);
            } catch (error){
                console.error('Error fetching product details', error);
            }
        }

        fetchProductDetails();
    }, [productId]);

    if (!product) {
        return <div>상품 정보를 불러오는중입니다.</div>
    }

    return (
      <div className="product-detail">
          <h1>{product.name}</h1>
          <img src={product.imageUrl} alt={product.name} className="product-detail-image"/>
          <div className="product-detail-info">
              <p>{product.description}</p>
              <p>{product.price}원</p>
          </div>
      </div>
    );
};