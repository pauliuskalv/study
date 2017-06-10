package interfaces;

import exceptions.exception;

/**
 * Defines the main function for changing the
 * core variables in the objects used in the
 * vending machine
 * @author pauliuskalv
 */
public class for_products {

    /**
     * Defines the interface for printing both
     * of the object's variables to stdout
     */
    public interface forPrinting {
        public abstract void println ( ) ;
    }

    /**
     * Defines the interface for changing the values
     * of core variables in the objects of the
     * vending machine
     */
    public interface forChanging {
        /**
         * Defines the changing of the price
         * @param kaina
         * The price, which is written as the object's
         * new price
         * @throws exception
         * Throws an exception if the new price, which
         * is to be written as the object's new price
         * , is the same as the old price
         */
        public void setPrice(double kaina ) throws exception;

        /**
         * Defines the changing of the title ( name )
         * @param pavadinimas
         * The title ( name ), which is written as the
         * object's new title ( name )
         */
        public void setTitle(String pavadinimas ) ;

        /**
         * Defines the getter function for getting
         * the price of of an object
         * @return
         * The value of the price variable in an object
         */
        public double getPrice( ) ;

        /**
         * Defines the getter function for getting
         * the old price of of an object
         * @return
         * The value of the old_price variable in an object
         */
        public double getOldPrice( ) ;

        /**
         * Defines the getter function for getting the
         * title ( name ) of of an object
         * @return
         * The String, defining the object's title ( name )
         */
        public String getTitle( ) ;
    }

    /**
     * Defines the interface which joins
     * the 2 interfaces together as one
     */
    public interface both extends forPrinting, forChanging {
        // Dar bus papildyta
    }
}