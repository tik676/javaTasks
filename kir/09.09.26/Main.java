import java.io.*;

public class Main {

    public static void main(String[] args) {
        
        System.out.println("=== 1. Строки ===");
        String text = "Хакер в реках";
        System.out.println("Исходная: " + text);
        System.out.println("Развернутая: " + reverseString(text));
        System.out.println("Палиндром? " + isPalindrome(text));

        System.out.println("\n=== 2. Исключения (multi-catch) ===");
        testMultiCatch(0); 
        testMultiCatch(2);  

        System.out.println("\n=== 3. Собственное исключение (NegativeBalanceException) ===");
        try {
            BankAccount account = new BankAccount(500.0);
            System.out.println("Баланс создан: " + account.getBalance());
            account.withdraw(600.0); 
        } catch (NegativeBalanceException e) {
            System.out.println("Перехвачено исключение: " + e.getMessage());
        }

        System.out.println("\n=== 4. Анализ текста через BufferedReader ===");
        String sampleFileContent = "Привет мир!\nJava отличный язык.\nТестируем BufferedReader.";
        analyzeText(sampleFileContent);

        
        System.out.println("\n=== 5. Сериализация и десериализация ===");
        try {
            Student student = new Student("Иван", 20, "secret_password_123");
            System.out.println("До сериализации: " + student);

            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(student);
            }

            
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            Student deserializedStudent;
            try (ObjectInputStream ois = new ObjectInputStream(bais)) {
                deserializedStudent = (Student) ois.readObject();
            }

            System.out.println("После десериализации (пароль будет null, т.к. transient): " + deserializedStudent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  Задание 1: 
    public static String reverseString(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    public static boolean isPalindrome(String s) {
        String cleaned = s.replaceAll("[^a-zA-ZА-Яа-я0-9]", "").toLowerCase();
        String reversed = new StringBuilder(cleaned).reverse().toString();
        return cleaned.equals(reversed);
    }

    //  Задание 2: 
    public static void testMultiCatch(int divisor) {
        try {
            int[] numbers = {10, 20, 30};
            int result = 100 / divisor;
            System.out.println("Результат деления: " + result);
            System.out.println("Элемент массива: " + numbers[5]); 
        } catch (ArithmeticException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Перехвачено исключение [" + e.getClass().getSimpleName() + "]: " + e.getMessage());
        }
    }

    // Задание 3: 
    public static class NegativeBalanceException extends Exception {
        public NegativeBalanceException(String message) {
            super(message);
        }
    }

    public static class BankAccount {
        private double balance;

        public BankAccount(double initialBalance) throws NegativeBalanceException {
            if (initialBalance < 0) {
                throw new NegativeBalanceException("Начальный баланс не может быть отрицательным.");
            }
            this.balance = initialBalance;
        }

        public void withdraw(double amount) throws NegativeBalanceException {
            if (balance - amount < 0) {
                throw new NegativeBalanceException("Недостаточно средств. Баланс уходит в минус.");
            }
            balance -= amount;
        }

        public double getBalance() {
            return balance;
        }
    }

    // Задание 4: 
    public static void analyzeText(String text) {
        try (BufferedReader reader = new BufferedReader(new StringReader(text))) {
            int lineCount = 0;
            int wordCount = 0;
            int charCount = 0;
            String line;

            while ((line = reader.readLine()) != null) {
                lineCount++;
                charCount += line.length();
                if (!line.trim().isEmpty()) {
                    String[] words = line.trim().split("\\s+");
                    wordCount += words.length;
                }
            }

            System.out.println("Строк: " + lineCount);
            System.out.println("Слов: " + wordCount);
            System.out.println("Символов: " + charCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Задание 5: 
    public static class Student implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private final String name;
        private final int age;
        private final transient String password; 

        public Student(String name, int age, String password) {
            this.name = name;
            this.age = age;
            this.password = password;
        }

        @Override
        public String toString() {
            return "Student{name='" + name + "', age=" + age + ", password='" + password + "'}";
        }
    }
}