package hva;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;



public class Animal implements Serializable  {
    private String _id;
    private String _name;
    private String _idSpecie;
    private String _idHabitat;
    private List<VaccinationResult> _historyAnimal=new ArrayList<>();
    private ArrayList<RegistVaccinatian> _registVaccinatian;


    public Animal(String id, String name, String idSpecie,
    String idHabitat){
        _id=id;
        _name=name;
        _idSpecie=idSpecie;
        _idHabitat=idHabitat;
        _registVaccinatian =new ArrayList<>();
    }

    public String getId(){
        return _id;
    }

    public String getName(){
        return _name;
    }

    public String getIdSpecies(){
        return _idSpecie;
    }

    public String getIdHabitat(){
        return _idHabitat;
    }

    public List<VaccinationResult> getHistoryAnimal(){
        return _historyAnimal;
    }

    public ArrayList<RegistVaccinatian> getRegistVaccinatian(){
        return _registVaccinatian;
    }

    public void setId(String id){
        _id=id;
    }

    public void setName(String name){
        _name=name;
    }

    public void setIdSpecies(String idSpecies){
        _idSpecie=idSpecies;
    }

    public void setIdHabitat(String idHabitat){
        _idHabitat=idHabitat;
    }

    /**
     * Concatenates the list of vaccination history of the animal
     * into a single string.
     *
     * @return a comma-separated string of the vaccination history
     */
    public String concatHistoryAnimal(){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < _historyAnimal.size(); i++) {
            res.append(_historyAnimal.get(i));
            if (i < _historyAnimal.size() - 1) {
                res.append(",");
            }
        }
        return res.toString();
    }



    @Override
    public String toString() {
        if (_historyAnimal.size() == 0)
            return "ANIMAL|" +_id + "|" + _name + "|" + _idSpecie+
            "|VOID|" + _idHabitat;
        return "ANIMAL|" +_id + "|" + _name + "|" + _idSpecie+ "|" + 
        concatHistoryAnimal() + "|" + _idHabitat;
    }
}
