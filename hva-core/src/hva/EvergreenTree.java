package hva;

public class EvergreenTree extends Tree{
    
    public EvergreenTree(String id,String name,Season season,int age,
    int difficultyClean){
        super(id,name,season,age,difficultyClean);
    }


    @Override
    public String biologicalCycle() {
        Season season = getSeason();
        if (season == Season.INVERNO)
            return "LARGARFOLHAS";
        else if (season == Season.PRIMAVERA)
            return "GERARFOLHAS";
        else
            return "COMFOLHAS";
    }


    @Override
    public int seasonalEffort() {
        Season season = getSeason();
        if (season == Season.INVERNO)
            return 2;
        else
            return 1;
    }


    @Override
    public double cleaningEffort() {
        return getDifficultyClean() * seasonalEffort() * 
        Math.log(getAge() + 1);
    }

    @Override
    public String toString() {
        return "ÁRVORE|" + getId() + "|" + getName() + "|" +
        getAge() + "|" + getDifficultyClean() + "|PERENE|"+ 
        biologicalCycle();
    }
}
