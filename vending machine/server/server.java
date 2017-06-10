package server;

/**
 * Created by pauliuskalv on 5/16/17.
 */
import products.* ;

import javax.crypto.Cipher;
import java.io.*;
import java.util.Vector ;

/**
 * This object is, in essence, a backend for
 * the vending machine. It stores both of the vending
 * machine products of both 5 different types and
 * contains them in dynamic arrays.
 */
public class server implements Serializable , Runnable {

    /**
     * Stores the current server password
     */
    private String password = "" ;

    /**
     * A vector, which contains both objects
     * of alc_drink type
     */
    private Vector alc_drinks = null ;
    /**
     * A vector, which contains both objects
     * of clothes type
     */
    private Vector clothes = null ;
    /**
     * A vector, which contains both objects
     * of drink type
     */
    private Vector drinks = null ;
    /**
     * A vector, which contains both objects
     * of food type
     */
    private Vector food = null ;
    /**
     * A vector, which contains both objects
     * of product type
     */
    private Vector products = null ;

    /**
     * Defines the specific capacity of the
     * server
     */
    private int capacity ;

    /**
     * Saves the current server type class
     * to a local directory /backup and saves
     * data to a file called "server.ser"
     */
    public void save ( ) {
        Thread gija = new Thread ( this ) ;
        gija.start ( ) ;
    }

