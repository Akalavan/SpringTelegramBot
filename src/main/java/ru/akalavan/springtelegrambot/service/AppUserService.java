package ru.akalavan.springtelegrambot.service;

import ru.akalavan.springtelegrambot.models.AppUser;

import java.util.Optional;

public interface AppUserService {

    Optional<AppUser> findUserByTelegramId(Long id);

    AppUser saveUser(AppUser user);
}
