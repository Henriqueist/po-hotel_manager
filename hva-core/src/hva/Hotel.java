package hva;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;


import java.io.IOException;

import hva.exceptions.NoResponsibilityException;
import hva.exceptions.UnknownEmployeeKeyException;
import hva.exceptions.UnknownVaccineKeyException;
import hva.exceptions.UnknownVeterinarianKeyException;
import hva.exceptions.DuplicateAnimalKeyException;
import hva.exceptions.DuplicateEmployeeKeyException;
import hva.exceptions.DuplicateHabitatKeyException;
import hva.exceptions.DuplicateSpecieKeyException;
import hva.exceptions.DuplicateSpecieNameException;
import hva.exceptions.DuplicateTreeKeyException;
import hva.exceptions.DuplicateVaccineKeyException;
import hva.exceptions.ImportFileException;
import hva.exceptions.UnknownAnimalKeyException;
import hva.exceptions.UnknownHabitatKeyException;
import hva.exceptions.UnrecognizedEntryException;
import hva.exceptions.VeterinarianNotAuthorizedException;
import hva.exceptions.WrongVaccineException;
import hva.exceptions.UnknownSpeciesKeyException;
import hva.exceptions.UnknownTreeKeyException;

/**
 * Class that represents the hotel.
 * 
 * Implements Serializable for object serialization.
 */
public class Hotel implements Serializable {

    @Serial
    private static final long serialVersionUID = 202407081733L;


    private boolean _changed;
    private Season _currentSeason;
    private Map<String,Keeper> _keepers ;
    private Map<String,Veterinarian> _veterinarians;
    private Map<String,Employee> _employees;
    private Map<String,Habitat> _habitats;
    private Map<String,Animal> _animals;
    private Map<String,Specie> _speciesId;
    private Map<String,Specie> _speciesName;
    private Map<String, Tree> _trees;
    private Map<String, Vaccine> _vaccines;
    private List<RegistVaccinatian> _registVaccinatian;
    private List<RegistVaccinatian> _niglecenceRegistVaccinatian;



    public Hotel(){
        _changed=false;
        _currentSeason=Season.PRIMAVERA;
        _keepers =new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        _veterinarians =new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        _employees= new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        _animals =new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        _habitats =new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        _speciesId=new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        _speciesName=new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        _trees =new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        _vaccines =new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        _registVaccinatian =new ArrayList<>();
        _niglecenceRegistVaccinatian =new ArrayList<>();
    }

    /**********************/
    /*    Gets e Sets     */
    /**********************/

    public Season getSeason(){
        return _currentSeason;
    }

    private Map<String,Animal> getAnimals(){
        return _animals;
    }

    private Map<String,Habitat> getHabitats(){
        return _habitats;
    }
    
    private Map<String,Specie> getSpeciesId(){
        return _speciesId;
    }

    private Map<String,Specie> getSpeciesName(){
        return _speciesName;
    }

    private Map<String,Keeper> getKeepers(){
        return _keepers;
    }
    
    private Map<String,Veterinarian> getVeterinarians(){
        return _veterinarians;
    }

    private Map<String,Employee> getEmployees(){
        return _employees;
    }

    private Map<String,Vaccine> getVaccines(){
        return _vaccines;
    }

    private Map<String,Tree> getTrees(){
        return _trees;
    }

    private List<RegistVaccinatian> getRegistVaccinatian(){
        return _registVaccinatian;
    }

    private List<RegistVaccinatian> getNiglecenceRegistVaccinatian(){
        return _niglecenceRegistVaccinatian;
    }




    /**********************/
    /*  Usado em Animais  */
    /**********************/


    /**
     * Creates a new animal.
     *
     * @param id animal id
     * @param name animal name
     * @param idSpecie animal's specie id
     * @param idHabitat animal's habitat id
     */
    private void createAnimal(String id,String name,String idSpecie,
    String idHabitat){
        Animal animal= new Animal(id,name,idSpecie,idHabitat);
        getAnimals().put(id,animal);
        getSpeciesId().get(idSpecie).addIdAnimal(animal);
        completeInformationInHabitats(animal);
        setChanged(true);
    }


    /**
     * Confirms if the animal key doesn't already exist.
     *
     * @param id animal id
     * @throws DuplicateAnimalKeyException
     */
    private void confirmIdAnimal(String id) 
        throws DuplicateAnimalKeyException {
        if (getAnimals().containsKey(id))
            throw new DuplicateAnimalKeyException();
    }


    /**
     * Checks if the unique id of the species already exists.
     * 
     * @param id a species id
     * @throws UnknownAnimalKeyException
     */
    private void existIdAnimal(String id) 
    throws UnknownAnimalKeyException {
        if (!getAnimals().containsKey(id))
            throw new UnknownAnimalKeyException();        
    }


    /**
     * Calculates the satisfaction score for a given animal based on
     * various factors such as the number of animals of the same
     * species in its habitat, the number of animals of different
     * species in its habitat, the area of the habitat and the
     * habitat influence on the species.
     *
     * @param animal the animal for which to calculate the
     * satisfaction score
     * @return the calculated satisfaction score as a float
     */
    private float satisfactionAnimal(Animal animal){
        return 20 + 3*(getHabitats().get(animal.getIdHabitat()).
        getNumberOfAnimalsSpecies().get(animal.getIdSpecies())-1)-
        2*(getHabitats().get(animal.getIdHabitat()).numberOfAnimals()
        -getHabitats().get(animal.getIdHabitat()).
        getNumberOfAnimalsSpecies().get(animal.getIdSpecies()))+
        getHabitats().get(animal.getIdHabitat()).getArea()/
        getHabitats().get(animal.getIdHabitat()).numberOfAnimals() + 
        getHabitats().get(animal.getIdHabitat()).getInfluence().
        get(animal.getIdSpecies());
    }


