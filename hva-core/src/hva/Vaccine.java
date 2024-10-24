package hva;
import java.util.Map;
import java.util.TreeMap;
import java.io.Serializable;

public class Vaccine implements Serializable {
    private String _id;
    private String _name;
    private int _nAplications=0;
    private Map<String,Specie> _species=new TreeMap<>(String.
    CASE_INSENSITIVE_ORDER);


    public Vaccine(String id, String name){
        _id=id;
        _name=name;
    }

    public String getId(){
        return _id;
    }

    public String getName(){
        return _name;
    }

    public int getNAplications(){
        return _nAplications;
    }

    public Map<String,Specie> getSpecies(){
        return _species;
    }

    /**
     * Incrementes the number applications of the Vaccine.
     */
    public void addNAplications(){
        _nAplications++;
    }

    @Override
    public String toString() {
        if(getSpecies().size()==0)
            return "VACINA|" +_id + "|" + _name + "|" + _nAplications;
        return "VACINA|" +_id + "|" + _name + "|" + _nAplications+ 
        "|" + String.join(",",_species.keySet());
    }
}
