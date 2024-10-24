package hva;
import java.io.Serializable;

import java.util.Collection;
import java.util.TreeMap;
import java.util.Map;
import java.util.HashMap;

public class Habitat implements Serializable{
    private String _id;
    private String _name;
    private int _area;
    private Map<String,Tree> _trees = 
    new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private Map<String,Animal> _animals = new HashMap<>();
    private Map<String,Integer> _numberOfAnimalsSpecies = 
    new HashMap<>();
    private Map<String,Integer> _specieInfluence = new HashMap<>();
    private int _numberOfKeepers = 0;


    public Habitat(String id, String name,int area){
        _id=id;
        _name=name;
        _area=area;
    }

    public String getId(){
        return _id;
    }

    public String getName(){
        return _name;
    }

    public int getArea(){
        return _area;
    }

    public void setArea(int area){
        _area=area;
    }

    public Map<String,Animal> getAnimals(){
        return _animals;
    }

    public Map<String,Integer> getNumberOfAnimalsSpecies(){
        return _numberOfAnimalsSpecies;
    }

    public Map<String,Tree> getTrees(){
        return _trees;
    }
    
    public Map<String,Integer> getInfluence(){
        return _specieInfluence;
    }

    public int getNumberOfKeepers(){
        return _numberOfKeepers;
    }

    /**
     * Increases the number of keepers assigned to the habitat by
     * one.
     */
    public void addNumberOfKeepers(){
        _numberOfKeepers++;
    }

    /**
     * Decreases the number of keepers assigned to the habitat by
     * one.
     */
    public void subNumberOfKeepers(){
        _numberOfKeepers--;
    }

    /**
     * Increments a number of animals in animal species in the habitat.
     * 
     * @param idSpecie the species ID to be added
     */
    public void addNumberOfAnimalsSpecies(String idSpecie){
        getNumberOfAnimalsSpecies().put(idSpecie, 
        getNumberOfAnimalsSpecies().getOrDefault(idSpecie, 0) + 1);    
    }

    /**
     * Gets the total number of animals in the habitat.
     *
     * @return the total number of animals across all species
     */
    public int numberOfAnimals(){
        int total=0;
        for(int _numberOfAnimalsSpecie : _numberOfAnimalsSpecies.
        values())
            total+=_numberOfAnimalsSpecie;
        return total;
    }

    /**
     * Removes an animal from the habitat and updates the number of
     * animals of that species in the habitat.
     *
     * @param animal the animal to be removed
     */
    public void removeAnimal(Animal animal){
        getAnimals().remove(animal.getId(),animal);
        getNumberOfAnimalsSpecies().put(animal.getIdSpecies(), 
        getNumberOfAnimalsSpecies().getOrDefault(animal.getIdSpecies()
        , 0) - 1);    
    }

    /**
     * Sets the influence of the habitat onto a certain species.
     *
     * @param idSpecie the species ID
     * @param newInfluence the new influence value as a string
     * ("POS", "NEG", or "NEU")
     */
    public void setSpecieInfluence(String idSpecie,String 
    newInfluence){
        int influence=interpret(newInfluence);
        _specieInfluence.put(idSpecie,influence);
    }

    /**
     * Ensures a species has an influence in the habitat, 
     * defaulting to neutral if none is set.
     *
     * @param idSpecie the species ID
     */
    public void needSpecieInfluence(String idSpecie){
        if (!_specieInfluence.containsKey(idSpecie))
            setSpecieInfluence(idSpecie,"NEU");
    }

    /**
     * Calculates the total cleaning effort of the habitat based on
     * its trees.
     *
     * @return the total cleaning effort as an integer
     */
    public int totalCleaningEffort(){
        int sum=0;
        for (Tree tree: _trees.values())
            sum+=tree.getDifficultyClean();
        return sum;
    }
    
    /**
     * Returns a collection of all the trees in the habitat.
     *
     * @return a collection of Tree objects
     */
    public Collection<Tree> trees(){
        return _trees.values();
    }

    /**
     * Interprets a string value for habit influence onto a species
     * and returns the corresponding numeric influence.
     *
     * @param influence the string representation of the influence
     * @return the numeric representation of the influence
     */
    public int interpret(String influence){
        if (influence.equals("POS"))
            return 20;
        else if (influence.equals("NEG"))
            return -20;
        return 0;
    } 



    @Override
    public String toString() {
        return "HABITAT|" +_id + "|" + _name + "|" + _area+ "|" + 
        _trees.size();
    }
}