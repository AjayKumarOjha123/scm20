package com.scm.scm20.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactForm {

    @NotBlank(message = "Name is required")
    private String name;
    @Email(message = "Email is Not Valid")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Description is required")
    private String description;
    private boolean favorate;
    private String websiteLink;
    private String linkedInLink;
    private MultipartFile contactImage;
    private String picture;
}
