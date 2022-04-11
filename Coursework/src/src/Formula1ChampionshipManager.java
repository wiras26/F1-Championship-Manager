import java.text.ParseException;
import java.time.*;
import java.time.format.*;
import java.time.chrono.IsoChronology;
import java.util.*;
import java.io.*;;
import com.bethecoder.ascii_table.ASCIITable;

interface ChampionshipManager {
    void createDriver() ;
    void deleteDriver();
    void viewDrivers();
    void changeTeam();
    void displayStats();
    void driverTable();
    void addRace() ;
    void saveToFile();
    void loadFile();
}

class PointComparator implements Comparator<Formula1Driver>{ //Comparator used to sort the current points in Descending order
    //https://www.geeksforgeeks.org/how-to-sort-arraylist-using-comparator

    @Override
    public int compare(Formula1Driver D1, Formula1Driver D2) {
        if (D1.getCurrentPoints() == D2.getCurrentPoints() && (D1.getFirstPtn()<D2.getFirstPtn()))
        return 0;
        else if (D1.getCurrentPoints() < D2.getCurrentPoints())
            return 1;
        else
            return -1;
    }
}

class PointComparatorAscendingCurrentPoints implements Comparator<Formula1Driver>{ //Comparator used to sort the current points in Ascending order

    @Override
    public int compare(Formula1Driver D1, Formula1Driver D2) {
        if (D1.getCurrentPoints() == D2.getCurrentPoints() && D1.getFirstPtn() > D2.getFirstPtn()) {
            return 0;
        } else if (D1.getCurrentPoints() > D2.getCurrentPoints()) {
            return 1;
        } else {
            return -1;
        }
    }
}

class PointComparatorDescendingFirstPosition implements Comparator<Formula1Driver>{ //Comparator used to sort the First Positions in Descending order

    @Override
    public int compare(Formula1Driver D1, Formula1Driver D2) {

         if (D1.getFirstPtn() < D2.getFirstPtn()){
            return 1;
        }else{
            return -1;
        }

    }
}

class PointComparatorAscendingRaceDate implements Comparator<Formula1Driver> {  //Comparator used to sort the Race Dates in Ascending order
    //https://stackoverflow.com/questions/32625407/sorting-localdatetime/32625500
    public int compare(Formula1Driver Driver1,Formula1Driver Driver2) {
        return Driver1.getRaceDate().compareTo(Driver2.getRaceDate());  }
}

class Formula1ChampionshipManager implements ChampionshipManager {

    static Scanner input = new Scanner(System.in);

    //Array List driverList declared as type Formula1Driver
    static ArrayList<Formula1Driver> driverList = new ArrayList<>();

    Formula1Driver F1DriverLink = new Formula1Driver();

    public static void main(String[] args) throws ParseException {
        boolean end = false;
        Formula1ChampionshipManager newManager = new Formula1ChampionshipManager();


        //Prompting the user if they want to load a previous saved file
        System.out.println(" ");
        System.out.println("Do You Want Resume the Program from Previous State? \nIf Yes --> Y else Click Any Key To Continue.....");
        String uInput = input.next();
        if (uInput.equalsIgnoreCase("Y")){
            //Calls the method to read the file
            newManager.loadFile();
        }

        while (!end) {
            System.out.println("************ F1 CHAMPIONSHIP MANAGER ************");
            //Displays all the options
            System.out.println("""
                    Option A : To Create & Add New Driver\s
                    Option B : Remove Driver\s
                    Option C : Change Driver Team\s
                    Option D : Display Driver Statistics\s
                    Option E : Display FORMULA 1 Driver Table\s
                    Option F : Add New Race Details\s
                    Option G : Save Data To A File\s
                    Option H : Open F1 GUI\s
                    Option I : Insert Test Case\s
                    Option Z : View Drivers\s
                    Option X : EXIT PROGRAM\s""");

            System.out.println("Enter your Option : ");
            //User enters a letter not case-sensitive
            String choice = input.next();
            choice = choice.toUpperCase();

            switch (choice) {

                //Calls the relevant method according to the option selected by user
                case "A" -> newManager.createDriver();
                case "B" -> newManager.deleteDriver();
                case "C" -> newManager.changeTeam();
                case "D" -> newManager.displayStats();
                case "E" -> newManager.driverTable();
                case "F" -> newManager.addRace();
                case "G" -> newManager.saveToFile();
                case "H" -> new FormulaManager_GUI();
                case "I" -> testCase();
                case "Z" -> newManager.viewDrivers();
                case "X" -> end = true;
            }
        }

    }

