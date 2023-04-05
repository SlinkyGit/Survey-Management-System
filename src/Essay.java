import java.io.Serializable;
import java.util.ArrayList;

public class Essay extends Question implements Serializable {
    private static final long serialVersionUID = 1L;
    Input input = new Input();

    protected ArrayList<String> answersArray = new ArrayList<String>();

    public Essay(String question, String questionType){
        super(question, questionType);

    }

    public String getSpecificChoice(int questionNumber){
        return answersArray.get(questionNumber);
    }


    @Override
    public void updateQuestion() {
        System.out.println("Do you wish to modify the prompt? Type 'Yes' or 'No'");
        String response = this.input.getInput();

        if (response.equalsIgnoreCase("Yes")){
            System.out.println("Enter a new prompt: ");
            response = this.input.getInput();
            this.setQuestion(response);
        } else {
            this.output.display("Please enter a valid input.");
            return;
        }

    }

    @Override
    public void getUserResponse() {
        int responseNum = 0;
        this.output.display("How many responses do you want to enter?");
        try {
            responseNum = Integer.parseInt(this.input.getInput());
        } catch(NumberFormatException e){
            this.output.display("Enter a number.");
        }
        this.output.display("Enter your response: ");

        for (int i = 0; i < responseNum; i++){
            System.out.print((i+1) + ". ");
            String responseFromUser = this.input.getInput();

            while (responseFromUser.length() > 100) {
                this.output.display("Character limit exceeded 50.");
                responseFromUser = this.input.getInput();
            }

            answersArray.add(responseFromUser);
        }

        this.setResponse(new ResponseCorrectAnswer(answersArray));

    }

    public void display(){
        super.display();
    }

    @Override
    public ResponseCorrectAnswer updateCorrectAnswer(ResponseCorrectAnswer specificCorrectAnswer) {
        this.output.display("No correct answer needed for essay questions.");
        return specificCorrectAnswer;
    }
}
