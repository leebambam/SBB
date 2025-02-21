package com.mysite.sbb.user;

import com.mysite.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor // 클래스의 final 필드나 @NonNull이 붙은 필드를 초기화하는 생성자를 자동으로 생성
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public SiteUser create(UserDto userDto){

        SiteUser user = SiteUser.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .phoneNumber(userDto.getPhoneNumber())
                .build();

        // 저장 및 반환
        this.userRepository.save(user);
        return user;
    }

    /*public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }*/

    // SiteUser를 조회
    public UserDto getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
        if (siteUser.isPresent()) {
            // MapStruct로 SiteUser를 SiteUserDto로 변환
            return this.userMapper.toDto(siteUser.get());
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }


    public void sendTemporaryPassword(FindPasswordDto findPasswordDto) {
        String username = findPasswordDto.getUsername();
        String email = findPasswordDto.getEmail();
        Optional<SiteUser> siteUserOptional  = userRepository.findByUsernameAndEmail(username, email);

        if (siteUserOptional.isPresent()) {
            SiteUser siteUser = siteUserOptional.get();
            // 임시 비밀번호 생성
            /*String tempPassword = UUID.randomUUID().toString().substring(0, 8);*/
            String tempPassword = generateRandomPassword();

            // 임시 비밀번호 암호화 후 저장
            SiteUser updatedUser = siteUser.toBuilder()
                    .password(passwordEncoder.encode(tempPassword))
                    .tempPassword(true)
                    .build();

            userRepository.save(updatedUser);

            // 이메일 전송
            String subject = "임시 비밀번호 발급 안내";
            String text = "안녕하세요.\n\n임시 비밀번호는 [" + tempPassword + "] 입니다.\n로그인 후 비밀번호를 변경해주세요.";

            emailService.sendEmail(email, subject, text);
        } else {
            throw new DataNotFoundException("등록되지 않은 사용자 ID 또는 이메일: ID=" + username + ", Email=" + email);
        }
    }

    private String generateRandomPassword() {
        int length = 10;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        return password.toString();
    }

    // id로 사용자 찾기
    public UserDto findByUsername(String username) {
        Optional<SiteUser> siteUser  = userRepository.findByusername(username);

        if (siteUser.isPresent()) {
            return this.userMapper.toDto(siteUser.get());
        } else  {
            throw new DataNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }
    }

    // 비밀번호 변경
    public void updatePassword(UserDto userDto, String newPassword) {
        if (userDto.getId() == null) {  // ✅ ID 값이 없으면 예외 발생
            throw new IllegalStateException("User ID is missing. Cannot update password.");
        }
        userDto.setPassword(passwordEncoder.encode(newPassword));
        userDto.setTempPassword(false); // ✅ 임시 비밀번호 상태 해제

        SiteUser user = userMapper.toEntity(userDto);
        userRepository.save(user);

    }
}
