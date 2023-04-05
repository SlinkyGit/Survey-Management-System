import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Survey implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String name;
    Input inputFromUser = new Input();
    Output output = new Output();

    List<Question> listOfQuestionsinSurvey = new ArrayList<Question>();

    public Survey(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public List getAllQuestionsinSurvey(){
        return listOfQuestionsinSurvey;
    }

    public Question getQuestioninSurvey(int surveyQuestionNumber){
        return listOfQuestionsinSurvey.get(surveyQuestionNumber);
    }

    public void displayQuestions(){
        int questionNumber = 1;
        for (Question question : listOfQuestionsinSurvey){
            System.out.print(questionNumber + ") ");
            question.display();
            questionNumber+=1;
            System.out.println();
        }

    }

    public Input getInputFromUser(){
        return inputFromUser;
    }

    public void addTrueFalseQuestion(String input){
        String questionType = "TrueFalse";
        this.listOfQuestionsinSurvey.add(new TrueFalse(input, questionType));
    }

    public void addMultipleChoiceQuestion(String input, ArrayList<String> questionChoices){
        String questionType = "MultipleChoice";
        this.listOfQuestionsinSurvey.add(new MultipleChoice(input, questionChoices, questionType));
    }

    public void addShortAnswerQuestion(String input){
        String questionType = "ShortAnswer";
        this.listOfQuestionsinSurvey.add(new ShortAnswer(input, questionType));
    }

    public void addEssayQuestion(String input){
        String questionType = "Essay";
        this.listOfQuestionsinSurvey.add(new Essay(input, questionType));
    }

    public void addValidDateQuestion(String input){
        String questionType = "ValidDate";
        this.listOfQuestionsinSurvey.add(new ValidDate(input, questionType));
    }

    public void addMatchingQuestion(String input, ArrayList<String> leftSide, ArrayList<String> rightSide){
        String questionType = "Matching";
        this.listOfQuestionsinSurvey.add(new Matching(input, leftSide, rightSide, questionType));
    }

    public void getSurveyResponses() {
        int questionNum = 1;
        for (Question question : listOfQuestionsinSurvey){
            System.out.print(questionNum + ") ");
            question.display();
            question.getUserResponse();
            questionNum++;
        }
    }
}
