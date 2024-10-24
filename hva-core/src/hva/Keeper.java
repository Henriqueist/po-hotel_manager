package hva;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Keeper extends Employee{

    private Map<String,Habitat> habitats=new TreeMap<>(String.
    CASE_INSENSITIVE_ORDER);

    public Keeper(String id, String name){       
        super(id,name);
    }

    public Map<String,Habitat> getIdHabitatResponsability(){
        return habitats;
    }

    /**
     * Adds a Habitat to the care of a Keeper.
     * 
     * @param idHabitat
     */
    public void addHabitats(Habitat habitat){
        habitats.put(habitat.getId(),habitat);
    }

    /**
     * Adds a Habitat to the care of a Keeper.
     * 
     * @param idHabitat
     */
    public void removeHabitats(Habitat habitat){
        habitats.remove(habitat.getId(),habitat);
    }
    
    @Override
    public float satisfaction(){
        int sum=0;
        for (Habitat habitat : habitats.values())
            sum += ((habitat.getArea()+3*habitat.getAnimals().size()+
            habitat.totalCleaningEffort())/habitat.
            getNumberOfKeepers());
        return 300-sum;
    }


    @Override
    public String toString(){
        if(habitats.isEmpty())
            return "TRT|"+getId()+"|"+getName();
        return "TRT|"+getId()+"|"+getName()+"|"+habitats.keySet().
        stream().collect(Collectors.joining(","));
    }
}
