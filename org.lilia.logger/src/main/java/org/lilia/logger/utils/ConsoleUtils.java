package org.lilia.logger.utils;

import org.lilia.logger.Logger;
import org.lilia.logger.LoggerFactory;
import org.lilia.logger.exception.InvalidUserInputException;

import java.util.Scanner;

public class ConsoleUtils {

    public static final Scanner SCANNER = new Scanner(System.in);

    public static void exit() {
        SCANNER.close();
    }

    private static final Logger logger = LoggerFactory.getLogger(ConsoleUtils.class);

    public static int readInteger() {
        int id = SCANNER.nextInt();
        feedNewLine();
        return id;
    }

    private static void feedNewLine() {
        SCANNER.nextLine();
    }

    public static int choiceActionForAddMaterial() {
        print("1 - create new");
        print("2 - open/edit");
        print("3 - output all");
        print("4 - delete");
        print("5 - create backup");
        print("6 - deserialization");
        print("7 - print all grouped by lecture");
        print("0 - " + Constants.EXIT);

        return Integer.parseInt(readAndValidationInput("[0-7]"));
    }

    public static int choiceAction() {
        print("1 - create new");
        print("2 - open/edit");
        print("3 - output all");
        print("4 - delete");
        print("5 - create backup");
        print("6 - deserialization");
        print("0 - " + Constants.EXIT);

        return Integer.parseInt(readAndValidationInput("[0-6]"));
    }

    public static int choiceParameterSort() {
        print("1 - sort by additionMaterialId");
        print("2 - sort by lectureId");
        print("3 - sort by resourceType");
        print("0 - " + Constants.EXIT);

        return Integer.parseInt(readAndValidationInput(Constants.SELECT_ACTION_FROM_4));
    }

    public static int choiceCategory() {
        print("select a category:");
        print("1 - course");
        print("2 - lecture");
        print("3 - teacher and student");
        print("4 - homework");
        print("5 - additional material");
        print("6 - control work");
        print("7 - start server");
        print("8 - start client");
        print("9 - filter logs");

        print("0 - " + Constants.EXIT);

        return Integer.parseInt(readAndValidationInput("[0-9]"));
    }

    public static String readAndValidationInput(String pattern) {
        String stringData = SCANNER.nextLine();
        try {
            validate(stringData, pattern);
        } catch (InvalidUserInputException e) {
            logger.warning("unexpected input", e);
            stringData = repeatInputUntilCorrect(pattern);
        }
        return stringData;
    }

    public static void validate(String data, String pattern) {
        boolean b = data.matches(pattern);
        if (!b) {
            logger.error("invalid user input");
            throw new InvalidUserInputException(data, pattern);
        }
    }

    private static String repeatInputUntilCorrect(String pattern) {
        do {
            String data = SCANNER.nextLine();
            boolean matches = data.matches(pattern);
            if (!matches) {
                logger.error("unexpected input");

            } else {
                return data;
            }
        } while (true);
    }

    public static void print(String string) {
        System.out.println(string);
    }

    public static String choiceParameterResource() {
        print("1 - URL");
        print("2 - VIDEO");
        print("3 - BOOK");

        return readAndValidationInput(Constants.SELECT_ACTION_FROM_3);
    }

    public static int workWithListAddMaterial() {
        print("\n1 - add additionMaterial to lecture");
        print("2 - delete additionMaterial");
        print("3 - sort additionMaterial");
        print("0 - " + Constants.EXIT);

        return Integer.parseInt(readAndValidationInput(Constants.SELECT_ACTION_FROM_4));
    }

    public static int workWithListHomework() {
        print("\n1 - add homework to lecture");
        print("2 - delete homework");
        print("3 - " + Constants.EXIT);

        return Integer.parseInt(readAndValidationInput(Constants.SELECT_ACTION_FROM_3));
    }

    public static int outputDate() {
        print("1 - output all");
        print("2 - output with date parameter");
        print("3 - select a lecture created earlier than others");
        print("4 - print lectures grouping by teacherId");
        print("0 - " + Constants.EXIT);

        return Integer.parseInt(readAndValidationInput(Constants.SELECT_ACTION_FROM_5));

    }

    public static int choiceDisplayType() {
        print("1 - before the date");
        print("2 - after the date");
        print("3 - between dates");
        print("0 - " + Constants.EXIT);

        return Integer.parseInt(readAndValidationInput(Constants.SELECT_ACTION_FROM_4));
    }
}