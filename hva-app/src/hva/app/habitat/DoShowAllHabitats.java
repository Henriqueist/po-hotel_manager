package hva.app.habitat;

import java.util.Iterator;
import java.util.Map;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import hva.Habitat;
import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoShowAllHabitats extends Command<Hotel> {

    DoShowAllHabitats(Hotel receiver) {
        super(Label.SHOW_ALL_HABITATS, receiver);
    }

    //coleção Habitats
    //for para cada Habitat
    //popup do Object Habitat
    //popup da Collection Trees
    @Override
    protected void execute() {
        for(Habitat habitat: _receiver.showAllHabitats()){
            _display.popup(habitat);
            _display.popup(habitat.trees());
        }
    }
}
