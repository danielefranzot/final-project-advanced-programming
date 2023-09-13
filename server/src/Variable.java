import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.math.RoundingMode.CEILING;

public class Variable {
    private String variableName;

    private BigDecimal[] valueArray;

    public Variable(String variableValuesFunction) throws Exception {
        //variable values function format   variableName:startingValue:step:endingValue
        String[] variableValuesFunctionArray = variableValuesFunction.split(":");


        if(!(variableValuesFunctionArray.length == 1 || variableValuesFunctionArray.length == 4)){
            throw new Exception("invalid format of variable values function, some parameters are missing");
        }

        BigDecimal startValue = new BigDecimal(variableValuesFunctionArray[1]);
        BigDecimal step = new BigDecimal(variableValuesFunctionArray[2]);
        BigDecimal endValue = new BigDecimal(variableValuesFunctionArray[3]);

        if(!checkVariableName(variableValuesFunctionArray[0])){
            throw new Exception("invalid variable name");
        }
        this.variableName = variableValuesFunctionArray[0];

        if(step.doubleValue() <= 0){
            throw new Exception("step value must be greater then 0");
        }

        if(startValue.doubleValue() >= endValue.doubleValue()){
            throw new Exception("start value must less then endValue");
        }

        this.valueArray = generateArray(startValue, step, endValue);

    }

    private BigDecimal[] generateArray(BigDecimal startValue, BigDecimal step, BigDecimal endValue){
        int arraySize = endValue.subtract(startValue).divide(step,CEILING).intValue() + 1;    //(endvalue - startvalue)/step
        BigDecimal[] arrayOfValues = new BigDecimal[arraySize];
        BigDecimal currentValue = startValue;   //essendo BigDecimal immutabile non corro il rischio di sovrascrivere il valore
        for (int i = 0; i < arraySize; i++) {
            arrayOfValues[i] = currentValue;
            currentValue = currentValue.add(step);
        }

        return arrayOfValues;
    }

    private static boolean checkVariableName(String name){
    String patternString = "[a-zA-Z]+[0-9]+";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public void printArray(){
        for (BigDecimal value : this.valueArray) {
            System.out.println(value.toString());
        }
    }
}
