package util;

import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class ConvenienceWrappers
{

    /**
     * Receives a TypeDeclaration (essentially a Class) and returns a List of private fields as FieldDeclaration.
     *
     * @param typeDeclaration - The target class
     * @return List of private FieldDeclaration. Empty list if none found.
     */
    public static List<FieldDeclaration> getPrivateFieldDeclarations ( TypeDeclaration typeDeclaration )
    {
        List<FieldDeclaration> privateFieldDeclarations = new ArrayList<>();
        FieldDeclaration[] fieldDeclarations = typeDeclaration.getFields();
        for ( FieldDeclaration fieldDeclaration : fieldDeclarations )
        {
            //Get list of field modifiers to check if it's a private variable or not.
            List<Modifier> modifiers = fieldDeclaration.modifiers();
            if ( modifiers.stream().anyMatch( m -> m.isPrivate() ) )
            {
                privateFieldDeclarations.add( fieldDeclaration );
            }
        }
        return privateFieldDeclarations;
    }

    public static Collection<MethodDeclaration> returnMethodDeclarations ( TypeDeclaration typeDeclaration )
    {
        return Arrays.stream( typeDeclaration.getMethods() ).collect( Collectors.toList() );
    }

    public static boolean isValidCompilationUnit ( CompilationUnit cu )
    {
        return !cu.types().isEmpty();
    }

}
