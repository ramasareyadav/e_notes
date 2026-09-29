package com.e_notes.service.impl;

import com.e_notes.dto.UserDto;
import com.e_notes.model.Role;
import com.e_notes.model.User;
import com.e_notes.repository.RoleRepository;
import com.e_notes.repository.UserRepository;
import com.e_notes.service.UserService;
import com.e_notes.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Validation validation;
    private final ModelMapper modelMapper;

    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            Validation validation,
            ModelMapper modelMapper) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.validation = validation;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean register(UserDto userDto) {

        validation.userValidation(userDto);

        User user = modelMapper.map(userDto, User.class);

        // Set default active status
        user.setIsActive(true);

        // Set roles
        setRole(userDto, user);

        User save = userRepository.save(user);

        return !ObjectUtils.isEmpty(save);
    }

    private void setRole(UserDto userDto, User user) {

        // Get role IDs from request
        List<Integer> reqRoleId = userDto.getRoles()
                .stream()
                .map(UserDto.RoleDto::getId)
                .toList();

        // Find roles from database
        List<Role> roles = roleRepository.findAllById(reqRoleId);

        // Convert List -> Set
        Set<Role> roleSet = new HashSet<>(roles);

        // Set roles into User entity
        user.setRoles(roleSet);
    }
}
