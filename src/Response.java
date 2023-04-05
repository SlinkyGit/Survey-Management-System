import java.io.Serializable;
import java.util.ArrayList;

public class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    protected Output output = new Output();
    protected ArrayList<String> userResponse = new ArrayList<String>();

    public Response(ArrayList<String> userResponse) {
        this.userResponse = userResponse;
    }

    public ArrayList<String> getUserResponse() {
        return userResponse;
    }

    public void display() {
        if (this.userResponse != null) {
            for (String character : userResponse) {
                this.output.display(character);
            }
        }
    }



}
