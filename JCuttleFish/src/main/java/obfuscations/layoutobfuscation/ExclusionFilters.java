package obfuscations.layoutobfuscation;

import org.eclipse.jdt.core.dom.*;
import util.OptionalUtils;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;


public class ExclusionFilters
{

    public static Predicate<MethodDeclaration> excludedMethods = md -> !md.isConstructor() &&
            !md.getName().getIdentifier().equals( "main" ) &&
            !md.getName().getIdentifier().equals( "compareTo" ) &&
            !md.getName().getIdentifier().equals( "equals" ) &&
            !md.getName().getIdentifier().equals( "toString" ) &&
            !md.getName().getIdentifier().equals( "accept" ) &&
            !md.getName().getIdentifier().equals( "clone" ) &&
            !md.getName().getIdentifier().equals( "finalize" );
    //
    public static Predicate<AbstractTypeDeclaration> excludedAbstractTypeDeclarations = atd -> {
        if ( atd instanceof TypeDeclaration )
        {
            List<Type> implementedInterfaces = ( ( TypeDeclaration )atd ).superInterfaceTypes();
            //Exclude Serializable
            return implementedInterfaces.stream()
                    .filter( t -> t.isSimpleType() )
                    .map( SimpleType.class::cast )
                    .noneMatch( simpleType -> simpleType.getName().getFullyQualifiedName().equals( "Serializable" ) );
        } else if ( atd instanceof EnumDeclaration )
        {
            return false;
        }
        return true;
    };

    public static BiFunction<SimpleName, IVariableBinding, Boolean> isSimpleNameEqualToMethodParam = ( sn, ivb ) ->
            OptionalUtils.getIVariableBinding( sn ).isPresent() &&
                    OptionalUtils.getIVariableBinding( sn ).get().isParameter() &&
                    OptionalUtils.getIVariableBinding( sn ).get().isEqualTo( ivb );
}