    /**
     * Calculates the sum of all the satisfaction scores of all
     * animals in the hotel.
     *
     * @return the sum of all satisfaction scores of all animals
     * as a float
     */
    private float globalSatisfactionAnimal(){
        float satisfaction=0;
        for (Animal animal:getAnimals().values())
            satisfaction+=satisfactionAnimal(animal);
        return satisfaction;
    }




    /**********************/
    /*  Usado em Espécie  */
    /**********************/


    /**
     * Creates a new specie.
     * 
     * @param id a species id
     * @param name a species name
     */
    private void createSpecie(String id,String name){
        Specie specie= new Specie(id,name);
        getSpeciesId().put(id,specie);
        getSpeciesName().put(name,specie);
    }


    /**
     * Confirms if the species key doesn't already exist.
     *
     * @param id species id
     * @throws DuplicateAnimalKeyException
     */
    private void confirmIdSpecie(String id) 
    throws DuplicateSpecieKeyException {
        if (getSpeciesId().containsKey(id))
            throw new DuplicateSpecieKeyException();
    }


    /**
     * Confirms if the species name doesn't already exist.
     *
     * @param name species name
     * @throws DuplicateAnimalKeyException
     */
    private void confirmNameSpecie(String name) 
    throws DuplicateSpecieNameException {
        if (getSpeciesName().containsKey(name))
            throw new DuplicateSpecieNameException();
    }


    /**
     * Checks if the unique id of the species already exists.
     * 
     * @param id a species id
     * @throws UnknownSpeciesKeyException
     */
    private void existIdSpecie(String id) 
    throws UnknownSpeciesKeyException {
        if (!getSpeciesId().containsKey(id))
            throw new UnknownSpeciesKeyException();        
    }

    
    /**
     * Checks if all of the species keys in the list exist.
     * 
     * @param list a list of species ids
     * @throws UnkownSpeciesKeyException
     */
    private void existAllListSpecies(String list) 
    throws UnknownSpeciesKeyException{
        String [] species=list.split(",");
        for(String specie:species)
            this.existIdSpecie(specie);
    }




    /**********************/
    /*  Usado em Habitats */
    /**********************/


    /**
     * Creates a new habitat.
     * 
     * @param id habitat id
     * @param name habitat name
     * @param area habitat area
     */
    private void createHabitat(String id,String name,int area){
        Habitat habitat=new Habitat(id,name,area);
        _habitats.put(id,habitat);
        setChanged(true);
    }


    /**
     * Confirms if the habitat key doesn't already exist.
     *
     * @param id habitat id
     * @throws DuplicateHabitatKeyException
     */
    private void confirmIdHabitat(String id) 
    throws DuplicateHabitatKeyException {
        if (getHabitats().containsKey(id))
            throw new DuplicateHabitatKeyException();
    }


    /**
     * Confirms if the habitat key already exist.
     *
     * @param id habitat id
     * @throws UnknownHabitatKeyException
     */
    private void existIdHabitat(String id)
    throws UnknownHabitatKeyException {
        if (!getHabitats().containsKey(id))
            throw new UnknownHabitatKeyException();        
    }


    /**
     * Put the trees in the habitat.
     * 
     * @param id habitat id
     * @param trees tree's id
     */
    private void putTreeInHabitat(String id, String[] trees) {
        Habitat habitat=getHabitats().get(id);
        for (String idtree:trees)
            habitat.getTrees().put(idtree,getTrees().get(idtree));
        setChanged(true);
    }


    /**
     * Updates the habitat infomation with a new animal.
     *
     * @param animal the animal whose information will be used to
     * update the habitat
     */
    private void completeInformationInHabitats(Animal animal){
        getHabitats().get(animal.getIdHabitat()).getAnimals().put(
            animal.getId(),animal);
        getHabitats().get(animal.getIdHabitat()).
        addNumberOfAnimalsSpecies(animal.getIdSpecies());
        getHabitats().get(animal.getIdHabitat()).setSpecieInfluence(
            animal.getIdSpecies(),"NEU");
    }




    /*******************/
    /*  Usado em Tree  */
    /*******************/


    /**
     * Create a new tree.
     * 
     * @param id tree id
     * @param name tree name
     * @param age tree age
     * @param difficultyClean tree difficultyClean
     * @param treetype treetype type
     * @param idHabitat habitat id
     */
    private void createTree(String id,String name,int age,
    int difficultyClean,String treetype,String idHabitat){
        Tree tree;
        if (treetype.equals("PERENE"))
            tree = new EvergreenTree(id, name, getSeason(), age, 
            difficultyClean);
        else
            tree = new DeciduousTree(id, name, getSeason(), age, 
            difficultyClean);

        _trees.put(id, tree);
        if (idHabitat != ""){
            Habitat habitat = _habitats.get(idHabitat);
            habitat.getTrees().put(id, tree);
        }
    }


    /**
     * Confirms if the tree key doesn't already exist.
     *
     * @param id tree id
     * @throws DuplicateTreeKeyException
     */
    private void confirmIdTree(String id)
     throws DuplicateTreeKeyException {
        if (getTrees().containsKey(id))
            throw new DuplicateTreeKeyException();
    }


    /**
     * Confirms if the tree type has correct.
     *
     * @param type tree id
     * @return boolean
     */
    private boolean confirmTreeType(String type) {
        if (type != "CADUCA" && type!="PERENE")
            return true;
        return false;
    }


