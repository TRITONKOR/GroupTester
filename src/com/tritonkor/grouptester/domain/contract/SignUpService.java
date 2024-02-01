package com.tritonkor.grouptester.domain.contract;

import com.tritonkor.grouptester.domain.dto.UserAddDto;

/**
 * The SignUpService interface defines the contract for user signup operations and verification code
 * management. It provides methods for user signup, generating and sending verification codes.
 */
public interface SignUpService {

    /**
     * Signs up a new user based on the provided UserAddDto and verifies the user with the given
     * verification code.
     *
     * @param userAddDto    The DTO containing information to create the new user.
     * @param userInputCode The user input code entered during the signup process.
     * @param verifyCode    The verification code sent to the user for validation.
     */
    void signUp(UserAddDto userAddDto, String userInputCode, String verifyCode);

    /**
     * Generates a verification code and sends it to the specified email address.
     *
     * @param email The email address to which the verification code should be sent.
     * @return The generated verification code.
     */
    String generateAndSendVerificationCode(String email);
}
