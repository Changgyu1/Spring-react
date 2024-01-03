import React, { useState, useEffect } from "react";
import axios from "axios";

const App = () => {
  const [products, setProducts] = useState([]);
  const [newProduct, setNewProduct] = useState({ name: "", price: 0 });
  const [isEditingProduct, setIsEditingProduct] = useState(null);
  const [isEditing, setIsEditing] = useState(false);

  useEffect(() => {
    axios
      .get("http://localhost:8081/api/product/item")
      .then((response) => {
        setProducts(response.data);
      })
      .catch((error) => {
        console.error(":", error);
      });
  }, []);

  const handleAddProduct = () => {
    axios
      .post("http://localhost:8081/api/product/add", newProduct)
      .then((response) => {
        setProducts((prevProducts) => [...prevProducts, response.data]);
        setNewProduct({ name: "", price: 0 });
      })
      .catch((error) => {
        console.error("추가 에러:", error);
      });
  };
  const handleDeleteProduct = (id) => {
    axios
      .delete(`http://localhost:8081/api/product/delete/${id}`)
      .then((response) => {
        setProducts((prevProduct) =>
          // 현재 목록에서 삭제할 제품을 제외하고 새로운 배열을 생성
          // 삭제할 제품의 ID와 다른 제품들만 필터로 남겨주겠다 해주는 것
          prevProduct.filter((product) => product.id !== id)
        );
      })
      .catch((error) => {
        console.error("error", error);
      });
  };

  // handleEditProduct
  const handleEditProduct = (product) => {
    setIsEditingProduct(product);
    setNewProduct({ name: product.name, price: product.price });
    setIsEditing(true);
  };
  // handleUpdateProduct

  // handleCancelProduct
  return (
    <div>
      <h2>상품 리스트</h2>
      <ul>
        {products.map((product) => (
          <li key={product.id}>
            ``
            {product.name} - ${product.price}
            <button onClick={() => handleDeleteProduct(product.id)}>
              삭제하기
            </button>
            <button onClick={() => handleEditProduct(product)}>수정하기</button>
          </li>
        ))}
      </ul>

      <h2>{isEditing ? "상품 수정" : "상품 추가"}</h2>
      <label>상품명:</label>
      <input
        type="text"
        value={newProduct.name}
        onChange={(e) => setNewProduct({ ...newProduct, name: e.target.value })}
      />

      <label>가격:</label>
      <input
        type="number"
        value={newProduct.price}
        onChange={(e) =>
          setNewProduct({ ...newProduct, price: e.target.valueAsNumber })
        }
      />
    </div>
  );
};

export default App;