    /**
     * Checks if the unique id of the tree already exists.
     * 
     * @param id a tre id
     * @throws UnknownTreeKeyException
     */
    private void existIdTree(String id)
     throws UnknownTreeKeyException {
        if (!getTrees().containsKey(id))
            throw new UnknownTreeKeyException();        
    }




    /***********************/
    /*  Usado em Employee  */
    /***********************/


    /**
     * Creates a new employee.
     *
     * @param id employee id
     * @param name employee name
     * @param type employee type
     * @param idWork work's id
     */
    private void createEmployee(String id,String name,String type,
    String idWork) {
        if (type.equals("VET")){
            Veterinarian veterinarian=new Veterinarian(id,name);
            if(idWork!=null)
                for (String idSpecie:idWork.split("\\,")){
                    veterinarian.addSpecies(this.getSpeciesId().
                    get(idSpecie));
                    this.getSpeciesId().get(idSpecie).
                    addNumberOfVeterinarians();
                }
            getVeterinarians().put(id, veterinarian);
            getEmployees().put(id, veterinarian);
        }
        else{
            Keeper keeper=new Keeper(id,name);
            if(idWork!=null)
                for (String idHabitat:idWork.split("\\,")){
                    keeper.addHabitats(this.getHabitats().
                    get(idHabitat));
                    this.getHabitats().get(idHabitat).
                    addNumberOfKeepers();
                }
            getKeepers().put(id, keeper);
            getEmployees().put(id, keeper);
        }
        setChanged(true);
    }

    
    /**
     * Confirms if the employee key doesn't already exist.
     * 
     * @param id
     * @throws DuplicateEmployeeKeyException
     */
    private void confirmIdEmployee(String id) 
    throws DuplicateEmployeeKeyException {
        if (getEmployees().containsKey(id))
            throw new DuplicateEmployeeKeyException();
    }
    

    /**
     * Checks if the unique id of the employee already exists.
     * 
     * @param id an employee id
     * @throws UnknownEmployeeKeyException
     */
    private void existIdEmployee(String id) 
    throws UnknownEmployeeKeyException {
        if (!getEmployees().containsKey(id))
            throw new UnknownEmployeeKeyException();
    } 
    

    /**
     * Checks if the unique id of the veterinarian already exists.
     * 
     * @param id a veterinarian id
     * @throws UnknownVeterinarianKeyException
     */
    private void existIdVeterinarian(String id) 
    throws UnknownVeterinarianKeyException {
        if (!getVeterinarians().containsKey(id))
            throw new UnknownVeterinarianKeyException();
    } 


    /**
     * Confirms the responsibility assignment for a given object
     * (either a Veterinarian or a Keeper) based on the specified
     * responsibility id.
     *
     * @param object the object to which the responsibility is being
     * assigned
     * @param responsability the id of the responsibility (species or
     * habitat)
     * @throws NoResponsibilityException if the specified
     * responsibility does not exist
     */
    private void confirmResponsability(Object object,String 
    responsability) throws NoResponsibilityException {
        if(object instanceof Veterinarian){
            if (!getSpeciesId().containsKey(responsability))
                throw new NoResponsibilityException();    
            Veterinarian veterianarian = (Veterinarian) object;
            veterianarian.addSpecies(getSpeciesId().get(
                responsability));
            getSpeciesId().get(responsability).
            addNumberOfVeterinarians();
        }
        else{
            if (!getHabitats().containsKey(responsability))
                throw new NoResponsibilityException();    
            Keeper keeper = (Keeper) object;
            keeper.addHabitats(getHabitats().get(responsability));
            getHabitats().get(responsability).addNumberOfKeepers();
        }
    }


    /**
     * Removes the responsibility assignment for a given object
     * (either a Veterinarian or a Keeper) based on the specified
     * responsibility id.
     *
     * @param object the object from which the responsibility is
     * being removed
     * @param responsability the id of the responsibility (species or
     * habitat)
     * @throws NoResponsibilityException if the specified
     * responsibility does not exist in the object's reponsabilities
     */
    private void remResponsability(Object object,String 
    responsability) throws NoResponsibilityException {
        if(object instanceof Veterinarian){
            Veterinarian veterian = (Veterinarian) object;
            if (!getSpeciesId().containsKey(responsability) || 
            !veterian.getIdSpeciesResponsability().containsKey(
                responsability))
                throw new NoResponsibilityException();
            veterian.removeSpecies(getSpeciesId().get(
                responsability));
            getSpeciesId().get(responsability).
            subNumberOfVeterinarians();
        }
        else{
            Keeper veterian = (Keeper) object;
            if (!getHabitats().containsKey(responsability) || 
            !veterian.getIdHabitatResponsability().containsKey(
                responsability))
                throw new NoResponsibilityException();
            veterian.removeHabitats(getHabitats().get(
                responsability));
            getHabitats().get(responsability).subNumberOfKeepers();
        }
    }    


    /**
     * Checks if a veterinarian is authorized to handle a specific
     * animal based on the veterinarian's species responsibility.
     *
     * @param idVeterinarian the id of the veterinarian
     * @param idAnimal the id of the animal
     * @throws VeterinarianNotAuthorizedException if the veterinarian
     * is not authorized for the species of the specified animal
     */
    private void veterinarianAuthorized(String idVeterinarian,String 
    idAnimal) throws VeterinarianNotAuthorizedException{
        if(!getVeterinarians().get(idVeterinarian).
        getIdSpeciesResponsability().
        containsKey(getAnimals().get(idAnimal).getIdSpecies()))
            throw new VeterinarianNotAuthorizedException();
    }


