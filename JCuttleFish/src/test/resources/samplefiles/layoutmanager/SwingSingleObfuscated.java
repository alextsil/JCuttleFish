package test;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;


public class PanelChoixType extends JPanel
{

    private Border a;

    private TitledBorder b;

    private JComboBox c;

    private JComboBox d;

    private JTextArea e;

    private JButton f;

    private JButton g;

    private JList h;

    private Fenetre i;

    public PanelChoixType ( Fenetre aa )
    {
        this.i = aa;
        String[] aaa = new String[]{ "Musique", "Video", "Documents Texte", "Images", "Gestion", "Cours" };
        a = BorderFactory.createLineBorder( Color.black );
        b = BorderFactory.createTitledBorder( a, "Gestion des types" );
        c = new JComboBox( aaa );
        e = new JTextArea( 2, 8 );
        e.setBorder( a );
        f = new JButton( "Ajouter" );
        g = new JButton( "Supprimer" );
        String[] bbb = new String[]{ "mp3", "wav", "machin" };
        h = new JList( bbb );
        setLayout( new FlowLayout() );

        add( c );
        add( h );
        add( g );
        add( e );
        add( f );
    }

}