    @Override
    //Creates a new driver and adds to the Array List driverList
    public void createDriver() {

        int firstPtn = 0;
        int secondPtn = 0;
        int thirdPtn = 0;
        int points = 0;
        int position;
        boolean checkName = true;
        boolean checkTeam = true;


            System.out.println("-------Driver Details--------");

            System.out.println("Enter Driver's Name: ");
            input.nextLine();
            String driverName = input.nextLine();
            //Calls the method to validate Driver Name
            while (checkName) {
                checkName = CheckName(driverName);
                if (checkName) {
                    //If validation is false user is asked to re-enter Name
                    System.out.println("Re-Enter Driver's Name: ");
                    driverName = input.nextLine();
                }
            }

            //Sets driver name
            F1DriverLink.setDriverName(driverName);

            System.out.println("Enter Driver's Location: ");
            String driverLocation = input.nextLine();

            //Sets driver location
            F1DriverLink.setDriverLocation(driverLocation);

            System.out.println("Enter Driver's Team: ");
            String driverTeam = input.nextLine();
            //Calls the method to validate Driver Team
            while (checkTeam) {
                checkTeam = CheckTeam(driverTeam);
                if (checkTeam) {
                    //If validation is false user is asked to re-enter Team
                    System.out.println("Re-Enter Driver's Team: ");
                    driverTeam = input.nextLine();
                }
            }

            //Sets Driver Team
            F1DriverLink.setDriverTeam(driverTeam);

            System.out.println("-------Driver Statistics----------");

            //User enters the current points' driver already has
            System.out.println("Enter The Number of Current Points the Driver has : ");
            int currentPoints = input.nextInt();

            //Sets Current Points
            F1DriverLink.setCurrentPoints(currentPoints);

            //User enters the total number of races driver has raced
            System.out.println("Enter Number Of Races Driver has Raced: ");
            int numRaces = input.nextInt();

            //Sets Number Of Races
            F1DriverLink.setNumRaces(numRaces);

            for (int i = 1; i < numRaces+1; i++) {
                boolean checkPosition = true;
                //User enters driver's position in each race
                System.out.println("Enter Driver's Position in Race "+i+" :");
                position = input.nextInt();
                //Validates user position
                while (checkPosition) {
                    checkPosition = CheckPosition(position);
                    if (checkPosition) {
                        //If invalid position entered user is asked to re-enter
                        System.out.println("Re-Enter Driver's Position : ");
                        position = input.nextInt();
                        checkPosition = CheckPosition(position);
                    }
                }
                //According to the position entered matching points are assigned and added to the driver's current points
                if (position >=1 && position <= 10) {
                    if (position == 1) {

                        points = points + 25;
                        firstPtn = firstPtn + 1;

                    } else if (position == 2) {

                        points = points + 18;
                        secondPtn++;

                    } else if (position == 3) {

                        points = points + 15;
                        thirdPtn++;

                    } else if (position == 4) {

                        points = points + 12;

                    } else if (position == 5) {

                        points = points + 10;

                    } else if (position == 6) {

                        points = points + 8;

                    } else if (position == 7) {

                        points =  points + 6;

                    } else if (position == 8) {

                        points = points + 4;

                    } else if (position == 9) {

                        points = points + 2;

                    } else if (position == 10) {

                        points = points + 1;
                    }
                }else{
                    System.out.println("Invalid Position Entered!");
                }
            }
            currentPoints = currentPoints + points;

            //Sets Driver's Current Points, Points, Position, First, Second and Third Positions Count
            F1DriverLink.setCurrentPoints(currentPoints);
            F1DriverLink.setFirstPtn(firstPtn);
            F1DriverLink.setSecondPtn(secondPtn);
            F1DriverLink.setThirdPtn(thirdPtn);

            //Assigns the data to the constructor F1Driver
            Formula1Driver F1Driver = new Formula1Driver(driverName, driverLocation, driverTeam, numRaces,firstPtn, secondPtn, thirdPtn,currentPoints);

            driverList.add(F1Driver);
            //Displays updated information
        System.out.println(" ");
        System.out.println("............... Driver Details Summary..................");
        System.out.println("Driver Name: "+ F1DriverLink.getDriverName());
        System.out.println("Driver Location: "+ F1DriverLink.getDriverLocation());
        System.out.println("Driver Team: "+ F1DriverLink.getDriverTeam());
        System.out.println("Number Of Races Drive Took Part In: "+ F1DriverLink.getNumRaces());
        System.out.println("Current Points Driver Has: "+ F1DriverLink.getCurrentPoints());
        System.out.println("Number Of First Positions: "+ F1DriverLink.getFirstPtn());
        System.out.println("Number Of Second Positions: "+ F1DriverLink.getSecondPtn());
        System.out.println("Number Of Third Positions: "+ F1DriverLink.getThirdPtn());
        System.out.println(" ");

    }

