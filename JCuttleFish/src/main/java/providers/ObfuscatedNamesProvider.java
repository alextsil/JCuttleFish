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
                    "s", "t", "u", "v", "w", "x", "y", "z" );

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
        } else
        {
            throw new RuntimeException( "Variation not supported " );
        }

        return variableNamesStream
                .distinct()
                .collect( Collectors.toCollection( LinkedList::new ) );
    }
}
