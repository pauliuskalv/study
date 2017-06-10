/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package products;

import java.io.Serializable;

/**
 * Defines the specific type of products called clothes,
 * which are products, but also hold an extra value
 * called size, which defines the size of the specific
 * clothes
 * @author pauliuskalv
 */
public class clothes extends product implements Serializable {
    private int size;
    public clothes( ) {
        try {
            super.setPrice( 0 ) ;
        } catch (Exception e) {
            System.out.println ( "" + e ) ;
        }
        setTitle( "" ) ;
        this.size = 0 ;
    }
    public clothes(double price , String title , int size) {
        try {
            setPrice(price);
        } catch (Exception e) {
            System.out.println ( e ) ;
        }
        setTitle(title);
        this.size = size;
    }
    public void setSize(int size ) {
        this.size = size ;
    }
    public int getSize( ) {
        return this.size;
    }
}
