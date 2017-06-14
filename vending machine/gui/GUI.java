package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import server.server;
import server.load ;

/**
 * Created by pauliuskalv on 5/16/17.
 */
public class GUI {
    public boolean is_visible = false ;
    private JButton Mygtukas1 ;
    private JButton Mygtukas2 ;
    private JFrame langas ;
    private Container contentPane ;

    private server serveris = null ;

    private ActionListener action1 ;
    private ActionListener action2 ;

    private JMenuBar menu ;
    private JMenu pasirinkimas_File ;
    private JMenuItem pasirinkimas_File_load ;
    private JMenuItem pasirinkimas_File_exit ;

    public GUI ( ) {
    }

    public void initGUI ( ) {
        langas = new JFrame ( "Prekiu automatas" ) ;
        contentPane = langas.getContentPane ( ) ;
        contentPane.setLayout ( null ) ;

        langas.setSize ( 300 , 200 );
        langas.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE ) ;

        Mygtukas1 = new JButton ( "As administratorius!" ) ;
        Mygtukas2 = new JButton ( "As klientas!" ) ;

        Mygtukas1.setSize ( 200 , 50 ) ;
        Mygtukas2.setSize ( 200 , 50 ) ;
        Mygtukas1.setLocation ( 50 , 0 ) ;
        Mygtukas2.setLocation ( 50 , 50 ) ;

        action1 = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                adminGUI temp ;
                JOptionPane patvirtinimas = new JOptionPane ( ) ;
                String password = patvirtinimas.showInputDialog ( "Iveskite slaptazodi:" ) ;
                if ( password.equals ( serveris.getPassword( ) ) ) {
                    disableGUI ( ) ;
                    temp = new adminGUI ( serveris ) ;
                    temp.initGUI ( ) ;
                }
                else
                    patvirtinimas.showMessageDialog ( null , "Ivestas blogas slaptazodis!" ,
                            "" , JOptionPane.ERROR_MESSAGE ) ;
            }
        } ;

        action2 = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if ( serveris.quantity( ) == 0 ) {
                    JOptionPane.showMessageDialog ( null , "Talpykla dar tuščia!\nSusisiekite " +
                            "su administratoriumi" , "" , JOptionPane.ERROR_MESSAGE ) ;
                }
                else {
                    disableGUI ( ) ;
                    userGUI laik = new userGUI ( serveris ) ;
                    laik.initGUI ( ) ;
                }
            }
        } ;

        Mygtukas1.addActionListener ( action1 ) ;
        Mygtukas2.addActionListener ( action2 ) ;

        menu = new JMenuBar ( ) ;
        pasirinkimas_File = new JMenu ( "File" ) ;

        pasirinkimas_File_exit = new JMenuItem ( "Išeiti" ) ;
        pasirinkimas_File_exit.addActionListener ( new ActionListener ( ) {
            public void actionPerformed ( ActionEvent actionEvent ) {
                disableGUI ( ) ;
            }
        } ) ;

        pasirinkimas_File.add ( pasirinkimas_File_exit ) ;
        menu.add ( pasirinkimas_File ) ;

        contentPane.add ( Mygtukas1 ) ;
        contentPane.add ( Mygtukas2 ) ;

        langas.setJMenuBar ( menu ) ;
        langas.setLocationRelativeTo ( null ) ;
        langas.setResizable ( false ) ;
        langas.setVisible ( true ) ;

        File failas = new File ( System.getProperty ( "user.dir" ) + "/backup/server.ser" ) ;
        if ( ! failas.exists ( ) ) {
            JOptionPane laik = new JOptionPane ( ) ;
            String temp = null ;
            temp = laik.showInputDialog ( "Iveskite naujos talpyklos dydi:" ) ;
            if ( temp == null ) {
                System.exit ( 0 ) ;
            }
            if ( temp.length() == 0 ) {
                laik.showMessageDialog ( null , "Negalima testi darbo. Susisiekite su" +
                        " administratoriumi!" , "" , JOptionPane.ERROR_MESSAGE ) ;
                System.exit ( 0 ) ;
            }
            try {
                Integer.parseInt ( temp ) ;
            } catch ( NumberFormatException e ) {
                laik.showMessageDialog ( null , "Ivestas ne skaičius! Susisiekite su" +
                        " administratoriumi" , "" , JOptionPane.ERROR_MESSAGE ) ;
                System.exit ( 0 ) ;
            }
            serveris = new server ( Integer.parseInt( temp  ) ) ;
            String pass_temp = null ;
            while ( true ) {
                pass_temp = JOptionPane.showInputDialog ( "Iveskite naują administratoriaus slaptažodį:" ) ;

                if ( pass_temp == null ) {
                    System.exit ( 0 ) ;
                }

                if ( pass_temp.equals ( "" ) ) {
                    JOptionPane.showMessageDialog ( null , "Reikia įvesti slaptažodį!" ,
                            "" , JOptionPane.ERROR_MESSAGE ) ;
                }
                else
                    break ;
            }
            serveris.setPassword( pass_temp ) ;
        }
        else
        {
            load laik = new load( ) ;
            serveris = laik.load ( ) ;
        }
    }

    public void disableGUI ( ) {
        langas.setVisible ( false ) ;
        langas.dispose ( ) ;
        is_visible = false ;
    }
}
