package ru.akalavan.springtelegrambot.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.akalavan.springtelegrambot.controller.UpdateController;
import ru.akalavan.springtelegrambot.models.AppUser;

@Service
@Log4j
public class MainServiceImpl implements MainService {

    private final AppUserService appUserService;
    private final UpdateController updateController;

    public MainServiceImpl(AppUserService appUserService, UpdateController updateController) {
        this.appUserService = appUserService;
        this.updateController = updateController;
    }

    @PostConstruct
    public void init() {
        updateController.setMainService(this);
    }

    @Override
    public void processTextMessage(Update update, AppUser user) {
        var text = update.getMessage().getText();
        var output = "Hello: " + user.getUsername() + ". We have received your message";
        sendAnswer(output, user.getTelegramUserId());
    }

    private void sendAnswer(String output, Long chatId) {
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(output);
        updateController.setAnswer(sendMessage);
    }
}
