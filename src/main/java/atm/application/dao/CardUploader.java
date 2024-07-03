package atm.application.dao;

import atm.application.exception.system.SystemException;
import atm.application.exception.validate.ValidateException;
import atm.application.model.Card;
import atm.application.validator.CardValidator;

import au.com.bytecode.opencsv.CSVWriter;

import lombok.experimental.UtilityClass;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

@UtilityClass
public class CardUploader {
    private static final String PATH_TO_FILE = "data/cards.csv";

    private static final CardValidator CARD_VALIDATOR = new CardValidator();

    public static void uploadCards(Set<Card> cards) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(PATH_TO_FILE), ' ')) {

            for (Card card : cards) {
                boolean isCardValid = CARD_VALIDATOR.validateCard(card);
                if (!isCardValid) {
                    throw new ValidateException(400, "Изначальный PIN-код или номер карты не валиден!");
                }

                String[] cardString = {
                        String.valueOf(card.getId()),
                        card.getCardNumber(),
                        card.getBalance().toString(),
                        String.valueOf(card.getPin()),
                        String.valueOf(card.isCardNonBlocked()),
                        String.valueOf(card.getCountOfInputIncorrectPin())
                };

                writer.writeNext(cardString);
            }
        } catch (IOException ioException) {
            throw new SystemException("Не удалось настроить соединение к файлу с данными! Попробуйте позже!");
        }

    }

}
