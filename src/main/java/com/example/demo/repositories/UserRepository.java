package com.example.demo.repositories;

import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET password=?2, updated_at=NOW() WHERE id = ?1", nativeQuery = true)
    void updateUserPassword(long userId, String password);

    @Modifying
    @Query(value = "UPDATE users SET email_verified=true WHERE id=?1 AND email = ?2", nativeQuery = true)
    void updateEmailVerificationStatus(long id, String email);

    @Modifying
    @Query(value = "UPDATE users SET phone_no_verified=true WHERE id=?1 AND phone_no = ?2 ", nativeQuery = true)
    void updatePhoneVerificationStatus(long id, String phoneNo);

    @Modifying
    @Query(value = "UPDATE users SET email=?2 WHERE id=?1", nativeQuery = true)
    void updateEmailByUserId(long id, String email);

    Optional<User> findByUsername(String username);
}