    /**
     * Calculates the sum of all the satisfaction scores of all
     * employees in the hotel.
     *
     * @return the sum of all satisfaction scores of all employees
     * as a float
     */
    private float globalSatisfactionEmployee(){
        float satisfaction=0;
        for (Employee employee:getEmployees().values())
            satisfaction+=employee.satisfaction();
        return satisfaction;
    }




    /**********************/
    /*  Usado em Vaccine  */
    /**********************/


    /**
     * Creates a new vaccine.
     * 
     * @param id vaccine id
     * @param name vaccine name
     * @param list specie's id
     */
    private void createVaccine(String id, String name, String list){
        Vaccine vaccine = new Vaccine(id,name);
        getVaccines().put(id, vaccine);
        if (list!="")
            addSpeciesInVacine(id,list);
        setChanged(true);
    }


    /**
     * Adds species IDs to a vaccine based on the provided
     * comma-separated list.
     *
     * @param id the ID of the vaccine to which species are being
     * added
     * @param list a comma-separated string of species IDs to add to
     * the vaccine
     */
    private void addSpeciesInVacine(String id,String list){
        String[] species = list.split(",");
        for (String idSpecies : species)
            getVaccines().get(id).getSpecies().put(idSpecies,
            getSpeciesId().get(idSpecies));
    }


    /**
     * Checks if the unique id of the vaccine already exists.
     *
     * @param id the id of the vaccine
     * @throws UnknownVaccineKeyException if the vaccine id does not exist
     */
    private void existIdVaccine(String id)
    throws UnknownVaccineKeyException {
        if (!getVaccines().containsKey(id))
            throw new UnknownVaccineKeyException();        
    }


    /**
     * Confirms if the vaccine key doesn't already exist.
     *
     * @param id vaccine id
     * @throws DuplicateVaccineKeyException vaccine id already exists
     */
    private void confirmIdVaccine(String id) throws 
    DuplicateVaccineKeyException {
        if (getVaccines().containsKey(id))
            throw new DuplicateVaccineKeyException();
    }


    /**
     * Adds values to required ArrayList<RegistVaccination>.
     * 
     * @param idVaccine vaccine id
     * @param idVeterinarian veterinarian id
     * @param idAnimal animal id
     * @throws WrongVaccineException specie id don't exist in the list of species of vaccine
     */
    private void typeDamage(String idVaccine,String idVeterinarian,
    String idAnimal) throws WrongVaccineException{
        Vaccine vaccine=getVaccines().get(idVaccine);
        Veterinarian veterinarian=getVeterinarians().get(
            idVeterinarian);
        Animal animal=getAnimals().get(idAnimal);
        vaccine.addNAplications();
        RegistVaccinatian registVaccinatian=new RegistVaccinatian(idVaccine, 
        idVeterinarian, animal.getIdSpecies());
        getRegistVaccinatian().add(registVaccinatian);
        veterinarian.getRegistVaccinatian().add(registVaccinatian);
        animal.getRegistVaccinatian().add(registVaccinatian);
        if(vaccine.getSpecies().containsKey(animal.getIdSpecies())){
            animal.getHistoryAnimal().add(VaccinationResult.NORMAL);
        }
        else{
            int damage=this.damage(vaccine,getSpeciesId().get(animal.
            getIdSpecies()));
            VaccinationResult result= this.analyseDamage(damage);
            animal.getHistoryAnimal().add(result);
            getNiglecenceRegistVaccinatian().add(registVaccinatian);
            throw new WrongVaccineException();
        } 
    }


    /**
     * Obtained the damage that the vaccine cause in the animal. 
     * 
     * @param vaccine type
     * @param specie type
     * @return the max damage
     */
    private int damage(Vaccine vaccine, Specie specie){
        int maxDamage=0;
        for (Specie specieVaccine: vaccine.getSpecies().values()){
            int sizeNames =sizeNames(specie.getName(),specieVaccine.
            getName());
            int commonCharacters= commonCharacters(specie.getName(),
            specieVaccine.getName());
            maxDamage = Math.max(maxDamage,
            sizeNames-commonCharacters);
        }
        return maxDamage;
    }


    /**
     * Analyse the damage of the vaccine in the animal.
     * 
     * @param damage animal damage
     * @return the VaccinationResult type of damage 
     */
    private VaccinationResult analyseDamage(int damage) {
        if (damage>=5)
            return VaccinationResult.ERRO;
        else if (damage>=1)
            return VaccinationResult.ACIDENTE;
        return VaccinationResult.CONFUSÃO;
        
    }


    /**
     * Counts the number of common characters between two names.
     *
     * @param name1 the first name to compare
     * @param name2 the second name to compare
     * @return the number of common characters between the two names
     */
    private int commonCharacters(String name1, String name2) {
        int common=0;
        boolean[] marcados = new boolean[name2.length()];

        for (int i=0;i<name1.length();i++){
            char c1=name1.charAt(i);
            for (int j=0;j<name2.length();j++){
                char c2 = name2.charAt(j);
                if (c1 == c2 && !marcados[j]) {
                    common++;
                    marcados[j] = true;
                    break;
                }
            }
        }
        return common;
    }
    

    /**
     * Returns the length of the longer of the two provided names.
     *
     * @param name1 the first name to compare
     * @param name2 the second name to compare
     * @return the length of the longer name
     */
    private int sizeNames(String name1, String name2) {
        return Math.max(name1.length(),name2.length());
    }




    /*********************/
    /*  Usado em Season  */
    /*********************/


    public boolean getChanged(){
        return _changed;
    }

    
    public void setChanged(boolean changed){
        _changed = changed;
    }


