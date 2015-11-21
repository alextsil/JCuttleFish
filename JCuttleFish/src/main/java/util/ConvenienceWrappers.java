package util;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.ArrayList;
import java.util.List;


public class ConvenienceWrappers
{

    /**
     * Receives a TypeDeclaration (essentially a Class) and returns a List of private fields as FieldDeclaration.
     *
     * @param typeDeclaration - The target class
     * @return List of private FieldDeclaration. Empty list if none found.
     */
    static public List<FieldDeclaration> getPrivateFieldDeclarations ( TypeDeclaration typeDeclaration )
    {
        List<FieldDeclaration> privateFieldDeclarations = new ArrayList<>();
        FieldDeclaration[] fieldDeclarations = typeDeclaration.getFields();
        for ( int i = 0; i < fieldDeclarations.length; i++ )
        {
            //Get list of field modifiers to check if it's a private variable or not.
            List<Modifier> modifiers = fieldDeclarations[ i ].modifiers();
            if ( modifiers.stream().anyMatch( m -> m.isPrivate() ) )
            {
                privateFieldDeclarations.add( fieldDeclarations[ i ] );
            }
        }
        return privateFieldDeclarations;
    }

}
