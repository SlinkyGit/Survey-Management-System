import java.io.Serializable;
import java.util.ArrayList;

public class TrueFalse extends Question implements Serializable {
    private static final long serialVersionUID = 1L;
    Input input = new Input();
    protected ArrayList<String> choicesArray = new ArrayList<String>();
    protected ArrayList<String> userResponse = new ArrayList<String>();

    public TrueFalse(String question, String questionType) {
        super(question, questionType);
//        choicesArray.add("True");
//        choicesArray.add("False");
//        this.setResponse(" ");

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
            this.output.display("Continuing...");
            return;
        }
    }

//    @Override
//    public ResponseCorrectAnswer getResponse(){
//        return getResponse();
//    }

    @Override
    public void getUserResponse() {
        //Same as multiple choice
        this.output.display("Enter 'True' or 'False': ");

        for (int i = 0; i < 1; i++){
            int whichResponse = 0;
            System.out.print((i+1) + ". ");

            String response = this.input.getInput();

            if ((response.equalsIgnoreCase("true")) || (response.equalsIgnoreCase("false"))){
                userResponse.add(response);
//                this.setResponse(userResponse.get(whichResponse));
                this.setResponse(new ResponseCorrectAnswer(userResponse));
            } else {
                this.output.display("Please enter a valid choice.");
                return;
            }
        }
    }

    public ArrayList<String> getAllQuestionChoices(){
        return this.choicesArray;
    }

    @Override
    public void display() {
        this.output.display(getPrompt());

        int questionNum = 1;

        for (String choice : getAllQuestionChoices()){
            System.out.print(questionNum + ". ");
            this.output.display(choice);
            questionNum++;
        }
    }

    @Override
    public ResponseCorrectAnswer updateCorrectAnswer(ResponseCorrectAnswer specificCorrectAnswer) {
        if (specificCorrectAnswer.getResponse().get(0).equalsIgnoreCase("true")){
            this.output.display("Do you wish to change the answer to False?");
            String responseForTrue = this.input.getInput();

            if (responseForTrue.equalsIgnoreCase("yes")){
                ArrayList<String> newAnswerList = new ArrayList<String>();
                newAnswerList.add("False");
                specificCorrectAnswer.setResponse(newAnswerList);
                this.output.display("Successfully updated choice.");
                return specificCorrectAnswer;

            }
            if (responseForTrue.equalsIgnoreCase("no")){
                this.output.display("No changes were made.");

            } if (!responseForTrue.equalsIgnoreCase("yes") && !responseForTrue.equalsIgnoreCase("no")) {
                this.output.display("Enter a valid 'yes' or 'no'");

            }
        }

        if (specificCorrectAnswer.getResponse().get(0).equalsIgnoreCase("false")){
            this.output.display("Do you wish to change the answer to True?");
            String responseForFalse = this.input.getInput();

            if (responseForFalse.equalsIgnoreCase("yes")){
                ArrayList<String> newAnswerList = new ArrayList<String>();
                newAnswerList.add("True");
                specificCorrectAnswer.setResponse(newAnswerList);
                this.output.display("Successfully updated choice.");
                return specificCorrectAnswer;
            }
            if (responseForFalse.equalsIgnoreCase("no")){
                this.output.display("No changes were made.");


            } if (!responseForFalse.equalsIgnoreCase("yes") && !responseForFalse.equalsIgnoreCase("no")) {
                this.output.display("Enter a valid 'yes' or 'no'");

            }
        }

        return specificCorrectAnswer;
    }


}
