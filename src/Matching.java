import java.io.Serializable;
import java.util.ArrayList;

public class Matching extends Question implements Serializable {
    private static final long serialVersionUID = 1L;
    protected ArrayList<String> leftSide;
    protected ArrayList<String> rightSide;
    Input input = new Input();
    protected ArrayList<String> userResponseArr = new ArrayList<String>();

    public Matching(String question, ArrayList<String> leftSide, ArrayList<String> rightSide, String questionType) {
        super(question, questionType);
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }


    @Override
    public void updateQuestion() {
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
            this.output.display("Which side do you want to modify? Enter 'left' or 'right'");
            this.display();
            String sideResponse = this.input.getInput();

            if (sideResponse.equalsIgnoreCase("left")){
                this.output.display("Which choice number do you want to modify?");
                int choiceToChange = Integer.parseInt(this.input.getInput());
                this.getLeftSideSpecific(choiceToChange - 1);
                this.output.display("Enter your new choice");
                String newChoice = this.input.getInput();
                leftSide.set((choiceToChange - 1), newChoice);
            }
            if (sideResponse.equalsIgnoreCase("right")){
                this.output.display("Which choice number do you want to modify?");
                int choiceToChange = Integer.parseInt(this.input.getInput());
                this.getRightSideSpecific(choiceToChange - 1);
                this.output.display("Enter your new choice");
                String newChoice = this.input.getInput();
                rightSide.set((choiceToChange - 1), newChoice);
            }
        } else {
            return;
        }

    }

    @Override
    public void getUserResponse() {
        this.output.display("Type in an element for your choice: ");
        String userResponse = this.input.getInput();
        int rightArrCnt = 1;
        int leftArrCnt = 1;

        for (String response : getRightSide()){
            if (getRightSide().get(rightArrCnt - 1).equalsIgnoreCase(response)){
                userResponseArr.add(response);
//                this.setResponse(userResponseArr.get(rightArrCnt - 1));
            } else {
                this.output.display("Please enter a valid choice.");
                return;
            }
            rightArrCnt++;
        }

        for (String response : getLeftSide()){
            if (getLeftSide().get(leftArrCnt - 1).equalsIgnoreCase(response)){
                userResponseArr.add(response);

            } else {
                this.output.display("Please enter a valid choice.");
                return;
            }
            leftArrCnt++;
        }

        this.setResponse(new ResponseCorrectAnswer(userResponseArr));

    }

    public ArrayList<String> getLeftSide(){
        return leftSide;
    }

    public ArrayList<String> getRightSide(){
        return rightSide;
    }

    public String getLeftSideSpecific(int choiceNumber){
        int leftArrayLength = leftSide.size();
        if (choiceNumber > leftArrayLength){
            int diff = choiceNumber - leftArrayLength;
            for (int i = 0; i < diff; i++){
                leftSide.add(" ");
            }
        }
        return leftSide.get(choiceNumber);
    }

    public String getRightSideSpecific(int choiceNumber){
        int rightArrayLength = rightSide.size();
        if (choiceNumber > rightArrayLength){
            int diff = choiceNumber - rightArrayLength;
            for (int i = 0; i < diff; i++){
                rightSide.add(" ");
            }
        }
        return rightSide.get(choiceNumber);
    }


    @Override
    public void display(){
        super.display();
        double masterArraySizeRowSize = Math.max(getLeftSide().size(), getRightSide().size()); //Since it's the number of rows (2 per row)

        for (int i = 0; i < masterArraySizeRowSize; i++){
            //From https://stackoverflow.com/questions/699878/is-there-an-easy-way-to-output-two-columns-to-the-console-in-java
            System.out.print(+ (i+1) + ") ");
            System.out.printf("%-30.30s  %-30.30s%n", getLeftSideSpecific(i), getRightSideSpecific(i));
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

            if ((numCorr > leftSide.size()) || (numCorr > rightSide.size())){
                this.output.display("Enter a valid number of correct answers.");
                return specificCorrectAnswer;
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        ArrayList<String> changedAnswer = new ArrayList<>();

        for (int i = 0; i < numCorr; i++) {
            this.display();
            System.out.println("Enter a new correct answer in [Number][Number] format: ");

            String newAns = this.input.getInput();

            if (newAns.length() != 2){
                this.output.display("Enter a correctly formatted choice (ex: '12').");
                return specificCorrectAnswer;
            } else {
                changedAnswer.add(newAns);
            }

        }
        specificCorrectAnswer.setResponse(changedAnswer);

        return specificCorrectAnswer;
    }


}
