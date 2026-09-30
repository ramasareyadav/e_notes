package com.e_notes.service.impl;

import com.e_notes.exception.ResourceNotFoundException;
import com.e_notes.exception.SuccessException;
import com.e_notes.model.AccountStatus;
import com.e_notes.model.User;
import com.e_notes.repository.UserRepository;
import com.e_notes.service.HomeService;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService {


    private final UserRepository userRepository;

    public HomeServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Boolean varifyAccount(Integer userId, String varificationCode) throws Exception {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("invalid user"));
       if (user.getStatus().getVarificationCode()==null)
       {
           throw new SuccessException("Account  already varified");
       }
        if (user.getStatus().getVarificationCode().equals(varificationCode)) {
            AccountStatus status = user.getStatus();
            status.setIsActive(true);
            status.setVarificationCode(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