    @Override
    //Deleting Current Driver From List By Index Number
    public void deleteDriver() {
        for (int i=0; i < driverList.size(); i++) {
            System.out.println("............... Driver Index " + i + "..................");
            System.out.println("Driver Name: " + driverList.get(i).getDriverName());
            System.out.println("_________________________________________________________");
        }

        //User enters the index relevant to the driver to be removed
        System.out.println("Enter Driver's Index To Be Removed: ");
        int index = input.nextInt();
        System.out.println("Driver "+driverList.get(index).getDriverName()+" was Successfully Removed");
        //Driver is removed from the Array List
        driverList.remove(index);

        viewDrivers();
    }

    @Override
    public void changeTeam(){
        System.out.println("............. Driver Team Changer .............");

        //Displays all the Drivers currently stored in the List
        for (int i=0; i < driverList.size(); i++) {
            System.out.println("............... Driver Index "+ i+"..................");
            System.out.println("Driver Name: "+ driverList.get(i).getDriverName());
            System.out.println("Driver Team: "+ driverList.get(i).getDriverTeam());
            System.out.println(" ");
        }

        //User enters the index number of driver to be swapped
        System.out.println("Enter Driver/Team Index number which to swap: ");
        int swapDriverTeam = input.nextInt();

        //Check whether the index is greater than zero and index number is smaller than the size of the array list
        if (swapDriverTeam >=0 && swapDriverTeam < driverList.size()){
            System.out.println("Enter Driver/Team of the Other team to swap with: ");
            //User enter the index number of the other driver to be swapped with
            int swapDriverTeamTo = input.nextInt();

            //Check whether the index is greater than zero and index number is smaller than the size of the array list
            if (swapDriverTeamTo >= 0 && swapDriverTeamTo < driverList.size()){

                //Gets the data from the driver and sets all the data elements to driver for which the data is to be setted
                driverList.get(swapDriverTeamTo).setDriverName(driverList.get(swapDriverTeam).getDriverName());
                driverList.get(swapDriverTeamTo).setDriverLocation(driverList.get(swapDriverTeam).getDriverLocation());
                driverList.get(swapDriverTeamTo).setCurrentPoints(driverList.get(swapDriverTeam).getCurrentPoints());
                driverList.get(swapDriverTeamTo).setPoints(driverList.get(swapDriverTeam).getPoints());
                driverList.get(swapDriverTeamTo).setNumRaces(driverList.get(swapDriverTeam).getNumRaces());
                driverList.get(swapDriverTeamTo).setPosition(driverList.get(swapDriverTeam).getPosition());
                driverList.get(swapDriverTeamTo).setFirstPtn(driverList.get(swapDriverTeam).getFirstPtn());
                driverList.get(swapDriverTeamTo).setSecondPtn(driverList.get(swapDriverTeam).getSecondPtn());
                driverList.get(swapDriverTeamTo).setThirdPtn(driverList.get(swapDriverTeam).getThirdPtn());
                driverList.get(swapDriverTeamTo).setRaceDate(driverList.get(swapDriverTeam).getRaceDate());

                //Displays the Updated Driver List
                System.out.println("------------------ New Driver Details ------------------ ");
                System.out.println("Driver Name: "+driverList.get(swapDriverTeamTo).getDriverName());
                System.out.println("Driver New Team: "+driverList.get(swapDriverTeamTo).getDriverTeam());
                System.out.println("Driver Location: "+driverList.get(swapDriverTeamTo).getDriverLocation());
                System.out.println("Driver Current Points: "+driverList.get(swapDriverTeamTo).getCurrentPoints());
                System.out.println("Driver Points Earned: "+driverList.get(swapDriverTeamTo).getPoints());

                //Removes the driver which was swapped to avoid same data repeating in the List
                driverList.remove(swapDriverTeam);
            }else{
                System.out.println("Invalid Index Number Entered!");
            }
        }
        else{
            System.out.println("Team Swap Range Invalid!");
        }
    }

