package hva;
import java.io.Serializable;

public abstract class Tree implements Serializable {
    private String _id;
    private String _name;
    private Season _season;
    private Season _breedingSeason;
    private int _age;
    private int _difficultyClean;

    public Tree(String id, String name,Season season,int age,int 
    difficultyClean){
        _id=id;
        _name= name;
        _season=season;
        _breedingSeason= season;
        _age= age;
        _difficultyClean= difficultyClean;

    }

    public String getId(){
        return _id;
    }

    public String getName(){
        return _name;
    }

    public Season getSeason(){
        return _season;
    }

    public Season getBreedingSeason(){
        return _breedingSeason;
    }

    public int getAge(){
        return _age;
    }

    public int getDifficultyClean(){
        return _difficultyClean;
    }

    
    /**
     * Update the season to the next season.
     */
    public void nextSeason(){
        _season=_season.nextSeason();
    }


    /**
     * Increases the age if it corresponds to the season in which it 
     * was created.
     * 
     * @param season
     */
    public void comparToBreedingSeason(Season season){
        if(season==_breedingSeason)
            _age++;
    }


    /**
     * Determines the biological cycle of the  tree based on the 
     * current season.
     *
     * @return a string representing the biological state of the
     * tree based on the season.
     */
    public abstract String biologicalCycle();


    /**
     * Determines the seasonal effort level based on the current
     * season.
     *
     * @return an integer representing the effort level based on the
     * season.
     */
    public abstract int seasonalEffort();


    /**
     * Calculates the cleaning effort required.
     *
     * @return a double representing the calculated cleaning effort.
     */
    public abstract double cleaningEffort();
}