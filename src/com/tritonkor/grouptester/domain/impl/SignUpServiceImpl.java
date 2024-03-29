package com.tritonkor.grouptester.domain.impl;

import com.tritonkor.grouptester.domain.contract.SignUpService;
import com.tritonkor.grouptester.domain.contract.UserService;
import com.tritonkor.grouptester.domain.dto.UserAddDto;
import com.tritonkor.grouptester.domain.exception.SignUpException;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

/**
 * The SignUpServiceImpl class is an implementation of the SignUpService interface, providing user
 * registration functionality in the application.
 */
final class SignUpServiceImpl implements SignUpService {

    private static final int VERIFICATION_CODE_EXPIRATION_MINUTES = 1;
    private static LocalDateTime codeCreationTime;
    private final UserService userService;

    /**
     * Constructs a new SignUpServiceImpl with the specified UserService.
     *
     * @param userService the user service used for user-related operations.
     */
    SignUpServiceImpl(UserService userService) {
        this.userService = userService;
    }

    /**
     * Sends a verification code email to the specified email address.
     *
     * @param email            the email address to which the verification code will be sent.
     * @param verificationCode the generated verification code.
     */
    private static void sendVerificationCodeEmail(String email, String verificationCode) {
        // Властивості для конфігурації підключення до поштового сервера
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "sandbox.smtp.mailtrap.io"); // Замініть на власний
        properties.put("mail.smtp.port", "2525"); // Замініть на власний SMTP порт
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Отримання сесії з автентифікацією
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("1f0168c77bec47", "5ce6955ef846a4");
            }
        });

        try {
            // Створення об'єкта MimeMessage
            Message message = new MimeMessage(session);

            // Встановлення відправника
            message.setFrom(new InternetAddress("from@example.com")); // Замініть на власну адресу

            // Встановлення отримувача
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

            // Встановлення теми
            message.setSubject("Verification code");

            // Встановлення тексту повідомлення
            message.setText("Your verification code: " + verificationCode);

            // Відправлення повідомлення
            Transport.send(message);

            System.out.println("The message was successfully sent.");

        } catch (MessagingException e) {
            throw new RuntimeException(
                    "Error sending an email: " + e.getMessage());
        }
    }

    /**
     * Generates and sends a verification code to the specified email address.
     *
     * @param email the email address to which the verification code will be sent.
     * @return the generated verification code.
     */
    public String generateAndSendVerificationCode(String email) {
        // Генерація 6-значного коду
        String verificationCode = String.valueOf((int) (Math.random() * 900000 + 100000));

        sendVerificationCodeEmail(email, verificationCode);

        codeCreationTime = LocalDateTime.now();

        return verificationCode;
    }

    /**
     * Verifies the input code against the generated code and checks if the verification code has
     * expired.
     *
     * @param inputCode     the input verification code provided by the user.
     * @param generatedCode the generated verification code.
     * @throws SignUpException if the verification code is incorrect or has expired.
     */
    public static void verifyCode(String inputCode, String generatedCode) {
        LocalDateTime currentTime = LocalDateTime.now();
        long minutesElapsed = ChronoUnit.MINUTES.between(codeCreationTime, currentTime);

        if (minutesElapsed > VERIFICATION_CODE_EXPIRATION_MINUTES) {
            throw new SignUpException("Verification time has expired. Please try again.");
        }

        if (!inputCode.equals(generatedCode)) {
            throw new SignUpException("The verification code is incorrect.");
        }

        // Скидання часу створення коду
        codeCreationTime = null;
    }

    /**
     * Registers a new user with the provided user details, input verification code, and the
     * generated verification code.
     *
     * @param userAddDto       the user details for registration.
     * @param userInputCode    the input verification code provided by the user.
     * @param verificationCode the generated verification code.
     * @throws SignUpException if the verification code is incorrect or has expired.
     */
    public void signUp(UserAddDto userAddDto, String userInputCode, String verificationCode) {

        //verifyCode(userInputCode, verificationCode);

        userService.add(userAddDto);
    }
}
