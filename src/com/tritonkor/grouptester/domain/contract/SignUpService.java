package com.tritonkor.grouptester.domain.contract;

import com.tritonkor.grouptester.domain.dto.UserAddDto;
import java.util.function.Supplier;

public interface SignUpService {

    void signUp(UserAddDto userAddDto, String userInputCode, String verifyCode);
    String generateAndSendVerificationCode(String email);
}
