package hva.app.search;

import hva.Hotel;
import hva.app.exceptions.UnknownAnimalKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoShowMedicalActsOnAnimal extends Command<Hotel> {

    DoShowMedicalActsOnAnimal(Hotel receiver) {
        super(Label.MEDICAL_ACTS_ON_ANIMAL, receiver);
        addStringField("id", hva.app.animal.Prompt.animalKey());
    }

    @Override
    protected void execute() throws CommandException {
        try{
            _display.popup(_receiver.showMedicalActsOnAnimal(
                stringField("id")));
        }catch (hva.exceptions.UnknownAnimalKeyException e){
            throw new UnknownAnimalKeyException(stringField("id"));
        }    
    }

}
