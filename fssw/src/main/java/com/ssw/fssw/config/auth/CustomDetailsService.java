package com.ssw.fssw.config.auth;

import com.ssw.fssw.domain.User;
import com.ssw.fssw.repository.UserApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;

//시큐리티 설정에서 loginProcessingUrl 으로 걸어왔을때
// 로그인 요청이 오면 자동으로 타입으로 loC되어 있는 loadUserByUsername 함수 실행!
@Service
public class CustomDetailsService implements UserDetailsService {

    @Autowired
    private UserApiRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailId(username);
        if(user != null){
            return new CustomUserDetails(user);
        }
        return null;
    }
}
