package gui;

import products.*;
import server.server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Defines the client's graphical user interface. In
 * it, the user can look trough both of the products
 * and buy the ones that are in stock.
 * <p>
 * Below is a sequence diagram, showing how the user
 * interface interacts with the server:
 * </p>
 * <p> <img alt="SequenceDiagram" src="SequenceUser.png" height=400 width=500> </p>
 */
public class userGUI implements Runnable {
    final private int WIDTH = 500 ;
    final private int HEIGHT = 280 ;

    final private int FONT_SIZE_DEFAULT = 16 ;
    final private Font FONT = new Font ( "Serif" , Font.BOLD , FONT_SIZE_DEFAULT ) ;

    /**
     * Defines the specific product which is painted
     * on the center of the user interface
     */
    private int index ;

    private JFrame frame;
    private Container contentPane ;

    private JMenuBar menu ;
    private JMenu menu_File ;

    private JPanel info ;
    private Container info_content ;

    private JPanel buy;

    private JButton next;
    private JButton previous;

    private Thread thread = new Thread ( this ) ;
    private boolean thread_alive = false ;

    /**
     * Defines the server, which the graphical user interface
     * communicates with
     */
    private server main_server;

    /**
     * @param main_server
     * Defines the server, which the graphical user interface
     * communicates with
     */
    public userGUI ( server main_server ) {
        this.main_server = main_server ;
    }

    public void run ( ) {
        while ( true ) {
            if ( thread_alive == false ) {
                break ;
            }
            frame.validate ( ) ;
            frame.repaint ( ) ;
            try {
                Thread.sleep ( 500 ) ;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Enables the graphical user interface
     */
    public void initGUI ( ) {
        frame = new JFrame ( ) ;
        frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE ) ;
        contentPane = frame.getContentPane ( ) ;
        frame.setSize ( WIDTH , HEIGHT ) ;
        frame.setLocationRelativeTo ( null ) ;
        contentPane.setLayout ( new BorderLayout () ) ;

        menu = new JMenuBar ( ) ;
        contentPane.add ( menu , BorderLayout.NORTH ) ;

        menu_File = new JMenu ( "File" ) ;
        menu.add ( menu_File ) ;

        JMenuItem menu_File_switch = new JMenuItem ( "Pereiti i administratoriaus sąsają" ) ;
        menu_File_switch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String slaptazodis = JOptionPane.showInputDialog ( null , "Įveskite slaptazodi:" ) ;
                if ( slaptazodis.equals ( main_server.getPassword( ) ) ) {
                    disableGUI ( ) ;
                    adminGUI laik = new adminGUI (main_server) ;
                    laik.initGUI ( ) ;
                }
                else
                    JOptionPane.showMessageDialog ( null , "Įvestas blogas slaptažodis!" ,
                            "" , JOptionPane.ERROR_MESSAGE ) ;
            }
        } ) ;
        menu_File.add ( menu_File_switch ) ;

