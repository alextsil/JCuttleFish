import obfuscations.layout.LayoutManager;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.UnitSourceInitiator;
import providers.FileSourceCodeProvider;

import java.io.File;

import static org.junit.Assert.assertEquals;


public class LayoutManagerTest
{

    LayoutManager layoutManager = new LayoutManager();

    UnitSourceInitiator initiator = new UnitSourceInitiator();

    FileSourceCodeProvider sourceCodeProvider = new FileSourceCodeProvider();

    private final Logger logger = LoggerFactory.getLogger( LayoutManagerTest.class );

    @Test
    public void testObfuscate_UnitSource ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/UnitSourceOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/UnitSourceObfuscated.java" );

        assertEquals( sourceCodeProvider.get( obfuscatedFile ),
                layoutManager.obfuscate( initiator.fetchUnitSource( sourceCodeProvider.get( originalFile ) ) )
                        .getDocument().get() );
    }

    @Test
    public void testObfuscate_ObfuscationEnvironment ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/ObfuscationEnvironmentOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/ObfuscationEnvironmentObfuscated.java" );

        assertEquals( sourceCodeProvider.get( obfuscatedFile ),
                layoutManager.obfuscate( initiator.fetchUnitSource( sourceCodeProvider.get( originalFile ) ) )
                        .getDocument().get() );
    }

    @Test
    public void testObfuscate_PrivateProtectedMatcher ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/PrivateProtectedMatcherOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/PrivateProtectedMatcherObfuscated.java" );

        assertEquals( sourceCodeProvider.get( obfuscatedFile ),
                layoutManager.obfuscate( initiator.fetchUnitSource( sourceCodeProvider.get( originalFile ) ) )
                        .getDocument().get() );
    }

    //Testing : pojo
    @Test
    public void testObfuscate_Contact ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/ContactOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/ContactObfuscated.java" );

        assertEquals( sourceCodeProvider.get( obfuscatedFile ),
                layoutManager.obfuscate( initiator.fetchUnitSource( sourceCodeProvider.get( originalFile ) ) )
                        .getDocument().get() );
    }

    @Test
    public void testObfuscate_SuffixFolderFilter ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/SuffixFolderFilterOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/SuffixFolderFilterObfuscated.java" );

        assertEquals( sourceCodeProvider.get( obfuscatedFile ),
                layoutManager.obfuscate( initiator.fetchUnitSource( sourceCodeProvider.get( originalFile ) ) )
                        .getDocument().get() );
    }

    @Test
    public void testObfuscate_SwingSingle ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/SwingSingleOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/SwingSingleObfuscated.java" );

        assertEquals( sourceCodeProvider.get( obfuscatedFile ),
                layoutManager.obfuscate( initiator.fetchUnitSource( sourceCodeProvider.get( originalFile ) ) )
                        .getDocument().get() );
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

        assertEquals( sourceCodeProvider.get( obfuscatedFile ),
                layoutManager.obfuscate( initiator.fetchUnitSource( sourceCodeProvider.get( originalFile ) ) )
                        .getDocument().get() );
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

        assertEquals( sourceCodeProvider.get( obfuscatedFile ),
                layoutManager.obfuscate( initiator.fetchUnitSource( sourceCodeProvider.get( originalFile ) ) )
                        .getDocument().get() );
    }

    /* Testing :
    - #36 Rename and thisify class field when there is a METHOD_INVOCATION on it
    - #37 Rename and thisify class field when there is an InfixExpression on the assignment
    - #38 Rename and thisify class field when there is an PrefixExpression on the assignment
     */
    @Test
    public void testObfuscate_Issue36_37_38 ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/Issue36_37_38Original.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/Issue36_37_38Obfuscated.java" );

        assertEquals( sourceCodeProvider.get( obfuscatedFile ),
                layoutManager.obfuscate( initiator.fetchUnitSource( sourceCodeProvider.get( originalFile ) ) )
                        .getDocument().get() );
    }

    @Test
    public void testObfuscate_AnimationsTest ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/animationsOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/animationsObfuscated.java" );

        assertEquals( sourceCodeProvider.get( obfuscatedFile ),
                layoutManager.obfuscate( initiator.fetchUnitSource( sourceCodeProvider.get( originalFile ) ) )
                        .getDocument().get() );
    }

    /* Testing :
    - #40 Obfuscate method invocation arguements that are of type QUALIFIED_NAME
     */
    @Test
    public void testObfuscate_Issue40 ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/Issue40Original.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/Issue40Obfuscated.java" );

        assertEquals( sourceCodeProvider.get( obfuscatedFile ),
                layoutManager.obfuscate( initiator.fetchUnitSource( sourceCodeProvider.get( originalFile ) ) )
                        .getDocument().get() );
    }

    //A "scratchpad" test
    @Test
    public void onthefly ()
    {
        File originalFile = new File( "src/test/resources/samplefiles/layoutmanager/ontheflyOriginal.java" );
        File obfuscatedFile = new File( "src/test/resources/samplefiles/layoutmanager/ontheflyObfuscated.java" );

        assertEquals( sourceCodeProvider.get( obfuscatedFile ),
                layoutManager.obfuscate( initiator.fetchUnitSource( sourceCodeProvider.get( originalFile ) ) )
                        .getDocument().get() );
    }
}