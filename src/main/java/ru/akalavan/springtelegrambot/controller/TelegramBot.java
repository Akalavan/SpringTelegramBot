package ru.akalavan.springtelegrambot.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.akalavan.springtelegrambot.config.BotConfig;


@Log4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final UpdateController updateController;

    public TelegramBot(BotConfig botConfig, UpdateController updateController) {
        log.info("TelegramBot");
        this.botConfig = botConfig;
        this.updateController = updateController;
    }

    @PostConstruct
    public void init() {
        log.info("TelegramBot");
        updateController.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        log.info(botConfig.getBotName());
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        log.info(botConfig.getToken());
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info(update);
        updateController.processUpdate(update);
    }

    public void sendAnswerMessage(SendMessage message) {
        if (message != null) {
            try {
                execute(message);
            } catch (TelegramApiException e) {
                log.error(e);
            }
        }
    }
}
