package hva.app.habitat;

import hva.Hotel;
import hva.app.exceptions.UnknownSpeciesKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoChangeHabitatInfluence extends Command<Hotel> {

    DoChangeHabitatInfluence(Hotel receiver) {
        super(Label.CHANGE_HABITAT_INFLUENCE, receiver);
        addStringField("idHabitat",Prompt.habitatKey());  
        addStringField("idSpecie",hva.app.animal.
        Prompt.speciesKey());   
        addOptionField("influence",Prompt.habitatInfluence(),"POS",
        "NEG","NEU");
    }

    @Override
    protected void execute() throws CommandException {
        try{
            _receiver.changeHabitatInfluence(stringField("idHabitat"), 
            stringField("idSpecie"), stringField("influence"));
        }catch(hva.exceptions.UnknownSpeciesKeyException e){
            throw new UnknownSpeciesKeyException(stringField(
                "idSpecie"));
        }
        catch(hva.exceptions.UnknownHabitatKeyException e){
            throw new UnknownHabitatKeyException(stringField(
                "idHabitat"));
        }
    }

}