    /**
     * Advances the current season and updates the state of all trees
     * accordingly.
     *
     * @return the integer value representing the new current season
     */
    public int advanceSeason() {
        _currentSeason=_currentSeason.nextSeason();
        setChanged(true);
        for (Tree tree: getTrees().values()){
            tree.nextSeason();
            tree.comparToBreedingSeason(_currentSeason);
        }
        return _currentSeason.getValue();
    }




    /***********************/
    /*  Satisfação global  */
    /***********************/


    /**
     * Calculates and returns the sum of satisfaction values for all
     * the animals and employees in the hotel.
     * 
     * @return the total satisfaction value rounded as an integer
     */
    public int showGlobalSatisfaction(){
        return Math.round(globalSatisfactionAnimal()+
        globalSatisfactionEmployee());
    }





    /******************************************/
    /*                                        */
    /*  Chamamos as seguintes funções na app  */
    /*                                        */
    /******************************************/




    /*****************/
    /*  Menu Animal  */
    /*****************/


//1
    /**
     * Show all animals that we have.
     * @return a collection of animal
     */
    public Collection<Animal> showAllAnimals(){
        return Collections.unmodifiableCollection(_animals.values());
    }


//2
    /**
     * Confirm information about animal.
     * 
     * @param id animal id
     * @param idHabitat habitat id
     * @throws DuplicateAnimalKeyException animal id already exists
     * @throws UnknownHabitatKeyException habitat id don't exist
     */
    public void checkInformationInAnimal(String id,String idHabitat) 
    throws DuplicateAnimalKeyException,UnknownHabitatKeyException {
        this.confirmIdAnimal(id);
        this.existIdHabitat(idHabitat);
    }


    /**
     * Checks if the a species unique key already exists.
     *
     * @param idSpecies a species id
     * @return a boolean if exist specie id
     */
    public boolean hasSpeciesId(String idSpecies) {
        Specie specie= (Specie) getSpeciesId().get(idSpecies);
        if (specie==null)
            return false;
        return true; 
    }


    /**
     * See if can create a new animal and creates, and if necessery creates a new specie
     * 
     * @param id animal id
     * @param name animal name
     * @param idSpecie animal id specie
     * @param idHabitat animal id habitat
     * @param nameSpecie specie name
     * @throws DuplicateSpecieNameException specie name already exist
     */
    public void registerAnimal(String id,String name,String idSpecie,
    String idHabitat,String nameSpecie) throws 
    DuplicateSpecieNameException  {
        if(nameSpecie!=null){
            this.confirmNameSpecie(nameSpecie);        
            this.createSpecie(idSpecie,nameSpecie);  
        }
        this.createAnimal(id,name,idSpecie,idHabitat);
    }


//3
    /**
     * Set the habitat of the animal.
     * 
     * @param id animal id
     * @param idNewHabitat animal new id habitat
     * @throws UnknownAnimalKeyException animal id don't exist
     * @throws UnknownHabitatKeyException habitat id don't exist
     */
    public void transferToHabitat(String id,String idNewHabitat) 
    throws UnknownAnimalKeyException,UnknownHabitatKeyException   {
        this.existIdAnimal(id);
        this.existIdHabitat(idNewHabitat);
        this.getHabitats().get(this.getAnimals().get(id).
        getIdHabitat()).removeAnimal(this.getAnimals().get(id));
        this.getAnimals().get(id).setIdHabitat(idNewHabitat);
        this.completeInformationInHabitats(this.getAnimals().get(id));
    }
    

//4
    /**
     * Show animal satisfaction.
     * 
     * @param id animal id
     * @return the satisfaction of the animal
     * @throws UnknownAnimalKeyException animal id don't exist
     */
    public int showSatisfactionOfAnimal(String id) throws 
    UnknownAnimalKeyException{
        this.existIdAnimal(id);
        Animal animal =this.getAnimals().get(id);
        return Math.round(satisfactionAnimal(animal));
    }




    /**********************/
    /*  Menu Funcionário  */
    /**********************/


//1
    /**
     * Show all employee that we have.
     * @return a collection of employee
     */
    public Collection<Employee> showAllEmployees(){
        return Collections.unmodifiableCollection(_employees.
        values());
    }


//2
    /**
     * See if can create a new employee and creates.
     * 
     * @param id employee id
     * @param name employee na
     * @param type employee type
     * @throws DuplicateEmployeeKeyException employee already exists
     */
    public void registerEmployee(String id,String name,String type) 
    throws DuplicateEmployeeKeyException{
        this.confirmIdEmployee(id);
        this.createEmployee(id,name,type,null);
    }


//3
    /**
     * Adds an employee responsablity.
     * 
     * @param id employee id
     * @param responsability specie/habitat id
     * @throws NoResponsibilityException specie/habitat don't exist
     * @throws UnknownEmployeeKeyException employee key don't exist
     */
    public void addResponsability(String id,String responsability) 
    throws NoResponsibilityException , UnknownEmployeeKeyException{
        this.existIdEmployee(id);
        if(getVeterinarians().containsKey(id) && !getVeterinarians().
        get(id).getIdSpeciesResponsability().containsKey(
            responsability)){
            this.confirmResponsability(getVeterinarians().get(id),
            responsability);
        }
        else if(getKeepers().containsKey(id) && !getKeepers().get(id).
        getIdHabitatResponsability().containsKey(responsability))
            this.confirmResponsability(getKeepers().get(id),
            responsability);
    }


//4
    /**
     * Take away the responsability from the employee.
     * 
     * @param id employee id
     * @param responsability specie/habitat id
     * @throws NoResponsibilityException specie/habitat don't exist
     * @throws UnknownEmployeeKeyException employee key don't exist
     */
    public void removeResponsability(String id,String responsability) 
    throws NoResponsibilityException , UnknownEmployeeKeyException{
        this.existIdEmployee(id);
        if(getVeterinarians().containsKey(id)){
            this.remResponsability(getVeterinarians().get(id),
            responsability);
        }
        else if(getKeepers().containsKey(id))
            this.remResponsability(getKeepers().get(id),
            responsability);
    }


//5
    /**
     * Show employee satisfaction.
     * 
     * @param id employee id
     * @return the satisfaction of the employee
     * @throws UnknownEmployeeKeyException employee id don't exist
     */
    public int showSatisfactionOfEmployee(String id) throws 
    UnknownEmployeeKeyException{
        this.existIdEmployee(id);
        if(getVeterinarians().containsKey(id))
            return Math.round(getVeterinarians().get(id).
            satisfaction());
        else
            return Math.round(getKeepers().get(id).satisfaction());
    }




