package com.t3h.quan_ly_ban_hang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank(message = "Username là bắt buộc")
    private String username;
    @NotBlank(message = "Mật khẩu là bắt buộc")
    private String password;
    @NotBlank(message = "Nhập lại mật khẩu là bắt buộc")
    private String rePassword;
    @NotBlank(message = "Không được để trống họ và tên")
    private String fullName;
    private String address;
    @NotBlank(message = "Số điện thoại là bắt buộc")
    private String phone;
    @Email(message = "Không đúng định dạng email")
    private String email;
    private Boolean status;
    private int sex;
    private String role;
    private String avatar;


    MultipartFile fileImage;

    public String getImagePath() {
        if (avatar == null)
            return "/images/image-thumbnail.png";

        return "/image-storage/user/" + this.id + "/" + this.avatar;
    }

}
