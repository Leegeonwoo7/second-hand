import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";


export default function ProductDetailView() {
    const {productId} = useParams();
    const [product, setProduct] = useState(null);
    const [quantity, setQuantity] = useState('');
    const [address, setAddress] = useState('');

    const token = sessionStorage.getItem('token');
    const navigate = useNavigate();

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
                console.log(data);
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

    const handleClickOrder = async (e) => {
        e.preventDefault();

        // try {
        //     const response = await fetch('http://localhost:8080/orders/new', {
        //         method: 'POST',
        //         headers: {
        //             'Content-Type': 'application/json',
        //             'Authorization': `Bearer ${token}`,
        //         },
        //         body: JSON.stringify(product.id),
        //     });
        //     if (response.ok) {
        //         const data = await response.json();
        //         const orderId = data.id;
        //         console.log("oderId: " + orderId);
        navigate(`/orders`, {state: {product}});
        //     }
        // } catch (error){
        //     console.error('Error creating order', error);
        // }
    }


    return (
      <div className="product-detail">
          <form onSubmit={handleClickOrder}>
              <h1>{product.name}</h1>
              <img src={product.imageUrl} alt={product.name} className="product-detail-image"/>
              <div className="product-detail-info">
                  <p>{product.description}</p>
                  <p>{product.price}원</p>
              </div>
              <button type="submit">주문하기</button>
          </form>
      </div>
    );
};