package hva;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Specie implements Serializable {
    private String _id;
    private String _name;
    private Map<String,Animal> _animals = new HashMap<>();
    private int _numberOfVeterinarian=0;



    public Specie(String id, String name){
        _id=id;
        _name=name;
    }

    public String getId(){
        return _id;
    
    }

    public String getName(){
        return _name;
    }

    public Map<String,Animal> getAnimals(){
        return _animals;
    }

    public int getNumberOfVeterinarian(){
        return _numberOfVeterinarian;
    }

    /**
     * Increases a number of veterinarians. 
     */
    public void addNumberOfVeterinarians(){
        _numberOfVeterinarian++;
    }

    /**
     * Decreases a number of veterinarians. 
     */
    public void subNumberOfVeterinarians(){
        _numberOfVeterinarian--;
    }

    /**
     * Associates the Animal to its Specie.
     * 
     * @param idAnimal
     */
    public void addIdAnimal(Animal animal){
        _animals.put(animal.getId(),animal);
    }
}
