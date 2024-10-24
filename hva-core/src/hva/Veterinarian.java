package hva;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Veterinarian extends Employee{

    private Map<String,Specie> species=new TreeMap<>(String.
    CASE_INSENSITIVE_ORDER);
    private ArrayList<RegistVaccinatian> _registVaccinatian;

    public Veterinarian(String id, String name){
        super(id,name);
        _registVaccinatian =new ArrayList<>();
    }

    public Map<String,Specie> getIdSpeciesResponsability(){
        return species;
    }

    public ArrayList<RegistVaccinatian> getRegistVaccinatian(){
        return _registVaccinatian;
    }

    /**
     * Adds a Specie to the care of a Veterinarian.
     * 
     * @param idSpecie
     */
    public void addSpecies(Specie specie){
        species.put(specie.getId(),specie);
    }

    /**
     * Adds a Specie to the care of a Veterinarian.
     * 
     * @param idSpecie
     */
    public void removeSpecies(Specie specie){
        species.remove(specie.getId(),specie);
    }

    @Override
    public float satisfaction(){
        int sum=0;
        for (Specie specie : species.values())
            sum+=(specie.getAnimals().size()/specie.
            getNumberOfVeterinarian());
        return 20-sum;
    }


    @Override
    public String toString(){
        if(species.isEmpty())
            return "VET|"+getId()+"|"+getName();
        return "VET|"+getId()+"|"+getName()+"|"+species.keySet().
        stream().collect(Collectors.joining(","));
    }
}

