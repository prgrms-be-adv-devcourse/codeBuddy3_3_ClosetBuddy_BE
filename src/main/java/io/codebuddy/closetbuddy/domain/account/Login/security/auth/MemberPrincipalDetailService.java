package io.codebuddy.closetbuddy.domain.account.Login.security.auth;

import io.codebuddy.closetbuddy.domain.common.model.entity.Member;
import io.codebuddy.closetbuddy.domain.account.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// UserDetailsService 를 구현한 클래스

@Service
public class MemberPrincipalDetailService implements UserDetailsService {

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 넘겨받은 id 로 DB 에서 회원 정보를 찾음
        Member member = loginRepository.findByUserid(username);
        System.out.println("username : " + username);
        System.out.println("member : " + member);

        // 없을경우 에러 발생
        if(member == null)
            throw new UsernameNotFoundException(username + "을 찾을 수 없습니다.");

        // MemberPrincipalDetails 에 Member 객체를 넘겨줌
        return new MemberPrincipalDetails(member);
    }
}
