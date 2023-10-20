package com.example.springboottls.dto.customerDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerCreateEditDto {
    private String name;
    private String email;
    private Integer age;
}
