package com.mqxu.usercenter.service;

import com.mqxu.usercenter.domain.dto.ResponseDTO;
import com.mqxu.usercenter.domain.dto.UserSignInDTO;

public interface BonusEventLogService {
    ResponseDTO signIn(UserSignInDTO signInDTO);

    ResponseDTO checkIsSign(UserSignInDTO signInDTO);
}
