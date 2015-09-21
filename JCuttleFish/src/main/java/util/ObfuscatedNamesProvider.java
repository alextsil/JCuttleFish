package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.enums.ObfuscatedNamesVariations;

import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ObfuscatedNamesProvider
{

    private final Logger logger = LoggerFactory.getLogger( ObfuscatedNamesProvider.class );

    public ObfuscatedNamesProvider ()
    {
    }

    public Deque<String> getObfuscatedNames ( ObfuscatedNamesVariations variation )
    {
        logger.info( "ObfuscatedNamesVariations parameter is ignored" );
        //TODO: build variations.
        Stream<String> variableNamesStream = Stream.of( "a", "b", "c", "d", "e", "f", "g", "h", "i" );
        Deque<String> obfuscatedVariableNames = variableNamesStream
                .distinct()
                .collect( Collectors.toCollection( LinkedList::new ) );
        return obfuscatedVariableNames;
    }
}
