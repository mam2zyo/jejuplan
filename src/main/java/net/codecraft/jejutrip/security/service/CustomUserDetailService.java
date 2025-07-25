package net.codecraft.jejutrip.security.service;

import lombok.RequiredArgsConstructor;
import net.codecraft.jejutrip.account.user.domain.User;
import net.codecraft.jejutrip.account.user.repository.UserRepository;
import net.codecraft.jejutrip.common.response.message.AccountMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User result = userRepository.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(AccountMessage.NOT_FOUNT_ACCOUNT.getMessage())
                );
        return result;
    }
}
