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

    public PanelChoixType ( Fenetre ff )
    {
        this.i = ff;
        String[] types = new String[]{ "Musique", "Video", "Documents Texte", "Images", "Gestion", "Cours" };
        this.a = BorderFactory.createLineBorder( Color.black );
        this.b = BorderFactory.createTitledBorder( a, "Gestion des types" );
        this.c = new JComboBox( types );
        this.e = new JTextArea( 2, 8 );
        this.e.setBorder( this.a );
        this.f = new JButton( "Ajouter" );
        this.g = new JButton( "Supprimer" );
        String[] ext = new String[]{ "mp3", "wav", "machin" };
        this.h = new JList( ext );
        setLayout( new FlowLayout() );

        add( this.c );
        add( this.h );
        add( this.g );
        add( this.e );
        add( this.f );
    }

}