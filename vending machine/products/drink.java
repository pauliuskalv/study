/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package products;

import java.io.Serializable;

/**
 * Defines the specific type of products called drinks,
 * which are products, but also hold an extra value volume,
 * which defines the volume of the drink contained ( in liters )
 * @author pauliuskalv
 */
public class drink extends product implements Cloneable , Serializable {
    double volume;
    public drink( ) {
        this.volume = 0 ;
        setTitle( "" ) ;
        try {
            super.setPrice( 0 ) ;
        }
        catch ( Exception e ) {

        }
    }
    public Object clone ( ) throws CloneNotSupportedException {
        drink laik = new drink( this.price, this.title, this.volume) ;
        return laik ;
    }
    public drink(double kaina , String pavadinimas , double volume) {
        this.volume = volume;
        setTitle( pavadinimas ) ;
        try {
            super.setPrice( kaina ) ;
        }
        catch ( Exception e ) {
        }
    }
    public void println ( ) {
        System.out.println ( "product: " + getTitle() ) ;
        System.out.println ( "Kaina: " + getPrice() ) ;
        System.out.println ( "Talpa: " + this.volume + "\n" ) ;
    }
    public void setVolume(double volume ) {
        this.volume = volume ;
    }
    public double getVolume( ) {
        return this.volume;
    }
}
