package obfuscations;

import obfuscations.layout.LayoutManager;
import parser.UnitSourceInitiator;
import pojo.UnitSource;
import providers.FileSourceCodeProvider;

import java.io.File;


//manages the different types of obfuscations and the order they will be applied.
public class ObfuscationCoordinator
{

    public ObfuscationCoordinator ()
    {
        //TODO : backup before any action

        LayoutManager layoutManager = new LayoutManager();
        File target = new File( "C:/1backup/main/java/pojo/UnitSource.java" );
        UnitSourceInitiator unitSourceInitiator = new UnitSourceInitiator();
        FileSourceCodeProvider sourceCodeProvider = new FileSourceCodeProvider();
        UnitSource unitSource = unitSourceInitiator.fetchUnitSource( sourceCodeProvider.get( target ) );
        layoutManager.obfuscate( unitSource );
    }
}