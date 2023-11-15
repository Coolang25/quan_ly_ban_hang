package com.t3h.quan_ly_ban_hang.utils;

import com.t3h.quan_ly_ban_hang.config.StaticResourceConfiguration;
import com.t3h.quan_ly_ban_hang.dto.UserDto;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class UserSaveFileHelper {


    public static void setAvatarName(MultipartFile imageMultipart, UserDto userDto) {
        if (imageMultipart != null && !imageMultipart.isEmpty()
            && !org.apache.commons.lang3.StringUtils.isBlank(imageMultipart.getOriginalFilename())) {
            String fileName = StringUtils.cleanPath(imageMultipart.getOriginalFilename());
            userDto.setAvatar(fileName);
        }
    }

    public static void saveUploadedAvatar(MultipartFile imageMultipart, UserDto userDto) throws IOException {
        if (!imageMultipart.isEmpty()) {
            String fileName = StringUtils.cleanPath(imageMultipart.getOriginalFilename());
            String uploadDir = StaticResourceConfiguration.FOLDER_STATIC + "user/" + userDto.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, imageMultipart);
        }
    }
}
