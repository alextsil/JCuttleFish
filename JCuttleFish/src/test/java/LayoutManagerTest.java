import obfuscations.NodeFinder;
import obfuscations.layout.LayoutManager;
import org.junit.Test;
import parser.UnitSourceInitiator;
import providers.FileSourceCodeProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;


public class LayoutManagerTest
{

    private LayoutManager layoutManager = new LayoutManager();
    private UnitSourceInitiator initiator = new UnitSourceInitiator();
    private NodeFinder nodeFinder = new NodeFinder();
    private FileSourceCodeProvider sourceCodeProvider = new FileSourceCodeProvider();

    @Test
    public void testObfuscate_UnitSource ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/UnitSourceOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/UnitSourceObfuscated.java" );

        assertEquals( this.sourceCodeProvider.get( obfuscatedFile ),
                this.layoutManager.obfuscate( this.nodeFinder
                        .getUnitNodesCollectionFromUnitSources( Stream.of( this.initiator.fetchUnitSource( originalFile ) )
                                .collect( Collectors.toCollection( ArrayList::new ) ) ) ).stream().collect( Collectors.toCollection( ArrayList::new ) )
                        .get( 0 ).getUnitSource().getDocument().get() );
    }

    @Test
    public void testObfuscate_ObfuscationEnvironment ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/ObfuscationEnvironmentOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/ObfuscationEnvironmentObfuscated.java" );

        assertEquals( this.sourceCodeProvider.get( obfuscatedFile ),
                this.layoutManager.obfuscate( this.nodeFinder
                        .getUnitNodesCollectionFromUnitSources( Stream.of( this.initiator.fetchUnitSource( originalFile ) )
                                .collect( Collectors.toCollection( ArrayList::new ) ) ) ).stream().collect( Collectors.toCollection( ArrayList::new ) )
                        .get( 0 ).getUnitSource().getDocument().get() );
    }

    @Test
    public void testObfuscate_PrivateProtectedMatcher ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/PrivateProtectedMatcherOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/PrivateProtectedMatcherObfuscated.java" );

        assertEquals( this.sourceCodeProvider.get( obfuscatedFile ),
                this.layoutManager.obfuscate( this.nodeFinder
                        .getUnitNodesCollectionFromUnitSources( Stream.of( this.initiator.fetchUnitSource( originalFile ) )
                                .collect( Collectors.toCollection( ArrayList::new ) ) ) ).stream().collect( Collectors.toCollection( ArrayList::new ) )
                        .get( 0 ).getUnitSource().getDocument().get() );
    }

    //Testing : pojo
    @Test
    public void testObfuscate_Contact ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/ContactOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/ContactObfuscated.java" );

        assertEquals( this.sourceCodeProvider.get( obfuscatedFile ),
                this.layoutManager.obfuscate( this.nodeFinder
                        .getUnitNodesCollectionFromUnitSources( Stream.of( this.initiator.fetchUnitSource( originalFile ) )
                                .collect( Collectors.toCollection( ArrayList::new ) ) ) ).stream().collect( Collectors.toCollection( ArrayList::new ) )
                        .get( 0 ).getUnitSource().getDocument().get() );
    }

    @Test
    public void testObfuscate_SuffixFolderFilter ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/SuffixFolderFilterOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/SuffixFolderFilterObfuscated.java" );

        assertEquals( this.sourceCodeProvider.get( obfuscatedFile ),
                this.layoutManager.obfuscate( this.nodeFinder
                        .getUnitNodesCollectionFromUnitSources( Stream.of( this.initiator.fetchUnitSource( originalFile ) )
                                .collect( Collectors.toCollection( ArrayList::new ) ) ) ).stream().collect( Collectors.toCollection( ArrayList::new ) )
                        .get( 0 ).getUnitSource().getDocument().get() );
    }

    @Test
    public void testObfuscate_SwingSingle ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/SwingSingleOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/SwingSingleObfuscated.java" );

        assertEquals( this.sourceCodeProvider.get( obfuscatedFile ),
                this.layoutManager.obfuscate( this.nodeFinder
                        .getUnitNodesCollectionFromUnitSources( Stream.of( this.initiator.fetchUnitSource( originalFile ) )
                                .collect( Collectors.toCollection( ArrayList::new ) ) ) ).stream().collect( Collectors.toCollection( ArrayList::new ) )
                        .get( 0 ).getUnitSource().getDocument().get() );
    }

    /* Testing :
    - Multiple arguments on return
    - Static variable obfuscation.
    - Private only (class) fields obfuscation
    */
    @Test
    public void testObfuscate_Stopwatch ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/StopwatchOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/StopwatchObfuscated.java" );

        assertEquals( this.sourceCodeProvider.get( obfuscatedFile ),
                this.layoutManager.obfuscate( this.nodeFinder
                        .getUnitNodesCollectionFromUnitSources( Stream.of( this.initiator.fetchUnitSource( originalFile ) )
                                .collect( Collectors.toCollection( ArrayList::new ) ) ) ).stream().collect( Collectors.toCollection( ArrayList::new ) )
                        .get( 0 ).getUnitSource().getDocument().get() );
    }

    /* Testing :
    - Static variable obfuscation
    - Final variable obfuscation
    - Static final variable ofbuscation
     */
    @Test
    public void testObfuscate_StaticFinalTest ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/staticFinalOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/staticFinalObfuscated.java" );

        assertEquals( this.sourceCodeProvider.get( obfuscatedFile ),
                this.layoutManager.obfuscate( this.nodeFinder
                        .getUnitNodesCollectionFromUnitSources( Stream.of( this.initiator.fetchUnitSource( originalFile ) )
                                .collect( Collectors.toCollection( ArrayList::new ) ) ) ).stream().collect( Collectors.toCollection( ArrayList::new ) )
                        .get( 0 ).getUnitSource().getDocument().get() );
    }

    @Test
    public void testObfuscate_Issue36_37_38 ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/Issue36_37_38Original.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/Issue36_37_38Obfuscated.java" );

        assertEquals( this.sourceCodeProvider.get( obfuscatedFile ),
                this.layoutManager.obfuscate( this.nodeFinder
                        .getUnitNodesCollectionFromUnitSources( Stream.of( this.initiator.fetchUnitSource( originalFile ) )
                                .collect( Collectors.toCollection( ArrayList::new ) ) ) ).stream().collect( Collectors.toCollection( ArrayList::new ) )
                        .get( 0 ).getUnitSource().getDocument().get() );
    }

    @Test
    public void testObfuscate_AnimationsTest ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/animationsOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/animationsObfuscated.java" );

        assertEquals( this.sourceCodeProvider.get( obfuscatedFile ),
                this.layoutManager.obfuscate( this.nodeFinder
                        .getUnitNodesCollectionFromUnitSources( Stream.of( this.initiator.fetchUnitSource( originalFile ) )
                                .collect( Collectors.toCollection( ArrayList::new ) ) ) ).stream().collect( Collectors.toCollection( ArrayList::new ) )
                        .get( 0 ).getUnitSource().getDocument().get() );
    }

    @Test
    public void testObfuscate_Issue40 ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/Issue40Original.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/Issue40Obfuscated.java" );

        assertEquals( this.sourceCodeProvider.get( obfuscatedFile ),
                this.layoutManager.obfuscate( this.nodeFinder
                        .getUnitNodesCollectionFromUnitSources( Stream.of( this.initiator.fetchUnitSource( originalFile ) )
                                .collect( Collectors.toCollection( ArrayList::new ) ) ) ).stream().collect( Collectors.toCollection( ArrayList::new ) )
                        .get( 0 ).getUnitSource().getDocument().get() );
    }

    @Test
    public void testObfuscate_IfTree ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/IfTreeOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/IfTreeObfuscated.java" );

        assertEquals( this.sourceCodeProvider.get( obfuscatedFile ),
                this.layoutManager.obfuscate( this.nodeFinder
                        .getUnitNodesCollectionFromUnitSources( Stream.of( this.initiator.fetchUnitSource( originalFile ) )
                                .collect( Collectors.toCollection( ArrayList::new ) ) ) ).stream().collect( Collectors.toCollection( ArrayList::new ) )
                        .get( 0 ).getUnitSource().getDocument().get() );
    }

    @Test
    public void testObfuscate_PostfixExpressions ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/PostfixExpressionsOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/PostfixExpressionsObfuscated.java" );

        assertEquals( this.sourceCodeProvider.get( obfuscatedFile ),
                this.layoutManager.obfuscate( this.nodeFinder
                        .getUnitNodesCollectionFromUnitSources( Stream.of( this.initiator.fetchUnitSource( originalFile ) )
                                .collect( Collectors.toCollection( ArrayList::new ) ) ) ).stream().collect( Collectors.toCollection( ArrayList::new ) )
                        .get( 0 ).getUnitSource().getDocument().get() );
    }

    @Test
    public void testObfuscate_WhileStatements ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/WhileStatementsOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/WhileStatementsObfuscated.java" );

        assertEquals( this.sourceCodeProvider.get( obfuscatedFile ),
                this.layoutManager.obfuscate( this.nodeFinder
                        .getUnitNodesCollectionFromUnitSources( Stream.of( this.initiator.fetchUnitSource( originalFile ) )
                                .collect( Collectors.toCollection( ArrayList::new ) ) ) ).stream().collect( Collectors.toCollection( ArrayList::new ) )
                        .get( 0 ).getUnitSource().getDocument().get() );
    }

    @Test
    public void testObfuscate_DoStatements ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/DoStatementsOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/DoStatementsObfuscated.java" );

        assertEquals( this.sourceCodeProvider.get( obfuscatedFile ),
                this.layoutManager.obfuscate( this.nodeFinder
                        .getUnitNodesCollectionFromUnitSources( Stream.of( this.initiator.fetchUnitSource( originalFile ) )
                                .collect( Collectors.toCollection( ArrayList::new ) ) ) ).stream().collect( Collectors.toCollection( ArrayList::new ) )
                        .get( 0 ).getUnitSource().getDocument().get() );
    }

    @Test
    public void testObfuscate_SwitchStatements ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/SwitchStatementsOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/SwitchStatementsObfuscated.java" );

        assertEquals( this.sourceCodeProvider.get( obfuscatedFile ),
                this.layoutManager.obfuscate( this.nodeFinder
                        .getUnitNodesCollectionFromUnitSources( Stream.of( this.initiator.fetchUnitSource( originalFile ) )
                                .collect( Collectors.toCollection( ArrayList::new ) ) ) ).stream().collect( Collectors.toCollection( ArrayList::new ) )
                        .get( 0 ).getUnitSource().getDocument().get() );
    }

    @Test
    public void testObfuscate_TryStatement ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/TryStatementOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/TryStatementObfuscated.java" );

        assertEquals( this.sourceCodeProvider.get( obfuscatedFile ),
                this.layoutManager.obfuscate( this.nodeFinder
                        .getUnitNodesCollectionFromUnitSources( Stream.of( this.initiator.fetchUnitSource( originalFile ) )
                                .collect( Collectors.toCollection( ArrayList::new ) ) ) ).stream().collect( Collectors.toCollection( ArrayList::new ) )
                        .get( 0 ).getUnitSource().getDocument().get() );
    }

    @Test
    public void testObfuscate_ArrayAccess ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/ArrayAccessOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/ArrayAccessObfuscated.java" );

        assertEquals( this.sourceCodeProvider.get( obfuscatedFile ),
                this.layoutManager.obfuscate( this.nodeFinder
                        .getUnitNodesCollectionFromUnitSources( Stream.of( this.initiator.fetchUnitSource( originalFile ) )
                                .collect( Collectors.toCollection( ArrayList::new ) ) ) ).stream().collect( Collectors.toCollection( ArrayList::new ) )
                        .get( 0 ).getUnitSource().getDocument().get() );
    }

    @Test
    public void testObfuscate_MethodVars ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/MethodVarsOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/MethodVarsObfuscated.java" );

        assertEquals( this.sourceCodeProvider.get( obfuscatedFile ),
                this.layoutManager.obfuscate( this.nodeFinder
                        .getUnitNodesCollectionFromUnitSources( Stream.of( this.initiator.fetchUnitSource( originalFile ) )
                                .collect( Collectors.toCollection( ArrayList::new ) ) ) ).stream().collect( Collectors.toCollection( ArrayList::new ) )
                        .get( 0 ).getUnitSource().getDocument().get() );
    }

    @Test
    public void testObfuscate_SuperInvocation ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/SuperInvocationOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/SuperInvocationObfuscated.java" );

        assertEquals( this.sourceCodeProvider.get( obfuscatedFile ),
                this.layoutManager.obfuscate( this.nodeFinder
                        .getUnitNodesCollectionFromUnitSources( Stream.of( this.initiator.fetchUnitSource( originalFile ) )
                                .collect( Collectors.toCollection( ArrayList::new ) ) ) ).stream().collect( Collectors.toCollection( ArrayList::new ) )
                        .get( 0 ).getUnitSource().getDocument().get() );
    }

    //A "scratchpad" test
    @Test
    public void onthefly ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/ontheflyOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/ontheflyObfuscated.java" );

        assertEquals( this.sourceCodeProvider.get( obfuscatedFile ),
                this.layoutManager.obfuscate( this.nodeFinder
                        .getUnitNodesCollectionFromUnitSources( Stream.of( this.initiator.fetchUnitSource( originalFile ) )
                                .collect( Collectors.toCollection( ArrayList::new ) ) ) ).stream().collect( Collectors.toCollection( ArrayList::new ) )
                        .get( 0 ).getUnitSource().getDocument().get() );
    }
}