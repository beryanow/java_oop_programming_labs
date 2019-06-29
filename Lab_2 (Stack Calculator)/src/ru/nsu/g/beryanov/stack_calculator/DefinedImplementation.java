package ru.nsu.g.beryanov.stack_calculator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.Scanner;

public class DefinedImplementation {

    static void implementOperations(String x) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Properties prop = new Properties();

        Scanner wordSequence = new Scanner(x);

        InputStream inputStream = Calculator.class.getClassLoader().getResourceAsStream("config.properties");
        prop.load(inputStream);

        while (wordSequence.hasNext()) {
            int possibleNumber;
            String temp = null;
            try {
                temp = wordSequence.next();
                possibleNumber = Integer.parseInt(temp);
                Calculator.getWordStack().push(possibleNumber);
            } catch (NumberFormatException e) {
                if (Calculator.getFunctions().containsKey(temp)) {
                    String newOperations = Calculator.getFunctions().get(temp);
                    DefinedImplementation.implementOperations(newOperations);
                    continue;
                }

                if (temp.equals("[")) {
                    String newCycle = "";
                    int y = 0;
                    while (!((temp = wordSequence.next()).equals("]") && (y == 0))) {
                        if (temp.equals("]")) {
                            y--;
                        }

                        if (!(newCycle.equals(""))) {
                            newCycle += " ";
                        }

                        if (temp.equals("[")) {
                            y++;
                        }

                        newCycle += temp;
                    }
                    CycleImplementation.implementOperations(newCycle);
                    continue;
                }

                temp = temp.toLowerCase();

                Operation op = null;

                String tmp = temp;
                temp = prop.getProperty(temp);

                if (temp == null) {
                    temp = tmp;
                    temp = temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase();
                    op = (Operation) Class.forName(("ru.nsu.g.beryanov.stack_calculator." + temp)).getDeclaredConstructor().newInstance();
                }
                else {
                    temp = temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase();
                    op = (Operation) Class.forName(("ru.nsu.g.beryanov.stack_calculator." + temp)).getDeclaredConstructor().newInstance();
                }

                op.performOperation(Calculator.getWordStack());
            }
        }
        wordSequence.close();
    }
}
