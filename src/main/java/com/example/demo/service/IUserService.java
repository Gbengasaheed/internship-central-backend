package com.example.demo.service;

import com.example.demo.exceptions.CommonsModuleException;
import com.example.demo.models.IAuthorities;
import com.example.demo.models.IUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.utils.Constants.EntityNames.AUTHORITIES;
import static com.example.demo.utils.Constants.EntityNames.USERS;


@Service
@Transactional
@RequiredArgsConstructor
public class IUserService implements UserDetailsService {

    private final JdbcTemplate jdbcTemplate;

    private IUserDetails findUser(String query, String arg) throws CommonsModuleException {
        try {
            return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(IUserDetails.class), arg);
        } catch (EmptyResultDataAccessException ignored) {
        }
        throw new CommonsModuleException("user.not_found", HttpStatus.NOT_FOUND);
    }

    public IUserDetails findUserDetailsByEmail(String email) throws CommonsModuleException {
        String query = "SELECT * FROM "
                + USERS + " WHERE email=?";
        return findUser(query, email);
    }

    public List<IAuthorities> findUserAuthoritiesByUsername(String username) throws CommonsModuleException {
        String query = "SELECT authority FROM " + AUTHORITIES + " WHERE email=?";
        return Optional.of(jdbcTemplate.queryForStream(query,
                        new BeanPropertyRowMapper<>(IAuthorities.class), username))
                .orElseThrow(() -> new CommonsModuleException("user.not.found", HttpStatus.NOT_FOUND))
                .collect(Collectors.toList());
    }

    public IUserDetails findFullUserDetailsByUsername(String email) throws CommonsModuleException {
        IUserDetails iUserDetails = findUserDetailsByEmail(email);
        iUserDetails.setAuthorities(findUserAuthoritiesByUsername(iUserDetails.getUsername()));
        return iUserDetails;
    }

//    public IUserDetails findFullUserDetailsById(long id) throws CommonsModuleException {
//        IUserDetails iUserDetails = findUserDetailsById(id);
//        iUserDetails.setAuthorities(findUserAuthoritiesByUsername(iUserDetails.getUsername()));
//        return iUserDetails;
//    }

    @Override
    @SneakyThrows
    public IUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return findFullUserDetailsByUsername(email);
    }
}
