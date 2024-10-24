package hva;

public class DeciduousTree extends Tree{
    
    public DeciduousTree(String id,String name,Season season,int age,
    int difficultyClean){
        super(id,name,season,age,difficultyClean);
    }

    
    @Override
    public String biologicalCycle() {
        Season season = getSeason();
        if (season == Season.INVERNO)
            return "SEMFOLHAS";
        else if (season == Season.PRIMAVERA)
            return "GERARFOLHAS";
        else if (season == Season.VERÃO)
            return "COMFOLHAS";
        else
            return "LARGARFOLHAS";
    }


    @Override
    public int seasonalEffort() {
        Season season = getSeason();
        if (season == Season.INVERNO)
            return 0;
        else if (season == Season.PRIMAVERA)
            return 1;
        else if (season == Season.VERÃO)
            return 2;
        else
            return 5;
    }


    @Override
    public double cleaningEffort() {
        return getDifficultyClean() * seasonalEffort() * 
        Math.log(getAge() + 1);
    }

    @Override
    public String toString() {
        return "ÁRVORE|" + getId() + "|" + getName() + "|" + getAge() 
        + "|" + getDifficultyClean() + "|CADUCA|"+ biologicalCycle();
    }
}