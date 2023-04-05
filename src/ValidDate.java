import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ValidDate extends Question implements Serializable {
    private static final long serialVersionUID = 1L;
    Input input = new Input();
    protected ArrayList<String> answersArray = new ArrayList<String>();
    //Used library for date (refer to citation below)
    SimpleDateFormat dateInput = new SimpleDateFormat("MM-dd-yyyy");

    public ValidDate(String question, String questionType) {
        super(question, questionType);
//        setResponse(" ");
    }

    @Override
    public void updateQuestion() {
        System.out.println("Do you wish to modify the prompt? Type 'Yes' or 'No'");
        String response = this.input.getInput();

        if (response.equalsIgnoreCase("yes")){
            System.out.println("Enter a new prompt: ");
            response = this.input.getInput();
            this.setQuestion(response);
        }
        if (response.equalsIgnoreCase("no")){
            this.output.display("No changes made.");
            return;
        }
        if (!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no")) {
            this.output.display("Please enter a valid input.");
            return;
        }

    }

    @Override
    public void getUserResponse() {
        this.output.display("How many responses do you want to enter?");
        int responseNum = Integer.parseInt(this.input.getInput());
        this.output.display("Enter your response: ");
        this.output.display("A date should be entered in the following format: 'MM-DD-YYYY'");
        for (int i = 0; i < responseNum; i++){
            System.out.print((i+1) + ". ");
            String responseFromUser = this.input.getInput();
            //Inspired by https://stackoverflow.com/questions/27580655/how-to-set-a-date-as-input-in-java :
            try
            {
                Date date = dateInput.parse(responseFromUser);
                this.output.display(new SimpleDateFormat("MM-dd-yyyy").format(date));
                answersArray.add(responseFromUser);
                this.setResponse(new ResponseCorrectAnswer(answersArray));
            }
            catch (ParseException e)
            {
                this.output.display("Please enter a valid date in the correct format");
                return;
            }
        }
    }

    public void display() {
        this.output.display(getPrompt());
    }

    @Override
    public ResponseCorrectAnswer updateCorrectAnswer(ResponseCorrectAnswer specificCorrectAnswer) {
        this.output.display("Your current saved correct answer is:");
        specificCorrectAnswer.display();

        this.output.display("Would you like to change this answer?");
        String changeAnswer = this.input.getInput();

        if (changeAnswer.equalsIgnoreCase("yes")){
            ArrayList<String> newAnswerList = new ArrayList<String>();
            this.output.display("Enter a new correct date answer.");
            String newCorrectDate = this.input.getInput();

            try {
                Date date = dateInput.parse(newCorrectDate);
                System.out.print("You entered: ");
                this.output.display(new SimpleDateFormat("MM-dd-yyyy").format(date));
                newAnswerList.add(newCorrectDate);
                specificCorrectAnswer.setResponse(newAnswerList);
                this.output.display("Successfully updated correct date answer.");
//                return specificCorrectAnswer;
            }
            catch (ParseException e)
            {
                this.output.display("Please enter a valid correct date in the correct format");
//                return specificCorrectAnswer;
            }

        }
        if (changeAnswer.equalsIgnoreCase("no")){
            this.output.display("No changes were made.");

        } if (!changeAnswer.equalsIgnoreCase("yes") && !changeAnswer.equalsIgnoreCase("no")) {
            this.output.display("Enter a valid 'yes' or 'no'");
        }

        this.output.display("Correct Answer set to: ");
        return specificCorrectAnswer;
    }
}
