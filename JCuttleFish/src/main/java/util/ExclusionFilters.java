package util;

import org.eclipse.jdt.core.dom.*;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;


public class ExclusionFilters
{

    public static Predicate<MethodDeclaration> excludedMethods = md -> !md.isConstructor() &&
            !md.getName().getIdentifier().equals( "main" ) &&
            ConvenienceWrappers.getMethodAnnotationsAsList( md ).stream()
                    .map( IAnnotationBinding::getName )
                    .noneMatch( a -> a.equals( "Override" ) );

    //Excludes Enums and classes that implement Serializable
    public static Predicate<AbstractTypeDeclaration> excludedAbstractTypeDeclarations = atd -> {
        if ( atd instanceof TypeDeclaration )
        {
            List<Type> implementedInterfaces = ( ( TypeDeclaration )atd ).superInterfaceTypes();
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

    //
    public static BiFunction<VariableDeclarationStatement, MethodDeclaration, Boolean> doesVariableBelongToMethod = ( vds, md ) ->
    {
        VariableDeclarationFragment vdf = ( VariableDeclarationFragment )vds.fragments().get( 0 );
        Optional<IVariableBinding> oivb = OptionalUtils.getIVariableBinding( vdf );
        if ( oivb.isPresent() )
        {
            if ( oivb.get().getDeclaringMethod().isEqualTo( md.resolveBinding() ) )
            {
                return true;
            }
        }
        return false;
    };

    public static BiFunction<MethodDeclaration, MethodInvocation, Boolean> isMethodInvocationOfThisMethodDeclaration = ( md, mi ) ->
            md.getName().resolveBinding().isEqualTo( mi.getName().resolveBinding() );

    public static BiFunction<MethodDeclaration, AbstractTypeDeclaration, Boolean> doesMethodBelongToAbstractTypeDeclaration = ( md, atd ) -> {
        AbstractTypeDeclaration parentAbstractTypeDeclaration = ( AbstractTypeDeclaration )md.getParent();
        return ( parentAbstractTypeDeclaration.resolveBinding().isEqualTo( atd.resolveBinding() ) );
    };

}
