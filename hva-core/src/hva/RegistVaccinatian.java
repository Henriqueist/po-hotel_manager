package hva;

public class RegistVaccinatian {

    private String _idVaccine;
    private String _idVeterinarian;
    private String _idSpecie;


    public RegistVaccinatian(String idVaccine, String idVeterinarian, 
    String idSpecie) {
        _idVaccine = idVaccine;
        _idVeterinarian = idVeterinarian;
        _idSpecie = idSpecie;
    }


    public String getIdVaccine() {
        return _idVaccine;
    }


    public String getIdVeterinarian() {
        return _idVeterinarian;
    }


    public String getIdSpecie() {
        return _idSpecie;
    }

    
    @Override
    public String toString(){
        return "REGISTO-VACINA|"+_idVaccine+"|"+_idVeterinarian+"|"+
        _idSpecie;
    }
}
