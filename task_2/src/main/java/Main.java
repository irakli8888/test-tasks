

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {

    private static final File FILE = new File("out.txt"); // файл записи
    private static AtomicInteger atomicInteger = new AtomicInteger(); // атомарная переменная для использования двумя потоками(пока переменную будет использовать один поток, другие не смогу ее изменять)
    private static final String START_VALUE = "0"; // начальное значение

    public static void main(String[] args) throws IOException {
        while (true)
        {
            try
            {
                Scanner sc = new Scanner(System.in);
                System.out.println("введите число больше нуля и кратное 2");
                int value = sc.nextInt();
                if (value < 0 || value % 2 != 0) //число должно быть кратно 2
                {
                    System.out.println("введеное число некорректно");
                } else
                    {
                    int start = Integer.parseInt(writeAndRead(FILE, START_VALUE)); // записываем, считываем начальное значение(0)
                    atomicInteger = new AtomicInteger(start); // присваеваем его атомарной переменной
                    for (int i = 0; i < 2; i++)  // создаем два потока и запускаем их
                    {
                        new MyThread(value).start();
                    }
                }
            } catch (Exception e) {
                System.out.println("введите число!");
            }
        }
    }

    static class MyThread extends Thread {
        int value;
        MyThread(int value)
        {
            this.value = value;
        }
        @Override
        public void run()
        {
            try
            {
                Thread currentThread = Thread.currentThread();
                while (atomicInteger.get() != value) // крутим цикл, пока атомарная переменная не дойдет до введенного значения
                {
                    String line= writeAndRead(FILE, String.valueOf(atomicInteger.get())); // получаем значение из файла каждую итерацию
                    atomicInteger.set(Integer.parseInt(line));  // и присваеваем ее переменной
                    String pastValue = String.valueOf(atomicInteger.get());
                    atomicInteger.incrementAndGet(); // +1 за каждую итерацию
                    String response = "текущий поток: " + currentThread.getName() +
                            "; старое значение: " + pastValue + "; новое значение: " + atomicInteger.get();
                    System.out.println(response);
                    writeAndRead(FILE, String.valueOf(atomicInteger.get())); //чтение и запись в файл
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static String writeAndRead(File file,String value)
    {
        try
        {
            FileWriter writer  = new FileWriter(file,false);
            writer.write(value);
            writer.flush();
            writer.close();

            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            return line;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
