package hva.app.vaccine;

import hva.Hotel;
import hva.app.exceptions.DuplicateVaccineKeyException;
import hva.app.exceptions.UnknownSpeciesKeyException;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoRegisterVaccine extends Command<Hotel> {

    DoRegisterVaccine(Hotel receiver) {
        super(Label.REGISTER_VACCINE, receiver);
        addStringField("id",Prompt.vaccineKey());
        addStringField("name",Prompt.vaccineName());
        addStringField("listSpecies",Prompt.listOfSpeciesKeys());      
    }

    @Override
    protected final void execute() throws CommandException {    
        try{
            _receiver.registerVaccine(stringField("id"),stringField(
                "name"),stringField("listSpecies"));
        }catch(hva.exceptions.DuplicateVaccineKeyException e){
            throw new DuplicateVaccineKeyException(stringField("id"));
        }
        catch(hva.exceptions.UnknownSpeciesKeyException e){
            throw new UnknownSpeciesKeyException(_receiver.
            specieNotExist(stringField("listSpecies")));
        }
    }

}
