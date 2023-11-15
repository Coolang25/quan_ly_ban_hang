package com.t3h.quan_ly_ban_hang.service;

import com.t3h.quan_ly_ban_hang.dto.ProductImageDto;
import com.t3h.quan_ly_ban_hang.dto.ProductSizeDto;
import com.t3h.quan_ly_ban_hang.entities.*;
import com.t3h.quan_ly_ban_hang.dto.ProductDto;
import com.t3h.quan_ly_ban_hang.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class ProductService {

    @Autowired
    ProductRepo productRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    BrandRepo brandRepo;
    @Autowired
    ProductImageRepo productImageRepo;
    @Autowired
    SizeRepo sizeRepo;
    @Autowired
    ProductSizeRepo productSizeRepo;
    @Autowired
    ModelMapper modelMapper;


    public List<ProductDto> findProductByName (String name) {
        List<Products> list = new ArrayList<>();
        if (StringUtils.isEmpty(name))
            list = productRepo.findAll();
        else
            list = productRepo.findAllByNameContaining(name);
        return list.stream().map(products -> modelMapper.map(products, ProductDto.class)).collect(Collectors.toList());
    }

    public ProductDto get(Long id) {
        ProductDto productDto = new ProductDto();
        Integer sold = 0;
        Products product = productRepo.findById(id).orElse(null);
        if (product == null) {
            return null;
        } else {
            List<ProductImages> images = productImageRepo.findAllByProductId(id);
            List<ProductSize> sizes = productSizeRepo.findAllByProductId(id);
            for (ProductSize p : sizes) {
                sold = sold + p.getSold();
            }
            BeanUtils.copyProperties(product, productDto);
            productDto.setImages(images.stream().map(productImages -> modelMapper.map(productImages, ProductImageDto.class)).collect(Collectors.toList()));
            productDto.setSizeList(sizes.stream().map(size -> modelMapper.map(size, ProductSizeDto.class)).collect(Collectors.toList()));
            for (ProductSizeDto ps : productDto.getSizeList()) {
                ps.setSizeName(sizeRepo.findById(ps.getSizeId()).orElse(null).getName());
            }
            productDto.setSold(sold);
            return productDto;
        }

    }

    public ProductDto create(ProductDto productDto) {
        Products product = new Products();
        if (productDto.getPrice() < 500000) productDto.setPriceLevel("level 1");
        else if ((500000 <= productDto.getPrice()) && (productDto.getPrice() < 1000000)) productDto.setPriceLevel("level 2");
        else if ((1000000 <= productDto.getPrice()) && (productDto.getPrice() < 3000000)) productDto.setPriceLevel("level 3");
        else if (3000000 <= productDto.getPrice()) productDto.setPriceLevel("level 4");

        BeanUtils.copyProperties(productDto, product);
        // Lưu vào bảng product để Lấy thông tin primarykey (ID)
        productRepo.save(product);
        productDto.setId(product.getId());

        productSizeRepo.deleteAllByProductId(productDto.getId());
        productImageRepo.deleteAllByProductId(productDto.getId());

        if (!CollectionUtils.isEmpty(productDto.getImages())) {
            List<ProductImageDto> productImageDtos = productDto.getImages();
            for (ProductImageDto productImageDto: productImageDtos) {
                if (productImageDto.getId() == null) productImageDto.setProductId(product.getId());
            }
            productImageRepo.saveAll(productImageDtos.stream().map(productImageDto -> modelMapper.map(productImageDto, ProductImages.class)).collect(Collectors.toList()));
        }


        if (!CollectionUtils.isEmpty(productDto.getSizeList())) {
            List<ProductSizeDto> productSizes = productDto.getSizeList();
            for (ProductSizeDto productSizeDto: productSizes) {
                if (productSizeDto.getId() == null)
                    productSizeDto.setProductId(product.getId());
            }
            productSizeRepo.saveAll(productSizes.stream().map(productSize -> modelMapper.map(productSize, ProductSize.class)).collect(Collectors.toList()));
        }
        return productDto;
    }

    public ProductDto update(Long id, ProductDto productDto) {
        Products product = productRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        BeanUtils.copyProperties(productDto, product);
        productRepo.save(product);
        return productDto;
    }

    public void delete(Long id) {
        productRepo.deleteById(id);
    }

    public Page<ProductDto> findProductsByPriceLevelContainingAndNameContaining(String priceLevel, String keyWord, int pageNumber){
        List<Products> products = productRepo.findProductsByPriceLevelContainingAndNameContaining(priceLevel, keyWord);
        List<ProductDto> list = products.stream().map(products1 -> modelMapper.map(products1, ProductDto.class)).collect(Collectors.toList());

        for (ProductDto productDto : list) {
            int sold = 0;
            List<ProductSize> sizes = productSizeRepo.findAllByProductId(productDto.getId());
            for (ProductSize p : sizes) {
                sold = sold + p.getSold();
            }
            productDto.setSold(sold);
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, 9);
        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > list.size() ? list.size() : start + pageable.getPageSize();
        List<ProductDto> subList = list.subList(start, end);
        Page<ProductDto> pageProduct  = new PageImpl<>(subList, pageable, list.size());
        return pageProduct;
    }

    public Page<ProductDto> findProductsByPriceLevelContainingAndNameContainingAndOrderByCreatedTimeDesc(String priceLevel, String keyWord, int pageNumber){
        List<Products> products = productRepo.findProductsByPriceLevelContainingAndNameContainingOrderByCreatedTimeDesc(priceLevel, keyWord);
        List<ProductDto> list = products.stream().map(products1 -> modelMapper.map(products1, ProductDto.class)).collect(Collectors.toList());

        for (ProductDto productDto : list) {
            int sold = 0;
            List<ProductSize> sizes = productSizeRepo.findAllByProductId(productDto.getId());
            for (ProductSize p : sizes) {
                sold = sold + p.getSold();
            }
            productDto.setSold(sold);
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, 9);
        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > list.size() ? list.size() : start + pageable.getPageSize();
        List<ProductDto> subList = list.subList(start, end);
        Page<ProductDto> pageProduct  = new PageImpl<>(subList, pageable, list.size());
        return pageProduct;
    }

    public Page<ProductDto> findProductsByPriceLevelContainingAndNameContainingAndOrderByPriceAsc(String priceLevel, String keyWord, int pageNumber){
        List<Products> products = productRepo.findProductsByPriceLevelContainingAndNameContainingOrderByPriceAsc(priceLevel, keyWord);
        List<ProductDto> list = products.stream().map(products1 -> modelMapper.map(products1, ProductDto.class)).collect(Collectors.toList());

        for (ProductDto productDto : list) {
            int sold = 0;
            List<ProductSize> sizes = productSizeRepo.findAllByProductId(productDto.getId());
            for (ProductSize p : sizes) {
                sold = sold + p.getSold();
            }
            productDto.setSold(sold);
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, 9);
        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > list.size() ? list.size() : start + pageable.getPageSize();
        List<ProductDto> subList = list.subList(start, end);
        Page<ProductDto> pageProduct  = new PageImpl<>(subList, pageable, list.size());
        return pageProduct;
    }

    public Page<ProductDto> findProductsByPriceLevelContainingAndNameContainingAndOrderByPriceDesc(String priceLevel, String keyWord, int pageNumber){
        List<Products> products = productRepo.findProductsByPriceLevelContainingAndNameContainingOrderByPriceDesc(priceLevel, keyWord);
        List<ProductDto> list = products.stream().map(products1 -> modelMapper.map(products1, ProductDto.class)).collect(Collectors.toList());

        for (ProductDto productDto : list) {
            int sold = 0;
            List<ProductSize> sizes = productSizeRepo.findAllByProductId(productDto.getId());
            for (ProductSize p : sizes) {
                sold = sold + p.getSold();
            }
            productDto.setSold(sold);
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, 9);
        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > list.size() ? list.size() : start + pageable.getPageSize();
        List<ProductDto> subList = list.subList(start, end);
        Page<ProductDto> pageProduct  = new PageImpl<>(subList, pageable, list.size());
        return pageProduct;
    }

    public Page<ProductDto> findProductsByCategoryIdAndPriceLevelContainingAndNameContaining(Long id, String priceLevel, String keyWord, int pageNumber){
        List<Products> products = productRepo.findProductsByCategoryIdAndPriceLevelContainingAndNameContaining(id, priceLevel, keyWord);
        List<ProductDto> list = products.stream().map(products1 -> modelMapper.map(products1, ProductDto.class)).collect(Collectors.toList());

        for (ProductDto productDto : list) {
            int sold = 0;
            List<ProductSize> sizes = productSizeRepo.findAllByProductId(productDto.getId());
            for (ProductSize p : sizes) {
                sold = sold + p.getSold();
            }
            productDto.setSold(sold);
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, 9);
        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > list.size() ? list.size() : start + pageable.getPageSize();
        List<ProductDto> subList = list.subList(start, end);
        Page<ProductDto> pageProduct  = new PageImpl<>(subList, pageable, list.size());
        return pageProduct;
    }

    public Page<ProductDto> findProductsByCategoryIdAndPriceLevelContainingAndNameContainingOrderByCreatedTimeDesc(Long id, String priceLevel, String keyWord, int pageNumber){
        List<Products> products = productRepo.findProductsByCategoryIdAndPriceLevelContainingAndNameContainingOrderByCreatedTimeDesc(id, priceLevel, keyWord);
        List<ProductDto> list = products.stream().map(products1 -> modelMapper.map(products1, ProductDto.class)).collect(Collectors.toList());

        for (ProductDto productDto : list) {
            int sold = 0;
            List<ProductSize> sizes = productSizeRepo.findAllByProductId(productDto.getId());
            for (ProductSize p : sizes) {
                sold = sold + p.getSold();
            }
            productDto.setSold(sold);
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, 9);
        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > list.size() ? list.size() : start + pageable.getPageSize();
        List<ProductDto> subList = list.subList(start, end);
        Page<ProductDto> pageProduct  = new PageImpl<>(subList, pageable, list.size());
        return pageProduct;
    }

    public Page<ProductDto> findProductsByCategoryIdAndPriceLevelContainingAndNameContainingOrderByPriceAsc(Long id, String priceLevel, String keyWord, int pageNumber){
        List<Products> products = productRepo.findProductsByCategoryIdAndPriceLevelContainingAndNameContainingOrderByPriceAsc(id, priceLevel, keyWord);
        List<ProductDto> list = products.stream().map(products1 -> modelMapper.map(products1, ProductDto.class)).collect(Collectors.toList());

        for (ProductDto productDto : list) {
            int sold = 0;
            List<ProductSize> sizes = productSizeRepo.findAllByProductId(productDto.getId());
            for (ProductSize p : sizes) {
                sold = sold + p.getSold();
            }
            productDto.setSold(sold);
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, 9);
        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > list.size() ? list.size() : start + pageable.getPageSize();
        List<ProductDto> subList = list.subList(start, end);
        Page<ProductDto> pageProduct  = new PageImpl<>(subList, pageable, list.size());
        return pageProduct;
    }

    public Page<ProductDto> findProductsByCategoryIdAndPriceLevelContainingAndNameContainingOrderByPriceDesc(Long id, String priceLevel, String keyWord, int pageNumber){
        List<Products> products = productRepo.findProductsByCategoryIdAndPriceLevelContainingAndNameContainingOrderByPriceDesc(id, priceLevel, keyWord);
        List<ProductDto> list = products.stream().map(products1 -> modelMapper.map(products1, ProductDto.class)).collect(Collectors.toList());

        for (ProductDto productDto : list) {
            int sold = 0;
            List<ProductSize> sizes = productSizeRepo.findAllByProductId(productDto.getId());
            for (ProductSize p : sizes) {
                sold = sold + p.getSold();
            }
            productDto.setSold(sold);
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, 9);
        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > list.size() ? list.size() : start + pageable.getPageSize();
        List<ProductDto> subList = list.subList(start, end);
        Page<ProductDto> pageProduct  = new PageImpl<>(subList, pageable, list.size());
        return pageProduct;
    }

    public Page<ProductDto> findProductsByBrandIdAndPriceLevelContainingAndNameContaining(Long id, String priceLevel, String keyWord, int pageNumber){
        List<Products> products = productRepo.findProductsByBrandIdAndPriceLevelContainingAndNameContaining(id, priceLevel, keyWord);
        List<ProductDto> list = products.stream().map(products1 -> modelMapper.map(products1, ProductDto.class)).collect(Collectors.toList());

        for (ProductDto productDto : list) {
            int sold = 0;
            List<ProductSize> sizes = productSizeRepo.findAllByProductId(productDto.getId());
            for (ProductSize p : sizes) {
                sold = sold + p.getSold();
            }
            productDto.setSold(sold);
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, 9);
        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > list.size() ? list.size() : start + pageable.getPageSize();
        List<ProductDto> subList = list.subList(start, end);
        Page<ProductDto> pageProduct  = new PageImpl<>(subList, pageable, list.size());
        return pageProduct;
    }

    public Page<ProductDto> findProductsByBrandIdAndPriceLevelContainingAndNameContainingOrderByCreatedTimeDesc(Long id, String priceLevel, String keyWord, int pageNumber){
        List<Products> products = productRepo.findProductsByBrandIdAndPriceLevelContainingAndNameContainingOrderByCreatedTimeDesc(id, priceLevel, keyWord);
        List<ProductDto> list = products.stream().map(products1 -> modelMapper.map(products1, ProductDto.class)).collect(Collectors.toList());

        for (ProductDto productDto : list) {
            int sold = 0;
            List<ProductSize> sizes = productSizeRepo.findAllByProductId(productDto.getId());
            for (ProductSize p : sizes) {
                sold = sold + p.getSold();
            }
            productDto.setSold(sold);
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, 9);
        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > list.size() ? list.size() : start + pageable.getPageSize();
        List<ProductDto> subList = list.subList(start, end);
        Page<ProductDto> pageProduct  = new PageImpl<>(subList, pageable, list.size());
        return pageProduct;
    }

    public Page<ProductDto> findProductsByBrandIdAndPriceLevelContainingAndNameContainingOrderByPriceAsc(Long id, String priceLevel, String keyWord, int pageNumber){
        List<Products> products = productRepo.findProductsByBrandIdAndPriceLevelContainingAndNameContainingOrderByPriceAsc(id, priceLevel, keyWord);
        List<ProductDto> list = products.stream().map(products1 -> modelMapper.map(products1, ProductDto.class)).collect(Collectors.toList());

        for (ProductDto productDto : list) {
            int sold = 0;
            List<ProductSize> sizes = productSizeRepo.findAllByProductId(productDto.getId());
            for (ProductSize p : sizes) {
                sold = sold + p.getSold();
            }
            productDto.setSold(sold);
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, 9);
        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > list.size() ? list.size() : start + pageable.getPageSize();
        List<ProductDto> subList = list.subList(start, end);
        Page<ProductDto> pageProduct  = new PageImpl<>(subList, pageable, list.size());
        return pageProduct;
    }

    public Page<ProductDto> findProductsByBrandIdAndPriceLevelContainingAndNameContainingOrderByPriceDesc(Long id, String priceLevel, String keyWord, int pageNumber){
        List<Products> products = productRepo.findProductsByBrandIdAndPriceLevelContainingAndNameContainingOrderByPriceDesc(id, priceLevel, keyWord);
        List<ProductDto> list = products.stream().map(products1 -> modelMapper.map(products1, ProductDto.class)).collect(Collectors.toList());

        for (ProductDto productDto : list) {
            int sold = 0;
            List<ProductSize> sizes = productSizeRepo.findAllByProductId(productDto.getId());
            for (ProductSize p : sizes) {
                sold = sold + p.getSold();
            }
            productDto.setSold(sold);
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, 9);
        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > list.size() ? list.size() : start + pageable.getPageSize();
        List<ProductDto> subList = list.subList(start, end);
        Page<ProductDto> pageProduct  = new PageImpl<>(subList, pageable, list.size());
        return pageProduct;
    }

    public List<ProductDto> findTrendProducts() {
        List<Products> products = new ArrayList<>();
        List<Long> coll = productSizeRepo.findProductIdBySold();

        for (Long a : coll) {
            products.add(productRepo.findById(a).orElse(null));
        }
        List<ProductDto> result = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());

        for (ProductDto productDto : result) {
            int sold = 0;
            List<ProductSize> sizes = productSizeRepo.findAllByProductId(productDto.getId());
            for (ProductSize p : sizes) {
                sold = sold + p.getSold();
            }
            productDto.setSold(sold);
        }
        return result;
    }

    public List<ProductDto> findNewProducts () {
        List<Products> products = productRepo.findNewProduct();

        List<ProductDto> result = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());

        for (ProductDto productDto : result) {
            int sold = 0;
            List<ProductSize> sizes = productSizeRepo.findAllByProductId(productDto.getId());
            for (ProductSize p : sizes) {
                sold = sold + p.getSold();
            }
            productDto.setSold(sold);
        }
        return result;
    }

}