    @Override
    //Displays the statistics of the driver from the input index
    public void displayStats() {


        System.out.println("Enter Index Number Of Driver: ");
        int index = input.nextInt();

        System.out.println("---------Driver Statistics---------");
        System.out.println("Driver Name: "+ driverList.get(index).getDriverName());
        System.out.println("Number Of Races Drive Took Part In: "+ driverList.get(index).getNumRaces());
        System.out.println("Current Total Points Driver Has: "+ driverList.get(index).getCurrentPoints());
        System.out.println("Driver's New Points earned: "+ driverList.get(index).getPoints());
        System.out.println("Number Of First Positions: "+ driverList.get(index).getFirstPtn());
        System.out.println("Number Of Second Positions: "+ driverList.get(index).getSecondPtn());
        System.out.println("Number Of Third Positions: "+ driverList.get(index).getThirdPtn());

    }

    @Override
    public void driverTable() {
        //Sorting arraylist by driver Points https://www.geeksforgeeks.org/how-to-sort-arraylist-using-comparator/
        //Point comparator is called and the array list is sorted
        driverList.sort(new PointComparator());
        for (Formula1Driver temp : driverList) {
            System.out.println(temp.getDriverName() + " " + temp.getCurrentPoints());
        }

        //https://code2care.org/java/display-output-in-java-console-as-a-table
        //1D Array to store the Table Headers
        String [] tableHeaders = {"Driver Name","Driver Team","Driver Location","Num Races","Current Driver Points","First Position Count","Second Position Count","Third Position Count"};
        //2D Array to store the Table Data
        String [] [] tableData = new String [driverList.size()][8];

        for (int i = 0; i < driverList.size(); i++){

            //Gets each data element from the array list and passes to the table data 2D Array as string
            tableData[i][0] = driverList.get(i).getDriverName();
            tableData[i][1] = driverList.get(i).getDriverLocation();
            tableData[i][2] = driverList.get(i).getDriverTeam();
            int temp = driverList.get(i).getNumRaces();
            tableData[i][3] = String.valueOf(temp);
            temp = driverList.get(i).getCurrentPoints();
            tableData[i][4] = String.valueOf(temp);
            temp = driverList.get(i).getFirstPtn();
            tableData[i][5] = String.valueOf(temp);
            temp = driverList.get(i).getSecondPtn();
            tableData[i][6] = String.valueOf(temp);
            temp = driverList.get(i).getThirdPtn();
            tableData[i][7] = String.valueOf(temp);

        }

        // Prints the Table with Headers and Data
        ASCIITable.getInstance().printTable(tableHeaders,tableData);

    }

