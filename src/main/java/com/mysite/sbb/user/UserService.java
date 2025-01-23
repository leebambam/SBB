package com.mysite.sbb.user;

import com.mysite.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor // 클래스의 final 필드나 @NonNull이 붙은 필드를 초기화하는 생성자를 자동으로 생성
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(UserDto userDto){

        SiteUser user = SiteUser.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
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
}
