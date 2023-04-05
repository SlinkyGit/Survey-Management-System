import java.io.Serializable;
import java.util.Scanner;
//Wrapper for Input
public class Input implements Serializable {
    private static final long serialVersionUID = 1L;
    public String getInput() {
        Scanner newInput = new Scanner(System.in);
        String hasNextLine = newInput.nextLine();
        return hasNextLine;
    }

}
