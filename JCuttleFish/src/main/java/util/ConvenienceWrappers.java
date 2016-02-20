package util;

import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;


public class ConvenienceWrappers
{

    public static Predicate<MethodDeclaration> excludedMethods = md -> !md.isConstructor() &&
            !md.getName().getIdentifier().equals( "main" ) &&
            !md.getName().getIdentifier().equals( "compareTo" ) &&
            !md.getName().getIdentifier().equals( "equals" ) &&
            !md.getName().getIdentifier().equals( "toString" ) &&
            !md.getName().getIdentifier().equals( "accept" ) &&
            !md.getName().getIdentifier().equals( "clone" ) &&
            !md.getName().getIdentifier().equals( "finalize" );

    public static Collection<FieldDeclaration> getPrivateFieldDeclarations ( AbstractTypeDeclaration abstractTypeDeclaration )
    {
        Collection<FieldDeclaration> privateFieldDeclarations = new ArrayList<>();

        if ( abstractTypeDeclaration instanceof TypeDeclaration )
        {
            TypeDeclaration typeDeclaration = ( TypeDeclaration )abstractTypeDeclaration;
            FieldDeclaration[] fieldDeclarations = typeDeclaration.getFields();
            privateFieldDeclarations = Arrays.asList( fieldDeclarations ).stream()
                    .filter( fd -> Modifier.isPrivate( fd.getModifiers() ) ).collect( toList() );

        } else if ( abstractTypeDeclaration instanceof EnumDeclaration )
        {
            EnumDeclaration enumDeclaration = ( EnumDeclaration )abstractTypeDeclaration;
            Collection<FieldDeclaration> fieldDeclarations = ( Collection<FieldDeclaration> )enumDeclaration.bodyDeclarations().stream()
                    .filter( bd -> bd instanceof FieldDeclaration )
                    .collect( toList() );
            //Find the private ones
            privateFieldDeclarations = fieldDeclarations.stream()
                    .filter( fd -> Modifier.isPrivate( fd.getModifiers() ) ).collect( toList() );
        }

        return privateFieldDeclarations;
    }

    public static Collection<FieldDeclaration> getFieldDeclarationsAsList ( AbstractTypeDeclaration abstractTypeDeclaration )
    {
        if ( abstractTypeDeclaration instanceof TypeDeclaration )
        {
            TypeDeclaration typeDeclaration = ( TypeDeclaration )abstractTypeDeclaration;
            return Arrays.stream( typeDeclaration.getFields() ).collect( toList() );
        } else if ( abstractTypeDeclaration instanceof EnumDeclaration )
        {
            EnumDeclaration enumDeclaration = ( EnumDeclaration )abstractTypeDeclaration;
            return ( Collection<FieldDeclaration> )enumDeclaration.bodyDeclarations().stream()
                    .filter( bd -> bd instanceof FieldDeclaration )
                    .collect( toList() );
        } else if ( abstractTypeDeclaration instanceof AnnotationTypeDeclaration )
        {
            throw new RuntimeException( "not implemented" );
        }

        throw new RuntimeException( "Unknown AbstractTypeDeclaration : " + abstractTypeDeclaration.getClass() );
    }

    public static Collection<MethodDeclaration> getMethodDeclarationsAsList ( AbstractTypeDeclaration abstractTypeDeclaration )
    {
        if ( abstractTypeDeclaration instanceof TypeDeclaration )
        {
            TypeDeclaration typeDeclaration = ( TypeDeclaration )abstractTypeDeclaration;
            return Arrays.stream( typeDeclaration.getMethods() ).collect( toList() );
        } else if ( abstractTypeDeclaration instanceof EnumDeclaration )
        {
            EnumDeclaration enumDeclaration = ( EnumDeclaration )abstractTypeDeclaration;
            return ( Collection<MethodDeclaration> )enumDeclaration.bodyDeclarations().stream()
                    .filter( bd -> bd instanceof MethodDeclaration )
                    .collect( toList() );
        } else if ( abstractTypeDeclaration instanceof AnnotationTypeDeclaration )
        {
            throw new RuntimeException( "not implemented" );
        }

        throw new RuntimeException( "Unknown AbstractTypeDeclaration : " + abstractTypeDeclaration.getClass() );
    }

}
