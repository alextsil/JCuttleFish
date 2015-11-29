package obfuscations.layout;

import obfuscations.layout.callbacks.AstNodeFoundCallback;
import obfuscations.layout.callbacks.SimpleNameNodeFoundCallBack;
import obfuscations.layout.visitors.StatementVisitor;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.UnitSource;
import util.ConvenienceWrappers;
import util.SourceUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


//https://github.com/alextsil/JCuttleFish/blob/884dc2f0651e7d94b41164a27fa5cdb268cace34/JCuttleFish/src/main/java/obfuscations/layout/LayoutManager.java
public class LayoutManager
{

    private final Logger logger = LoggerFactory.getLogger( LayoutManager.class );

    public UnitSource obfuscate ( UnitSource unitSource )
    {
        CompilationUnit cu = unitSource.getCompilationUnit();
        cu.recordModifications();

        if ( !cu.types().isEmpty() )
        {
            TypeDeclaration typeDecl = ( TypeDeclaration )cu.types().get( 0 );
            if ( typeDecl.resolveBinding().isClass() )
            {
                List<FieldDeclaration> classFields = ConvenienceWrappers.getPrivateFieldDeclarations( typeDecl );
                Collection<AstNodeFoundCallback> callbacks = this.registerAndReturnCallbacks();

                Collection<MethodDeclaration> methodDeclarations = this.returnMethodDeclarations( typeDecl );
                for ( MethodDeclaration methodDeclaration : methodDeclarations )
                {
                    List<Statement> statements = methodDeclaration.getBody().statements();
                    statements.stream().forEach( s -> new StatementVisitor( callbacks ).visit( s ) );
                }

                List<SimpleName> classFieldsAsSimpleNames = classFields.stream()
                        .map( f -> ( VariableDeclarationFragment )f.fragments().get( 0 ) )
                        .map( vdf -> vdf.getName() ).collect( Collectors.toList() );

                List<SimpleName> obfuscationCandidates = callbacks.stream()
                        .filter( c -> c instanceof SimpleNameNodeFoundCallBack )
                        .map( c -> ( SimpleNameNodeFoundCallBack )c )
                        .findFirst()
                        .get()
                        .getFoundNodes()
                        .stream()
                        .map( SimpleName.class::cast )
                        .collect( Collectors.toList() );
                //TODO : Filters candidates based on local class variables.
                //the criteria has to be the simplename's qualifier and not the actual object.
                //TODO : run obfuscation on both lists.
                int debug = 1;
            }
        }
        //TODO: Move "replace" to ObfuscationCoordinator
        return SourceUtil.replace( unitSource );
    }

    private Collection<AstNodeFoundCallback> registerAndReturnCallbacks ()
    {
        Collection<AstNodeFoundCallback> callbacks = new ArrayList<>();
        callbacks.add( new SimpleNameNodeFoundCallBack() );
        return callbacks;
    }

    private Collection<MethodDeclaration> returnMethodDeclarations ( TypeDeclaration typeDeclaration )
    {
        return Arrays.stream( typeDeclaration.getMethods() ).collect( Collectors.toList() );
    }
}
