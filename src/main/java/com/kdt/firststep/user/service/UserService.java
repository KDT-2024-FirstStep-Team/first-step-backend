package com.kdt.firststep.user.service;

import com.kdt.firststep.user.dto.JoinDTO;
import com.kdt.firststep.user.dto.LoginRequestDTO;

public interface UserService {
    void joinUser(JoinDTO joinDTO);

    void login(LoginRequestDTO loginRequestDTO);
}
