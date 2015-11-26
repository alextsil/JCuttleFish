package obfuscations.layout;

import obfuscations.layout.visitors.MethodDeclarationVisitor;
import obfuscations.layout.visitors.StatementVisitor;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.ObfuscationInfo;
import pojo.UnitSource;
import providers.ObfuscatedNamesProvider;
import util.ConvenienceWrappers;
import util.SourceUtil;
import util.enums.ObfuscatedNamesVariations;

import java.util.Deque;
import java.util.List;


public class LayoutManager
{

    private final Logger logger = LoggerFactory.getLogger( LayoutManager.class );

    public UnitSource obfuscate ( UnitSource unitSource )
    {
        CompilationUnit cu = unitSource.getCompilationUnit();
        cu.recordModifications();
        ObfuscatedNamesProvider obfNamesProvider = new ObfuscatedNamesProvider();
        Deque<String> obfuscatedVariableNames = obfNamesProvider.getObfuscatedNames( ObfuscatedNamesVariations.ALPHABET );

        if ( !cu.types().isEmpty() )
        {
            TypeDeclaration typeDecl = ( TypeDeclaration )cu.types().get( 0 );
            if ( typeDecl.resolveBinding().isClass() )
            {
                for ( FieldDeclaration fieldDeclaration : ConvenienceWrappers.getPrivateFieldDeclarations( typeDecl ) )
                {
                    VariableDeclarationFragment originalVdf = ( VariableDeclarationFragment )fieldDeclaration.fragments().get( 0 );
                    SimpleName originalVarSimpleName = originalVdf.getName();
                    String obfuscatedVarName = obfuscatedVariableNames.pollFirst();
                    ObfuscationInfo obfuscationInfo = new ObfuscationInfo( originalVarSimpleName, obfuscatedVarName, cu.getAST() );

                    for ( MethodDeclaration methodDeclaration : typeDecl.getMethods() )
                    {
                        List<Statement> statements = methodDeclaration.getBody().statements();
                        statements.stream().forEach( s -> new StatementVisitor( obfuscationInfo ).visit( s ) );

                        new MethodDeclarationVisitor( obfuscationInfo ).visit( methodDeclaration );
                    }
                    //Change declaration name after modifying all usages.
                    originalVdf.getName().setIdentifier( obfuscatedVarName );
                }
            } else
            {
                throw new RuntimeException( "Bad input. isClass returned false." );
            }
        }
        //TODO: Move "replace" to ObfuscationCoordinator
        return SourceUtil.replace( unitSource );
    }
}
