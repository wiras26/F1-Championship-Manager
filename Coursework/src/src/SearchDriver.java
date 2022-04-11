import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SearchDriver extends JFrame implements ActionListener {

        JLabel managerTopic;
        JLabel labelWarning;
        JTextField searchDriverField;
        JButton searchButton;
        JTable driverTable;
        JScrollPane driverScrollPane;
        JPanel container;
        public ArrayList<Formula1Driver> driverList = Formula1ChampionshipManager.exportList();

        public  SearchDriver(){

            searchDriverField = new JTextField(30);
            searchButton = new JButton("Search");
            managerTopic = new JLabel();
            labelWarning = new JLabel();
            driverTable = new JTable();// Creating a table using JTable
            driverScrollPane = new JScrollPane(driverTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            container = new JPanel();

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
            insertData(driverList,driverTable);

            setLayout(new FlowLayout());
            add(driverScrollPane).setPreferredSize(new Dimension(1300,500));

            add(searchDriverField);
            add(searchButton);

            //Calls the relevant method using action listener when each button is pressed
            searchButton.addActionListener(this);

        }

        public void insertData(ArrayList<Formula1Driver> driverList,JTable driverTable){
            String driverNameSearch = searchDriverField.getText().toLowerCase();
            for(int i=0; i<driverList.size(); i++) {
                //Checks for name entered the search field and then display that driver's information on the table
                if (driverNameSearch.equalsIgnoreCase(driverList.get(i).getDriverName())){
                    ((DefaultTableModel) driverTable.getModel()).addRow(new Object[]{
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
    }

    public void removeRows(){ //Removes unnecessary rows from the table
        DefaultTableModel newModel = (DefaultTableModel)driverTable.getModel();
        int rowCount = newModel.getRowCount();

        for (int i = rowCount - 1; i >= 0; i--){
            newModel.removeRow(i);
        }
    }

    public void searchData(){
            //Calls the insert data method
            removeRows();
            insertData(driverList,driverTable);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            //Calls the searchData method when the button is clicked
        if(e.getSource().equals(searchButton)){
            searchData();
        }
    }
}

