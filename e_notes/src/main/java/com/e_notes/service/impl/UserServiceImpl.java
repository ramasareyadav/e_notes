package com.e_notes.service.impl;

import com.e_notes.dto.EmailRequest;
import com.e_notes.dto.UserDto;
import com.e_notes.model.*;
import com.e_notes.repository.RoleRepository;
import com.e_notes.repository.UserRepository;
import com.e_notes.security.CustomUserDetails;
import com.e_notes.service.UserService;
import com.e_notes.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private Validation validation;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public Boolean register(UserDto userDto, String url) throws Exception {

        // 1. Validate request
        validation.userValidation(userDto);

        // 2. DTO -> Entity
        User user = mapper.map(userDto, User.class);

        // 3. Set roles
        setRole(userDto, user);

        // 4. Create account status
        AccountStatus status = AccountStatus.builder()
                .isActive(false)
                .varificationCode(UUID.randomUUID().toString())
                .build();

        user.setStatus(status);

        // 5. Encode password
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        // 6. Save user
        User saveUser = userRepo.save(user);

        // 7. Send verification email
        if (!ObjectUtils.isEmpty(saveUser)) {

            emailSend(saveUser, url);

            return true;
        }

        return false;
    }


    private void emailSend(User saveUser, String url) throws Exception {

        String message =
                "Hi,<b>[[username]]</b> "
                        + "<br> Your account register successfully.<br>"
                        + "<br> Click the below link verify & Active your account <br>"
                        + "<a href='[[url]]'>Click Here</a> <br><br>"
                        + "Thanks,<br>Enotes.com";

        message = message.replace(
                "[[username]]",
                saveUser.getFirstName()
        );

        message = message.replace(
                "[[url]]",
                url
                        + "/api/v1/home/verify?uid="
                        + saveUser.getId()
                        + "&&code="
                        + saveUser.getStatus().getVarificationCode()
        );

        EmailRequest emailRequest = EmailRequest.builder()
                .to(saveUser.getEmail())
                .title("Account Creating Confirmation")
                .subject("Account Created Success")
                .message(message)
                .build();

        emailService.sendEmail(emailRequest);
    }


    private void setRole(UserDto userDto, User user) {

        List<Integer> reqRoleId = userDto.getRoles()
                .stream()
                .map(UserDto.RoleDto::getId)
                .toList();

        List<Role> roles = roleRepo.findAllById(reqRoleId);

        if (roles.size() != reqRoleId.size()) {
            throw new IllegalArgumentException(
                    "One or more role IDs are invalid: " + reqRoleId
            );
        }

        user.setRoles(new ArrayList<>(roles));
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        Authentication authenticate =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getEmail(),
                                loginRequest.getPassword()
                        )
                );

        CustomUserDetails customUserDetails =
                (CustomUserDetails) authenticate.getPrincipal();

        String token =
                "safdghhfdssaghnggsdsgfvswaef";

        return LoginResponse.builder()
                .user(
                        mapper.map(
                                customUserDetails.getUser(),
                                UserDto.class
                        )
                )
                .token(token)
                .build();
    }
}