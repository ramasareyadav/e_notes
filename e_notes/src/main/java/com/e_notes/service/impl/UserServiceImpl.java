package com.e_notes.service.impl;

import java.util.List;
import java.util.UUID;

import com.e_notes.dto.EmailRequest;
import com.e_notes.dto.UserDto;
import com.e_notes.model.AccountStatus;
import com.e_notes.model.Role;
import com.e_notes.model.User;
import com.e_notes.repository.RoleRepository;
import com.e_notes.repository.UserRepository;
import com.e_notes.service.UserService;
import com.e_notes.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


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

    @Override
    public Boolean register(UserDto userDto) throws Exception {

        validation.userValidation(userDto);
        User user = mapper.map(userDto, User.class);

        setRole(userDto, user);

        AccountStatus status=AccountStatus.builder()
                .isActive(false)
                .varificationCode(UUID.randomUUID().toString())
                .build();
        user.setStatus(status);

        User saveUser = userRepo.save(user);
        if (!ObjectUtils.isEmpty(saveUser)) {
            // send email
            emailSend(saveUser);
            return true;
        }
        return false;
    }

    private void emailSend(User saveUser) throws Exception {

        String message = "Hi,<b>" + saveUser.getFirstName() + "</b> "
                + "<br> Your account register sucessfully.<br>"
                + "<br> Click the below link verify & Active your account <br>"
                + "<a href='#'>Click Here</a> <br><br>"
                + "Thanks,<br>Enotes.com";

        EmailRequest emailRequest = EmailRequest.builder()
                .to(saveUser.getEmail())
                .title("Account Creating Confirmation")
                .subject("Account Created Success")
                .message(message)
                .build();
        emailService.sendEmail(emailRequest);
    }

    private void setRole(UserDto userDto, User user) {
        List<Integer> reqRoleId = userDto.getRoles().stream().map(r -> r.getId()).toList();
        List<Role> role = roleRepo.findAllById(reqRoleId);
        user.setRoles(role);
    }
}