package ru.akalavan.springtelegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.akalavan.springtelegrambot.models.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByTelegramUserId(Long id);
}
