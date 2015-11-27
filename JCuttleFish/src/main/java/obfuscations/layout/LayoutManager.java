package obfuscations.layout;

import obfuscations.layout.visitors.StatementVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.UnitSource;
import util.SourceUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


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
                Collection<AstNodeFoundCallback> callbacks = new HashSet<>();
                callbacks.add( new SimpleNameNodeFoundCallBack() );

                List<MethodDeclaration> methodDeclarations = Arrays.stream( typeDecl.getMethods() )
                        .collect( Collectors.toList() );
                for ( MethodDeclaration methodDeclaration : methodDeclarations )
                {
                    List<Statement> statements = methodDeclaration.getBody().statements();
                    statements.stream().forEach( s -> new StatementVisitor( callbacks ).visit( s ) );
                }

                int debug = 1;
            }
        }
        //TODO: Move "replace" to ObfuscationCoordinator
        return SourceUtil.replace( unitSource );
    }
}