        JMenuItem menu_File_exit = new JMenuItem ( "Išeiti" ) ;
        menu_File_exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                disableGUI ( ) ;
            }
        } ) ;
        menu_File.add ( menu_File_exit ) ;

        JPanel pirmyn_panel = new JPanel ( ) ;
        pirmyn_panel.setLayout ( new GridBagLayout() ) ;
        next = new JButton ( "Sekantis" ) ;
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if ( index == main_server.quantity( ) - 1 ) {
                    index = 0 ;
                } else
                    index ++ ;

                paint ( ) ;
            }
        } ) ;
        pirmyn_panel.add (next) ;
        contentPane.add ( pirmyn_panel , BorderLayout.EAST ) ;

        JPanel atgal_panel = new JPanel ( ) ;
        atgal_panel.setLayout ( new GridBagLayout() ) ;
        previous = new JButton ( "Praeitas" ) ;
        previous.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if ( index == 0 ) {
                    index = main_server.quantity( ) - 1 ;
                } else
                    index -- ;

                paint ( ) ;
            }
        } ) ;
        atgal_panel.add (previous) ;
        contentPane.add ( atgal_panel , BorderLayout.WEST ) ;

        info = new JPanel ( ) ;
        info.setBorder ( BorderFactory.createLineBorder ( Color.black ) ) ;
        contentPane.add ( info , BorderLayout.CENTER ) ;
        if ( main_server.getList().length != 0 ) {
            info.setLayout ( new BoxLayout ( info , BoxLayout.Y_AXIS ) ) ;
        } else {
            info.setLayout ( new BoxLayout ( info , BoxLayout.Y_AXIS ) ) ;
        }

        buy = new JPanel ( ) ;
        contentPane.add (buy, BorderLayout.SOUTH ) ;

        thread_alive = true ;
        thread.start ( ) ;

        index = 0 ;
        paint ( ) ;

        frame.setResizable ( false ) ;
        frame.setVisible ( true ) ;
    }

    /**
     * Paints the appropriate pruduct's information
     */
    public void paint ( ) {
        if ( main_server.getPreke ( index ) instanceof alc_drink) {
            paint_alc_drink( (alc_drink) main_server.getPreke ( index ) ) ;
        }
        else
            if ( main_server.getPreke ( index ) instanceof drink) {
            paint_drink( (drink) main_server.getPreke ( index ) ) ;
        }
        else
            if ( main_server.getPreke ( index ) instanceof clothes) {
            paint_clothes( (clothes) main_server.getPreke ( index ) ) ;
        }
        else
            if ( main_server.getPreke ( index ) instanceof food) {
            paint_food( (food) main_server.getPreke ( index ) ) ;
        }
        else
            paint_product( (product) main_server.getPreke ( index ) ) ;

        product laik = (product) main_server.getPreke ( index ) ;
        if ( laik.getQuantity() == 0 ) {
            buy.removeAll ( ) ;
            JLabel temp = new JLabel ( "Pasirinkta prekė išpirkta!" ) ;
            temp.setFont ( new Font ( "Monospaced" , Font.CENTER_BASELINE , 15 ) ) ;
            temp.setPreferredSize ( new Dimension ( 250 , 30 ) ) ;
            buy.add ( temp ) ;
            frame.validate ( ) ;
            frame.repaint ( ) ;
        }
        else {
            buy.removeAll ( ) ;
            JButton patvirtinimas = new JButton ( "Pirkti" ) ;
            patvirtinimas.setPreferredSize ( new Dimension ( 250 , 30 ) ) ;
            patvirtinimas.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    product temp = (product) main_server.getPreke ( index ) ;
                    temp.setQuantity( temp.getQuantity( ) - 1 ) ;
                    JOptionPane.showMessageDialog ( null ,
                            "Nuskaičiuota nuo Jūsų sąskaitos: " + Double.toString ( temp.getPrice() ) ,
                            "Patvirtinimas" , JOptionPane.INFORMATION_MESSAGE ) ;
                    main_server.save ( ) ;
                    paint ( ) ;
                }
            } ) ;
            buy.add ( patvirtinimas ) ;
            frame.validate ( ) ;
            frame.repaint ( ) ;
        }
    }

    /**
     * Paints the object's, which is of alc_drink type, information
     * on the center
     * @param object
     * The object, from which information is drawn
     */
    public void paint_alc_drink(alc_drink object ) {
        info.removeAll ( ) ;
        info.setLayout ( new BoxLayout ( info , BoxLayout.Y_AXIS ) ) ;

        JLabel info_pavadinimas = new JLabel ( object.getTitle( ) ) ;
        info_pavadinimas.setFont ( FONT ) ;
        info.add ( info_pavadinimas ) ;

        JLabel info_kaina = new JLabel ( "Kaina: " + Double.toString ( object.getPrice( ) ) ) ;
        info_kaina.setFont ( FONT ) ;
        info.add ( info_kaina ) ;

        JLabel info_stiprumas = new JLabel ( "Alkoholio kiekis (%): " + Double.toString ( object.getAlcoholContent( ) ) ) ;
        info_stiprumas.setFont ( FONT ) ;
        info.add ( info_stiprumas ) ;

        JLabel info_talpa = new JLabel ("Talpa (l): " + Double.toString ( object.getVolume( ) ) ) ;
        info_talpa.setFont ( FONT ) ;
        info.add ( info_talpa ) ;

        JLabel info_kiekis = new JLabel ( "Automate dar liko: " + Integer.toString ( object.getQuantity( ) ) ) ;
        info_kiekis.setFont ( FONT ) ;
        info.add ( info_kiekis ) ;

        frame.validate ( ) ;
        frame.repaint ( ) ;
    }

    /**
     * Paints the object's, which is of drink type, information
     * on the center
     * @param object
     * The object, from which information is drawn
     */
    public void paint_drink(drink object ) {
        info.removeAll ( ) ;
        info.setLayout ( new BoxLayout ( info , BoxLayout.Y_AXIS ) ) ;

        JLabel info_pavadinimas = new JLabel ( object.getTitle( ) ) ;
        info_pavadinimas.setFont ( FONT ) ;
        info.add ( info_pavadinimas ) ;

        JLabel info_kaina = new JLabel ( "Kaina: " + Double.toString ( object.getPrice( ) ) ) ;
        info_kaina.setFont ( FONT ) ;
        info.add ( info_kaina ) ;

        JLabel info_talpa = new JLabel ( "Talpa (l): " + Double.toString ( object.getVolume( ) ) ) ;
        info_talpa.setFont ( FONT ) ;
        info.add ( info_talpa ) ;

        JLabel info_kiekis = new JLabel ( "Automate dar liko: " +  Integer.toString ( object.getQuantity( ) ) ) ;
        info_kiekis.setFont ( FONT ) ;
        info.add ( info_kiekis ) ;

        frame.validate ( ) ;
        frame.repaint ( ) ;
    }

    /**
     * Paints the object's, which is of clothes type, information
     * on the center
     * @param object
     * The object, from which information is drawn
     */
    public void paint_clothes(clothes object ) {
        info.removeAll ( ) ;
        info.setLayout ( new BoxLayout ( info , BoxLayout.Y_AXIS ) ) ;

        JLabel info_pavadinimas = new JLabel ( object.getTitle( ) ) ;
        info_pavadinimas.setFont ( FONT ) ;
        info.add ( info_pavadinimas ) ;

        JLabel info_kaina = new JLabel ( "Kaina: " + Double.toString ( object.getPrice( ) ) ) ;
        info_kaina.setFont ( FONT ) ;
        info.add ( info_kaina ) ;

        JLabel info_dydis = new JLabel ( "Dydis: " + Integer.toString ( object.getSize( ) ) ) ;
        info_dydis.setFont ( FONT ) ;
        info.add ( info_dydis ) ;

        JLabel info_kiekis = new JLabel ( "Automate dar liko: " + Integer.toString ( object.getQuantity( ) ) ) ;
        info_kiekis.setFont ( FONT ) ;
        info.add ( info_kiekis ) ;

        frame.validate ( ) ;
        frame.repaint ( ) ;
    }

    /**
     * Paints the object's, which is of food type, information
     * on the center
     * @param object
     * The object, from which information is drawn
     */
    public void paint_food(food object ) {
        info.removeAll ( ) ;
        info.setLayout ( new BoxLayout ( info , BoxLayout.Y_AXIS ) ) ;

        JLabel info_pavadinimas = new JLabel ( object.getTitle( ) ) ;
        info_pavadinimas.setFont ( FONT ) ;
        info.add ( info_pavadinimas ) ;

        JLabel info_kaina = new JLabel ( "Kaina: " + Double.toString ( object.getPrice( ) ) ) ;
        info_kaina.setFont ( FONT ) ;
        info.add ( info_kaina ) ;

        JLabel info_galiojimas = new JLabel ( "Galiojimas (h): " + Integer.toString ( object.getExpiry( ) ) ) ;
        info_galiojimas.setFont ( FONT ) ;
        info.add ( info_galiojimas ) ;

        JLabel info_kiekis = new JLabel ( "Automate dar liko: " + Integer.toString ( object.getQuantity( ) ) ) ;
        info_kiekis.setFont ( FONT ) ;
        info.add ( info_kiekis ) ;

        frame.validate ( ) ;
        frame.repaint ( ) ;
    }

    /**
     * Paints the object's, which is of product type, information
     * on the center
     * @param object
     * The object, from which information is drawn
     */
    public void paint_product (product object ) {
        info.removeAll ( ) ;
        info.setLayout ( new BoxLayout ( info , BoxLayout.Y_AXIS ) ) ;

        JLabel info_pavadinimas = new JLabel ( object.getTitle( ) ) ;
        info_pavadinimas.setFont ( FONT ) ;
        info.add ( info_pavadinimas ) ;

        JLabel info_kaina = new JLabel ( "Kaina: " + Double.toString ( object.getPrice( ) ) ) ;
        info_kaina.setFont ( FONT ) ;
        info.add ( info_kaina ) ;

        JLabel info_kiekis = new JLabel ( "Automate dar liko: " + Integer.toString ( object.getQuantity( ) ) ) ;
        info_kiekis.setFont ( FONT ) ;
        info.add ( info_kiekis ) ;

        frame.validate ( ) ;
        frame.repaint ( ) ;
    }

    /**
     * Disables the graphical user interface
     */
    public void disableGUI ( ) {
        thread_alive = false ;
        frame.setVisible ( false ) ;
        frame.dispose ( ) ;
    }
}
