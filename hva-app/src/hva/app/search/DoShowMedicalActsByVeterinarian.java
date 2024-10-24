package hva.app.search;

import hva.Hotel;
import hva.app.exceptions.UnknownVeterinarianKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoShowMedicalActsByVeterinarian extends Command<Hotel> {

    DoShowMedicalActsByVeterinarian(Hotel receiver) {
        super(Label.MEDICAL_ACTS_BY_VET, receiver);
        addStringField("id", hva.app.employee.Prompt.employeeKey());
    }

    @Override
    protected void execute() throws CommandException {
        try{
            _display.popup(_receiver.showMedicalActsByVeterinarian(
                stringField("id")));
        }catch (hva.exceptions.UnknownVeterinarianKeyException e){
            throw new UnknownVeterinarianKeyException(stringField(
                "id"));
        }      
    }

}
