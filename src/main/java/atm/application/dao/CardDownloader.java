package atm.application.dao;

import atm.application.exception.system.SystemException;
import atm.application.model.Card;

import au.com.bytecode.opencsv.CSVReader;

import lombok.experimental.UtilityClass;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import static java.nio.file.StandardOpenOption.WRITE;

@UtilityClass
public class CardDownloader {

    private static final String PATH_TO_FILE = "data/cards.csv";

    public static Set<Card> downloadCards() {
        Set<Card> cards = new HashSet<>();

        try (CSVReader reader = new CSVReader(new FileReader(PATH_TO_FILE), ' ')) {
            String[] cardString;

            while ((cardString = reader.readNext()) != null) {
                long id = Long.parseLong(cardString[0]);
                String cardNumber = cardString[1];
                BigDecimal balance = BigDecimal.valueOf(Double.parseDouble(cardString[2]));
                long pin = Long.parseLong(cardString[3]);
                boolean isCardNonBlocked = Boolean.parseBoolean(cardString[4]);
                int countOfInputIncorrectCount = Integer.parseInt(cardString[5]);

                Card card = new Card(id, cardNumber, balance, pin, isCardNonBlocked, countOfInputIncorrectCount);
                cards.add(card);
            }

            clearFile();

            return cards;
        } catch (IOException ioException) {
            throw new SystemException("Не удалось настроить соединение к файлу с данными! Попробуйте позже!");
        }
    }

    private static void clearFile() {
        try (FileChannel fileChannel = FileChannel.open(Paths.get(PATH_TO_FILE), WRITE)) {
            fileChannel.truncate(0).close();
        } catch (IOException ioException) {
            throw new SystemException("Не удалось настроить соединение к файлу с данными! Попробуйте позже!");
        }
    }
}