    /******************/
    /*  Menu Habitat  */
    /******************/


//1    
    /**
     * Show all habitats that we have.
     * @return a collection of habitat
     */
    public Collection<Habitat> showAllHabitats(){
        return Collections.unmodifiableCollection(_habitats.values());
    }


//2
    /**
     * See if can create a new habitat and creates.
     * 
     * @param id habitat id
     * @param name habitat name
     * @param area habitt area
     * @throws DuplicateHabitatKeyException habitat id already exist
     */
    public void registerHabitat(String id,String name,int area) throws
     DuplicateHabitatKeyException{
        this.confirmIdHabitat(id);
        this.createHabitat(id,name,area);
    }


//3
    /**
     * Change the area of the habitat.
     * 
     * @param id habitat id
     * @param newArea habitat new area
     * @throws UnknownHabitatKeyException habitat id don't exist
     */
    public void changeHabitatArea(String id,int newArea) throws
    UnknownHabitatKeyException{
        this.existIdHabitat(id);
        this.getHabitats().get(id).setArea(newArea);
    }


//4
    /**
     * Change the influence of the specie in the habitat.
     * 
     * @param idHabitat habitat id
     * @param idSpecie specie id
     * @param influence new influence
     * @throws UnknownHabitatKeyException habitat id don't exist
     * @throws UnknownSpeciesKeyException specie id don't exist
     */
    public void changeHabitatInfluence(String idHabitat,String 
    idSpecie,String influence) throws UnknownHabitatKeyException,
     UnknownSpeciesKeyException{
        this.existIdHabitat(idHabitat);
        this.existIdSpecie(idSpecie);
        this.getHabitats().get(idHabitat).setSpecieInfluence(idSpecie
        ,influence);
    }


//5
    /**
     * See if can create a new tree in the habitat.
     * 
     * @param idHabitat habitat id
     * @param idTree tree id
     * @param nameTree tree name
     * @param ageTree tree age
     * @param difficultyClean tree difficultyClean
     * @param treeType tree treeType
     * @return a tree created
     * @throws DuplicateTreeKeyException tree id already exist
     * @throws UnknownHabitatKeyException habitat id don't exists
     */
    public String addTreeToHabitat(String idHabitat,String idTree,
    String nameTree,int ageTree,int difficultyClean,String treeType) 
    throws DuplicateTreeKeyException,UnknownHabitatKeyException{
        this.confirmIdTree(idTree);
        this.existIdHabitat(idHabitat);
        this.createTree(idTree,nameTree,ageTree,
        difficultyClean,treeType,idHabitat);
        return getTrees().get(idTree).toString();
    }


//6
    /**
     * Show all trees that we have in the habitat.
     * @param id habitat id
     * @return a collection of trees
     * @throws UnknownHabitatKeyException habitat id don't exist
     */
    public Collection<Tree> showAllTreesInHabitat(String id) throws
     UnknownHabitatKeyException{
        this.existIdHabitat(id);
        return Collections.unmodifiableCollection(getHabitats().
        get(id).getTrees().values());
    }
    



    /*****************/
    /*  Menu Vacina  */
    /*****************/  
    

//1
    /**
     * Show all vaccines that we have.
     * @return a collection of vaccine
     */
    public Collection<Vaccine> showAllVaccines(){
        return Collections.unmodifiableCollection(_vaccines.values());
    }

    
//2
    /**
     * See if can create a new vaccine and creates.
     * 
     * @param id vaccine id
     * @param name vaccine name
     * @param list vaccine list of species
     * @throws DuplicateVaccineKeyException vaccine id already exist  
     * @throws UnknownSpeciesKeyException specie id don't exist
     */
    public void registerVaccine(String id,String name,String list) 
    throws DuplicateVaccineKeyException, UnknownSpeciesKeyException{
        this.confirmIdVaccine(id);
        this.existAllListSpecies(list);
        this.createVaccine(id,name,list);
    }


