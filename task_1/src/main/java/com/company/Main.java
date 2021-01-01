package com.company;


import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {

    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("введите ваше число ");
                int value = sc.nextInt();
                if (value <= 0) {
                    while (value <= 0 || value >= Integer.MAX_VALUE) {
                        System.out.println("введите число повторно\nчисло должно быть больше 0 ");
                        value = sc.nextInt();
                    }
                }
                System.out.println(firstMethod(value) + "\n" +
                        secondMethod(value) + "\n" +
                        thirdMethod(value) + "\n" +
                        fourthMethod(value, AVAILABLE_PROCESSORS));
            } catch (InputMismatchException e) { // ловим строки, не являющиеся числовым значением
                System.out.println("ваше значение некоректно!");
            }

        }
    }

    public static StringBuilder firstMethod(int value){
        long start = System.currentTimeMillis();
        StringBuilder stringBuilder=new StringBuilder();
        Stream<Integer> streamFromIterate = Stream.iterate(1, n -> n+1);//создаем стрим, значение которого задаются арифметической прогрессией х+1
        streamFromIterate.limit(value).peek(a->{ //задаем лимит, соответсвующий полученному значению
            if(a%3== 0 || a%5 == 0){ // если число кратно 3 или 5, то заходим в тело условного оператора
                String c = a%3== 0? "foo ": ""; // присваеваем переменной значение в зависимости от значения элемента стрим
                String e = a%5== 0? "bar": "";
                stringBuilder.append(c+e); // в stringBuilder заносим значения
            }
            else stringBuilder.append(a);  // нет совпадений по условному оператору - заносим число
            stringBuilder.append("\n"); // чистим stringBuilder для следющих итераций
        }).collect(Collectors.toList());
        long finish = System.currentTimeMillis();
        long timeConsumedMillis = finish - start; // время выполнения
        String answer="первый метод (классическое ветвление + стрим + тернарные операторы)"+
                "\nвремя выполнения "+timeConsumedMillis+"мс";
        return stringBuilder.append(answer);
    }

    public static StringBuilder secondMethod(int value){
        long start = System.currentTimeMillis();
        StringBuilder stringBuilder=new StringBuilder();
        for(int i = 1;i < value + 1 ; i++){
            if(i%3 == 0) stringBuilder.append("foo ");
            if(i%5 == 0) stringBuilder.append("bar");
            if(i%3 != 0 && i%5 != 0) stringBuilder.append(i);
            stringBuilder.append("\n");
        }
        long finish = System.currentTimeMillis();
        long timeConsumedMillis = finish - start;
        String answer="второй метод (классическое ветвление)"+
                "\nвремя выполнения "+timeConsumedMillis+"мс";
        return stringBuilder.append(answer);
    }

    public static StringBuilder thirdMethod(int value){
        long start = System.currentTimeMillis();
        StringBuilder stringBuilder=new StringBuilder();
        Stream<Integer> streamFromIterate = Stream.iterate(1, n -> n+1); //стрим с геометрической прогрессией
        streamFromIterate.limit(value).peek(a->{
            if(a%3 == 0) stringBuilder.append("foo "); // заносим в буффер подходящие значения
            if(a%5 == 0) stringBuilder.append("bar");
            if(a%3 != 0 && a%5 != 0) stringBuilder.append(a);
            stringBuilder.append("\n");
        }).collect(Collectors.toList());
        long finish = System.currentTimeMillis();
        long timeConsumedMillis = finish - start;
        String answer ="третий метод (классическое ветвление + стрим)"+
                "\nвремя выполнения "+timeConsumedMillis+"мс";
        return  stringBuilder.append(answer);
    }

    public static StringBuilder fourthMethod(int value, int AVAILABLE_PROCESSORS) throws InterruptedException {
        long start = System.currentTimeMillis();
        StringBuilder stringBuilder=new StringBuilder();
        int place=0; //точка остчета, переменная для слежения за ходом итераций
        int step=Math.round(value/AVAILABLE_PROCESSORS)+1; //рамер отрезка для обработки одним потоком
        ExecutorService service = Executors.newSingleThreadExecutor(); //обработка потоков будет происходить поочередно, чтобы вывод был корректен
        for(int x = 0; x < AVAILABLE_PROCESSORS; x++) { //создаем потоки и раздаем им задачи
            int finalPlace = place;
            int finalValue = value;
            Thread d = new Thread(() -> {
                for (int i = finalPlace + 1; i <finalPlace + 1 + step; i++) {
                    if (i % 3 == 0) stringBuilder.append("foo ");
                    if (i % 5 == 0) stringBuilder.append("bar");
                    if (i % 3 != 0 && i % 5 != 0) stringBuilder.append(i);
                    stringBuilder.append("\n");
                    if(i == finalValue) break;
                }
            });

            service.execute(d); //отпрвляем потоки в сервис
            place = finalPlace + step; // изменяем положение точки отсчета
        }
        service.shutdown(); //ожидаем выполнение задач и завершаем работу
        while (!service.isTerminated()){
            Thread.yield(); // пока потоки не доделают работу главный поток ждет
        }
        long finish = System.currentTimeMillis();
        long timeConsumedMillis = finish - start;
        String answer="четвертый метод (разбиение по потокам + классическое ветвление) "+
                "\nзайдействовано потоков: "+ AVAILABLE_PROCESSORS +
                "\nобработка значений для одного ядра: "+ step +
                "\nвремя выполнения: "+ timeConsumedMillis+ "мс ";
        return stringBuilder.append(answer);
    }
}
