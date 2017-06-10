/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package products;

import interfaces.for_products;

import java.io.Serializable;

/**
 * Defines the specific type of products called
 * alcoholic drinks, which are in essence drinks, but
 * also have a specific ammount of alcohol
 * @author pauliuskalv
 */
public class alc_drink extends drink implements for_products.both, Serializable {
    private double alcohol_content;

    /**
     *
     * @param price
     * Defines the product's price
     * @param title
     * Defines the product's name
     * @param alcohol_content
     * Defines the product's alcohol content
     * @param volume
     * Defines the product's volume ( in liters )
     */
    public alc_drink(double price , String title , double alcohol_content, double volume ) {
        this.alcohol_content = alcohol_content;
        try {
            setPrice( price ) ;
        } catch (Exception e) {
        }
        setTitle( title ) ;
        this.volume = volume ;
    }

    /**
     * Creates a new Alcoholic drink with both of
     * the parameters set to 0
     */
    public alc_drink( ) {
        this.alcohol_content = 0;
        try {
            setPrice(0);
        } catch (Exception e) {
        }
        setTitle("");
        this.volume = 0;
    }

    /**
     * Changes the current product's alcohol
     * content
     * @param alcohol_content
     * Defines the alcohol content for the specific product
     */
    public void change_alcohol_content(double alcohol_content ) {
        this.alcohol_content = alcohol_content ;
    }

    /**
     * Prints out both of the product's information
     * to stdout
     */
    public void println ( ) {
        System.out.println ( "Pavadinimas: " + getTitle() ) ;
        System.out.println ( "Kaina: " + getPrice() ) ;
        System.out.println ( "Sena price: " + getOldPrice( ) ) ;
        System.out.println ( "Talpa: " + getVolume( ) ) ;
        System.out.println ( "Stiprumas: " + getAlcoholContent( ) ) ;
    }

    /**
     * Returns the current alcohol content
     * @return
     * The current alcohol content
     */
    public double getAlcoholContent( ) {
        return this.alcohol_content;
    }

    /**
     * Returns the current volume
     * @return
     * The current drink volume
     */
    public double getVolume( ) {
        return this.volume;
    }
}
