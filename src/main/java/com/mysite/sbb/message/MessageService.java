package com.mysite.sbb.message;

import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserDto;
import com.mysite.sbb.user.UserMapper;
import com.mysite.sbb.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MessageService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getUser() {
        List<SiteUser> siteUser = this.userRepository.findAll();

        return userMapper.toDtoList(siteUser);
    }
}
