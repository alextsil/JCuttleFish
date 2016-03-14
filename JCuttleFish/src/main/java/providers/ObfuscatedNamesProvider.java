package providers;

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
        Stream<String> variableNamesStream = Stream.empty();

        if ( variation == ObfuscatedNamesVariations.ALPHABET )
        {
            variableNamesStream = Stream.of( "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
                    "l", "m", "n", "o", "p", "q", "r",
                    "s", "t", "u", "v", "w", "x", "y", "z", "a1", "b2", "c3", "d4", "e5", "f6" );

        } else if ( variation == ObfuscatedNamesVariations.METHOD_PARAMETERS )
        {
            variableNamesStream = Stream.of( "aa", "bb", "cc", "dd", "ee", "ff", "gg", "hh", "ii", "jj", "kk",
                    "ll", "mm", "nn", "oo", "pp", "qq", "rr",
                    "ss", "tt", "uu", "vv", "ww", "xx", "yy", "zz" );
        } else if ( variation == ObfuscatedNamesVariations.METHOD_LOCAL_VARS )
        {
            variableNamesStream = Stream.of( "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk",
                    "lll", "mmm", "nnn", "ooo", "ppp", "qqq", "rrr",
                    "sss", "ttt", "uuu", "vvv", "www", "xxx", "yyy", "zzz" );
        } else if ( variation == ObfuscatedNamesVariations.SUBCLASS_METHODS )
        {
            variableNamesStream = Stream.of( "as", "bs", "cs", "ds", "es", "fs", "gs", "hs", "is", "js", "ks",
                    "ls", "ms", "ns", "os", "ps", "qs", "rs",
                    "ss", "ts", "us", "vs", "ws", "xs", "ys", "zs", "a1s", "b2s", "c3s", "d4s", "e5s", "f6s" );
        } else if ( variation == ObfuscatedNamesVariations.INTERFACE_METHODS )
        {
            variableNamesStream = Stream.of( "ai", "bi", "ci", "di", "ei", "fi", "gi", "hi", "ii", "ji", "ki",
                    "li", "mi", "ni", "oi", "pi", "qi", "ri",
                    "si", "ti", "ui", "vi", "wi", "xi", "yi", "zi" );
        } else
        {
            throw new RuntimeException( "Variation not supported " );
        }

        return variableNamesStream
                .distinct()
                .collect( Collectors.toCollection( LinkedList::new ) );
    }
}
