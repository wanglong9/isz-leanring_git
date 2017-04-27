package com.e7code.crud.manager;

import com.e7code.common.core.manager.BaseManager;
import com.e7code.crud.model.User;
import com.e7code.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManager extends BaseManager<User, Integer> {
    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.repository = userRepository;
    }
}
