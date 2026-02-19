package com.levelvini.picpay_simplificado.dtos;

import com.levelvini.picpay_simplificado.model.enums.UserType;

import java.math.BigDecimal;

public record UserDTO(String firstName, String LastName, String document, BigDecimal balance, String email, String password,
                      UserType userType) {
}
