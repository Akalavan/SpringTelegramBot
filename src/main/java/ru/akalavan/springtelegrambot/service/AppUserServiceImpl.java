package ru.akalavan.springtelegrambot.service;

import org.springframework.stereotype.Service;
import ru.akalavan.springtelegrambot.models.AppUser;
import ru.akalavan.springtelegrambot.repository.AppUserRepository;

import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository userRepository;

    public AppUserServiceImpl(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Поиск пользователя по id пользователя из телеграмма
     * @param id id пользователя из телеграмма
     * @return Возвращаяет опцинал пользователя
     */
    @Override
    public Optional<AppUser> findUserByTelegramId(Long id) {
       return userRepository.findByTelegramUserId(id);
    }

    /**
     * Сохраняет пользователя в БД
     * @param user Пользователь, которого нужно сохранить
     * @return Только что сохраненного пользователя
     */
    @Override
    public AppUser saveUser(AppUser user) {
        return userRepository.save(user);
    }
}
