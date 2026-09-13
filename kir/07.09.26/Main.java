import java.util.Objects;


public class Main {
    public static void main(String[] args) {
        System.out.println(" 1. БАЗОВЫЙ УРОВЕНЬ ");
        
        Student student = new Student("Леха", 20);
        System.out.println("Студент: " + student.getName() + ", возраст: " + student.getAge());
        
        Animal genericAnimal = new Animal();
        Animal dog = new Dog();
        Animal cat = new Cat();
        
        genericAnimal.makeSound();
        dog.makeSound();
        cat.makeSound();

        System.out.println("\n 2. СРЕДНИЙ УРОВЕНЬ ");
        
        Employee dev = new Developer("Халла", 1000, 300);
        Employee manager = new Manager("Саня", 1200, 0.25);
        Employee designer = new Designer("Мухаммад", 900, 10);

        System.out.println(dev.name + " зарплата: " + dev.calculateSalary());
        System.out.println(manager.name + " зарплата: " + manager.calculateSalary());
        System.out.println(designer.name + " зарплата: " + designer.calculateSalary());

        System.out.println("\n 3. СЛОЖНЫЙ УРОВЕНЬ ");
        
        Printable doc = new Document();
        Report report = new Report();
        
        doc.print();
        ((Savable) doc).save();
        
        report.print();
        report.save();
        report.exportToPDF();

        System.out.println("\n 4. ОЧЕНЬ СЛОЖНЫЙ УРОВЕНЬ (Мини-проект «Банк») ");
        
        Account acc1 = new SavingsAccount("ACC-001", 5000.0, 5.0);
        Account acc2 = new CreditAccount("ACC-002", 1000.0, 2000.0);

        System.out.println("До перевода:");
        System.out.println(acc1);
        System.out.println(acc2);

        acc1.transfer(acc2, 1500.0);
        System.out.println("\nПосле перевода 1500 со сберегательного на кредитный:");
        System.out.println(acc1);
        System.out.println(acc2);

        Account acc1Clone = acc1.clone();
        System.out.println("\nКлонированный аккаунт равен оригиналу? " + acc1.equals(acc1Clone));
    }
}


class Student {
    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        setAge(age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 16 && age <= 100) {
            this.age = age;
        } else {
            throw new IllegalArgumentException("Возраст должен быть в диапазоне от 16 до 100 лет.");
        }
    }
}

class Animal {
    public void makeSound() {
        System.out.println("Животное издает звук.");
    }
}

class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Гав-гав!");
    }
}

class Cat extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Мяу!");
    }
}



abstract class Employee {
    protected String name;
    protected double baseSalary;

    public Employee(String name, double baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
    }

    public abstract double calculateSalary();
}

class Developer extends Employee {
    private double completedProjectsBonus;

    public Developer(String name, double baseSalary, double completedProjectsBonus) {
        super(name, baseSalary);
        this.completedProjectsBonus = completedProjectsBonus;
    }

    @Override
    public double calculateSalary() {
        return baseSalary + completedProjectsBonus;
    }
}

class Manager extends Employee {
    private double kpiBonus;

    public Manager(String name, double baseSalary, double kpiBonus) {
        super(name, baseSalary);
        this.kpiBonus = kpiBonus;
    }

    @Override
    public double calculateSalary() {
        return baseSalary + (baseSalary * kpiBonus);
    }
}

class Designer extends Employee {
    private int hoursWorkedExtra;

    public Designer(String name, double baseSalary, int hoursWorkedExtra) {
        super(name, baseSalary);
        this.hoursWorkedExtra = hoursWorkedExtra;
    }

    @Override
    public double calculateSalary() {
        return baseSalary + (hoursWorkedExtra * 50);
    }
}



interface Printable { 
    void print(); 
}

interface Savable { 
    void save(); 
}

interface Exportable { 
    void exportToPDF(); 
}

class Document implements Printable, Savable {
    @Override 
    public void print() { 
        System.out.println("Печать документа..."); 
    }
    
    @Override 
    public void save() { 
        System.out.println("Сохранение документа..."); 
    }
}

class Image implements Printable {
    @Override 
    public void print() { 
        System.out.println("Печать изображения..."); 
    }
}

class Report implements Printable, Savable, Exportable {
    @Override 
    public void print() { 
        System.out.println("Печать отчета..."); 
    }
    
    @Override 
    public void save() { 
        System.out.println("Сохранение отчета..."); 
    }
    
    @Override 
    public void exportToPDF() { 
        System.out.println("Экспорт отчета в PDF..."); 
    }
}



interface Transferable {
    void transfer(Account destination, double amount);
}

abstract class Account implements Transferable, Cloneable {
    private String accountNumber;
    protected double balance;

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() { 
        return accountNumber; 
    }
    
    public double getBalance() { 
        return balance; 
    }

    @Override
    public void transfer(Account destination, double amount) {
        if (amount > 0 && balance >= amount) {
            this.balance -= amount;
            destination.balance += amount;
        } else {
            System.out.println("Ошибка перевода: недостаточно средств или некорректная сумма.");
        }
    }

    @Override
    public Account clone() {
        try {
            return (Account) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public String toString() {
        return "Account{number='" + accountNumber + "', balance=" + balance + "}";
    }
}

class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, double balance, double interestRate) {
        super(accountNumber, balance);
        this.interestRate = interestRate;
    }
}

class CreditAccount extends Account {
    private double creditLimit;

    public CreditAccount(String accountNumber, double balance, double creditLimit) {
        super(accountNumber, balance);
        this.creditLimit = creditLimit;
    }
}