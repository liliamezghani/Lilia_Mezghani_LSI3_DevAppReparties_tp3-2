import java.io.Serializable;
public class Operation implements Serializable {
    public double n1;
    public double n2;
    public String op;
    public Operation(double n1, double n2, String op) {
        this.n1 = n1;
        this.n2 = n2;
        this.op = op;
    }
}