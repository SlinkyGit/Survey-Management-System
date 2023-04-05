import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test extends Survey implements Serializable {
    private static final long serialVersionUID = 1L;

    Input inputFromUser = new Input();
    Output output = new Output();
    List<ResponseCorrectAnswer> listOfCorrectAnswersinTest = new ArrayList<ResponseCorrectAnswer>();
    SimpleDateFormat dateInput = new SimpleDateFormat("MM-dd-yyyy");


    public Test(String name) {
        super(name);
    }

    public String getName(){
        return name;
    }

    public List getAllCorrectAnswersinTest(){
        return listOfCorrectAnswersinTest;
    }

    public ResponseCorrectAnswer getSpecificCorrectAnswer(int specificQuestion) {
        return listOfCorrectAnswersinTest.get(specificQuestion);
    }

    public void displayCorrectAnswers(){
        int questionNumber = 1;

        for (Question question : listOfQuestionsinSurvey){
            System.out.print(questionNumber + ") ");
            question.display();

            this.output.display("Correct Answer(s): ");
            listOfCorrectAnswersinTest.get(questionNumber-1).display();
            questionNumber+=1;
            System.out.println();
        }

    }


    public void addTrueFalseQuestionTest(String input){
        super.addTrueFalseQuestion(input);

        //Ask for the correct answer
        this.output.display("Please enter the correct answer (True/False):");
        String corrAnswer = this.inputFromUser.getInput();
        if (corrAnswer.equalsIgnoreCase("true")){
            ArrayList<String> ansArr = new ArrayList<>();
            ansArr.add("True");
            listOfCorrectAnswersinTest.add(new ResponseCorrectAnswer(ansArr));

        }
        if (corrAnswer.equalsIgnoreCase("false")){
            ArrayList<String> ansArr = new ArrayList<>();
            ansArr.add("False");
            listOfCorrectAnswersinTest.add(new ResponseCorrectAnswer(ansArr));
        }

        if (!corrAnswer.equalsIgnoreCase("true") && !corrAnswer.equalsIgnoreCase("false")) {
            this.output.display("Please enter a valid choice.");
            return;
        }

    }

    public void addEssayQuestionTest(String input) {
        super.addEssayQuestion(input);
        listOfCorrectAnswersinTest.add(new ResponseCorrectAnswer(new ArrayList<>()));
    }

    public void addShortAnswerQuestionTest(String input) {
        super.addShortAnswerQuestion(input);
        listOfCorrectAnswersinTest.add(new ResponseCorrectAnswer(new ArrayList<>()));
    }

    public void addMultipleChoiceQuestionTest(String input, ArrayList<String> questionChoices){
        super.addMultipleChoiceQuestion(input, questionChoices);
        int numberOfCorrect = 0;
        this.output.display("Enter the number of correct answers: ");

        try {
            numberOfCorrect = Integer.parseInt(this.inputFromUser.getInput());

            if (numberOfCorrect > questionChoices.size()){
                this.output.display("Invalid number of correct choices.");
                return;
            }

        } catch (NumberFormatException e){
            this.output.display("Enter the specific number.");
        }

        ArrayList<String> correctAnswer = new ArrayList<>();

        //ask for number of correct answers
        for (int i = 0; i < numberOfCorrect; i++){
//            this.getQuestioninSurvey().display(); //error bc trying to get i+1 question when it doesnt exist
            super.getQuestioninSurvey(i).display();
            this.output.display("Enter the correct answer for Q#" + (i+1));
            String corrAns = this.inputFromUser.getInput();

            correctAnswer.add(corrAns);
        }
        listOfCorrectAnswersinTest.add(new ResponseCorrectAnswer(correctAnswer));

    }

    public void addDateQuestionTest(String input){
        super.addValidDateQuestion(input);
        ArrayList<String> correctAnswer = new ArrayList<>();

        this.output.display("A date should be entered in the following format: 'MM-DD-YYYY'");
        this.output.display("Enter the correct date answer:");
        String correctDate = this.inputFromUser.getInput();

        try {
            Date date = dateInput.parse(correctDate);
            this.output.display(new SimpleDateFormat("MM-dd-yyyy").format(date));
            correctAnswer.add(correctDate);
            listOfCorrectAnswersinTest.add(new ResponseCorrectAnswer(correctAnswer));
        }
        catch (ParseException e)
        {
            this.output.display("Please enter a valid correct date in the correct format");
            return;
        }


    }

    public void addMatchingQuestionTest(String input, ArrayList<String> leftSide, ArrayList<String> rightSide){
       super.addMatchingQuestion(input, leftSide, rightSide);
       ArrayList<String> correctAnswer = new ArrayList<>();
       int numberOfCorrect = 0;
       int numberOfQuestions = this.getAllQuestionsinSurvey().size();

        this.output.display("Enter the number of correct answers: ");

        try {
            numberOfCorrect = Integer.parseInt(this.inputFromUser.getInput());

            if (numberOfCorrect > leftSide.size()){
                this.output.display("Invalid number of correct choices.");
                return;
            }

            if (numberOfCorrect > numberOfQuestions){
                this.output.display("Only 1 correct response per question.");
                return;
            }

        } catch (NumberFormatException e){
            this.output.display("Enter the specific number.");
        }

       for (int i = 0; i < numberOfCorrect; i++){

           super.getQuestioninSurvey(i).display();


           System.out.print("Enter the correct answer(s) for Q#" + (i+1));
           getSpecificCorrectAnswer(i).display();
           this.output.display(" (Enter in [Number] [Number] format, with the number representing both left/right, i.e. '11')");
           String correctMatching = this.inputFromUser.getInput();
           int correctMatchingNumber = Integer.parseInt(correctMatching);

           if (correctMatching.length() != 2){
               this.output.display("Enter in the [Number] [Number] format.");
               this.output.display("For example, if the first row is '1', type: '11'");
               return;
           }
           correctAnswer.add(correctMatching);
       }

        listOfCorrectAnswersinTest.add(new ResponseCorrectAnswer(correctAnswer));
    }


    public double grade() {
        int totalScore = 100;
        double pointsPerQuestion = totalScore / getAllQuestionsinSurvey().size();
        double score = 0;
        int numCorrect = 0;
        int dontGrade = 0;
        int questionCnt = 0;

        if (listOfCorrectAnswersinTest.isEmpty()){
            this.output.display("This test has no answers.");
            return score;
        }

        for (Question question : listOfQuestionsinSurvey){
        // 'instanceof' https://stackoverflow.com/questions/4294844/check-if-an-object-belongs-to-a-class-in-java

            if (question instanceof Essay){
                dontGrade+=1;

            } else if (question instanceof ShortAnswer) {
                dontGrade+=1;
            }

            else {
                ResponseCorrectAnswer responseCorrectAnswer = question.getResponse();

                if (responseCorrectAnswer.compare(listOfCorrectAnswersinTest.get(questionCnt))){
                    score += pointsPerQuestion;
                    numCorrect+=1;
                }
            }
            questionCnt++;
        }


        if (dontGrade > 0){
            this.output.display("You received an " + score + " on the test.");
            this.output.display("The test was worth 100 points, but only " + numCorrect +
                    " of those points could be auto-graded because there were " + dontGrade + " essay/short-answer question(s).");
        } else {
            score = Math.round(score); //?
            this.output.display("You received an " + score + " on the test.");
            this.output.display("The test was worth 100 points, and you scored: " + numCorrect);
        }

        return score;
    }

}


