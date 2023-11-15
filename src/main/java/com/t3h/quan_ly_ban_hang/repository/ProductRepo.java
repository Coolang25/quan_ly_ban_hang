package com.t3h.quan_ly_ban_hang.repository;

import com.t3h.quan_ly_ban_hang.entities.Products;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
@Transactional
public interface ProductRepo extends JpaRepository<Products, Long> {
    List<Products> findAllByNameContaining(String name);

    @Query(value = "select * from products p order by p.created_time desc limit 8", nativeQuery = true)
        List<Products> findNewProduct();

    List<Products> findProductsByPriceLevelContainingAndNameContaining(String priceLevel, String keyWord);
    List<Products> findProductsByPriceLevelContainingAndNameContainingOrderByCreatedTimeDesc(String priceLevel, String keyWord);
    List<Products> findProductsByPriceLevelContainingAndNameContainingOrderByPriceAsc(String priceLevel, String keyWord);
    List<Products> findProductsByPriceLevelContainingAndNameContainingOrderByPriceDesc(String priceLevel, String keyWord);

    List<Products> findProductsByCategoryIdAndPriceLevelContainingAndNameContaining(Long id, String priceLevel, String keyWord);
    List<Products> findProductsByCategoryIdAndPriceLevelContainingAndNameContainingOrderByCreatedTimeDesc(Long id, String priceLevel, String keyWord);
    List<Products> findProductsByCategoryIdAndPriceLevelContainingAndNameContainingOrderByPriceAsc(Long id, String priceLevel, String keyWord);
    List<Products> findProductsByCategoryIdAndPriceLevelContainingAndNameContainingOrderByPriceDesc(Long id, String priceLevel, String keyWord);

    List<Products> findProductsByBrandIdAndPriceLevelContainingAndNameContaining(Long id, String priceLevel, String keyWord);
    List<Products> findProductsByBrandIdAndPriceLevelContainingAndNameContainingOrderByCreatedTimeDesc(Long id, String priceLevel, String keyWord);
    List<Products> findProductsByBrandIdAndPriceLevelContainingAndNameContainingOrderByPriceAsc(Long id, String priceLevel, String keyWord);
    List<Products> findProductsByBrandIdAndPriceLevelContainingAndNameContainingOrderByPriceDesc(Long id, String priceLevel, String keyWord);
}
