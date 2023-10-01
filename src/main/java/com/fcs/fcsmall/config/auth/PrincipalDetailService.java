package com.fcs.fcsmall.config.auth;

import com.fcs.fcsmall.domain.User;
import com.fcs.fcsmall.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 사용자명은 기반으로 UserDetails 객체를 로드하는 역활
 * 로그인 시 사용됨
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("PrincipalDetailService.loadUserByUsername('{}')",username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("DB에 유저 정보가 없음"));
        return new PrincipalDetails(user);
    }
}
