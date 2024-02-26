package ru.klaes.library.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.klaes.library.model.ReaderAuthData;
import ru.klaes.library.repository.ReaderAuthDataRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LibraryUserDetailsService implements UserDetailsService {

    private final ReaderAuthDataRepository readerAuthDataRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ReaderAuthData user = readerAuthDataRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        List<GrantedAuthority> grantedAuthorityList = user.getRoles().stream()
                .map(it -> new SimpleGrantedAuthority(it.getName()))
                .collect(Collectors.toList());

        return new User(user.getLogin(), user.getPassword(), grantedAuthorityList);
    }
}
