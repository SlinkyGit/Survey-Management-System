import java.io.Serializable;
import java.util.ArrayList;

public class ResponseCorrectAnswer implements Serializable {
    private static final long serialVersionUID = 1L;
    protected Output output = new Output();
    protected Input input = new Input();
    protected ArrayList<String> response = new ArrayList<String>();

    public ResponseCorrectAnswer(ArrayList<String> response) {
        this.response = response;
    }

    public ArrayList<String> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<String> response) {
        this.response = response;
    }

    public void display() {
        if (this.response != null) {
            for (int i = 0; i < response.size(); i++) {
                this.output.display("Q" + (i + 1) + ": " + response.get(i));
            }
        }
    }

    public Boolean compare(ResponseCorrectAnswer responseCorrectAnswer) {
        if (response.size() == responseCorrectAnswer.getResponse().size()) { //Checks to see if both arrays match in size
            for (String answer : responseCorrectAnswer.getResponse()) {
                if (response.contains(answer.toLowerCase())) { //if the response from user matches contained correct answer
                    return true;
                }
            }
        }
        return false;
    }
}
