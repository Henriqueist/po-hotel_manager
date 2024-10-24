package hva.app.animal;


import hva.Hotel;
import hva.app.exceptions.DuplicateAnimalKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.DuplicateSpecieNameException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoRegisterAnimal extends Command<Hotel> {

    

    DoRegisterAnimal(Hotel receiver) {
        super(Label.REGISTER_ANIMAL, receiver);
        addStringField("id",Prompt.animalKey());
        addStringField("name",Prompt.animalName());
        addStringField("idSpecie",Prompt.speciesKey());   
        addStringField("idHabitat",
        hva.app.habitat.Prompt.habitatKey());
    }

    @Override
    protected final void execute() throws CommandException {
        try{
            _receiver.checkInformationInAnimal(stringField("id"),
            stringField("idHabitat"));

            String nameSpecie = _receiver.hasSpeciesId(
                stringField("idSpecie")) ? null : Form.requestString(
                    Prompt.speciesName());
            _receiver.registerAnimal(stringField("id"),
            stringField("name"),stringField("idSpecie"),
            stringField("idHabitat"),nameSpecie);            
        }catch(hva.exceptions.DuplicateAnimalKeyException e){
            throw new DuplicateAnimalKeyException(stringField("id"));
        }
        catch(hva.exceptions.UnknownHabitatKeyException e){
            throw new UnknownHabitatKeyException(stringField(
                "idHabitat"));
        }catch(DuplicateSpecieNameException e){
            _display.popup("O nome da espécie já existe.");
        }
    }

  
    
}

