import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SurveyHelper implements Serializable {
    private static final long serialVersionUID = 1L;
    protected Survey survey;
    protected Test test;
    protected Input input = new Input();
    protected Output output = new Output();

    SurveyHelper() {

    }
    //Helper for all things related to the survey: creating, modifying, taking, saving, etc.
    //Also used for direction communication from the user (adding specific questions, which are called in the respective Question classes)

    public void createSurvey(){
        System.out.println("Enter a new survey name: ");
        String newSurveyName = this.input.getInput();
        this.survey = new Survey(newSurveyName);
    }

    public void createTest(){
        System.out.println("Enter a new test name: ");
        String newTestName = this.input.getInput();
        this.test = new Test(newTestName);
    }

    public void takeSurvey() throws IOException {
        if (this.survey != null){
            this.survey.getSurveyResponses();
            this.saveSurveyAfterTaking();
        } else {
            this.output.display("You must have a survey loaded in order to display it.");
        }
    }

    public void takeTest() throws IOException {
        if (this.test != null){
            this.test.getSurveyResponses();
            this.saveTestAfterTaking();
        } else {
            this.output.display("You must have a test loaded in order to display it.");
        }
    }

    private void saveTestAfterTaking() throws IOException {
        String separator = File.separator;
        String path = "." + separator + "responses";
        String subPath = path + separator + test.getName();
        File directory = new File(path);
        File subdirectory = new File(subPath);

        if (!directory.exists()){
            Files.createDirectory(Paths.get(path));
        }

        if (!subdirectory.exists()){
            Files.createDirectory(Paths.get(subPath));
        }

        //loop thru sub directory and increment

        if (test != null) {
            try {
//https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
                DateTimeFormatter formatPath = DateTimeFormatter.ofPattern("MM-dd-yyyy-HHmmss");
                LocalDateTime dateTime = LocalDateTime.now();

// Inspiration from: https://stackoverflow.com/questions/7488559/current-timestamp-as-filename-in-java
                String newPath = subPath + separator + formatPath.format(dateTime) + ".ser";
                FileOutputStream filo = new FileOutputStream(newPath);
                ObjectOutputStream oos = new ObjectOutputStream(filo);
                oos.writeObject(test); //test stays

                this.output.display("File has been serialized into: " + newPath);

                if (filo != null)
                    filo.close();

                if (oos != null)
                    oos.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            this.output.display("You must have a survey loaded in order to save it.");
        }
    }

    public void saveTest() throws IOException {
        //Taken from the studentrepo (TransientWhenSerializing.java/serialize)
        String separator = File.separator;
        String path = "." + separator + "tests";
        File directory = new File(path);

        if (!directory.exists()){
            Files.createDirectory(Paths.get(path));
        }

        if (test != null) {
            try {
                String newPath = path + separator + test.getName() + ".ser";
                FileOutputStream filo = new FileOutputStream(newPath);
                ObjectOutputStream oos = new ObjectOutputStream(filo);
                oos.writeObject(test);

                this.output.display("File has been serialized into: " + newPath);

                if (filo != null)
                    filo.close();

                if (oos != null)
                    oos.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            this.output.display("You must have a test loaded in order to save it.");
        }
    }

    public void displayTestWithoutCorrectAnswers(){
        if (this.test != null){
            this.test.displayQuestions();
        } else {
            this.output.display("You must have a survey loaded in order to display it.");
        }
    }

    public void displayTestWithCorrectAnswers(){
        if (this.test != null){
            this.test.displayCorrectAnswers();
        } else {
            this.output.display("You must have a survey loaded in order to display it.");
        }
        this.test.getAllCorrectAnswersinTest();//print correct answers for each question?
    }

    public void displaySurvey(){
        if (this.survey != null){
            this.survey.displayQuestions();
        } else {
            this.output.display("You must have a survey loaded in order to display it.");
        }
    }

    public void modifyTest(){
        if (this.test != null){
            this.test.displayQuestions();
        } else {
            this.output.display("You must have a form loaded in order to display it.");
        }

        int whichQuestionToModify = 0;

        if (this.test == null){
            this.output.display("Please load a test first.");
            System.exit(0);
        }
        try {
            this.output.display("Which question do you want to modify?");
            whichQuestionToModify = Integer.parseInt(this.input.getInput());

            if (whichQuestionToModify > test.getAllQuestionsinSurvey().size()){
                this.output.display("Invalid question number.");
                return;
            }

        } catch (NumberFormatException e) {
            this.output.display("Please enter a number and try again.");
            return;
        }
        Question modifiableQuestion = test.listOfQuestionsinSurvey.get(whichQuestionToModify - 1);

        modifiableQuestion.updateQuestion();

        //change correct answer
        this.output.display("Do you wish to modify the correct answers?");
        String responseForChangingCorrectAnswers = this.input.getInput();

        if (responseForChangingCorrectAnswers.equalsIgnoreCase("yes")){
            test.getQuestioninSurvey(whichQuestionToModify-1).updateCorrectAnswer(test.getSpecificCorrectAnswer(whichQuestionToModify-1));
            System.out.println(test.getSpecificCorrectAnswer(whichQuestionToModify-1).getResponse());
        }
        if (responseForChangingCorrectAnswers.equalsIgnoreCase("no")){
            this.output.display("No changes were made.");
            return;
        } if (!responseForChangingCorrectAnswers.equalsIgnoreCase("yes") && !responseForChangingCorrectAnswers.equalsIgnoreCase("no")) {
            this.output.display("Enter a valid 'yes' or 'no'");
            System.exit(0);
        }

    }

    public void modifySurvey(){ //Fix to handle improper input
        this.displaySurvey();
        int whichQuestionToModify = 0;

        if (this.survey == null){
            this.output.display("Please load a survey/test first.");
            System.exit(0);
        }
        try {
            this.output.display("Which question do you want to modify?");
            whichQuestionToModify = Integer.parseInt(this.input.getInput());
        } catch (NumberFormatException e) {
            this.output.display("Please enter a number and try again.");
            return;
        }
        Question modifiableQuestion = survey.listOfQuestionsinSurvey.get(whichQuestionToModify - 1);

        modifiableQuestion.updateQuestion();
    }

    public void saveSurvey() throws IOException { //serialize
        //Taken from the studentrepo (TransientWhenSerializing.java/serialize)
        String separator = File.separator;
        String path = "." + separator + "surveys";
        File directory = new File(path);

        if (!directory.exists()){
            Files.createDirectory(Paths.get(path));
        }

        if (survey != null) {
            try {
                String newPath = path + separator + survey.getName() + ".ser";
                FileOutputStream filo = new FileOutputStream(newPath);
                ObjectOutputStream oos = new ObjectOutputStream(filo);
                oos.writeObject(survey);

                this.output.display("File has been serialized into: " + newPath);

                if (filo != null)
                    filo.close();

                if (oos != null)
                    oos.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            this.output.display("You must have a survey loaded in order to save it.");
        }
    }


    public void saveSurveyAfterTaking() throws IOException {
        String separator = File.separator;
        String path = "." + separator + "responses";
        String subPath = path + separator + survey.getName();
        File directory = new File(path);
        File subdirectory = new File(subPath);

        if (!directory.exists()){
            Files.createDirectory(Paths.get(path));
        }

        if (!subdirectory.exists()){
            Files.createDirectory(Paths.get(subPath));
        }
        if (survey != null) {
            try {
                //https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
                DateTimeFormatter formatPath = DateTimeFormatter.ofPattern("MM-dd-yyyy-HHmmss");
                LocalDateTime dateTime = LocalDateTime.now();

// Inspiration from: https://stackoverflow.com/questions/7488559/current-timestamp-as-filename-in-java
                String newPath = subPath + separator + formatPath.format(dateTime) + ".ser";
                FileOutputStream filo = new FileOutputStream(newPath);
                ObjectOutputStream oos = new ObjectOutputStream(filo);
                oos.writeObject(survey);

                this.output.display("File has been serialized into: " + newPath);

                if (filo != null)
                    filo.close();

                if (oos != null)
                    oos.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            this.output.display("You must have a survey loaded in order to save it.");
        }
    }

    public void loadSurvey(String path){ //deserialize
        //Needs helper to load an existing survey
        //Taken from the studentrepo (TransientWhenSerializing.java/deserialize)
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        String separator = File.separator;

        String newPath = "." + separator + "surveys" + separator + path;

        try {
            fis = new FileInputStream(newPath);
            ois = new ObjectInputStream(fis);
            this.survey = (Survey) ois.readObject(); //the deserialized object

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void loadTest(String path){
        //Needs helper to load an existing test
        //Taken from the studentrepo (TransientWhenSerializing.java/deserialize)
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        String separator = File.separator;

        String newPath = "." + separator + "tests" + separator + path;

        try {
            fis = new FileInputStream(newPath);
            ois = new ObjectInputStream(fis);
            this.test = (Test) ois.readObject(); //the deserialized object

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

   public void loadTestToGrade(String path){
        //Needs helper to load an existing test
        //Taken from the studentrepo (TransientWhenSerializing.java/deserialize)
        FileInputStream fis = null;
        ObjectInputStream ois = null;

       try {
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            this.test = (Test) ois.readObject(); //the deserialized object

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void displayAndLoad() {
        //Helper for getting which file the user wants from the file directory and displaying it
        String separator = File.separator;
        String path = "." + separator + "surveys";
        File files = new File(path);
        File[] listOfFiles = files.listFiles();
        int fileCnt = 0;

        if (listOfFiles == null){
            this.output.display("No surveys in directory to load");
            return;
        }

        //Get all files in directory
        while(fileCnt < listOfFiles.length){
            if (listOfFiles[fileCnt].isFile()) {
                System.out.print((fileCnt+1) + ") ");
                this.output.display(listOfFiles[fileCnt].getName());
            }
            fileCnt++;
        }

        this.output.display("Which file number would you want to display?");
        int fileNumber = Integer.parseInt(this.input.getInput());
        if (fileNumber < 0){
            this.output.display("Enter a valid file number from the directory.");
            this.output.display("Which file number would you want to display?");
            fileNumber = Integer.parseInt(this.input.getInput());
        }

        loadSurvey(listOfFiles[fileNumber - 1].getName());
//        loadSurvey(responsePath);

        this.output.display(listOfFiles[fileNumber - 1].getName() + " has been loaded. " +
                "What would you like to do with " + listOfFiles[fileNumber - 1].getName() + "?");
    }

    public void displayAndLoadTest() {
        //Helper for getting which file the user wants from the file directory and displaying it
        String separator = File.separator;
        String path = "." + separator + "tests";
        File files = new File(path);
        File[] listOfFiles = files.listFiles();
        int fileCnt = 0;

        if (listOfFiles == null){
            this.output.display("No tests in directory to load");
            return;
        }

//        Get all files in directory
        while(fileCnt < listOfFiles.length){
            if (listOfFiles[fileCnt].isFile()) {
                System.out.print((fileCnt+1) + ") ");
                this.output.display(listOfFiles[fileCnt].getName());
            }
            fileCnt++;
        }


        this.output.display("Which file number would you want to display?");
        int fileNumber = Integer.parseInt(this.input.getInput());
        if (fileNumber < 0){
            this.output.display("Enter a valid file number from the directory.");
            this.output.display("Which file number would you want to display?");
            fileNumber = Integer.parseInt(this.input.getInput());
        }

        loadTest(listOfFiles[fileNumber - 1].getName()); //Load specified test

        this.output.display(listOfFiles[fileNumber - 1].getName() + " has been loaded. " +
                "What would you like to do with " + listOfFiles[fileNumber - 1].getName() + "?");
    }

    public void getTrueFalseQuestionFromUserHelper(){
        System.out.println("Enter the prompt for your True/False question: ");
        String input = this.input.getInput();
        survey.addTrueFalseQuestion(input);
    }

    public void getTrueFalseQuestionFromUserHelperTest(){
        System.out.println("Enter the prompt for your True/False question: ");
        String input = this.input.getInput();
        test.addTrueFalseQuestionTest(input);
    }

    public void getEssayQuestionFromUserHelper(){
        System.out.println("Enter the prompt for your Essay question: ");
        String input = this.input.getInput();
        survey.addEssayQuestion(input);
    }

    public void getEssayQuestionFromUserHelperTest(){
        System.out.println("Enter the prompt for your Essay question: ");
        String input = this.input.getInput();
        test.addEssayQuestionTest(input);
    }

    public void getSAQuestionFromUserHelper(){
        System.out.println("Enter the prompt for your Short Answer question: ");
        String input = this.input.getInput();
        survey.addShortAnswerQuestion(input);
    }

    public void getSAQuestionFromUserHelperTest(){
        System.out.println("Enter the prompt for your Short Answer question: ");
        String input = this.input.getInput();
        test.addShortAnswerQuestionTest(input);
    }

    public void getDateQuestionFromUserHelper(){
        System.out.println("Enter the prompt for your date question: ");
        String input = this.input.getInput();
        survey.addValidDateQuestion(input);
    }

    public void getDateQuestionFromUserHelperTest(){
        System.out.println("Enter the prompt for your date question: ");
        String input = this.input.getInput();
        test.addDateQuestionTest(input);
    }

    public void getMCQuestionFromUserHelper(){
        int numberOfChoices = 0;
        this.output.display("Enter the prompt for your multiple-choice question:");
        String input = this.input.getInput();

        this.output.display("Enter the number of choices for your multiple-choice question:");

        try {
            numberOfChoices = Integer.parseInt(this.input.getInput());
        } catch (NumberFormatException e){
            this.output.display("Enter the specific number, not the word.");
        }

        ArrayList<String> choicesArray = new ArrayList<String>();

        for (int i = 0; i < numberOfChoices; i++){
            this.output.display("Add choice # " + (i+1));
            String choice = this.input.getInput();
            choicesArray.add(choice);
        }

        survey.addMultipleChoiceQuestion(input, choicesArray);

    }

    public void getMCQuestionFromUserHelperTest(){
        int numberOfChoices = 0;
        this.output.display("Enter the prompt for your multiple-choice question:");
        String input = this.input.getInput();

        this.output.display("Enter the number of choices for your multiple-choice question:");

        try {
            numberOfChoices = Integer.parseInt(this.input.getInput());
        } catch (NumberFormatException e){
            this.output.display("Enter the specific number, not the word.");
        }

        ArrayList<String> choicesArray = new ArrayList<String>();

        for (int i = 0; i < numberOfChoices; i++){
            this.output.display("Add choice # " + (i+1));
            String choice = this.input.getInput();
            choicesArray.add(choice);
        }

        test.addMultipleChoiceQuestionTest(input, choicesArray);

    }

    public void getMatchingQuestionFromUserHelper(){ //breaks
        System.out.println("Enter the prompt for your matching question: ");
        String input = this.input.getInput();
        ArrayList<String> leftArray = new ArrayList<String>();
        ArrayList<String> rightArray = new ArrayList<String>();

        this.output.display("How many choices do you want for the left side? Enter a number.");
        String leftInput = this.input.getInput();
        int leftInputNumber = Integer.parseInt(leftInput);

        //add left choices
        for (int i = 0; i < leftInputNumber; i++){
            this.output.display("Add the " + (i+1) +" element on the left side");
            String leftElement = this.input.getInput();
            leftArray.add(leftElement);
        }
        Collections.shuffle(leftArray);

        this.output.display("How many choices do you want for the right side? Enter a number.");
        String rightInput = this.input.getInput();
        int rightInputNumber = Integer.parseInt(rightInput);

        //add right choices
        for (int i = 0; i < rightInputNumber; i++){
            this.output.display("Add the " + (i+1) +" element on the right side");
            String rightElement = this.input.getInput();
            rightArray.add(rightElement);
        }
        Collections.shuffle(rightArray);

        //If there is an imbalance amongst the two arrays:
        if (leftArray.size() < rightArray.size()){
            int rightArraySize = rightArray.size();
            int leftArraySize = leftArray.size();
            int difference = rightArraySize - leftArraySize;

            for (int i = 0; i < difference; i++){
                leftArray.add(" ");
            }
        }

        if (rightArray.size() < leftArray.size()){
            int rightArraySize = rightArray.size();
            int leftArraySize = leftArray.size();
            int difference = leftArraySize - rightArraySize;

            for (int i = 0; i < difference; i++){
                rightArray.add(" ");
            }
        }
        survey.addMatchingQuestion(input, leftArray, rightArray);
    }

    public void getMatchingQuestionFromUserHelperTest(){
        System.out.println("Enter the prompt for your matching question: ");
        String input = this.input.getInput();
        ArrayList<String> leftArray = new ArrayList<String>();
        ArrayList<String> rightArray = new ArrayList<String>();

        this.output.display("How many choices do you want for the left side? Enter a number.");
        String leftInput = this.input.getInput();
        int leftInputNumber = Integer.parseInt(leftInput);

        //add left choices
        for (int i = 0; i < leftInputNumber; i++){
            this.output.display("Add the " + (i+1) +" element on the left side");
            String leftElement = this.input.getInput();
            leftArray.add(leftElement);
        }
        Collections.shuffle(leftArray);

        this.output.display("How many choices do you want for the right side? Enter a number.");
        String rightInput = this.input.getInput();
        int rightInputNumber = Integer.parseInt(rightInput);

        //add right choices
        for (int i = 0; i < rightInputNumber; i++){
            this.output.display("Add the " + (i+1) +" element on the right side");
            String rightElement = this.input.getInput();
            rightArray.add(rightElement);
        }
        Collections.shuffle(rightArray);

        //If there is an imbalance amongst the two arrays:
        if (leftArray.size() < rightArray.size()){
            int rightArraySize = rightArray.size();
            int leftArraySize = leftArray.size();
            int difference = rightArraySize - leftArraySize;

            for (int i = 0; i < difference; i++){
                leftArray.add(" ");
            }
        }

        if (rightArray.size() < leftArray.size()){
            int rightArraySize = rightArray.size();
            int leftArraySize = leftArray.size();
            int difference = leftArraySize - rightArraySize;

            for (int i = 0; i < difference; i++){
                rightArray.add(" ");
            }
        }

        test.addMatchingQuestionTest(input, leftArray, rightArray);
    }

    public void tabulate(){

        String separator = File.separator;
        String responsePath = "." + separator + "responses";
        File files = new File(responsePath);
        File[] listOfFiles = files.listFiles();
        int fileCnt = 0;

        if (listOfFiles == null){
            this.output.display("No surveys in directory to load");
            return;
        }

        //Get all files in directory
        while(fileCnt < listOfFiles.length){
            if (listOfFiles[fileCnt].isDirectory()) {
                System.out.print((fileCnt+1) + ") ");
                this.output.display(listOfFiles[fileCnt].getName());
            }
            fileCnt++;
        }

        this.output.display("Which file number would you want to tabulate?");
        int fileNumber = Integer.parseInt(this.input.getInput());
        if ((fileNumber < 0) || (fileNumber > listOfFiles.length) ){
            this.output.display("Enter a valid file number from the directory.");
            this.output.display("Which file number would you want to display?");
            fileNumber = Integer.parseInt(this.input.getInput());
        }

        File[] listOfSurveys = listOfFiles[fileNumber-1].listFiles();

        ArrayList<Survey> directory = new ArrayList<>();

        for (File deserializedFile : listOfSurveys) {
            try {
                FileInputStream fis = new FileInputStream(deserializedFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Survey form = (Survey) ois.readObject(); //the deserialized object

                if (form.getAllQuestionsinSurvey() != null) {
                    directory.add(form);
                } else {
                    this.output.display("This survey is empty.");
                    return;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        List<Question> listOfQuestions = directory.get(0).getAllQuestionsinSurvey();

        int questionCnt = 0;
        for (Question question : listOfQuestions){
            this.output.display("");
            this.output.display("QUESTION: ");
            System.out.print("Q: ");
            question.display();
            this.output.display("");

            ArrayList<Integer> numResponses = new ArrayList<>();
            ArrayList<ArrayList<String>> responsesToTest = new ArrayList<>();
            ArrayList<String> responseList;

            this.output.display("RESPONSES: ");
            for (Survey survey : directory) {
                responseList = survey.getQuestioninSurvey(questionCnt).getResponse().getResponse();

                System.out.print("Q" + (questionCnt+1) + ") ");
                this.output.display(responseList.toString());
                this.output.display("The number of distinct answers given: " + responseList.size());

                if (responsesToTest.contains(responseList)) {
                    int indexOfResponse = responsesToTest.indexOf(responseList);
                    numResponses.set(indexOfResponse, numResponses.get(indexOfResponse) + 1); //Increase count of same answers
                }
                else {
                    numResponses.add(1); //increment number of responses
                    responsesToTest.add(responseList);
                }
            }
            questionCnt+=1;
            this.output.display("");
            this.output.display("TABULATION: ");

            for (int i = 0; i < numResponses.size(); i++) {
                this.output.display("*****");
                this.output.display("Attempt #"+ (i+1) + ": ");
                System.out.print(numResponses.get(i) + " ");
                System.out.println(responsesToTest.get(i));
            }
            this.output.display("***");

        }
    }

    public void gradeTestHelper() {
        String separator = File.separator;
        String responsePath = "." + separator + "responses";
        File files = new File(responsePath);
        File[] listOfFiles = files.listFiles();
        int fileCnt = 0;
        int fileCnt2 = 0;

        if (listOfFiles == null){
            this.output.display("No tests in directory to load");
            return;
        }

        //Get all files in directory
        while(fileCnt < listOfFiles.length){
            if (listOfFiles[fileCnt].isDirectory()) {
                System.out.print((fileCnt+1) + ") ");
                this.output.display(listOfFiles[fileCnt].getName());
            }
            fileCnt++;
        }

        this.output.display("Which test would you like to grade?");
        int fileNumber = Integer.parseInt(this.input.getInput())-1;
        if ((fileNumber < 0) || (fileNumber > listOfFiles.length) ){
            this.output.display("Enter a valid file number from the directory.");
            this.output.display("Which file number would you want to display?");
            fileNumber = Integer.parseInt(this.input.getInput());
        }

        File[] listOfTests = listOfFiles[fileNumber].listFiles();

        if (listOfTests == null){
            this.output.display("No tests in directory to load");
            return;
        }

        //List subdirectory
        while(fileCnt2 < listOfTests.length){
            if (listOfTests[fileCnt2].isFile()) {
                System.out.print((fileCnt2+1) + ") ");
                this.output.display(listOfTests[fileCnt2].getName());
            }
            fileCnt2++;
        }

        this.output.display("Which attempt would you like to grade?");
        int whichTest = Integer.parseInt(this.input.getInput()) - 1;
        if ((whichTest < 0) || (whichTest > listOfFiles.length) ){
            this.output.display("Enter a valid file number from the directory.");
            this.output.display("Which file number would you want to display?");
            whichTest = Integer.parseInt(this.input.getInput());
        }

        loadTestToGrade(listOfTests[whichTest].getPath());
        this.test.grade();

    }
}


