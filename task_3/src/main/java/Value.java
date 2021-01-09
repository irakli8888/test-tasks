import java.util.*;

// будет существовать два типа величин:
// 1 - старший предок
//2 - младший предок
//у младшего предка будет ссылка на старшего предка, также коэфициент, на который его значение нужно умножить, чтобы сравняться со старшим
public class Value {

    private double coefficient;

    private String name;

    private Value bigParent;

    public Value(double coefficient, String name, Value bigParent) {
        this.coefficient = coefficient;
        this.name = name;
        this.bigParent = bigParent;
    }

    public Value(double coefficient, String name) {
        this.coefficient = coefficient;
        this.name = name;
        this.bigParent = bigParent;
    }

    //узнаем всех предков
    public List<Value> getAllParents(Value value){
        ArrayList<Value> values = new ArrayList<>();
        Value v=value;
        while (v.getParent() != null) {
            v = v.getParent();
            values.add(v);
        }return values;
    }

    //является ли величина предком
    public boolean isBigParent(String name){
        return getAllParents(this).stream().anyMatch(a->a.getName().equals(name));
    }

    //получаем всю семью величин
    public List<Value> getFamily(){
        ArrayList<Value> values = new ArrayList<>();
        Value v=this;
        values.add(v);
        while (v.getParent()!=null) {
            values.add(v);
            v = v.getParent();
        }return values;
    }

    //относится ли величина к данной семье
    public boolean isFamily(String name){
        return getFamily().stream().anyMatch(a->a.getName().equals(name));
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public Value getParent() {
        return bigParent;
    }

    public void setParent(Value parent) {
        this.bigParent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Value value = (Value) o;
        return Double.compare(value.coefficient, coefficient) == 0 &&
                Objects.equals(name, value.name) &&
                Objects.equals(bigParent, value.bigParent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coefficient, name, bigParent);
    }

    @Override
    public String toString() {
        return "Value{" +
                "coefficient=" + coefficient +
                ", name='" + name + '\'' +
                ", bigParent=" + bigParent +
                '}';
    }
}
