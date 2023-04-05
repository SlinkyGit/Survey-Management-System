import java.io.Serializable;
import java.util.ArrayList;

public abstract class Question implements Serializable {
    protected String questionType, questionPrompt;
    protected ResponseCorrectAnswer userResponse;
    private static final long serialVersionUID = 1L;
    ArrayList<String> listOfQuestions = new ArrayList<String>();
    protected Output output = new Output();

    public Question(String question, String questionType) {
        this.setQuestion(question);
        this.questionType = questionType;
        this.userResponse = new ResponseCorrectAnswer(new ArrayList<String>());
    }

    public void setQuestion(String question){
        this.questionPrompt = question;
    }

    public String getQuestion(int questionNumber){
        return listOfQuestions.get(questionNumber);
    }

    public String getPrompt(){
        return questionPrompt;
    }

    public void setQuestionType(String question){
        this.questionType = questionType;
    }

    public String getQuestionType(String question){
        return questionType;
    }

    public void setResponse(ResponseCorrectAnswer userResponse) {
        this.userResponse = userResponse;
    }

    public ResponseCorrectAnswer getResponse(){
        return userResponse;
    }

    public abstract void updateQuestion();

    public abstract void getUserResponse();

    public String getQuestionType(int questionNumber){
        return questionType;
    }

    public void display(){
        output.display(getPrompt());
    }

    public abstract ResponseCorrectAnswer updateCorrectAnswer(ResponseCorrectAnswer specificCorrectAnswer);
}
