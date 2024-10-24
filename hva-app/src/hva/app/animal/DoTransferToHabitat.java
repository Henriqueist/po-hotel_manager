package hva.app.animal;

import hva.Hotel;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoTransferToHabitat extends Command<Hotel> {

    DoTransferToHabitat(Hotel hotel) {
        super(Label.TRANSFER_ANIMAL_TO_HABITAT, hotel);
        addStringField("id",Prompt.animalKey()); 
        addStringField("idNewHabitat",
        hva.app.habitat.Prompt.habitatKey());    
    }

    @Override
    protected final void execute() throws CommandException {
        try{
            _receiver.transferToHabitat(stringField("id"), 
            stringField("idNewHabitat"));
        }catch(hva.exceptions.UnknownAnimalKeyException e){
            throw new UnknownAnimalKeyException(stringField("id"));
        }
        catch(hva.exceptions.UnknownHabitatKeyException e){
            throw new UnknownHabitatKeyException(stringField(
                "idNewHabitat"));
        }
    }
    

}
