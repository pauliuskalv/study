package gui ;
/**
 * Created by pauliuskalv on 5/9/17.
 */
import exceptions.exception;
import products.* ;
import server.server ;
import javax.swing.* ;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.* ;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This object defines the vending machine's administrator
 * graphical user interface. In this interface, a user
 * is able to manipulate the database of the working
 * vending machine.
 * <p>
 * Below is a sequence diagram, showing how the user interface
 * communicates with the server:
 * </p>
 * <p> <img alt="SequenceDiagram" src="SequenceAdmin.png" height=400 width=500> </p>
 */
public class adminGUI implements Runnable {
    final private int WIDTH = 500 ;
    final private int HEIGHT = 280 ;
    private JFrame frame;
    private Container contentPane ;
    private JMenuBar menu ;

    /**
     * Defines the server, with which the
     * graphical user interface
     * communicates with
     */
    private server main_server;

    private Border border = BorderFactory.createLineBorder ( Color.black ) ;

    private JMenu selection_File ;
    private JMenuItem selection_File_switch ;
    private JMenuItem selection_File_save ;
    private JMenuItem selection_File_exit ;

    private JMenu preferrences;
    private JMenuItem preferrences_change_password ;
    private JMenuItem preferrences_new_server ;

    private JPanel panel ;
    private JScrollPane list ;
    private JList data ;

    private JPanel info ;

    private boolean thread_alive = true ;
    Thread thread ;

    /**
     * @param main_server
     * Defines the server, which the graphical user interface
     * communicates with
     */
    public adminGUI ( server main_server) {
        this.main_server = main_server;
    }

