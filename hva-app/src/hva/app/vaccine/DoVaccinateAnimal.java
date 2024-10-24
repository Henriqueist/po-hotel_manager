package hva.app.vaccine;

import hva.Hotel;
import hva.app.exceptions.UnknownVaccineKeyException;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.app.exceptions.VeterinarianNotAuthorizedException;
import hva.app.exceptions.UnknownVeterinarianKeyException;
import hva.exceptions.WrongVaccineException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoVaccinateAnimal extends Command<Hotel> {

    DoVaccinateAnimal(Hotel receiver) {
        super(Label.VACCINATE_ANIMAL, receiver);
        addStringField("idVaccine",Prompt.vaccineKey());
        addStringField("idVeterinarian",Prompt.veterinarianKey());
        addStringField("idAnimal",hva.app.animal.Prompt.animalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        try{
            _receiver.vaccinateAnimal(stringField("idVaccine"),
            stringField("idVeterinarian"),stringField("idAnimal"));
        }catch(hva.exceptions.UnknownVaccineKeyException e){
            throw new UnknownVaccineKeyException(stringField(
                "idVaccine"));
        }
        catch(hva.exceptions.UnknownAnimalKeyException e){
            throw new UnknownAnimalKeyException(stringField(
                "idAnimal"));
        }
        catch(hva.exceptions.UnknownVeterinarianKeyException e){
            throw new UnknownVeterinarianKeyException(stringField(
                "idVeterinarian"));
        }
        catch(hva.exceptions.VeterinarianNotAuthorizedException e){
            throw new VeterinarianNotAuthorizedException(stringField(
                "idVeterinarian"),_receiver.getIdOfSpecie(stringField(
                    "idAnimal")));
        }
        catch(WrongVaccineException e){
            _display.popup(Message.wrongVaccine(stringField(
                "idVaccine"),stringField("idAnimal")));
        }
    }

}
