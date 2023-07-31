package ru.akalavan.springtelegrambot.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.akalavan.springtelegrambot.models.AppUser;

public interface MainService {
    void processTextMessage(Update update, AppUser user);
}