    public void run ( ) {
        while ( true ) {
            frame.validate ( ) ;
            frame.repaint ( ) ;
            try {
                Thread.sleep ( 400 ) ;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if ( this.thread_alive == false ) {
                break ;
            }
        }
    }

    /**
     * Enables the graphical user interface
     */
    public void initGUI ( ) {
        frame = new JFrame ( "Prekiu automatas" ) ;
        contentPane = frame.getContentPane ( ) ;
        contentPane.setLayout ( new BoxLayout ( contentPane , BoxLayout.X_AXIS ) ) ;

        frame.setSize ( WIDTH , HEIGHT ) ;
        frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE ) ;
        frame.setLocationRelativeTo ( null ) ;

        menu = new JMenuBar ( ) ;
        selection_File = new JMenu ( "File" ) ;

        selection_File_switch = new JMenuItem ( "Pereiti i vartotojo sąsają" ) ;
        selection_File_switch.addActionListener (new ActionListener ( ) {
            public void actionPerformed ( ActionEvent actionEvent ) {
                if ( main_server.quantity( ) == 0 ) {
                    JOptionPane.showMessageDialog ( null , "Talpykla tuščia!" ,
                            "" , JOptionPane.ERROR_MESSAGE ) ;
                } else {
                    disableGUI ( ) ;
                    userGUI laik = new userGUI (main_server) ;
                    laik.initGUI ( ) ;
                }
            }
        } ) ;

        selection_File_save = new JMenuItem ( "Išsaugoti į failą" ) ;
        selection_File_save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                main_server.save ( ) ;
                JOptionPane.showMessageDialog ( null , "Sėkmingai išsaugoti duomenys!" ,
                        "" , JOptionPane.INFORMATION_MESSAGE ) ;
            }
        } ) ;

        selection_File_exit = new JMenuItem ( "Išeiti" ) ;

        selection_File_exit.addActionListener (new ActionListener ( ) {
            public void actionPerformed ( ActionEvent actionEvent ) {
                disableGUI ( ) ;
            }
        } ) ;

        selection_File.add (selection_File_switch) ;
        selection_File.add (selection_File_save) ;
        selection_File.add (selection_File_exit) ;
        menu.add (selection_File) ;

        JMenu pasirinkimas_Add = new JMenu ( "Pridėti" ) ;

        JMenuItem pasirinkimas_Add_alk_gerimai = new JMenuItem ( "Alkoholinį gėrimą" ) ;
        pasirinkimas_Add_alk_gerimai.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                new_alc_drink( ) ;
            }
        } ) ;

        JMenuItem pasirinkimas_Add_gerimai = new JMenuItem ( "Gėrimą" ) ;
        pasirinkimas_Add_gerimai.addActionListener ( new ActionListener ( ) {
            public void actionPerformed(ActionEvent actionEvent) {
                new_drink( ) ;
            }
        } ) ;

        JMenuItem pasirinkimas_Add_drabuziai = new JMenuItem ( "Drabužius" ) ;
        pasirinkimas_Add_drabuziai.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                new_clothes( ) ;
            }
        } ) ;

        JMenuItem pasirinkimas_Add_maistas = new JMenuItem ( "Maisto produktą" ) ;
        pasirinkimas_Add_maistas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                new_food( ) ;
            }
        } ) ;

        JMenuItem pasirinkimas_Add_preke = new JMenuItem ( "Prekę" ) ;
        pasirinkimas_Add_preke.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                new_product( ) ;
            }
        } ) ;

        pasirinkimas_Add.add ( pasirinkimas_Add_alk_gerimai ) ;
        pasirinkimas_Add.add ( pasirinkimas_Add_gerimai ) ;
        pasirinkimas_Add.add ( pasirinkimas_Add_drabuziai ) ;
        pasirinkimas_Add.add ( pasirinkimas_Add_maistas ) ;
        pasirinkimas_Add.add ( pasirinkimas_Add_preke ) ;
        menu.add ( pasirinkimas_Add ) ;

        preferrences = new JMenu ( "Parinktys" ) ;

        preferrences_change_password = new JMenuItem ( "Keisti slaptažodį" ) ;
        preferrences_change_password.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String laik = JOptionPane.showInputDialog ( "Įveskite esamą slaptažodį:" ) ;

                if ( laik == null || laik.length() == 0 ) return ;

                if ( laik.equals ( main_server.getPassword( ) ) ) {
                    laik = JOptionPane.showInputDialog ( "Įveskite naują slaptažodį:" ) ;

                    if ( laik == null ) return ;

                    if ( laik.equals ( "" ) ) {
                        JOptionPane.showMessageDialog ( null , "Slaptažodžio laukas tuščias!" ,
                                "" , JOptionPane.ERROR_MESSAGE ) ;
                    }
                    else {
                        main_server.setPassword( laik ) ;
                        JOptionPane.showMessageDialog ( null , "Slaptažodis sėkmingai pakeistas!" ,
                                "" , JOptionPane.OK_OPTION ) ;
                    }
                } else
                    JOptionPane.showMessageDialog ( null , "Įvestas blogas slaptažodis!" ,
                            "" , JOptionPane.ERROR_MESSAGE ) ;
            }
        } ) ;

        preferrences_new_server = new JMenuItem ( "Perkurti prekių automato talpyklą" ) ;
        preferrences_new_server.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String laik = JOptionPane.showInputDialog ( "Įveskite esamą slaptažodį:" ) ;

                if ( laik == null ) return ;

                if ( laik.equals ( main_server.getPassword( ) ) ) {
                    String temp = null ;
                    temp = JOptionPane.showInputDialog ( "Įveskite naujos talpyklos dydį:" ) ;

                    if ( temp == null || temp.length() == 0 ) return ;

                    int kiekis = Integer.parseInt ( temp ) ;
                    if ( kiekis == 0 ) {
                        JOptionPane.showMessageDialog ( null , "Talpyklos dydis negali būti 0!" ,
                                "" , JOptionPane.ERROR_MESSAGE ) ;
                    } else {
                        String pass_temp = main_server.getPassword( ) ;
                        main_server = new server ( kiekis ) ;
                        main_server.setPassword( pass_temp ) ;
                        disableGUI ( ) ;
                        initGUI ( ) ;
                    }
                }
            }
        } ) ;

        preferrences.add (preferrences_change_password) ;
        preferrences.add (preferrences_new_server) ;
        menu.add (preferrences) ;

        panel = new JPanel ( ) ;
        data = new JList ( main_server.getList() ) ;
        data.addListSelectionListener(new ListSelectionListener ( ) {
            public void valueChanged ( ListSelectionEvent listSelectionEvent ) {
                System.out.println ( main_server.getPreke(data.getSelectedIndex()) ) ;
                if ( main_server.getPreke ( data.getSelectedIndex() ) instanceof alc_drink) {
                    paint_alc_drink( (alc_drink) main_server.getPreke ( data.getSelectedIndex ( ) ) ) ;
                }
                else
                    if ( main_server.getPreke ( data.getSelectedIndex ( ) ) instanceof drink) {
                    paint_drink( (drink) main_server.getPreke ( data.getSelectedIndex ( ) ) ) ;
                }
                else
                    if ( main_server.getPreke ( data.getSelectedIndex ( ) ) instanceof clothes) {
                    paint_clothes( (clothes) main_server.getPreke ( data.getSelectedIndex ( ) ) ) ;
                }
                else
                    if ( main_server.getPreke ( data.getSelectedIndex ( ) ) instanceof food) {
                    paint_food( (food) main_server.getPreke ( data.getSelectedIndex ( ) ) ) ;
                }
                else
                    if ( main_server.getPreke ( data.getSelectedIndex ( ) ) instanceof product) {
                    paint_product( (product) main_server.getPreke ( data.getSelectedIndex ( ) ) ) ;
                }
                else
                    if ( main_server.getPreke ( data.getSelectedIndex ( ) ) == null ) {
                    info.removeAll ( ) ;
                    frame.validate ( ) ;
                    frame.repaint ( ) ;
                }
            }
        } ) ;
        list = new JScrollPane ( data ) ;
        list.setPreferredSize ( panel.getPreferredSize() ) ;
        list.setWheelScrollingEnabled ( true ) ;
        list.createVerticalScrollBar ( ) ;
        list.setPreferredSize ( new Dimension ( WIDTH / 2 - 10 , HEIGHT - 60 ) ) ;
        panel.add ( list ) ;

        info = new JPanel ( ) ;
        info.setLayout ( new BoxLayout ( info , BoxLayout.Y_AXIS ) ) ;
        info.setPreferredSize ( new Dimension ( WIDTH / 2 - 10 , HEIGHT - 60 ) ) ;
        info.add ( new JPanel ( ) ) ;
        info.setBorder (border) ;

        contentPane.add (panel) ;
        contentPane.add ( info ) ;
        frame.setJMenuBar ( menu ) ;
        frame.setVisible ( true ) ;
        frame.setResizable ( false ) ;

        thread = new Thread ( this ) ;
        thread.start ( ) ;
    }

    /**
     * Paints the object's, which is of alc_drink type, information
     * on the right side
     * @param object
     * The object to be used for painting
     * it's information. This specific object is of
     * alc_drink type
     */
    private void paint_alc_drink (alc_drink object ) {
        info.removeAll ( ) ;
        info.setLayout ( new BoxLayout ( info , BoxLayout.Y_AXIS ) ) ;

        JPanel info_data = new JPanel ( ) ;
        info_data.setLayout ( new BoxLayout ( info_data , BoxLayout.Y_AXIS ) ) ;

        JTextField pavadinimas = new JTextField ( object.getTitle( ) ) ;
        JLabel pavadinimas_info = new JLabel ( "Pavadinimas" ) ;
        pavadinimas_info.setLabelFor ( pavadinimas ) ;
        info_data.add ( pavadinimas_info ) ;
        info_data.add ( pavadinimas ) ;

        JTextField kaina = new JTextField ( Double.toString ( object.getPrice() ) ) ;
        JLabel kaina_info = new JLabel ( "Kaina" ) ;
        kaina_info.setLabelFor ( kaina ) ;
        info_data.add ( kaina_info ) ;
        info_data.add ( kaina ) ;

        JTextField stiprumas = new JTextField ( Double.toString ( object.getAlcoholContent() ) ) ;
        JLabel stiprumas_info = new JLabel ( "Stiprumas(%)" ) ;
        stiprumas_info.setLabelFor ( stiprumas ) ;
        info_data.add ( stiprumas_info ) ;
        info_data.add ( stiprumas ) ;

        JTextField talpa = new JTextField ( Double.toString ( object.getVolume() ) ) ;
        JLabel talpa_info = new JLabel ( "Talpa" ) ;
        talpa_info.setLabelFor ( talpa ) ;
        info_data.add ( talpa_info ) ;
        info_data.add ( talpa ) ;

        JTextField kiekis = new JTextField ( Integer.toString ( object.getQuantity() ) ) ;
        JLabel kiekis_info = new JLabel ( "Kiekis vienetais" ) ;
        kiekis_info.setLabelFor ( kiekis ) ;
        info_data.add ( kiekis_info ) ;
        info_data.add ( kiekis ) ;

        JButton patvirtinimas = new JButton ( "Patvirtinti" ) ;
        patvirtinimas.addActionListener ( new ActionListener ( ) {
            public void actionPerformed ( ActionEvent actionEvent ) {
                if ( kaina.getText().equals("") || pavadinimas.getText().equals("") ||
                        stiprumas.getText().equals("") || talpa.getText().equals("") ||
                        kiekis.getText().equals("") ) {
                    JOptionPane.showMessageDialog ( null , "Kai kurie laukai tušti!" ,
                            "" , JOptionPane.ERROR_MESSAGE ) ;
                }
                else {
                    try {
                        object.setPrice( Double.parseDouble ( kaina.getText ( ) ) ) ;
                    } catch (exception exception) {
                    }
                    object.setTitle( pavadinimas.getText ( ) ) ;
                    object.change_alcohol_content( Double.parseDouble ( stiprumas.getText ( ) ) ) ;
                    object.setVolume( Double.parseDouble ( talpa.getText ( ) ) ) ;
                    object.setQuantity( Integer.parseInt ( kiekis.getText ( ) ) ) ;
                    data.setListData ( main_server.getList ( ) ) ;
                    info.removeAll ( ) ;
                    info.add ( new JPanel ( ) ) ;
                    frame.validate ( ) ;
                    frame.repaint ( ) ;
                }
            }
        } ) ;

        JButton isimti = new JButton ( "Ištrinti" ) ;
        isimti.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                main_server.removeProduct( data.getSelectedIndex ( ) ) ;
                data.setListData ( main_server.getList ( ) ) ;
                info.removeAll ( ) ;
                info.add ( new JPanel ( ) ) ;
                frame.validate ( ) ;
                frame.repaint ( ) ;
            }
        } ) ;

        info.add ( info_data ) ;

        JPanel apacia = new JPanel ( ) ;
        apacia.setLayout ( new BoxLayout ( apacia , BoxLayout.X_AXIS ) ) ;
        apacia.add ( patvirtinimas ) ;
        apacia.add ( isimti ) ;
        info.add ( apacia ) ;

        frame.validate ( ) ;
        frame.repaint ( ) ;
    }

    /**
     * Paints the object's, which is of drink type, information
     * on the right side
     * @param object
     * The object to be used for painting
     * it's information. This specific object
     * is of drink type
     */
    private void paint_drink (drink object ) {
        info.removeAll ( ) ;
        info.setLayout ( new BoxLayout ( info , BoxLayout.Y_AXIS ) ) ;

        JPanel info_data = new JPanel ( ) ;
        info_data.setLayout ( new BoxLayout ( info_data , BoxLayout.Y_AXIS ) ) ;

        JTextField pavadinimas = new JTextField ( object.getTitle() ) ;
        JLabel pavadinimas_info = new JLabel ( "Pavadinimas" ) ;
        pavadinimas_info.setLabelFor ( pavadinimas ) ;
        info_data.add ( pavadinimas_info ) ;
        info_data.add ( pavadinimas ) ;

        JTextField kaina = new JTextField ( Double.toString ( object.getPrice() ) ) ;
        JLabel kaina_info = new JLabel ( "Kaina" ) ;
        kaina_info.setLabelFor ( kaina ) ;
        info_data.add ( kaina_info ) ;
        info_data.add ( kaina ) ;

        JTextField talpa = new JTextField ( Double.toString ( object.getVolume( ) ) ) ;
        JLabel talpa_info = new JLabel ( "Talpa" ) ;
        talpa_info.setLabelFor ( talpa ) ;
        info_data.add ( talpa_info ) ;
        info_data.add ( talpa ) ;

        JTextField kiekis = new JTextField ( Integer.toString ( object.getQuantity() ) ) ;
        JLabel kiekis_info = new JLabel ( "Kiekis vienetais" ) ;
        kiekis_info.setLabelFor ( kiekis ) ;
        info_data.add ( kiekis_info ) ;
        info_data.add ( kiekis ) ;

        JButton patvirtinimas = new JButton ( "Patvirtinti" ) ;
        patvirtinimas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if ( pavadinimas.getText().equals("") || kaina.getText().equals("") ||
                        talpa.getText().equals("") || kiekis.getText().equals("") ) {
                    JOptionPane.showMessageDialog ( null , "Kai kurie laukai tušti!" ,
                            "" , JOptionPane.ERROR_MESSAGE ) ;
                }
                else {
                    object.setTitle( pavadinimas.getText ( ) ) ;
                    object.setVolume( Double.parseDouble ( talpa.getText ( ) ) ) ;
                    try {
                        object.setPrice( Double.parseDouble ( kaina.getText ( ) ) ) ;
                    } catch (exception exception) {
                    }
                    object.setQuantity( Integer.parseInt ( kiekis.getText() ) ) ;
                    data.setListData ( main_server.getList ( ) ) ;
                    info.removeAll ( ) ;
                    info.add ( new JPanel ( ) ) ;
                    frame.validate ( ) ;
                    frame.repaint ( ) ;
                }
            }
        } ) ;

        JButton isimti = new JButton ( "Istrinti" ) ;
        isimti.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                main_server.removeProduct( data.getSelectedIndex ( ) ) ;
                data.setListData ( main_server.getList ( ) ) ;
                info.removeAll ( ) ;
                info.add ( new JPanel ( ) ) ;
                frame.validate ( ) ;
                frame.repaint ( ) ;
            }
        } ) ;

        info.add ( info_data ) ;

        Container apacia = new Container ( ) ;
        apacia.setLayout ( new BoxLayout ( apacia , BoxLayout.X_AXIS ) ) ;
        apacia.add ( patvirtinimas ) ;
        apacia.add ( isimti ) ;
        info.add ( apacia ) ;

        frame.validate ( ) ;
        frame.repaint ( ) ;
    }

    /**
     * Paints the object's, which is of clothes type, information
     * on the right side
     * @param object
     * The object to be used for painting
     * it's information. This specific object
     * is of clothes type
     */
    private void paint_clothes (clothes object ) {
        info.removeAll ( ) ;
        info.setLayout ( new BoxLayout ( info , BoxLayout.Y_AXIS ) ) ;

        JPanel info_data = new JPanel ( ) ;
        info_data.setLayout ( new BoxLayout ( info_data , BoxLayout.Y_AXIS ) ) ;

        JTextField pavadinimas = new JTextField ( object.getTitle( ) ) ;
        JLabel pavadinimas_info = new JLabel ( "Pavadinimas" ) ;
        pavadinimas_info.setLabelFor ( pavadinimas ) ;
        info_data.add ( pavadinimas_info ) ;
        info_data.add ( pavadinimas ) ;

        JTextField kaina = new JTextField ( Double.toString ( object.getPrice( ) ) ) ;
        JLabel kaina_info = new JLabel ( "Kaina" ) ;
        kaina_info.setLabelFor ( kaina ) ;
        info_data.add ( kaina_info ) ;
        info_data.add ( kaina ) ;

        JTextField dydis = new JTextField ( Integer.toString ( object.getSize( ) ) ) ;
        JLabel dydis_info = new JLabel ( "Dydis" ) ;
        dydis_info.setLabelFor ( dydis ) ;
        info_data.add ( dydis_info ) ;
        info_data.add ( dydis ) ;

        JTextField kiekis = new JTextField ( Integer.toString ( object.getQuantity() ) ) ;
        JLabel kiekis_info = new JLabel ( "Kiekis vienetais" ) ;
        kiekis_info.setLabelFor ( kiekis ) ;
        info_data.add ( kiekis_info ) ;
        info_data.add ( kiekis ) ;

        JButton patvirtinimas = new JButton ( "Patvirtinti" ) ;
        patvirtinimas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if ( pavadinimas.getText().equals("") || kaina.getText().equals("") ||
                        dydis.getText().equals("") || kiekis.getText().equals("") ) {
                    JOptionPane.showMessageDialog ( null , "Kai kurie tušti!" , "" ,
                            JOptionPane.ERROR_MESSAGE ) ;
                }
                else {
                    object.setTitle( pavadinimas.getText ( ) ) ;
                    object.setSize( Integer.parseInt ( dydis.getText ( ) ) ) ;
                    try {
                        object.setPrice( Double.parseDouble ( kaina.getText ( ) ) ) ;
                    } catch (exception exception) {
                    }
                    object.setQuantity( Integer.parseInt ( kiekis.getText() ) ) ;
                    data.setListData ( main_server.getList ( ) ) ;
                    info.removeAll ( ) ;
                    info.add ( new JPanel ( ) ) ;
                    frame.validate ( ) ;
                    frame.repaint ( ) ;
                }
            }
        } ) ;

        JButton isimti = new JButton ( "Ištrinti" ) ;
        isimti.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                main_server.removeProduct( data.getSelectedIndex ( ) ) ;
                data.setListData ( main_server.getList ( ) ) ;
                info.removeAll ( ) ;
                info.add ( new JPanel ( ) ) ;
                frame.validate ( ) ;
                frame.repaint ( ) ;
            }
        } ) ;

        info.add ( info_data ) ;

        Container apacia = new Container ( ) ;
        apacia.setLayout ( new BoxLayout ( apacia , BoxLayout.X_AXIS ) ) ;
        apacia.add ( patvirtinimas ) ;
        apacia.add ( isimti ) ;
        info.add ( apacia ) ;

        frame.validate ( ) ;
        frame.repaint ( ) ;
    }

    /**
     * Paints the object's, which is of food type, information
     * on the right side
     * @param object
     * The object to be used for painting
     * it's information. This specific object
     * is of food type
     */
    private void paint_food (food object ) {
        info.removeAll ( ) ;
        info.setLayout ( new BoxLayout ( info , BoxLayout.Y_AXIS ) ) ;

        JPanel info_data = new JPanel ( ) ;
        info_data.setLayout ( new BoxLayout ( info_data , BoxLayout.Y_AXIS ) ) ;

        JTextField pavadinimas = new JTextField ( object.getTitle() ) ;
        JLabel pavadinimas_info = new JLabel ( "Pavadinimas" ) ;
        pavadinimas_info.setLabelFor ( pavadinimas ) ;
        info_data.add ( pavadinimas_info ) ;
        info_data.add ( pavadinimas ) ;

        JTextField kaina = new JTextField ( Double.toString ( object.getPrice( ) ) ) ;
        JLabel kaina_info = new JLabel ( "Kaina" ) ;
        kaina_info.setLabelFor ( kaina ) ;
        info_data.add ( kaina_info ) ;
        info_data.add ( kaina ) ;

        JTextField galiojimas = new JTextField ( Integer.toString ( object.getExpiry( ) ) ) ;
        JLabel galiojimas_info = new JLabel ( "Galiojimas(h)" ) ;
        galiojimas_info.setLabelFor ( galiojimas ) ;
        info_data.add ( galiojimas_info ) ;
        info_data.add ( galiojimas ) ;

        JTextField kiekis = new JTextField ( Integer.toString ( object.getQuantity() ) ) ;
        JLabel kiekis_info = new JLabel ( "Kiekis vienetais" ) ;
        kiekis_info.setLabelFor ( kiekis ) ;
        info_data.add ( kiekis_info ) ;
        info_data.add ( kiekis ) ;

        JButton patvirtinimas = new JButton ( "Patvirtinti" ) ;
        patvirtinimas.addActionListener ( new ActionListener ( ) {
            public void actionPerformed(ActionEvent actionEvent) {
                if ( pavadinimas.getText().equals("") || kaina.getText().equals("") ||
                        galiojimas.getText().equals("") || kiekis.getText().equals("") ) {
                    JOptionPane.showMessageDialog ( null , "Kai kurie laukai tušti!" , "" ,
                            JOptionPane.ERROR_MESSAGE ) ;
                }
                else {
                    object.setTitle( pavadinimas.getText ( ) ) ;
                    object.setExpiry( Integer.parseInt ( galiojimas.getText ( ) ) ) ;
                    try {
                        object.setPrice( Double.parseDouble ( kaina.getText ( ) ) ) ;
                    } catch (exception exception) {
                    }
                    object.setQuantity( Integer.parseInt ( kiekis.getText() ) ) ;
                    data.setListData ( main_server.getList ( ) ) ;
                    info.removeAll ( ) ;
                    info.add ( new JPanel ( ) ) ;
                    frame.validate ( ) ;
                    frame.repaint ( ) ;
                }
            }
        } ) ;

        JButton isimti = new JButton ( "Ištrinti" ) ;
        isimti.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                main_server.removeProduct( data.getSelectedIndex ( ) ) ;
                data.setListData ( main_server.getList ( ) ) ;
                info.removeAll ( ) ;
                info.add ( new JPanel ( ) ) ;
                frame.validate ( ) ;
                frame.repaint ( ) ;
            }
        } ) ;

        info.add ( info_data ) ;

        Container apacia = new Container ( ) ;
        apacia.setLayout ( new BoxLayout ( apacia , BoxLayout.X_AXIS ) ) ;
        apacia.add ( patvirtinimas ) ;
        apacia.add ( isimti ) ;
        info.add ( apacia ) ;

        frame.validate ( ) ;
        frame.repaint ( ) ;
    }

    /**
     * Paints the object's, which is of product type, information
     * on the right side
     * @param object
     * The object to be used for painting it's
     * information. This specific object is of
     * product type
     */
    private void paint_product (product object ) {
        info.removeAll ( ) ;
        info.setLayout ( new BoxLayout ( info , BoxLayout.Y_AXIS ) ) ;

        JPanel info_data = new JPanel ( ) ;
        info_data.setLayout ( new BoxLayout ( info_data , BoxLayout.Y_AXIS ) ) ;

        JTextField pavadinimas = new JTextField ( object.getTitle( ) ) ;
        JLabel pavadinimas_info = new JLabel ( "Pavadinimas" ) ;
        pavadinimas_info.setLabelFor ( pavadinimas ) ;
        info_data.add ( pavadinimas_info ) ;
        info_data.add ( pavadinimas ) ;

        JTextField kaina = new JTextField ( Double.toString ( object.getPrice( ) ) ) ;
        JLabel kaina_info = new JLabel ( "Kaina" ) ;
        info_data.add ( kaina_info ) ;
        info_data.add ( kaina ) ;

        JTextField kiekis = new JTextField ( Integer.toString ( object.getQuantity() ) ) ;
        JLabel kiekis_info = new JLabel ( "Kiekis vienetais" ) ;
        kiekis_info.setLabelFor ( kiekis ) ;
        info_data.add ( kiekis_info ) ;
        info_data.add ( kiekis ) ;

        JButton patvirtinimas = new JButton ( "Patvirtinti" ) ;
        patvirtinimas.addActionListener ( new ActionListener ( ) {
            public void actionPerformed(ActionEvent actionEvent) {
                if ( pavadinimas.getText().equals("") || kaina.getText().equals("") ||
                        kiekis.getText().equals("") ) {
                    JOptionPane.showMessageDialog ( null , "Kai kurie laukai tušti!" , "" ,
                            JOptionPane.ERROR_MESSAGE ) ;
                }
                else {
                    object.setTitle( pavadinimas.getText ( ) ) ;
                    try {
                        object.setPrice( Double.parseDouble ( kaina.getText ( ) ) ) ;
                    } catch (exception exception) {
                    }
                    object.setQuantity( Integer.parseInt ( kiekis.getText ( ) ) ) ;
                    data.setListData ( main_server.getList ( ) ) ;
                    info.removeAll ( ) ;
                    info.add ( new JPanel ( ) ) ;
                    frame.validate ( ) ;
                    frame.repaint ( ) ;
                }
            }
        } ) ;

        JButton isimti = new JButton ( "Ištrinti" ) ;
        isimti.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                main_server.removeProduct( data.getSelectedIndex ( ) ) ;
                data.setListData ( main_server.getList ( ) ) ;
                info.removeAll ( ) ;
                info.add ( new JPanel ( ) ) ;
                frame.validate ( ) ;
                frame.repaint ( ) ;
            }
        } ) ;

        info.add ( info_data ) ;

        Container apacia = new Container ( ) ;
        apacia.setLayout ( new BoxLayout ( apacia , BoxLayout.X_AXIS ) ) ;
        apacia.add ( patvirtinimas ) ;
        apacia.add ( isimti ) ;
        info.add ( apacia ) ;

        frame.validate ( ) ;
        frame.repaint ( ) ;
    }

    /**
     * Opens up a window in which you can create
     * a new alc_drink type product
     */
    private void new_alc_drink ( ) {
        JFrame laik = new JFrame ( ) ;
        laik.setSize ( 200 , 200 ) ;
        Container laik_content = laik.getContentPane ( ) ;
        laik_content.setLayout ( new BoxLayout ( laik_content , BoxLayout.Y_AXIS ) ) ;
        laik.setLocationRelativeTo ( null ) ;

        JTextField pavadinimas = new JTextField ( "" ) ;
        JLabel pavadinimas_info = new JLabel ( "Pavadinimas" ) ;
        pavadinimas_info.setLabelFor ( pavadinimas ) ;
        laik_content.add ( pavadinimas_info ) ;
        laik_content.add ( pavadinimas ) ;

        JTextField kaina = new JTextField ( "" ) ;
        JLabel kaina_info = new JLabel ( "Kaina" ) ;
        kaina_info.setLabelFor ( kaina ) ;
        laik_content.add ( kaina_info ) ;
        laik_content.add ( kaina ) ;

        JTextField stiprumas = new JTextField ( "" ) ;
        JLabel stiprumas_info = new JLabel ( "Stiprumas" ) ;
        stiprumas_info.setLabelFor ( stiprumas ) ;
        laik_content.add ( stiprumas_info ) ;
        laik_content.add ( stiprumas ) ;

        JTextField talpa = new JTextField ( "" ) ;
        JLabel talpa_info = new JLabel ( "Talpa" ) ;
        talpa_info.setLabelFor ( talpa ) ;
        laik_content.add ( talpa_info ) ;
        laik_content.add ( talpa ) ;

        JButton patvirtinimas = new JButton ( "Patvirtinti" ) ;
        patvirtinimas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if ( pavadinimas.getText().equals("") || kaina.getText().equals("") ||
                        stiprumas.getText().equals("") || talpa.getText().equals("") ) {
                    JOptionPane temp = new JOptionPane ( ) ;
                    temp.showMessageDialog ( null , "Ne visi laukai uzpildyti!"
                            , "" , JOptionPane.ERROR_MESSAGE ) ;
                }
                else {
                    main_server.add_alc_drink( Double.parseDouble ( kaina.getText ( ) ) , pavadinimas.getText ( )
                    , Double.parseDouble ( stiprumas.getText ( ) ) , Double.parseDouble ( talpa.getText ( ) ) ) ;
                    laik.setVisible ( false ) ;
                    laik.dispose ( ) ;
                    data.setListData ( main_server.getList ( ) ) ;
                    frame.validate ( ) ;
                    frame.repaint ( ) ;
                }
            }
        } ) ;
        laik_content.add ( patvirtinimas ) ;

        laik.setResizable ( false ) ;
        laik.setVisible ( true ) ;
    }

    /**
     * Opens up a window in which you can create
     * a new drink type product
     */
    private void new_drink ( ) {
        JFrame laik = new JFrame ( ) ;
        laik.setSize ( 200 , 200 ) ;
        Container laik_content = laik.getContentPane ( ) ;
        laik_content.setLayout ( new BoxLayout ( laik_content , BoxLayout.Y_AXIS ) ) ;
        laik.setLocationRelativeTo ( null ) ;

        JTextField pavadinimas = new JTextField ( "" ) ;
        JLabel pavadinimas_info = new JLabel ( "Pavadinimas" ) ;
        pavadinimas_info.setLabelFor ( pavadinimas ) ;
        laik_content.add ( pavadinimas_info ) ;
        laik_content.add ( pavadinimas ) ;

        JTextField kaina = new JTextField ( "" ) ;
        JLabel kaina_info = new JLabel ( "Kaina" ) ;
        kaina_info.setLabelFor ( kaina ) ;
        laik_content.add ( kaina_info ) ;
        laik_content.add ( kaina ) ;

        JTextField talpa = new JTextField ( "" ) ;
        JLabel talpa_info = new JLabel ( "Talpa" ) ;
        talpa_info.setLabelFor ( talpa ) ;
        laik_content.add ( talpa_info ) ;
        laik_content.add ( talpa ) ;

        JButton patvirtinimas = new JButton ( "Patvirtinti" ) ;
        patvirtinimas.addActionListener ( new ActionListener ( ) {
            public void actionPerformed(ActionEvent actionEvent) {
                if ( pavadinimas.getText().equals("") || kaina.getText().equals("") ||
                        talpa.getText().equals("") ) {
                    JOptionPane temp = new JOptionPane () ;
                    temp.showMessageDialog ( null , "Ne visi laukai užpildyti!" , "" ,
                            JOptionPane.ERROR_MESSAGE ) ;
                }
                else {
                    main_server.add_drink( Double.parseDouble ( kaina.getText ( ) ) , pavadinimas.getText ( ) ,
                            Double.parseDouble ( talpa.getText ( ) ) ) ;
                    data.setListData ( main_server.getList ( ) ) ;
                    laik.setVisible ( false ) ;
                    laik.dispose ( ) ;
                    frame.validate ( ) ;
                    frame.repaint ( ) ;
                }
            }
        } ) ;
        laik_content.add ( patvirtinimas ) ;

        laik.setResizable ( false ) ;
        laik.setVisible ( true ) ;
    }

    /**
     * Opens up a window in which you can create
     * a new clothes type product
     */
    private void new_clothes ( ) {
        JFrame laik = new JFrame ( ) ;
        Container laik_content = laik.getContentPane ( ) ;
        laik_content.setLayout ( new BoxLayout ( laik_content , BoxLayout.Y_AXIS ) ) ;
        laik.setSize ( 200 , 200 ) ;
        laik.setLocationRelativeTo ( null ) ;

        JTextField pavadinimas = new JTextField ( "" ) ;
        JLabel pavadinimas_info = new JLabel ( "Pavadinimas" ) ;
        pavadinimas_info.setLabelFor ( pavadinimas ) ;
        laik_content.add ( pavadinimas_info ) ;
        laik_content.add ( pavadinimas ) ;

        JTextField kaina = new JTextField ( "" ) ;
        JLabel kaina_info = new JLabel ( "Kaina" ) ;
        kaina_info.setLabelFor ( kaina ) ;
        laik_content.add ( kaina_info ) ;
        laik_content.add ( kaina ) ;

        JTextField dydis = new JTextField ( "" ) ;
        JLabel dydis_info = new JLabel ( "Dydis" ) ;
        dydis_info.setLabelFor ( dydis ) ;
        laik_content.add ( dydis_info ) ;
        laik_content.add ( dydis ) ;

        JButton patvirtinimas = new JButton ( "Patvirtinti" ) ;
        patvirtinimas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if ( pavadinimas.getText().equals("") || kaina.getText().equals("") ||
                        dydis.getText().equals("") ) {
                    JOptionPane temp = new JOptionPane ( ) ;
                    temp.showMessageDialog ( null , "Ne visi laukai užpildyti" , "" ,
                            JOptionPane.ERROR_MESSAGE ) ;
                }
                else {
                    main_server.add_clothes( Double.parseDouble ( kaina.getText ( ) ) , pavadinimas.getText ( )
                    , Integer.parseInt ( dydis.getText ( ) ) ) ;
                    laik.setVisible ( false ) ;
                    laik.dispose ( ) ;
                    data.setListData ( main_server.getList ( ) ) ;
                    frame.validate ( ) ;
                    frame.repaint ( ) ;
                }
            }
        } ) ;
        laik_content.add ( patvirtinimas ) ;

        laik.setResizable ( false ) ;
        laik.setVisible ( true ) ;
    }

    /**
     * Opens up a window in which you can create
     * a new food type product
     */
    private void new_food ( ) {
        JFrame laik = new JFrame ( ) ;
        Container laik_content = laik.getContentPane ( ) ;
        laik_content.setLayout ( new BoxLayout ( laik_content , BoxLayout.Y_AXIS ) ) ;
        laik.setSize ( 200 , 200 ) ;
        laik.setLocationRelativeTo ( null ) ;

        JTextField pavadinimas = new JTextField ( "" ) ;
        JLabel pavadinimas_info = new JLabel ( "Pavadinimas" ) ;
        pavadinimas_info.setLabelFor ( pavadinimas ) ;
        laik_content.add ( pavadinimas_info ) ;
        laik_content.add ( pavadinimas ) ;

        JTextField kaina = new JTextField ( "" ) ;
        JLabel kaina_info = new JLabel ( "Kaina" ) ;
        kaina_info.setLabelFor ( kaina ) ;
        laik_content.add ( kaina_info ) ;
        laik_content.add ( kaina ) ;

        JTextField galiojimas = new JTextField ( "" ) ;
        JLabel galiojimas_info = new JLabel ( "Galiojimas(h)" ) ;
        galiojimas_info.setLabelFor ( galiojimas ) ;
        laik_content.add ( galiojimas_info ) ;
        laik_content.add ( galiojimas ) ;

        JButton patvirtinimas = new JButton ( "Patvirtinti" ) ;
        patvirtinimas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if ( pavadinimas.getText().equals("") || kaina.getText().equals("") ||
                        galiojimas.getText().equals("") ) {
                    JOptionPane temp = new JOptionPane ( ) ;
                    temp.showMessageDialog ( null , "Ne visi laukai užpildyti!" , ""
                    , JOptionPane.ERROR_MESSAGE ) ;
                }
                else {
                    main_server.add_food( Double.parseDouble ( kaina.getText ( ) ) , pavadinimas.getText ( ) ,
                            Integer.parseInt ( galiojimas.getText ( ) ) ) ;
                    laik.setVisible ( false ) ;
                    laik.dispose ( ) ;
                    data.setListData ( main_server.getList ( ) ) ;
                    frame.validate ( ) ;
                    frame.repaint ( ) ;
                }
            }
        } ) ;
        laik_content.add ( patvirtinimas ) ;

        laik.setResizable ( false ) ;
        laik.setVisible ( true ) ;
    }

    /**
     * Opens up a window in which you can create
     * a new product type product
     */
    private void new_product ( ) {
        JFrame laik = new JFrame ( ) ;
        Container laik_content = laik.getContentPane ( ) ;
        laik_content.setLayout ( new BoxLayout ( laik_content , BoxLayout.Y_AXIS ) ) ;
        laik.setSize ( 200 , 200 ) ;
        laik.setLocationRelativeTo ( null ) ;

        JTextField pavadinimas = new JTextField ( "" ) ;
        JLabel pavadinimas_info = new JLabel ( "Pavadinimas" ) ;
        pavadinimas_info.setLabelFor ( pavadinimas ) ;
        laik_content.add ( pavadinimas_info ) ;
        laik_content.add ( pavadinimas ) ;

        JTextField kaina = new JTextField ( "" ) ;
        JLabel kaina_info = new JLabel ( "Kaina" ) ;
        kaina_info.setLabelFor ( kaina ) ;
        laik_content.add ( kaina_info ) ;
        laik_content.add ( kaina ) ;

        JButton patvirtinimas = new JButton ( "Patvirtinti" ) ;
        patvirtinimas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if ( pavadinimas.getText().equals("") || kaina.getText().equals("") ) {
                    JOptionPane temp = new JOptionPane ( ) ;
                    temp.showMessageDialog ( null , "Ne visi laukai užpildyti!" ,
                            "" , JOptionPane.ERROR_MESSAGE ) ;
                }
                else {
                    main_server.add_product( pavadinimas.getText ( ) ,
                            Double.parseDouble ( kaina.getText ( ) ) ) ;
                    laik.setVisible ( false ) ;
                    laik.dispose ( ) ;
                    data.setListData ( main_server.getList ( ) ) ;
                    frame.validate ( ) ;
                    frame.repaint ( ) ;
                }
            }
        } ) ;
        laik_content.add ( patvirtinimas ) ;

        laik.setResizable ( false ) ;
        laik.setVisible ( true ) ;
    }

    /**
     * Disables the active graphical user interface
     */
    public void disableGUI ( ) {
        this.thread_alive = false ;
        frame.setVisible ( false ) ;
        frame.dispose ( ) ;
    }
}