    /**
     * See what the specie in the list of species of vaccine that not exist.
     * @param list vaccine list of species
     * @return specie that not exist
     */
    public String specieNotExist(String list){
        String [] idSpecies=list.split(",");
        for(String idSpecie:idSpecies)
            if (!getSpeciesId().containsKey(idSpecie))
                return idSpecie; 
        return null;
    }


//3
    /**
     * Confirms if the veterinarian can vaccinate the animal with vaccine, and if it's possible vaccinate the animal.
     * 
     * @param idVaccine vaccine id
     * @param idVeterinarian veterinarian id
     * @param idAnimal animal id
     * @throws UnknownVaccineKeyException vaccine key don't exist
     * @throws UnknownAnimalKeyException animal key don't exist
     * @throws UnknownVeterinarianKeyException veterinarian key don't exist
     * @throws VeterinarianNotAuthorizedException veterinarian don't autorized to vaccine the animal
     * @throws WrongVaccineException specie id don't exist in the list of species of vaccine
     */
    public void vaccinateAnimal(String idVaccine,String idVeterinarian
    ,String idAnimal) 
    throws UnknownVaccineKeyException, UnknownAnimalKeyException, 
    UnknownVeterinarianKeyException, 
    VeterinarianNotAuthorizedException, WrongVaccineException{
        this.existIdVaccine(idVaccine);
        this.existIdAnimal(idAnimal);
        this.existIdVeterinarian(idVeterinarian);
        this.veterinarianAuthorized(idVeterinarian,idAnimal);
        this.typeDamage(idVaccine,idVeterinarian,idAnimal);
    }


    /**
     * Get a specie id of animal
     * 
     * @param idAnimal animal id
     * @return the specie id
     */
    public String getIdOfSpecie(String idAnimal){
        return getAnimals().get(idAnimal).getIdSpecies();
    }


//4
    /**
     * Show all vaccinations the animals has received.
     * 
     * @return a collection of RegistVaccinatian
     */
    public Collection<RegistVaccinatian> showVaccinations(){
        return Collections.unmodifiableCollection(_registVaccinatian);
    }




    /*******************/
    /*  Menu Consulta  */
    /*******************/  


//1
    /**
     * Show all animals in the habitat.
     * 
     * @return a collection of Animal
     * @throws UnknownHabitatKeyException habitat key don't exist
     */
    public Collection<Animal> showAnimalsInHabitat(String idHabitat) 
    throws UnknownHabitatKeyException{
        this.existIdHabitat(idHabitat);
        Habitat habitat=_habitats.get(idHabitat);
        return Collections.unmodifiableCollection(habitat.getAnimals()
        .values());
    }


//2
    /**
     * Show all vaccinations the animal has received
     * 
     * @return a collection of RegistVaccinatian
     * @throws UnknownAnimalKeyException animal key don't exist
     */
    public Collection<RegistVaccinatian> showMedicalActsOnAnimal(
        String idAnimal) throws UnknownAnimalKeyException{
        this.existIdAnimal(idAnimal);
        Animal animal=getAnimals().get(idAnimal);
        return Collections.unmodifiableCollection(animal.
        getRegistVaccinatian());
    }


//3    
    /**
    * Show all vaccinations the veterinarian has given.
    * 
    * @return a collection of RegistVaccinatian
    * @throws UnknownVeterinarianKeyException veterinary key don't exist
    */
    public Collection<RegistVaccinatian> showMedicalActsByVeterinarian(
        String idVeterinarian) throws UnknownVeterinarianKeyException{
        this.existIdVeterinarian(idVeterinarian);
        Veterinarian veterinarian=getVeterinarians().get(
            idVeterinarian);
        return Collections.unmodifiableCollection(veterinarian.
        getRegistVaccinatian());
    }


//4
    /**
     * Show all vaccinations that have had damage to the animals.
     * 
     * @return a collection of RegistVaccinatian
     */
    public Collection<RegistVaccinatian> showWrongVaccinations(){
        return Collections.unmodifiableCollection(
            _niglecenceRegistVaccinatian);
    }





    /*************************/
    /*  Usado em ImportFile  */
    /*************************/


    /**
     * Read text input file and create domain entities.
     *
     * @param filename name of the text input file
     * @throws ImportFileException if we have a fail when import the file
     */
    void importFile(String filename) throws ImportFileException {
	    try (BufferedReader s = new 
        BufferedReader(new FileReader(filename))) {
            String line;
            while ((line=s.readLine()) != null)
                importLineFields(line.split("\\|"));
        } catch (IOException | UnrecognizedEntryException e) {
            throw new ImportFileException(filename, e);
        }
    }


    /**
     * See what type of input it is.
     * 
     * @param fields
     * @throws UnrecognizedEntryException if the entry specification is not recognized
     */
    private void importLineFields(String[] fields) 
    throws UnrecognizedEntryException {
        switch ((fields[0])) {
            case "ESPÉCIE" ->this.importSpecie(fields);
            case "ÁRVORE" ->this.importTree(fields);
            case "HABITAT" ->this.importHabitat(fields);                
            case "ANIMAL" ->this.importAnimal(fields);
            case "TRATADOR" ->this.importKeeper(fields);
            case "VETERINÁRIO" ->this.importVeterinarian(fields);
            case "VACINA" ->this.importVaccine(fields);

            default -> throw new 
            UnrecognizedEntryException(String.join("|",fields));
        }
    }


    /**
     * Insert a specie.
     * 
     * @param fields
     * @throws UnrecognizedEntryException if the entry specification is not recognized
     */
    private void importSpecie(String[] fields) throws 
    UnrecognizedEntryException{
        if (fields.length!=3)
            throw new 
            UnrecognizedEntryException(String.join("|",fields));
        try{
            this.confirmIdSpecie(fields[1]);
            this.confirmNameSpecie(fields[2]);
            this.createSpecie(fields[1],fields[2]);
        }catch(DuplicateSpecieKeyException e){
            throw new 
            UnrecognizedEntryException(String.join("|",fields));
        }
        catch(DuplicateSpecieNameException e){
            throw new 
            UnrecognizedEntryException(String.join("|",fields));
        }
        createSpecie(fields[1],fields[2]);
        setChanged(true);
        
    }


