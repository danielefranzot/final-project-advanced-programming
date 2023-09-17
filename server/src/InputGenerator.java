import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class InputGenerator {


    private String valuesKind;
    private Variable[] variablesDataValues;

    List<List<BigDecimal>> equationInputArray = new ArrayList<>();

    public InputGenerator(String inputMode, Variable... variablesArray) {
        if (inputMode.equals("LIST") || inputMode.equals("GRID")) this.valuesKind = inputMode;
        this.variablesDataValues = variablesArray;
        this.generateInputArray();
    }

    private void generateInputArray() {
        if (this.valuesKind.equals("LIST")) {
            this.equationInputArray = new ArrayList<>();
            int size = this.variablesDataValues[0].getValueArraySize();
            System.out.println("denteo generate input");
            for (int i = 0; i < size; i++) {
                List<BigDecimal> tuple = new ArrayList<>();
                System.out.println("tupla" + i);
                for (Variable variable : this.variablesDataValues) {
                    System.out.println(variable.getValueFromArray(i).toString());
                    tuple.add(variable.getValueFromArray(i));
                }
                this.equationInputArray.add(tuple);
            }

        } else if (this.valuesKind.equals("GRID")) {
            generateGridInput(new ArrayList<>(), 0);
        }

    }

    private void generateGridInput(List<BigDecimal> tuple, int index) {
        if (index == this.variablesDataValues.length) {

            this.equationInputArray.add(tuple.stream().toList());
            return;
        }

        BigDecimal[] variableValues = this.variablesDataValues[index].getValueArray();

        for (BigDecimal value : variableValues) {
            tuple.add(value);
            this.generateGridInput(tuple, index + 1);

            tuple.remove(tuple.size() - 1);
        }

    }

    public void printInput() {
        for (List<BigDecimal> tuple : this.equationInputArray) {
            System.out.print("(");
            for (BigDecimal value : tuple) {
                System.out.print(value.toString() + " ");
            }
            System.out.println(")");
        }
        System.out.println(this.equationInputArray.size());
        System.out.println(this.equationInputArray.get(0).size());
    }

}
