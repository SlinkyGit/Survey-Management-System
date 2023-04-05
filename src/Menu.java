import java.io.IOException;
//Main Driver
public class Menu {
    static SurveyHelper surveyHelper = new SurveyHelper();
    static Input input = new Input();
    static Output output = new Output();
    private static int currentMenu = 1;
    private static int currentTestMenu = 1;


//Allows for the user to choose which menu they would like to see
    public static void displayMenu(int currentMenu) {
        switch (currentMenu) {
            case 1: //Menu 1
                menu1();
                break;
            case 2: //Menu 2
                menu2();
                break;
        }
    }

    public static void displayTestMenu(int currentTestMenu) {
        switch (currentTestMenu) {
            case 1: //Menu 1
                menu1Test();
                break;
            case 2: //Menu 2
                menu2();
                break;
        }
    }

//Allows for the user to choose a specific choice within the respective menu
    public static void goThruSurveyMenu(int menuNumber, int choiceNumber) throws IOException {
            switch (menuNumber) {
                // Menu 1
                case 1:
                    switch (choiceNumber) {
                        case 1:
                            surveyHelper.createSurvey();
                            currentMenu = 2;
                            break;
                        case 2:
                            surveyHelper.displaySurvey();
                            break;
                        case 3:
                            surveyHelper.displayAndLoad();
                            break;
                        case 4:
                            surveyHelper.saveSurvey();
                            break;
                        case 5:
                            surveyHelper.takeSurvey();
                            break;
                        case 6:
                            surveyHelper.modifySurvey();
                            break;

                        case 7:
                            surveyHelper.tabulate();
                            break;

                        case 8:
                            output.display("Goodbye!");
                            System.exit(0);
                            break;
                    }
                    break;
                // Menu 2
                case 2:
                    switch (choiceNumber){
                        case 1:
                            surveyHelper.getTrueFalseQuestionFromUserHelper();
                            break;
                        case 2:
                            surveyHelper.getMCQuestionFromUserHelper();
                            break;
                        case 3:
                            surveyHelper.getSAQuestionFromUserHelper();
                            break;
                        case 4:
                            surveyHelper.getEssayQuestionFromUserHelper();
                            break;
                        case 5:
                            surveyHelper.getDateQuestionFromUserHelper();
                            break;
                        case 6:
                            surveyHelper.getMatchingQuestionFromUserHelper();
                            break;
                        case 7:
                            currentMenu = 1;
                            output.display("Taking you back to the previous menu...");
                            break;
                    }
                    break;
            }
    }

    public static void goThruTestMenu(int menuNumberTest, int choiceNumberTest) throws IOException {
        switch (menuNumberTest) {
            // Menu 1
            case 1:
                switch (choiceNumberTest) {
                    case 1:
                        surveyHelper.createTest();
                        currentTestMenu = 2;
                        break;
                    case 2:
                        surveyHelper.displayTestWithoutCorrectAnswers();
                        break;
                    case 3:
                        surveyHelper.displayTestWithCorrectAnswers();
                        break;
                    case 4:
                        surveyHelper.displayAndLoadTest();
                        break;
                    case 5:
                        surveyHelper.saveTest();
                        break;
                    case 6:
                        surveyHelper.takeTest();
                        break;
                    case 7:
                        surveyHelper.modifyTest();
                        break;
                    case 8:
                        surveyHelper.tabulate();
                        break;
                    case 9:
                        surveyHelper.gradeTestHelper();
                        break;
                    case 10:
                        output.display("Goodbye!");
                        System.exit(0);
                        break;
                }
                break;
            // Menu 2
            case 2:
                switch (choiceNumberTest){
                    case 1:
                        surveyHelper.getTrueFalseQuestionFromUserHelperTest();
                        break;
                    case 2:
                        surveyHelper.getMCQuestionFromUserHelperTest();
                        break;
                    case 3:
                        surveyHelper.getSAQuestionFromUserHelperTest();
                        break;
                    case 4:
                        surveyHelper.getEssayQuestionFromUserHelperTest();
                        break;
                    case 5:
                        surveyHelper.getDateQuestionFromUserHelperTest();
                        break;
                    case 6:
                        surveyHelper.getMatchingQuestionFromUserHelperTest();
                        break;
                    case 7:
                        currentTestMenu = 1;
                        output.display("Taking you back to the previous menu...");
                        break;
                }
                break;
        }
    }

    public static void menu1(){
        System.out.println("1) Create a new Survey");
        System.out.println("2) Display an existing Survey");
        System.out.println("3) Load an existing Survey");
        System.out.println("4) Save the current Survey");
        System.out.println("5) Take the current Survey");
        System.out.println("6) Modifying the current Survey");
        System.out.println("7) Tabulate");
        System.out.println("8) Quit");
    }

    public static void menu2(){
        System.out.println("1) Add a new T/F question");
        System.out.println("2) Add a new multiple-choice question");
        System.out.println("3) Add a new short answer question");
        System.out.println("4) Add a new essay question");
        System.out.println("5) Add a new date question");
        System.out.println("6) Add a new matching question");
        System.out.println("7) Return to previous menu");
    }

    public static void menu1Test(){
        System.out.println("1) Create a new Test");
        System.out.println("2) Display an existing Test without correct answers");
        System.out.println("3) Display an existing Test with correct answers");
        System.out.println("4) Load an existing Test");
        System.out.println("5) Save the current Test");
        System.out.println("6) Take the current Test");
        System.out.println("7) Modify the current Test");
        System.out.println("8) Tabulate");
        System.out.println("9) Grade a Test");
        System.out.println("10) Exit");
    }

    public static void menu3(){
        System.out.println("1) Add a new T/F question");
        System.out.println("2) Add a new multiple-choice question");
        System.out.println("3) Add a new short answer question");
        System.out.println("4) Add a new essay question");
        System.out.println("5) Add a new date question");
        System.out.println("6) Add a new matching question");
        System.out.println("7) Return to previous menu");
    }

    public static void main(String[] args) throws IOException {
        Boolean runFlag = true;
        int userChoice = 0;
        int userChoiceTest = 0;
        output.display("**************************");
        output.display("Welcome to Form Manager");
        output.display("**************************");

        //if statement to run the right menu depending on menu or test
        output.display("Would you enter the Survey menu or Test menu?");
        String whichMenu = input.getInput();

        if (whichMenu.equalsIgnoreCase("survey")){
            //run survey menu

            while(true){
                displayMenu(currentMenu);
                output.display("Choose a number:");
                try {
                    userChoice = Integer.parseInt(input.getInput());
                    if ((userChoice <= 0) || (userChoice > 8)){
                        output.display("Please enter a valid number from 1-8.");
                    }
                } catch (NumberFormatException e) {
                    output.display("Please enter an integer.");
                    userChoice = 0;
//                    e.printStackTrace();
                }
                goThruSurveyMenu(currentMenu, userChoice);
            }

        }
        if (whichMenu.equalsIgnoreCase("test")) {
            //run test menu
            while(true) {
                displayTestMenu(currentTestMenu);
                output.display("Choose a number:");
                String inputFromUser = input.getInput();
                try {
                    userChoiceTest = Integer.parseInt(inputFromUser);
                    if ((userChoiceTest <= 0) || (userChoiceTest > 10)) {
                        output.display("Please enter a valid number from 1-10.");
                    }
                } catch (NumberFormatException e) {
                    output.display("Please enter an integer.");
                    userChoiceTest = 0;
//                    e.printStackTrace();
                }
                goThruTestMenu(currentTestMenu, userChoiceTest);
            }
        }
        else {
            output.display("Enter a valid menu.");
            return;
        }

    }
}