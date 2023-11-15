package com.t3h.quan_ly_ban_hang.utils;

import com.t3h.quan_ly_ban_hang.config.StaticResourceConfiguration;
import com.t3h.quan_ly_ban_hang.dto.CategoryDto;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class CategorySaveFileHelper {


    public static void setImageName(MultipartFile imageMultipart, CategoryDto categoryDto) {
        if (imageMultipart != null && !imageMultipart.isEmpty()
            && !org.apache.commons.lang3.StringUtils.isBlank(imageMultipart.getOriginalFilename())) {
            String fileName = StringUtils.cleanPath(imageMultipart.getOriginalFilename());
            categoryDto.setImage(fileName);
        }
    }

    public static void saveUploadedImages(MultipartFile imageMultipart, CategoryDto categoryDto) throws IOException {
        if (!imageMultipart.isEmpty()) {
            String fileName = StringUtils.cleanPath(imageMultipart.getOriginalFilename());
            String uploadDir = StaticResourceConfiguration.FOLDER_STATIC + "category/" + categoryDto.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, imageMultipart);
        }
    }
}
