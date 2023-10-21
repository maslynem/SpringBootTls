package com.example.springboottls.dto.customerDto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerCreateEditDto {
    @NotNull(message = "name can not be null")
    @NotBlank(message = "name can not be blank")
    private String name;

    @NotNull(message = "email can not be null")
    @Email(message = "invalid email")
    private String email;

    @NotNull(message = "age can not be null")
    @Min(0)
    @Max(150)
    private Integer age;

    private String companyName;
}