    /**
     * Insert a tree.
     * 
     * @param fields
     * @throws UnrecognizedEntryException if the entry specification is not recognized
     */
    private void importTree(String[] fields)
    throws UnrecognizedEntryException{
        if (fields.length!=6 || !this.confirmTreeType(fields[5]))
            throw new 
            UnrecognizedEntryException(String.join("|",fields));
        try {
            this.confirmIdTree(fields[1]);
            this.createTree(fields[1], fields[2], 
            Integer.parseInt(fields[3]), Integer.parseInt(fields[4]), 
            fields[5],"");
        }
        catch(DuplicateTreeKeyException e){
            throw new UnrecognizedEntryException(String.join("|",
            fields));
        }
    }


    /**
     * Insert a habitat.
     * 
     * @param fields
     * @throws UnrecognizedEntryException if the entry specification is not recognized
     */
    private void importHabitat(String[] fields) 
    throws UnrecognizedEntryException{
        if ((fields.length!=4 && fields.length!=5))
            throw new 
            UnrecognizedEntryException(String.join("|",fields) );
        try {
            this.confirmIdHabitat(fields[1]);
            String [] trees=new String[0];
            if (fields.length==5){
                trees=fields[4].split(",");
                for (String tree:trees)
                    this.existIdTree(tree);
            }
            this.createHabitat(fields[1], fields[2], 
            Integer.parseInt(fields[3]));
            this.putTreeInHabitat(fields[1],trees);
        }
        catch(DuplicateHabitatKeyException e){
            throw new 
            UnrecognizedEntryException(String.join("|",fields));
        }
        catch(UnknownTreeKeyException e){
            throw new 
            UnrecognizedEntryException(String.join("|",fields));
        }
    }


    /**
     * Insert a animal.
     * 
     * @param fields
     * @throws UnrecognizedEntryException if the entry specification is not recognized
     */
    private void importAnimal(String[] fields) 
    throws UnrecognizedEntryException{
        if (fields.length!=5)
            throw new 
            UnrecognizedEntryException(String.join("|",fields));
        try {
            this.confirmIdAnimal(fields[1]);
            this.existIdHabitat(fields[4]);
            this.existIdSpecie(fields[3]);
            this.createAnimal(fields[1], fields[2], fields[3], 
            fields[4]);
        }
        catch(DuplicateAnimalKeyException e){
            throw new 
            UnrecognizedEntryException(String.join("|",fields));
        }
        catch(UnknownHabitatKeyException e){
            throw new 
            UnrecognizedEntryException(String.join("|",fields));
        }
        catch(UnknownSpeciesKeyException e){
            throw new 
            UnrecognizedEntryException(String.join("|",fields));
        }
    }


    /**
     * Insert a keeper.
     * 
     * @param fields
     * @throws UnrecognizedEntryException if the entry specification is not recognized
     */
    private void importKeeper(String[] fields) 
    throws UnrecognizedEntryException{
        if (fields.length!=3 && fields.length!=4)
            throw new 
            UnrecognizedEntryException(String.join("|",fields));
        try {
            this.confirmIdEmployee(fields[1]);
            String [] habitats=new String[0];
            if (fields.length==4){
                habitats=fields[3].split(",");
                for (String habitat:habitats)
                    this.existIdHabitat(habitat);
                this.createEmployee(fields[1], fields[2], "TRT",
                fields[3]);
            }
            else
               this.createEmployee(fields[1], fields[2], "TRT",
               null); 
        }
        catch(DuplicateEmployeeKeyException e){
            throw new UnrecognizedEntryException(String.join("|",
            fields));
        }
        catch(UnknownHabitatKeyException e){
            throw new UnrecognizedEntryException(String.join("|",
            fields));
        }
    }


    /**
     * Insert a veterinarian.
     * 
     * @param fields
     * @throws UnrecognizedEntryException if the entry specification is not recognized
     */
    private void importVeterinarian(String[] fields) 
    throws UnrecognizedEntryException{
        if (fields.length!=3 && fields.length!=4)
            throw new 
            UnrecognizedEntryException(String.join("|",fields));
        try {
            this.confirmIdEmployee(fields[1]);
            String [] species=new String[0];
            if (fields.length==4){
                species=fields[3].split(",");
                for (String specie:species)
                    this.existIdSpecie(specie);
                this.createEmployee(fields[1], fields[2], "VET",
                fields[3]);
            }
            else 
                this.createEmployee(fields[1], fields[2], "VET",
                null);
        }
        catch(DuplicateEmployeeKeyException e){
            throw new 
            UnrecognizedEntryException(String.join("|",fields));
        }
        catch(UnknownSpeciesKeyException e){
            throw new 
            UnrecognizedEntryException(String.join("|",fields));
        }
    }


    /**
     * Insert a vaccine.
     * 
     * @param fields
     * @throws UnrecognizedEntryException if the entry specification is not recognized
     */
    private void importVaccine(String[] fields) 
    throws UnrecognizedEntryException{
        if (fields.length!=3 && fields.length!=4)
            throw new 
            UnrecognizedEntryException(String.join("|",fields));
        try {
            this.confirmIdVaccine(fields[1]);
            String [] species=new String[0];
            if (fields.length==4){
                species=fields[3].split(",");
                for (String specie:species)
                    this.existIdSpecie(specie);
                this.createVaccine(fields[1], fields[2],fields[3]);
            }
            else
                this.createVaccine(fields[1], fields[2],"");       
        }
        catch(DuplicateVaccineKeyException e){
            throw new 
            UnrecognizedEntryException(String.join("|",fields));
        }
        catch(UnknownSpeciesKeyException e){
            throw new 
            UnrecognizedEntryException(String.join("|",fields));
        }
    }
}