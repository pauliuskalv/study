/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package products;

import java.io.Serializable;

/**
 * Defines the specific type of products called food,
 * which are products, but hold an extra value expiry,
 * which defines the expiry of the specific food ( in hours )
 * @author pauliuskalv
 */
public class food extends product implements Serializable {
    private int expiry;
    public food( ) {
        this.expiry = 0 ;
        try {
            this.setPrice( 0 ) ;
        } catch (Exception e) {
        }
        this.setTitle( "" ) ;
    }
    public food(double kaina , String pavadinimas , int expiry) {
        try {
            setPrice( kaina ) ;
        } catch (Exception e) {
        }
        setTitle( pavadinimas ) ;
        this.expiry = expiry;
    }
    public void setExpiry(int galiojimas ) {
        this.expiry = galiojimas ;
    }
    public int getExpiry( ) {
        return this.expiry;
    }
}
