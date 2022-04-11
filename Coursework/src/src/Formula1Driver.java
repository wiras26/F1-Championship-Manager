import java.time.LocalDate;

class Formula1Driver extends Driver {
    private int firstPtn;
    private int secondPtn;
    private int thirdPtn;
    private int numRaces;
    private LocalDate raceDate;
    private int points;
    private int currentPoints;
    private int position;
    private int finishPosition;
    private int winProbability;

    public Formula1Driver(String driverName, String driverLocation, String driverTeam,
                          int numRaces,int firstPtn, int secondPtn, int thirdPtn, int currentPoints) {
        super(driverName, driverLocation, driverTeam);
        this.firstPtn = firstPtn;
        this.secondPtn = secondPtn;
        this.thirdPtn = thirdPtn;
        this.numRaces = numRaces;
        this.currentPoints = currentPoints;
    }

    public Formula1Driver(){

    }

    public Formula1Driver(String driverName, String driverLocation, String driverTeam,
                          int numRaces,int firstPtn, int secondPtn, int thirdPtn, int currentPoints,LocalDate raceDate) {
        super(driverName, driverLocation, driverTeam);
        this.firstPtn = firstPtn;
        this.secondPtn = secondPtn;
        this.thirdPtn = thirdPtn;
        this.numRaces = numRaces;
        this.currentPoints = currentPoints;
        this.raceDate = raceDate;
    }

     public int getFirstPtn(){return firstPtn;}
     public void setFirstPtn(int firstPtn){
        this.firstPtn = firstPtn;
     }

     public int getSecondPtn(){return secondPtn;}
     public void setSecondPtn(int secondPtn){
        this.secondPtn = secondPtn;
     }

     public int getThirdPtn(){return thirdPtn;}
     public void setThirdPtn(int thirdPtn){
        this.thirdPtn = thirdPtn;
     }

     public void setRaceDate(LocalDate raceDate){
        this.raceDate = raceDate;
     }

     public LocalDate getRaceDate(){return raceDate;}

     public int getPoints(){return points;}

     public void setPoints(int points){
        this.points = points;
     }

     public void setCurrentPoints(int currentPoints){this.currentPoints = currentPoints;}

    public int getCurrentPoints(){return currentPoints;}

     public int getNumRaces(){return numRaces;}

     public void setNumRaces(int numRaces){
        this.numRaces = numRaces;
     }

     public int getPosition(){
        return  position;
    }
     public void setPosition(int position){
        this.position = position;
     }

     public void setFinishPosition(int finishPosition){
         this.finishPosition = finishPosition;
     }
     public int getFinishPosition(){
        return finishPosition;
     }

     public void setWinProbability(int winProbability){
        this.winProbability = winProbability;
     }
     public int getWinProbability(){return winProbability;}

}
