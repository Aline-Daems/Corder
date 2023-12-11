package be.technobel.corder.pl.models.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChangePasswordForm {
    @NotBlank(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String oldPassword;

    @NotBlank(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String newPassword;


    public String getOldPassword() {
        return oldPassword;
    }


    public String getNewPassword() {
        return newPassword;
    }


}


