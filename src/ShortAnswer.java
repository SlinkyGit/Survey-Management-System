import java.io.Serializable;

public class ShortAnswer extends Essay implements Serializable {
    private static final long serialVersionUID = 1L;

    public ShortAnswer(String question, String questionType) {
        super(question, questionType);
    }

    @Override
    public void updateQuestion() {
        super.updateQuestion();
    }

    @Override
    public ResponseCorrectAnswer updateCorrectAnswer(ResponseCorrectAnswer specificCorrectAnswer) {

        if (specificCorrectAnswer.getResponse().size() < 50){
            this.output.display("Please keep the response to under 50 characters.");
            System.exit(0);
        }

        this.output.display("No correct answer needed for Short Answer questions.");
        return specificCorrectAnswer;
    }
}
