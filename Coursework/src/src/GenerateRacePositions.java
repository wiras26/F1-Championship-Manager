import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;

public class GenerateRacePositions extends JFrame {

    JLabel managerTopic;
    JTable driverTable;
    JScrollPane driverScrollPane;
    JPanel container;
    public ArrayList<Formula1Driver> driverList = Formula1ChampionshipManager.exportList();

    public GenerateRacePositions(){

        //Makes a new window and a table
        managerTopic = new JLabel();
        driverTable = new JTable();
        driverScrollPane = new JScrollPane(driverTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        container = new JPanel();

        //Sets the size of the window
        setSize(1400,700);
        setResizable(false);
        setTitle("F1 CHAMPIONSHIP SCORES");
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //The table headings are stored in an 1D Array
        String[] TableFields = {"Driver Name","Driver Team","Driver Location", "Number Of Races","Current Driver Points",
                "First Position Count","Second Position Count","Third Position Count","Start Position","End Position","Win Probability"};
        driverTable.setModel(new javax.swing.table.DefaultTableModel(new Object[][]{},TableFields));//Defining the structure of the table
        testCase(driverList);
        raceData(driverList,driverTable);


        setLayout(new FlowLayout());
        add(driverScrollPane).setPreferredSize(new Dimension(1300,500));


    }

    public void raceData(ArrayList<Formula1Driver> driverList,JTable driverTable) {

        boolean winCheck = true;//Boolean flag declared to check if the 10th position driver has won the race
        int x = 0;
        removeRows();
        Integer[] arr = {1,2,3,4,5,6,7,8,9,10};

        Collections.shuffle(Arrays.asList(arr));//Shuffles the elements in the array arr

        for (Formula1Driver formula1Driver : driverList) {
            //Using a loop elements from the array are accessed and are stored as the position
            int driverPosition = arr[x];
            if (x <= 9) {

                //According to the position entered the win probability is stored for each driver
                if (driverPosition == 1){
                    formula1Driver.setWinProbability(40);
                }
                else if (driverPosition == 2){
                    formula1Driver.setWinProbability(30);
                }
                else if (driverPosition == 3 || driverPosition == 4){
                    formula1Driver.setWinProbability(10);
                }
                else if (driverPosition >= 5 && driverPosition <= 9){
                    formula1Driver.setWinProbability(2);
                }
                else if (driverPosition == 10){
                    formula1Driver.setWinProbability(0);
                }

                formula1Driver.setPosition(driverPosition);

                x++;
            }
        }
        //Displays the new generated race results in the table
        finishPtn();

        //The loop repeats until the 10th position is not the 1st at the end of the race
        while (winCheck) {
            for (int i = 0; i < driverList.size(); i++) {
                if (driverList.get(i).getPosition() == 10 ) {
                    if (driverList.get(i).getFinishPosition() == 1) {
                        finishPtn();
                    } else {
                        winCheck = false;
                    }
                }
            }
        }

        removeRows();
        for (int i = 0; i < driverList.size(); i++) {
            ((DefaultTableModel) driverTable.getModel()).addRow(new Object[]{
                    //Displays the new generated data in the table
                    driverList.get(i).getDriverName(),
                    driverList.get(i).getDriverTeam(),
                    driverList.get(i).getDriverLocation(),
                    driverList.get(i).getNumRaces(),
                    driverList.get(i).getCurrentPoints(),
                    driverList.get(i).getFirstPtn(),
                    driverList.get(i).getSecondPtn(),
                    driverList.get(i).getThirdPtn(),
                    driverList.get(i).getPosition(),
                    driverList.get(i).getFinishPosition(),
                    driverList.get(i).getWinProbability()
            });

        }
    }

    public void finishPtn(){
        int x = 0;

        Integer[] arr = {1,2,3,4,5,6,7,8,9,10};

        Collections.shuffle(Arrays.asList(arr));//Shuffles the elements in the array arr

        for (Formula1Driver formula1Driver : driverList) {
            int driverPosition = arr[x];
            //Using a loop elements from the array are accessed and are stored as the position
                formula1Driver.setFinishPosition(driverPosition);
                //According to the position the driver statistics are updated
                switch (driverPosition) {
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
                x++;
            }
    }

    public void removeRows(){ //Removes unnecessary rows from the table
        DefaultTableModel newModel = (DefaultTableModel)driverTable.getModel();
        int rowCount = newModel.getRowCount();

        for (int i = rowCount - 1; i >= 0; i--){
            newModel.removeRow(i);
        }
    }

    //Test case used to test the program
    public static void testCase(ArrayList<Formula1Driver> driverList){

        driverList.clear();
        Formula1Driver driver1 = new Formula1Driver("Sebastian Vettel","Germany","Aston Martin",6,1,2,2,131, LocalDate.of(2021,12,12));
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
        Formula1Driver driver8 = new Formula1Driver("Pierre Gasly","France","AlphaTauri",6,1,1,1,101,LocalDate.of(2021,11,23));
        driverList.add(driver8);
        Formula1Driver driver9 = new Formula1Driver("George Russell","United Kingdom","Williams",5,1,1,1,81,LocalDate.of(2021,11,21));
        driverList.add(driver9);
        Formula1Driver driver10 = new Formula1Driver("Mick Schumacher","Germany","Haas F1 Team",6,2,1,0,106,LocalDate.of(2021,12,3));
        driverList.add(driver10);

    }



}
