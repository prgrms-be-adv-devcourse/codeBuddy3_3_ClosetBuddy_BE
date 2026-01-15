package io.codebuddy.closetbuddy.domain.form.Login.security.auth;

import io.codebuddy.closetbuddy.domain.common.model.entity.Member;
import io.codebuddy.closetbuddy.domain.oauth.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// UserDetailsService 를 구현한 클래스
@Service
public class MemberPrincipalDetailService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserid(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        System.out.println("username : " + username);
        System.out.println("id : " + member);

        return new MemberPrincipalDetails(member);
    }
}
