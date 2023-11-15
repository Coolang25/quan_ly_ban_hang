package com.t3h.quan_ly_ban_hang.utils;

import com.t3h.quan_ly_ban_hang.config.StaticResourceConfiguration;
import com.t3h.quan_ly_ban_hang.dto.ProductImageDto;
import com.t3h.quan_ly_ban_hang.dto.ProductSizeDto;
import com.t3h.quan_ly_ban_hang.entities.ProductImages;
import com.t3h.quan_ly_ban_hang.entities.ProductSize;
import com.t3h.quan_ly_ban_hang.dto.ProductDto;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProductSaveFileHelper {

    // Set tên ảnh chính
    public static void setMainImageName(MultipartFile mainImageMultipart, ProductDto product) {
        if (mainImageMultipart != null && !mainImageMultipart.isEmpty()
            && !org.apache.commons.lang3.StringUtils.isBlank(mainImageMultipart.getOriginalFilename())) {
            String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());
            product.setMainImage(fileName);
        }
    }

    // Set ảnh đã có trên hệ thống
    public static void setExistingExtraImageNames(String[] imageIDs, String[] imageNames, ProductDto product) {
        if (imageIDs == null || imageIDs.length == 0) return;

        List<ProductImageDto> images = new ArrayList<>();

        for (int count = 0; count < imageIDs.length; count++) {
            Long id = Long.parseLong(imageIDs[count]);
            String name = imageNames[count];

            images.add(new ProductImageDto(id, name, product.getId()));
        }

        product.setImages(images);

    }

   public  static void setNewExtraImageNames(MultipartFile[] extraImageMultiparts, ProductDto product) {
        if (extraImageMultiparts.length > 0) {
            for (MultipartFile multipartFile : extraImageMultiparts) {
                if (multipartFile != null && !multipartFile.isEmpty()
                        && !org.apache.commons.lang3.StringUtils.isBlank(multipartFile.getOriginalFilename())) {
                    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

                    if (!product.containsImageName(fileName)) {
                        product.addExtraImage(fileName);
                    }
                }
            }
        }
    }

    public static void saveUploadedImages(MultipartFile mainImageMultipart,
                                   MultipartFile[] extraImageMultiparts, ProductDto savedProduct) throws IOException {
        if (!mainImageMultipart.isEmpty()) {
            String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());
            String uploadDir = StaticResourceConfiguration.FOLDER_STATIC + "product/" + savedProduct.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, mainImageMultipart);
        }

        if (extraImageMultiparts.length > 0) {
            String uploadDir = StaticResourceConfiguration.FOLDER_STATIC + "product/" + savedProduct.getId() + "/extras";

            for (MultipartFile multipartFile : extraImageMultiparts) {
                if (multipartFile.isEmpty()) continue;

                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            }
        }

    }

    public static void deleteExtraImagesWeredRemovedOnForm(ProductDto product) {
        String extraImageDir = StaticResourceConfiguration.FOLDER_STATIC  + "product/" + product.getId() + "/extras";
        Path dirPath = Paths.get(extraImageDir);

        try {
            Files.list(dirPath).forEach(file -> {
                String filename = file.toFile().getName();

                if (!product.containsImageName(filename)) {
                    try {
                        Files.delete(file);

                    } catch (IOException e) {
                    }
                }

            });
        } catch (IOException ex) {
        }
    }

    public static void setExistingProductSizes(String[] productSizeIDs, Integer[] solds, Integer[] inStocks, String[] sizeIDs, ProductDto productDto) {
        if (productSizeIDs == null || solds == null || inStocks == null || sizeIDs == null) return;
        List<ProductSizeDto> sizes = new ArrayList<>();

        for (int count = 0; count < sizeIDs.length; count++) {
            Long productSizeID = Long.parseLong(productSizeIDs[count]);
            Integer sold = solds[count];
            Integer inStock = inStocks[count];
            Long sizeID = Long.parseLong(sizeIDs[count]);
            sizes.add(new ProductSizeDto(productSizeID, sold, inStock, productDto.getId(), sizeID));
        }
        if (sizes.size() == 0) return;
        productDto.setSizeList(sizes);
    }

    public static void setNewProductSizes(Integer[] newSolds, Integer[] newInStocks, String[] newSizeIDs, ProductDto productDto) {
        if (newSolds == null || newInStocks == null || newSizeIDs == null) return;
        for (int count = 0; count < newSizeIDs.length; count++) {
            Integer sold = newSolds[count];
            Integer inStock = newInStocks[count];
            Long sizeID = Long.parseLong(newSizeIDs[count]);

            if (!productDto.containsSizeId(sizeID)) {
                productDto.addExtraSize(sold, inStock, sizeID);
            }
        }
    }
}