    @Override
    public void addRace() {

        System.out.println("--------Add Race--------");

        //Checks if the Array List is empty
        if (driverList.size()>1) {
            while(true) {
                //Checks if the race was completed by the Driver
                System.out.println("Enter 'Y' if race was completed or Click Any Key if not completed....");
                String choice = input.next();
                if (choice.equalsIgnoreCase("Y")) {
                    System.out.println("..................................");

                    //Calls the race date function
                    if (checkDate()) {
                        System.out.println("Race Date: " + F1DriverLink.getRaceDate());

                        //User is prompted to enter driver's position to each driver in Array List
                        for (Formula1Driver formula1Driver : driverList) {
                            while (true) {
                                System.out.println(" Driver Name: " + formula1Driver.getDriverName());
                                System.out.println(" Driver Team: " + formula1Driver.getDriverTeam());

                                System.out.println("Enter Driver's Position: ");
                                if (input.hasNextInt()) {
                                    System.out.println(" ");

                                    int datePosition = input.nextInt();
                                    //Checks if the entered position is between 1 and 10
                                    if (datePosition >= 1 && datePosition <= 10) {
                                        //Points, First, Second and Third Position counts updated according to the Position
                                        switch (datePosition) {
                                            case 1 -> {
                                                formula1Driver.setCurrentPoints(25 + formula1Driver.getCurrentPoints());
                                                formula1Driver.setFirstPtn(1 + formula1Driver.getFirstPtn());
                                                formula1Driver.setNumRaces(1 + formula1Driver.getNumRaces());
                                                System.out.println("You've Earned First Place!!");
                                            }
                                            case 2 -> {
                                                formula1Driver.setCurrentPoints(18 + formula1Driver.getCurrentPoints());
                                                formula1Driver.setSecondPtn(1 + formula1Driver.getSecondPtn());
                                                formula1Driver.setNumRaces(1 + formula1Driver.getNumRaces());
                                                System.out.println("You've Earned Second Place!!");
                                            }
                                            case 3 -> {
                                                formula1Driver.setCurrentPoints(15 + formula1Driver.getCurrentPoints());
                                                formula1Driver.setThirdPtn(1 + formula1Driver.getThirdPtn());
                                                formula1Driver.setNumRaces(1 + formula1Driver.getNumRaces());
                                                System.out.println("You've Earned Third Place!!");
                                            }
                                            case 4 -> {
                                                formula1Driver.setCurrentPoints(12 + formula1Driver.getCurrentPoints());
                                                formula1Driver.setNumRaces(1 + formula1Driver.getNumRaces());
                                                System.out.println("You've Earned Fourth Place!!");
                                            }
                                            case 5 -> {
                                                formula1Driver.setCurrentPoints(10 + formula1Driver.getCurrentPoints());
                                                formula1Driver.setNumRaces(1 + formula1Driver.getNumRaces());
                                                System.out.println("You've Earned Fifth Place!!");
                                            }
                                            case 6 -> {
                                                formula1Driver.setCurrentPoints(8 + formula1Driver.getCurrentPoints());
                                                formula1Driver.setNumRaces(1 + formula1Driver.getNumRaces());
                                                System.out.println("You've Earned Sixth Place!!");
                                            }
                                            case 7 -> {
                                                formula1Driver.setCurrentPoints(6 + formula1Driver.getCurrentPoints());
                                                formula1Driver.setNumRaces(1 + formula1Driver.getNumRaces());
                                                System.out.println("You've Earned Seventh Place!!");
                                            }
                                            case 8 -> {
                                                formula1Driver.setCurrentPoints(4 + formula1Driver.getCurrentPoints());
                                                formula1Driver.setNumRaces(1 + formula1Driver.getNumRaces());
                                                System.out.println("You've Earned Eighth Place!!");
                                            }
                                            case 9 -> {
                                                formula1Driver.setCurrentPoints(2 + formula1Driver.getCurrentPoints());
                                                formula1Driver.setNumRaces(1 + formula1Driver.getNumRaces());
                                                System.out.println("You've Earned Ninth Place!!");
                                            }
                                            case 10 -> {
                                                formula1Driver.setCurrentPoints(1 + formula1Driver.getCurrentPoints());
                                                formula1Driver.setNumRaces(1 + formula1Driver.getNumRaces());
                                                System.out.println("You've Earned Tenth Place!!");
                                            }
                                        }
                                    } else {
                                        System.out.println("Invalid Position Entered");
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    } else {
                        System.out.println("Race was not Completed by Driver");
                    }
                }
                break;
            }
        }
    }

    @Override
    //Writing Array List To File
    //https://samderlust.com/dev-blog/java/write-read-arraylist-object-file-java
    public void saveToFile() {
        System.out.println("--------SAVING TO FILE--------");
        try {
            //Created a new Text File F1 Manager
            FileOutputStream writeData = new FileOutputStream("F1 Manager.ser");
            //writeStream declared as the Output Stream
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            //Contents from the Array List is written in to the File
            writeStream.writeObject(driverList);

            writeStream.flush();
            writeStream.close();
            System.out.println("All Data Successfully Stored To File!");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error Writing to File.....");
        }
    }

    @Override
    //Reading data from file and loading to array list
    // https://www.javatpoint.com/serialization-in-java
    public  void loadFile(){
        System.out.println("--------Reading Previous Save File--------");
        try{
            //F1 Manager file is loaded to the input stream
            FileInputStream readData = new FileInputStream("F1 Manager.ser");
            //readStream declare as the InputStream
            ObjectInputStream readStream = new ObjectInputStream(readData);

            //Contents from the File is copied to the Array List Driver List
            driverList = (ArrayList<Formula1Driver>) readStream.readObject();
            readStream.close();
            System.out.println("Successfully Loaded Contents From the file");

        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Error Loading File....");
        }
    }

    @Override
    //Prints Out All The Data In Array List
    public void viewDrivers() {
        for (int i = 0; i < driverList.size(); i++) {
            System.out.println("............... Driver Details Summary..................");
            System.out.println("............... Driver Number "+ i +"....................");
            System.out.println("Driver Name: "+ driverList.get(i).getDriverName());
            System.out.println("Driver Location: "+ driverList.get(i).getDriverLocation());
            System.out.println("Driver Team: "+ driverList.get(i).getDriverTeam());
            System.out.println("Number Of Races Drive Took Part In: "+ driverList.get(i).getNumRaces());
            System.out.println("Current Total Points Driver Has: "+ driverList.get(i).getCurrentPoints());
            System.out.println("Driver's New Points earned: "+ driverList.get(i).getPoints());
            System.out.println("Number Of First Positions: "+ driverList.get(i).getFirstPtn());
            System.out.println("Number Of Second Positions: "+ driverList.get(i).getSecondPtn());
            System.out.println("Number Of Third Positions: "+ driverList.get(i).getThirdPtn());
        }
    }

    //Pre defining the date format
    //https://stackoverflow.com/questions/27580655/how-to-set-a-date-as-input-in-java
    private static final Locale defaultFormattingLocale
            = Locale.getDefault(Locale.Category.FORMAT);
    private static final String defaultDateFormat = DateTimeFormatterBuilder
            .getLocalizedDateTimePattern(FormatStyle.SHORT, null,
                    IsoChronology.INSTANCE, defaultFormattingLocale);
    private static final DateTimeFormatter dateFormatter
            = DateTimeFormatter.ofPattern(defaultDateFormat, defaultFormattingLocale);

    private boolean checkDate() {
        Scanner input = new Scanner(System.in);

        //Date format declared as MM/dd/yy
        LocalDate sampleDate
                = Year.now().minusYears(1).atMonth(Month.NOVEMBER).atDay(26);
        System.out.println("Enter date in " + defaultDateFormat
                + " format, for example " + sampleDate.format(dateFormatter));
        String dateString = input.nextLine();

        //Validates if the entered date is in the correct format and Valid
        try {
            LocalDate raceDate = LocalDate.parse(dateString, dateFormatter);
            System.out.println("Date entered was " + raceDate);
            //Sets the Race Date
            F1DriverLink.setRaceDate(raceDate);
            return true;
        } catch (DateTimeParseException dtpe) {
            System.out.println("Invalid date: " + dateString);
            return false;
        }

    }

    //Validates the driver's Name
    public boolean CheckName(String driverName) {
        boolean flag = false;
        for (Formula1Driver formula1Driver : driverList) {
            //Loops through the array list checking if the same name is already used
            if (formula1Driver.getDriverName().equals(driverName)) {
                flag = true;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    //Validates the driver's Team
    public boolean CheckTeam(String driverTeam) {
        boolean flag = false;
        for (Formula1Driver formula1Driver : driverList) {
            //Loops through the array list to check whether a driver from that team is already added
            if (formula1Driver.getDriverTeam().equals(driverTeam)) {
                flag = true;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    //Checks if the added position is between 1 and 10
    public boolean CheckPosition(int position) {
        boolean flag;
        if (position < 1 || position > 10) {
            flag = true;
        } else {
            flag = false;
        }

        return flag;
    }

    //Returns the Array List
    public static ArrayList<Formula1Driver> exportList(){
        return driverList;
    }


    public static void testCase(){
      Formula1Driver driver1 = new Formula1Driver("Sebastian Vettel","Germany","Aston Martin",6,1,2,2,131);
      driverList.add(driver1);
      Formula1Driver driver2 = new Formula1Driver("Lewis Hamilton","United Kingdom","Mercedes",6,2,3,1,163);
      driverList.add(driver2);
      Formula1Driver driver3 = new Formula1Driver("Max Verstappen","Netherlands","Red Bull Racing",6,2,2,2,149);
      driverList.add(driver3);
      Formula1Driver driver4 = new Formula1Driver("Antonio Giovinazzi","Italy","Alfa Romeo Racing",6,1,2,0,110);
      driverList.add(driver4);
      Formula1Driver driver5 = new Formula1Driver("Charles Leclerc","Monaco","Ferrari",6,1,1,1,104);
      driverList.add(driver5);
      Formula1Driver driver6 = new Formula1Driver("Lando Norris","United Kingdom","Mclaren",4,2,1,1,87);
      driverList.add(driver6);
      Formula1Driver driver7 = new Formula1Driver("Fernando Alonso","Spain","Alpine",4,0,0,1,59);
      driverList.add(driver7);
    }

}





