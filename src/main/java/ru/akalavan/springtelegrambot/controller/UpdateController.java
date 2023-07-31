package ru.akalavan.springtelegrambot.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.akalavan.springtelegrambot.models.AppUser;
import ru.akalavan.springtelegrambot.service.AppUserService;
import ru.akalavan.springtelegrambot.service.MainService;

import java.util.Optional;

@Component
@Log4j
public class UpdateController {

    private final AppUserService appUserService;
    private MainService mainService;
    private TelegramBot telegramBot;

    public UpdateController(AppUserService appUserService) {
        log.info("UpdateController");
        this.appUserService = appUserService;

    }

    public void registerBot(TelegramBot telegramBot) {
        log.info("registerBot");
        this.telegramBot = telegramBot;
    }

    public void setMainService(MainService mainService) {
        this.mainService = mainService;
    }

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received update is null");
            return;
        }

        if (update.hasMessage()) {
            AppUser user = findOrSaveAppUser(update);
            distributeMessagesByType(update, user);
        } else {
            log.error("Unsupported message type is received" + update);
        }
    }

    /**
     * Ответ пользователю от бота
     * @param sendMessage Сообщение ответа
     */
    public void setAnswer(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }

    /**
     * Сохраняет нового пользователя в БД
     * @param update Сообщение из телеграмма
     * @return Возвращает только что созданного пользователя или существующего из БД
     */
    private AppUser findOrSaveAppUser(Update update) {
        User telegramUser = update.getMessage().getFrom();
        Optional<AppUser> optionalAppUser = appUserService.findUserByTelegramId(telegramUser.getId());
        if (optionalAppUser.isEmpty()) {
            AppUser newAppUser = AppUser.builder()
                    .telegramUserId(telegramUser.getId())
                    .firstName(telegramUser.getFirstName())
                    .lastName(telegramUser.getLastName())
                    .username(telegramUser.getUserName())
                    .build();
            return appUserService.saveUser(newAppUser);
        }
        return optionalAppUser.get();
    }

    private void distributeMessagesByType(Update update, AppUser user) {
        Message message = update.getMessage();

        if (message.hasText()) {
            mainService.processTextMessage(update, user);
        }
    }
}
