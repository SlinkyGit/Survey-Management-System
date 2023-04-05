import java.io.Serializable;
import java.util.ArrayList;

public class MultipleChoice extends Question implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String question;
    Input input = new Input();
    protected ArrayList<String> choicesArray = new ArrayList<String>();
    protected ArrayList<String> userResponse = new ArrayList<String>();


    public MultipleChoice(String question, ArrayList<String> choicesArray, String questionType){
        super(question, questionType);
        this.setQuestionChoices(choicesArray);
    }

    public void setQuestionChoices(ArrayList<String> choicesArray){
        this.choicesArray = choicesArray;
    }

    public ArrayList<String> getAllQuestionChoices(){
        return this.choicesArray;
    }

    public int getNumberofChoices(){
        return choicesArray.size();
    }

    public String getSpecificChoice(int questionNumber){
        return choicesArray.get(questionNumber);
    }

    @Override
    public void updateQuestion() {
        //Here is where the user can modify the prompt and choices
        this.output.display("Do you wish to modify the prompt? Type 'Yes' or 'No'");
        this.input = new Input(); //due to old serialized files in directory
        String response = this.input.getInput();

        if (response.equalsIgnoreCase("Yes")){
            System.out.println("Enter a new prompt: ");
            response = this.input.getInput();
            this.setQuestion(response);
        }

        this.output.display("Do you wish to modify choices?");
        this.input = new Input();
        String choicesResponse = this.input.getInput();
        if (choicesResponse.equalsIgnoreCase("Yes")){
            this.output.display("Which choice do you want to modify?");
            this.display();
            int choiceToChange = Integer.parseInt(this.input.getInput());
            this.getSpecificChoice(choiceToChange - 1);
            this.output.display("Enter your new choice");
            String newChoice = this.input.getInput();
            choicesArray.set((choiceToChange - 1), newChoice);
        } else {
            return;
        }
    }

    @Override
    public void getUserResponse() {

        this.output.display("How many responses do you want to enter?");
        int responseNum = Integer.parseInt(this.input.getInput());

        if (responseNum > getNumberofChoices()){
            this.output.display("Invalid number of choices.");
            return;
        }
        this.output.display("Enter your response number: ");

        for (int i = 0; i < responseNum; i++){
            int whichResponse = 1;
            System.out.print((i+1) + ". ");
            try{
                whichResponse = Integer.parseInt(this.input.getInput());
            } catch (NumberFormatException e){
                this.output.display("Enter the specific choice number, not the choice.");
                return;
            }
            String responseFromUser = Integer.toString(whichResponse);

            if (getNumberofChoices() >= (whichResponse - 1)){

                userResponse.add(responseFromUser);

                this.setResponse(new ResponseCorrectAnswer(userResponse));

            } else {
                this.output.display("Please enter a valid choice.");
                return;
            }
        }
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
        int numCorr = 0;
        this.output.display("Your current saved correct answer is:");
        specificCorrectAnswer.display();

        try {
            this.output.display("How many correct answers do you want?");
            numCorr = Integer.parseInt(this.input.getInput());

            if ((numCorr > getNumberofChoices())){
                this.output.display("Enter a valid number of correct answers.");
                return specificCorrectAnswer;
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        ArrayList<String> changedAnswer = new ArrayList<>();

        for (int i = 0; i < numCorr; i++) {
            display();
            System.out.println("Enter a new correct answer in integer format: ");

            String newAns = this.input.getInput();
            int newAnsInt = Integer.parseInt(newAns);

            if ((newAns.length() > getNumberofChoices()) || (newAnsInt < 0) || (newAnsInt > getNumberofChoices())){
                this.output.display("Enter a correctly formatted choice (ex: '12').");
                return specificCorrectAnswer;
            } else if (specificCorrectAnswer.getResponse().contains(newAns)) {
                this.output.display("This correct answer is already stored as a correct one:");
                System.out.print("Your current saved correct answer is: ");
                return specificCorrectAnswer;
            }
            else {
                changedAnswer.add(newAns);
            }

        }
        //Set new correct answer
        specificCorrectAnswer.setResponse(changedAnswer);

        return specificCorrectAnswer;
    }
}
