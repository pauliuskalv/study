/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package products;
import interfaces.for_products;
import exceptions.exception;

import java.io.Serializable;

/**
 * Defines the most basic product, which can
 * hold only the most basic information, which
 * is the name of the product and it's price
 * @author pauliuskalv
 */

public class product implements for_products.both, Serializable {
    protected String title = "" ;
    protected double price;
    protected double old_price;
    protected int quantity = 0 ;
    private short sukurta = 0 ;
    public void setPrice(double kaina ) throws exception {
        if ( kaina == this.price) {
            throw new exception( "Kaina, i kuria bandoma keisti, sutampa su esama price!\n" ) ;
        }
        else {
            this.old_price = this.price;
            this.price = kaina ;
        }
    }
    public void setQuantity(int kiekis ) {
        this.quantity = kiekis ;
    }
    public int getQuantity( ) {
        return this.quantity;
    }
    public void setTitle(String pavadinimas ) {
        this.title = pavadinimas ;
    }
    public double getPrice( ) {
        return this.price;
    }
    public double getOldPrice( ) { return this.old_price; }
    public String getTitle( ) {
        return this.title;
    }
    public product(String title, double price) {
        this.title = title;
        this.price = price;
        this.old_price = 0 ;
        //sukurimoData = sukurimoData.getTime ( ) ;
        this.sukurta = 1 ;
    }
    public product( ) {
        this.price = 0 ;
        this.old_price = 0 ;
    }
    public void println ( ) {
        if ( this.title == "" ) {
            System.out.println ( "Dar nepilnai sukurta prekė!" ) ;
            return ;
        }
        System.out.println ( "Pasirinkta prekė: " + this.title) ;
        System.out.println ( "Dabartine price: " + this.price + " " ) ;
        System.out.println ( "Sena price: " + this.old_price + " " ) ;
    }
    public double difference( ) {
        return this.price - this.old_price;
    }
}
