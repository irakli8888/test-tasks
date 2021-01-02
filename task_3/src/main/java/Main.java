
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static HashSet<Value> values = new HashSet<>();
    private static final Pattern VALUES_TO_INPUT = Pattern.compile("\\s*\\d+\\s+[A-Za-zА-Яа-я]+\\s*=\\s*\\d+\\s+[A-Za-zА-Яа-я]+\\s*");
    private static final Pattern VALUES_FOR_THE_OUTPUT = Pattern.compile("\\s*\\d+\\s+[A-Za-zА-Яа-я]+\\s*=\\s*\\?\\s+[A-Za-zА-Яа-я]+\\s*");
    private static final String INCORRECT_DATA = "некоректные данные!";

    public static void main(String[] args) {

        System.out.println("введите соотношения: \nдля выхода из программы введите \"stop\"");

        try {
            while (true) {
                Scanner sc = new Scanner(System.in);
                String inputData = sc.nextLine();
                Matcher matcherForInput = VALUES_TO_INPUT.matcher(inputData);
                Matcher matcherForOutput = VALUES_FOR_THE_OUTPUT.matcher(inputData);
                if (inputData.equals("stop")) break;
                if (matcherForInput.matches())
                {
                    System.out.println(recordingAndProcessing(inputData));
                }
                else if (matcherForOutput.matches())
                {
                    System.out.println(calculationOfResults(inputData));
                }else System.out.println(INCORRECT_DATA);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String calculationOfResults(String inputData){
        //делим полученную строку на 4 главных элемента
        // (где 0 - величина первой переменной, 1 - ее наименование, 2 - величина второ1 переменной, 3 - ее наименование)
        String mas[] = inputData.trim().replaceAll("=", "").replaceAll("[\\s]{2,}", " ").split(" ");

        //если величины есть в сете и 2 величина входит в семейство первой, то начинам вычисления
        if (values.stream().anyMatch(value -> value.getName().equals(mas[3])
                && value.isFamily(mas[3])) && values.stream().anyMatch(value -> value.isFamily(mas[1]))) //если в наших значениях уже есть такая единица и она входит в состав семьи идем дальше
        {
            //коэфициент умножения для искомой величниы
            double finalCoefficient = values.stream().filter(value -> value.getName().equals(mas[3])).mapToDouble(value ->
            {
                //если первое значение старший предок второго, то перемножаем коэфициенты до тех пор, пока не дойдем до первого значения
                if (value.isBigParent(mas[1]))
                {
                    double finalFactor = value.getCoefficient();
                    Value pValue = value.getParent();
                    while (!pValue.getName().equals(mas[1]))
                    {
                        finalFactor *= pValue.getCoefficient();
                        pValue = pValue.getParent();
                    }
                    return finalFactor;
                    // если второе значение младше предка, то начиная отсчет от него также перемножаем коэфициенты
                    // и делим 1 на результат
                } else
                {
                    Value pValue = values.stream().filter(value1 -> value1.getName().equals(mas[1])).collect(Collectors.toList()).get(0);
                    double finalFactor = pValue.getCoefficient();

                    //отсчет от младшего к старшему
                    while (!pValue.getParent().getName().equals(mas[3]))
                    {
                        pValue = pValue.getParent();
                        finalFactor *= pValue.getCoefficient();
                    }
                    return 1/finalFactor;
                }
            }).average().getAsDouble();
            String response = mas[0] + " " + mas[1] + " = " + Double.valueOf(mas[0]) * finalCoefficient + " " + mas[3];
            return response;
        } else return  INCORRECT_DATA;
    }

    public static String recordingAndProcessing(String inputData)
    {
        //делим полученную строку на 4 главных элемента
        // (где 0 - величина первой переменной, 1 - ее наименование, 2 - величина второй переменной, 3 - ее наименование)
        String mas[] = inputData.trim().replaceAll("=", "").replaceAll("[\\s]{2,}", " ").split(" ");

        //будет существовать 2 основных варианта развития событий:
        // 1 значение больше 2 ; 2 больше 1
        if (Double.valueOf(mas[0]) > Double.valueOf(mas[2])) //1 значение больше 2
        {
            Value olderValue;
            Value lowerValue = null;

            //если величина 1 уже есть и у нее нет старшего предка, то удаляем старое значение
            // и записываем новое, но уже с предком, соответсвующим значению 2
            if(values.stream().anyMatch(value -> value.getName().equals(mas[1]) && value.getParent() == null))
            {
                olderValue = new Value(1, mas[3]);
                lowerValue=values.stream().filter(value->value.getName().equals(mas[1])).collect(Collectors.toList()).get(0);
                lowerValue.setParent(olderValue);
                lowerValue.setCoefficient(Double.valueOf(mas[0]) / Double.valueOf(mas[2]));
                values.removeIf(value -> value.getName().equals(mas[1]));
                values.add(lowerValue);
                values.add(olderValue);
            }
            //если есть величина 2, то добавляем только 1, указывая предком 2
            else if (values.stream().anyMatch(value -> value.getName().equals(mas[3]) && value.getParent() != null))
            {
                lowerValue = new Value(Double.valueOf(mas[0]) / Double.valueOf(mas[2]), mas[1],
                        values.stream().filter(value -> value.getName().equals(mas[3])).collect(Collectors.toList()).get(0));
                values.add(lowerValue);
                //если совпадений по велечинам нет, то вписываем обе
            }else if(values.stream().noneMatch(value -> value.getName().equals(mas[3]) &&
                    values.stream().noneMatch(value2 -> value2.getName().equals(mas[1]))))
            {
                olderValue = new Value(1, mas[3]);
                lowerValue = new Value(Double.valueOf(mas[0]) / Double.valueOf(mas[2]),
                        mas[1], olderValue);
                values.add(olderValue);
                values.add(lowerValue);
            }
            if (values.stream().anyMatch(value -> value.getParent() != null))
            {
                Value finalLowerValue = lowerValue;
                values.stream().filter(value -> value.getParent() != null).filter(value ->
                        value.getParent().getName().equals(finalLowerValue.getName())).
                        peek(value -> value.setParent(finalLowerValue)).collect(Collectors.toList());
                values.removeIf(value -> value.getName().equals(finalLowerValue) && value.getFamily() == null);
            }
        }
        else //2 значение больше 1
        // аналогичные дейсвтия, но меням местами 2 и 1
        {
            Value olderValue;
            Value lowerValue = null;

            if(values.stream().anyMatch(value -> value.getName().equals(mas[3]) && value.getParent() == null))
            {
                olderValue = new Value(1, mas[1]);
                lowerValue=values.stream().filter(value->value.getName().equals(mas[3])).collect(Collectors.toList()).get(0);
                lowerValue.setParent(olderValue);
                lowerValue.setCoefficient(Double.valueOf(mas[2]) / Double.valueOf(mas[0]));
                values.removeIf(value -> value.getName().equals(mas[3]));
                values.add(lowerValue);
                values.add(olderValue);
            }
            else if(values.stream().anyMatch(value -> value.getName().equals(mas[1]) && value.getParent() != null))
            {
                lowerValue = new Value(Double.valueOf(mas[2]) / Double.valueOf(mas[0]), mas[3],
                        values.stream().filter(value -> value.getName().equals(mas[1])).collect(Collectors.toList()).get(0));
                values.add(lowerValue);
            }
            else if(values.stream().noneMatch(value -> value.getName().equals(mas[3]) &&
                    values.stream().noneMatch(value2 -> value2.getName().equals(mas[1]))))
            {
                olderValue = new Value(1, mas[1]);
                lowerValue = new Value(Double.valueOf(mas[2]) / Double.valueOf(mas[0]), mas[3],
                        olderValue);
                values.add(olderValue);
                values.add(lowerValue);
            }
            if (values.stream().anyMatch(value -> value.getParent() != null))
            {
                Value finalSmallerValue = lowerValue;
                values.stream().filter(value -> value.getParent() != null).filter(value ->
                        value.getParent().getName().equals(finalSmallerValue.getName())).
                        peek(value -> value.setParent(finalSmallerValue)).collect(Collectors.toList());
                values.removeIf(value -> value.getName().equals(finalSmallerValue) && value.getFamily() == null);
            }
        }
        String response =values.stream().map(value -> value.getName() + " " +
                value.getCoefficient() + " " + value.getParent()).collect(Collectors.toList()).toString();
        return response;
    }
}
