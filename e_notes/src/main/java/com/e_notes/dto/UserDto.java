package com.e_notes.dto;


import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String mobNumber;

    private String password;

   // private Boolean isActive;

    private Set<RoleDto> roles;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RoleDto {

        private Integer id;

        private String name;
    }
}