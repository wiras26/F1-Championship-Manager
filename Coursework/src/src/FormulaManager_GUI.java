import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class FormulaManager_GUI extends JFrame implements ActionListener {

    JTable driverTable;
    JScrollPane driverScrollPane;
    JButton descendingPoints;//Button to sort the current points in Descending order
    JButton ascendingPoints;//Button to sort the current points in Ascending order
    JButton driverSearch;//Button used to search the driver
    JButton descendingFirstPtn;//Button used to sort the first position count in descending order
    JButton addRandomRace;//Button used to add a random race to the current table
    JButton SortRaceDates;//Sorts the race dates
    JButton generatePositions;//Generate the start and end positions of a race automatically
    public ArrayList<Formula1Driver>driverList = Formula1ChampionshipManager.exportList();


    FormulaManager_GUI(){
        driverTable = new JTable();// Creating a table using JTable
        driverScrollPane = new JScrollPane(driverTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        descendingPoints =  new JButton("Current Points In Descending Order");
        ascendingPoints = new JButton("Current Points In Ascending Order");
        descendingFirstPtn = new JButton("Largest Number Of First Positions (Descending Order)");
        addRandomRace = new JButton("Generate Random Race");
        SortRaceDates = new JButton("Sort Race Dates in Ascending");
        driverSearch = new JButton("Search Driver");
        generatePositions = new JButton("Generate Race Winner with Positions");

        //Setting the size of the JFrame
        setSize(1400,700);
        setResizable(false);
        setTitle("F1 CHAMPIONSHIP SCORES");
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Declaring the table headings which uses a 1D array to store the field names
        String[] TableFields = {"Driver Name","Driver Team","Driver Location",
                "Number Of Races","Current Driver Points","First Position Count","Second Position Count","Third Position Count","Race Date"};
        driverTable.setModel(new javax.swing.table.DefaultTableModel(new Object[][]{},TableFields));//Defining the structure of the table
        testCase(driverList);//Calling the test case function to test the program
        //Inserting the data in the Array List to the table
        insertData(driverList,driverTable);


        setLayout(new FlowLayout());
        add(driverScrollPane).setPreferredSize(new Dimension(1300,500));
        add(descendingPoints);
        add(ascendingPoints);
        add(descendingFirstPtn);
        add(addRandomRace);
        add(SortRaceDates);
        add(driverSearch);
        add(generatePositions);

        //Calls the relevant method using action listener when each button is pressed
        descendingPoints.addActionListener(this);
        ascendingPoints.addActionListener(this);
        descendingFirstPtn.addActionListener(this);
        addRandomRace.addActionListener(this);
        SortRaceDates.addActionListener(this);
        driverSearch.addActionListener(this);
        generatePositions.addActionListener(this);

    }
    //Method to insert data to the table
    public void insertData(ArrayList<Formula1Driver> driverList,JTable driverTable){
        for(int i=0; i<driverList.size(); i++){
            //For loop is used to loop through the array list and get all the information regarding each driver
            ((DefaultTableModel)driverTable.getModel()).addRow(new Object[]{
                    driverList.get(i).getDriverName(),
                    driverList.get(i).getDriverTeam(),
                    driverList.get(i).getDriverLocation(),
                    driverList.get(i).getNumRaces(),
                    driverList.get(i).getCurrentPoints(),
                    driverList.get(i).getFirstPtn(),
                    driverList.get(i).getSecondPtn(),
                    driverList.get(i).getThirdPtn(),
                    driverList.get(i).getRaceDate()
            });

        }
    }

    public void removeRows(){ //Removes unnecessary rows from the table to avoid repetition of the same data
        DefaultTableModel newModel = (DefaultTableModel)driverTable.getModel();
        int rowCount = newModel.getRowCount();

        for (int i = rowCount - 1; i >= 0; i--){
            newModel.removeRow(i);
        }
    }

    //Method which is used to sort the Current driver points in descending order
    public void sortDriverPointsDescending(){
        removeRows();
        Collections.sort(driverList, new PointComparator());
        insertData(driverList,driverTable);
    }

    //Method to sort the current driver points in ascending order
    public void sortDriverPointsAscending(){
        removeRows();
        Collections.sort(driverList, new PointComparatorAscendingCurrentPoints());
        insertData(driverList,driverTable);
    }

    //Method to sort the First Positions in the descending order
    public void sortDriverFirstPtn(){
        removeRows();
        Collections.sort(driverList, new PointComparatorDescendingFirstPosition());
        insertData(driverList,driverTable);
    }

    //Method to sort the race dates in the Ascending order
    public void sortRaceDates(){
        removeRows();
        Collections.sort(driverList, new PointComparatorAscendingRaceDate());
        insertData(driverList,driverTable);
    }

    //Generates random dates between the given limits
    public static LocalDate between(LocalDate startInclusive, LocalDate endExclusive){ // https://www.baeldung.com/java-random-dates
        long startDay = startInclusive.toEpochDay();
        long endDay = endExclusive.toEpochDay();
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startDay, endDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    //Method used to generate a random race
    public void GenerateRace()  {
        removeRows();
        int minNum = 1;
        int maxNum = 10;

        //Generates a random date for each driver
        for(int i = 0; i < driverList.size(); i++) { // https://stackoverflow.com/questions/3985392/generate-random-date-of-birth
            Random random = new Random();
            int minDay = (int) LocalDate.of(2015, 1, 1).toEpochDay();
            int maxDay = (int) LocalDate.of(2021, 1, 1).toEpochDay();
            long randomDay = minDay + random.nextInt(maxDay - minDay);

            LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
            // https://stackoverflow.com/questions/22929237/convert-java-time-localdate-into-java-util-date-type
            driverList.get(i).setRaceDate(randomDate);
        }


        if (driverList.size() > 1) {


            for (Formula1Driver formula1Driver : driverList) {
                //Generates a random position for each driver
                int datePosition = (int) (Math.random() * (maxNum - minNum)) + minNum;

                //According to the position the driver stats are updated for each driver
                switch (datePosition) {
                    case 1 -> {
                        formula1Driver.setCurrentPoints(25 + formula1Driver.getCurrentPoints());
                        formula1Driver.setPoints(25);
                        formula1Driver.setFirstPtn(1 + formula1Driver.getFirstPtn());
                        formula1Driver.setNumRaces(1 + formula1Driver.getNumRaces());

                    }
                    case 2 -> {
                        formula1Driver.setCurrentPoints(18 + formula1Driver.getCurrentPoints());
                        formula1Driver.setPoints(18);
                        formula1Driver.setSecondPtn(1 + formula1Driver.getSecondPtn());
                        formula1Driver.setNumRaces(1 + formula1Driver.getNumRaces());

                    }
                    case 3 -> {
                        formula1Driver.setCurrentPoints(15 + formula1Driver.getCurrentPoints());
                        formula1Driver.setPoints(15);
                        formula1Driver.setThirdPtn(1 + formula1Driver.getThirdPtn());
                        formula1Driver.setNumRaces(1 + formula1Driver.getNumRaces());

                    }
                    case 4 -> {
                        formula1Driver.setCurrentPoints(12 + formula1Driver.getCurrentPoints());
                        formula1Driver.setPoints(12);
                        formula1Driver.setNumRaces(1 + formula1Driver.getNumRaces());

                    }
                    case 5 -> {
                        formula1Driver.setCurrentPoints(10 + formula1Driver.getCurrentPoints());
                        formula1Driver.setPoints(10);
                        formula1Driver.setNumRaces(1 + formula1Driver.getNumRaces());

                    }
                    case 6 -> {
                        formula1Driver.setCurrentPoints(8 + formula1Driver.getCurrentPoints());
                        formula1Driver.setPoints(8);
                        formula1Driver.setNumRaces(1 + formula1Driver.getNumRaces());

                    }
                    case 7 -> {
                        formula1Driver.setCurrentPoints(6 + formula1Driver.getCurrentPoints());
                        formula1Driver.setPoints(6);
                        formula1Driver.setNumRaces(1 + formula1Driver.getNumRaces());

                    }
                    case 8 -> {
                        formula1Driver.setCurrentPoints(4 + formula1Driver.getCurrentPoints());
                        formula1Driver.setPoints(4);
                        formula1Driver.setNumRaces(1 + formula1Driver.getNumRaces());

                    }
                    case 9 -> {
                        formula1Driver.setCurrentPoints(2 + formula1Driver.getCurrentPoints());
                        formula1Driver.setPoints(2);
                        formula1Driver.setNumRaces(1 + formula1Driver.getNumRaces());

                    }
                    case 10 -> {
                        formula1Driver.setCurrentPoints(1 + formula1Driver.getCurrentPoints());
                        formula1Driver.setPoints(1);
                        formula1Driver.setNumRaces(1 + formula1Driver.getNumRaces());

                    }
                }
            }
        }
        //Inserts the updated data to the table from array list
        insertData(driverList,driverTable);
    }

    //Test cases which are used to test the program
    public static void testCase(ArrayList<Formula1Driver> driverList){
        driverList.clear();
        Formula1Driver driver1 = new Formula1Driver("Sebastian Vettel","Germany","Aston Martin",6,1,2,2,131,LocalDate.of(2021,12,12));
        driverList.add(driver1);
        Formula1Driver driver2 = new Formula1Driver("Lewis Hamilton","United Kingdom","Mercedes",6,2,3,1,163,LocalDate.of(2021,12,4));
        driverList.add(driver2);
        Formula1Driver driver3 = new Formula1Driver("Max Verstappen","Netherlands","Red Bull Racing",6,2,2,2,149,LocalDate.of(2021,11,28));
        driverList.add(driver3);
        Formula1Driver driver4 = new Formula1Driver("Antonio Giovinazzi","Italy","Alfa Romeo Racing",6,1,2,0,110,LocalDate.of(2021,12,6));
        driverList.add(driver4);
        Formula1Driver driver5 = new Formula1Driver("Charles Leclerc","Monaco","Ferrari",6,1,1,1,104,LocalDate.of(2021,11,30));
        driverList.add(driver5);
        Formula1Driver driver6 = new Formula1Driver("Lando Norris","United Kingdom","Mclaren",4,2,1,1,87,LocalDate.of(2021,12,1));
        driverList.add(driver6);
        Formula1Driver driver7 = new Formula1Driver("Fernando Alonso","Spain","Alpine",4,0,0,1,59,LocalDate.of(2021,11,27));
        driverList.add(driver7);
        Formula1Driver driver8 = new Formula1Driver("Lewis Hamilton","United Kingdom","Mercedes",6,1,1,1,101,LocalDate.of(2021,11,23));
        driverList.add(driver8);
        Formula1Driver driver9 = new Formula1Driver("Max Verstappen","Netherlands","Red Bull Racing",5,1,1,1,81,LocalDate.of(2021,11,21));
        driverList.add(driver9);
        Formula1Driver driver10 = new Formula1Driver("Max Verstappen","Netherlands","Red Bull Racing",6,2,1,0,106,LocalDate.of(2021,12,3));
        driverList.add(driver10);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(descendingPoints)){
            sortDriverPointsDescending();
        }
        else if(e.getSource().equals(ascendingPoints)){
            sortDriverPointsAscending();
        }
        else if(e.getSource().equals(descendingFirstPtn)){
            sortDriverFirstPtn();
        }
        else if(e.getSource().equals(addRandomRace)){
            GenerateRace();
        }
        else if(e.getSource().equals(SortRaceDates)){
            sortRaceDates();
        }
        else if (e.getSource().equals(driverSearch)){
            new SearchDriver();
        }
        else if (e.getSource().equals(generatePositions)){
            new GenerateRacePositions();

        }
    }
}


