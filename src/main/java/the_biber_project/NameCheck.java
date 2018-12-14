package the_biber_project;

import java.util.Scanner;

public class NameCheck {
    public static void main(String[] args) {
        System.out.println("Enter name: ");
        Scanner scanner = new Scanner(System.in);
        String name;
        do {
            name = scanner.nextLine();              //считываем данные, введенные пользователем
        } while (!checkName(name));                  //цикл будет идти до тех пор, пока имя не будет корректно
        System.out.println("Name is ok");
    }

    public static boolean checkName(String name) {
        if (name.length() < 5 || name.length() > 15) {                   //если нас не устраивает длина, возвращаем false
            System.err.println("Name length must be between 5 and 15");
            return false;
        }
        name = name.toLowerCase();                                      //переводим в буквы нижнего регистра, чтобы было удобнее
        char nameChars[] = new char[name.length()];
        name.getChars(0, name.length(), nameChars, 0);  //переводим строку в массив char
        for (char ch : nameChars) {
            if (!isRussianOrEnglishLetter(ch)) {
                System.err.println("All symbols must be English or Russian letters");
                return false;
            }
        }
        return true;
    }

    private static boolean isRussianOrEnglishLetter(char ch) {
        return ((ch >= 'a' && ch <= 'z') || (ch >= 'а' && ch <= 'я'));       //проверяем является ли буква из английского
    }                                                                   // или русского алфавита
}
