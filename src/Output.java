import java.io.Serializable;
//Wrapper for Output
public class Output implements Serializable {
    private static final long serialVersionUID = 1L;
    public void display(String s) {
        System.out.println(s);
    }

}
