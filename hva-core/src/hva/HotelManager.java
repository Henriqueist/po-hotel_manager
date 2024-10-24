package hva;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

import hva.exceptions.ImportFileException;
import hva.exceptions.MissingFileAssociationException;


/**
 * Class that represents the hotel application.
 */
public class HotelManager {

    /**
     * This is the current hotel. 
     * */
    private Hotel _hotel = new Hotel();

    /**  
     * This is the current filename. 
     * */
    private String _filename = null;



    public Hotel getHotel(){
        return _hotel;
    }

    public String getFilename(){
        return _filename;
    }


    /**
     * Checks if there have been changes in the hotel.
     *
     * @return true if the hotel has changes, false otherwise
     */
    public boolean changed(){
        return _hotel.getChanged();
    }

    /**
     * Resets the hotel to its default state.
     */
    public void reset(){
        _hotel = new Hotel();
        _filename = null;
    }

    /**
     * Advances the current season.
     * 
     * @return the new season as an integer
     */
    public int advanceSeason(){
        return _hotel.advanceSeason();
    }

    /**
     * Calculates and returns the sum of satisfaction values for all
     * the animals and employees in the hotel.
     * 
     * @return the total satisfaction value rounded as an integer
     */
    public int showGlobalSatisfaction(){
        return _hotel.showGlobalSatisfaction();
    }



    /**
     * Saves the serialized application's state into the file associated to the current network.
     *
     * @throws FileNotFoundException if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current network does not have a file.
     * @throws IOException if there is some error while serializing the state of the network to disk.
     */
    public void save() throws FileNotFoundException, 
    MissingFileAssociationException, IOException {
        if (_filename == null || _filename.equals(""))
            throw new MissingFileAssociationException();
        if (this.changed()){
            try(ObjectOutputStream oos= new 
            ObjectOutputStream(new BufferedOutputStream(new 
            FileOutputStream(_filename)))){
                oos.writeObject(_hotel);
                _hotel.setChanged(false);
            }
        }
        
    }

    /**
     * Saves the serialized application's state into a new file associated to the current network.
     *
     * @param filename name of the new file to save the serialized application's state
     * 
     * @throws FileNotFoundException if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current network does not have a file.
     * @throws IOException if there is some error while serializing the state of the network to disk.
     */
    public void saveAs(String filename) throws FileNotFoundException, 
    MissingFileAssociationException, IOException {
        _filename = filename;
        save();
    }

    /**
     * Loads the serialized state of the application from the specified file.
     *
     * @param filename the name of the file containing the serialized 
     *                 application's state to load
     * 
     * @throws ClassNotFoundException if the class of a serialized object 
     *         cannot be found
     * * @throws IOException if there is some error while serializing the state of the network to disk.
     */
    public void load(String filename) throws ClassNotFoundException, IOException {
        try(ObjectInputStream object = new ObjectInputStream(
            new BufferedInputStream(new FileInputStream(filename)))) {
            _hotel = (Hotel) object.readObject();
            this._filename=filename;
            _hotel.setChanged(false);
        }
        catch (IOException | ClassNotFoundException e) {
            this.reset();
        }
    }

    /**
     * Read text input file.
     *
     * @param filename name of the text input file
     * 
     * @throws ImportFileException
     */
    public void importFile(String filename)throws ImportFileException{
        _hotel.importFile(filename);
    }
}