    public void run ( ) {
        File failas = new File ( System.getProperty ( "user.dir" ) + "/backup" ) ;
        failas.mkdir ( ) ;
        failas = new File ( System.getProperty ( "user.dir" ) + "/backup/server.ser" ) ;
        try {
            if ( ! failas.exists() ) {
                failas.createNewFile ( ) ;
            }
            FileOutputStream fileOut = new FileOutputStream ( failas , false ) ;
            ObjectOutputStream out = new ObjectOutputStream ( fileOut ) ;
            out.writeObject ( this ) ;
            out.close();
            fileOut.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a alc_drink type product to the active server
     * @param price
     * Defines the specific price
     * for the new product of alc_drink type
     * @param title
     * Defines the specific title
     * ( name ) for the new product of alc_drink type
     * @param alcohol_content
     * Defines the specific alcohol content
     * for the new product of alc_drink type
     * @param volume
     * Defines the specific volume ( in liters )
     * for the new product of alc_drink type
     * @return
     * <p> Returns true if addition of the product was successful </p>
     * <p> Returns false if the specific capacity of the vending machine was reached </p>
     */
    public boolean add_alc_drink(double price , String title , double alcohol_content , double volume ) {
        if ( this.capacity == quantity() ) {
            return false ;
        }
        this.alc_drinks.add ( new alc_drink( price , title , alcohol_content , volume ) ) ;
        return true ;
    }

    /**
     * Adds the clothes type product to the active server
     * @param price
     * Defines the specific price of the new
     * product of clothes type
     * @param title
     * Defines the specific title ( name ) of the new
     * product of clothes type
     * @param size
     * Defines the specific size of the new
     * product of clothes type
     * @return
     * <p> Returns true if addition of the product was successful </p>
     * <p> Returns false if the specific capacity of the vending machine was reached </p>
     */
    public boolean add_clothes(double price , String title , int size ) {
        if ( this.capacity == quantity() ) {
            return false ;
        }
        this.clothes.add ( new clothes( price , title , size ) ) ;
        return true ;
    }

    /**
     * Adds the drink type product to the active server
     * @param price
     * Defines the specific price of the new
     * product of drink type
     * @param title
     * Defines the specific title ( name ) of the new
     * product of drink type
     * @param volume
     * Defines the specific volume ( in liters ) of the new
     * product of drink type
     * @return
     * <p> Returns true if addition of the product was successful </p>
     * <p> Returns false if the specific capacity of the vending machine was reached </p>
     */
    public boolean add_drink(double price , String title , double volume ) {
        if ( this.capacity == quantity() ) {
            return false ;
        }
        this.drinks.add ( new drink( price , title , volume ) ) ;
        return true ;
    }

    /**
     * Adds the food type product to the active server
     * @param price
     * Defines the specific price of the new product
     * of food type
     * @param title
     * Defines the specific title of the new product
     * of food type
     * @param expiry
     * Defines the specific expiry ( in hours ) of the new product
     * of food type
     * @return
     * <p> Returns true if addition of the product was successful </p>
     * <p> Returns false if the specific capacity of the vending machine was reached </p>
     */
    public boolean add_food(double price , String title , int expiry ) {
        if ( this.capacity == quantity() ) {
            return false ;
        }
        this.food.add ( new food( price , title , expiry ) ) ;
        return true ;
    }

    /**
     * Adds the product type product to the server
     * @param title
     * Defines the specific title ( name ) of the new product
     * of product type
     * @param price
     * Defines the specific price of the product
     * of product type
     * @return
     * <p> Returns true if addition of the product was successful </p>
     * <p> Returns false if the specific capacity of the vending machine was reached </p>
     */
    public boolean add_product(String title, double price ) {
        if ( this.capacity == quantity() ) {
            return false ;
        }
        this.products.add ( new product( title , price ) ) ;
        return true ;
    }

    /**
     * A getter function, which returns the current integer
     * defining the quantity of pruducts in the server
     * @return
     * Returns the current quantity of products
     * in the active server as an integer
     */
    public int quantity ( ) {
        return alc_drinks.size() + clothes.size() + drinks.size() + food.size() + products.size() ;
    }

    /**
     * A getter function, which returns a String type array
     * , containing both of the products names
     * @return
     * Returns a String type array, containing both of the titles ( names )
     * of the products currently in the server
     */
    public String[] getList ( ) {
        int kiekis = alc_drinks.size() + clothes.size() + drinks.size() + food.size() + products.size() ;
        String [] sarasas = new String [ kiekis ] ;
        int j = 0 ;
        for (int i = 0; i < alc_drinks.size() ; i ++ ) {
            alc_drink laik = (alc_drink) alc_drinks.get ( i ) ;
            sarasas [ j ] = new String ( laik.getTitle() ) ;
            j ++ ;
        }
        for (int i = 0; i < clothes.size() ; i ++ ) {
            clothes laik = (clothes) clothes.get ( i ) ;
            sarasas [ j ] = laik.getTitle( ) ;
            j ++ ;
        }
        for (int i = 0; i < drinks.size() ; i ++ ) {
            drink laik = (drink) drinks.get ( i ) ;
            sarasas [ j ] = laik.getTitle( ) ;
            j ++ ;
        }
        for (int i = 0; i < food.size() ; i ++ ) {
            food laik = (food) food.get ( i ) ;
            sarasas [ j ] = laik.getTitle( ) ;
            j ++ ;
        }
        for (int i = 0; i < products.size() ; i ++ ) {
            product laik = (product) products.get ( i ) ;
            sarasas [ j ] = laik.getTitle( ) ;
            j ++ ;
        }
        return sarasas ;
    }

    /**
     * A function, which allows the change of a password of the
     * current server
     * @param password
     * Defines the password, which is to replace the
     * current server password
     */
    public void setPassword(String password ) {
        password
        this.password = password ;
    }

    /**
     * A function, which returns the current server's password
     * @return
     * Returns the current server password
     */
    public String getPassword( ) {
        return this.password;
    }

    /**
     * A getter function, which returns the appropriate product
     * from the server
     * @param index
     * Defines the integer, which defines a specific product
     * currently in the server
     * @return
     * <p> Returns an object if such a product exists in the current server </p>
     * <p> Return null if the index was over or under the current quantity of products in the server </p>
     */
    public Object getPreke ( int index ) {
        if ( index == -1 ) {
            return null ;
        }
        int kiekis = alc_drinks.size () ;
        if ( kiekis > index ) {
            return alc_drinks.get ( index ) ;
        }
        kiekis += clothes.size () ;
        if ( kiekis > index ) {
            return clothes.get ( index - alc_drinks.size () ) ;
        }
        kiekis += drinks.size () ;
        if ( kiekis > index ) {
            return drinks.get ( index - alc_drinks.size () - clothes.size () ) ;
        }
        kiekis += food.size () ;
        if ( kiekis > index ) {
            return food.get ( index - alc_drinks.size () - clothes.size () - drinks.size () ) ;
        }
        kiekis += products.size () ;
        if ( kiekis >= index ) {
            return products.get ( index - alc_drinks.size () - clothes.size () - drinks.size () - food.size () ) ;
        }
        return null ;
    }

    /**
     * A function, which removes a particular product,
     * defined by a integer, from the server
     * @param index
     * Defines the integer, which defines a particular product
     * in the server
     */
    public void removeProduct(int index ) {
        int kiekis = alc_drinks.size () ;
        if ( kiekis > index ) {
            alc_drinks.remove ( index ) ;
            return ;
        }
        kiekis += clothes.size () ;
        if ( kiekis > index ) {
            clothes.remove ( index - alc_drinks.size () ) ;
            return ;
        }
        kiekis += drinks.size () ;
        if ( kiekis > index ) {
            drinks.remove ( index - alc_drinks.size () - clothes.size () ) ;
            return ;
        }
        kiekis += food.size () ;
        if ( kiekis > index ) {
            food.remove ( index - alc_drinks.size () - clothes.size () - drinks.size () ) ;
            return ;
        }
        kiekis += products.size () ;
        if ( kiekis >= index ) {
            products.remove ( index - alc_drinks.size () - clothes.size () - drinks.size () - food.size () ) ;
            return ;
        }
    }

    /**
     * A function, which checks if a product
     * exists in the server
     * @param to_check
     * A String type argument, which is checked against both of
     * the products in the current server
     * @return
     * <p> Returns true if such a product exists in the server </p>
     * <p> Returns false otherwise </p>
     */
    public boolean exists(String to_check ) {
        for (int i = 0; i < alc_drinks.size ( ) ; i ++ ) {
            alc_drink laik = (alc_drink) alc_drinks.get ( i ) ;
            if ( laik.getTitle().equals(to_check) ) {
                return true ;
            }
        }
        for (int i = 0; i < clothes.size() ; i ++ ) {
            clothes laik = (clothes) clothes.get ( i ) ;
            if ( laik.getTitle().equals(to_check) ) {
                return true ;
            }
        }
        for (int i = 0; i < drinks.size() ; i ++ ) {
            drink laik = (drink) drinks.get ( i ) ;
            if ( laik.getTitle().equals(to_check) ) {
                return true ;
            }
        }
        for (int i = 0; i < food.size() ; i ++ ) {
            food laik = (food) food.get ( i ) ;
            if ( laik.getTitle().equals(to_check) ) {
                return true ;
            }
        }
        for (int i = 0; i < products.size() ; i ++ ) {
            product laik = (product) products.get ( i ) ;
            if ( laik.getTitle().equals(to_check) ) {
                return true ;
            }
        }
        return false ;
    }

    /**
     * @param capacity
     * Defines the specific capacity of
     * the vending machine
     */
    public server ( int capacity ) {
        this.capacity = capacity ;
        alc_drinks = new Vector ( ) ;
        clothes = new Vector ( ) ;
        drinks = new Vector ( ) ;
        food = new Vector ( ) ;
        products = new Vector ( ) ;
    }
}
