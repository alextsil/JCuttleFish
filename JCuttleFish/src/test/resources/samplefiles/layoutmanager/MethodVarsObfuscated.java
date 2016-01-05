public class a
{

    public void a ()
    {
        int aaa = 5;
        String bbb = "test";
        String ccc = "test2322";

        ccc = bbb + aaa;
        ccc = null;
    }

    public void b ( String aa, String bb )
    {
        a( aa, bb );
    }

    public static double c ( double[] aa, int bb, int cc )
    {
        int aaa = cc - bb + 1;
        if ( bb < 0 || cc >= aa.length || bb > cc )
            throw new IndexOutOfBoundsException( "Subarray indices out of bounds" );
        if ( aaa == 0 ) return Double.NaN;
        double bbb = mean( aa, bb, cc );
        double ccc = 0.0;
        for ( int i = bb; i <= cc; i++ )
        {
            ccc += ( aa[ i ] - bbb ) * ( aa[ i ] - bbb );
        }
        return ccc / ( aaa - 1 );
    }

    public static void d ( boolean[][] aa, boolean bb )
    {
        int aaa = aa.length;
        StdDraw.setXscale( -1, aaa );
        StdDraw.setYscale( -1, aaa );
        for ( int i = 0; i < aaa; i++ )
            for ( int j = 0; j < aaa; j++ )
                if ( aa[ i ][ j ] == bb )
                    StdDraw.filledSquare( j, aaa - i - 1, .5 );
    }

    public static void main ( String[] aa )
    {
        if ( aa.length != 2 )
        {
            printUsageAndExit();
        }
        File aaa = new File( aa[ 0 ] );
        File bbb = new File( aa[ 1 ] );
        AudioInputStream ccc = null;
        try
        {
            ccc = AudioSystem.getAudioInputStream( aaa );
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
        if ( ccc == null )
        {
            out( "cannot open input file" );
            System.exit( 1 );
        }
        if ( DEBUG )
        {
            out( "AudioDecoder: ais: " + ccc );
        }
        if ( DEBUG )
        {
            out( "AudioDecoder: ais AudioFormat: " + ccc.getFormat() );
        }
        if ( DEBUG )
        {
            out( "AudioDecoder: ais length (frames): " + ccc.getFrameLength() );
        }
        AudioFormat.Encoding ddd = AudioFormat.Encoding.PCM_SIGNED;
        AudioInputStream eee = AudioSystem.getAudioInputStream( ddd, ccc );
        if ( DEBUG )
        {
            out( "AudioDecoder: pcmAIS: " + eee );
        }
        if ( DEBUG )
        {
            out( "AudioDecoder: pcmAIS AudioFormat: " + eee.getFormat() );
        }
        if ( DEBUG )
        {
            out( "AudioDecoder: pcmAIS length (frames): " + eee.getFrameLength() );
        }
        AudioFileFormat.Type fff = AudioFileFormat.Type.AU;
        int ggg = 0;
        try
        {
            ggg = AudioSystem.write( eee, fff, bbb );
            if ( DEBUG ) out( "AudioDecoder: written (bytes): " + ggg );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    public static void e ( String[] aa )
    {
        AudioFormat.Encoding aaa = null;
        String bbb = null;
        String ccc = null;
        AudioFileFormat.Type ddd = null;
        List<String> eee = new ArrayList<String>();

        int fff = -1;
        int ggg = -1;

		/*
         *	Parsing of command-line options takes place...
		 */
        Getopt hhh = new Getopt( "AudioEncoder", aa, "he:b:q:t:T:V:D" );
        int iii;
        while ( ( iii = hhh.getopt() ) != -1 )
        {
            switch ( iii )
            {
                case 'h':
                    printUsageAndExit();

                case 'e':
                    aaa = new AudioFormat.Encoding( hhh.getOptarg() );
                    if ( DEBUG ) out( "AudioEncoder.main(): using encoding: " + aaa );
                    break;

                case 'q':
                    fff = Integer.parseInt( hhh.getOptarg() );
                    break;

                case 'b':
                    ggg = Integer.parseInt( hhh.getOptarg() );
                    break;

                case 't':
                    bbb = hhh.getOptarg();
                    break;

                case 'T':
                    ccc = hhh.getOptarg();
                    break;

                case 'V':
                    eee.add( hhh.getOptarg() );
                    if ( DEBUG ) out( "adding comment: " + hhh.getOptarg() );
                    break;

                case 'D':
                    DEBUG = true;
                    break;

                case '?':
                    printUsageAndExit();

                default:
                    out( "getopt() returned " + iii );
                    break;
            }
        }
        if ( aaa == null )
        {
            out( "AudioEncoder.main(): no encoding specified!" );
            printUsageAndExit();
        }

        if ( bbb != null && ccc != null )
        {
            ddd = new AudioFileFormat.Type( bbb,
                    ccc );
        } else
        {
            ddd = AudioFileFormat.Type.WAVE;
        }
        if ( DEBUG ) out( "AudioEncoder.main(): using file type: " + ddd );

		/* We make shure that there are only two more arguments, which
           we take as the filenames of the input and output soundfile. */
        int jjj = hhh.getOptind();
        if ( aa.length - jjj != 2 )
        {
            printUsageAndExit();
        }
        File kkk = new File( aa[ jjj ] );
        File lll = new File( aa[ jjj + 1 ] );

        AudioInputStream mmm = null;
        try
        {
            mmm = AudioSystem.getAudioInputStream( kkk );
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
        if ( mmm == null )
        {
            out( "cannot open audio file" );
            System.exit( 1 );
        }
        AudioFormat nnn = null;
        if ( eee.size() > 0 || fff != -1 || ggg != -1 )
        {
            Map<String, Object> ooo = new HashMap<String, Object>();
            if ( fff != -1 )
            {
                ooo.put( "quality", new Integer( fff ) );
            }
            if ( ggg != -1 )
            {
                ooo.put( "bitrate", new Integer( ggg ) );
            }
            if ( eee.size() > 0 )
            {
                ooo.put( "vorbis.comments", eee );
                if ( DEBUG ) out( "adding vorbis comments to properties map" );
            }
            AudioFormat ppp = mmm.getFormat();
            nnn = new AudioFormat( aaa,
                    ppp.getSampleRate(),
                    AudioSystem.NOT_SPECIFIED,
                    ppp.getChannels(),
                    AudioSystem.NOT_SPECIFIED,
                    AudioSystem.NOT_SPECIFIED,
                    ppp.isBigEndian(),
                    ooo );
        }
        AudioInputStream qqq = null;
        if ( nnn != null )
        {
            qqq = AudioSystem.getAudioInputStream( nnn, mmm );
        } else
        {
            qqq = AudioSystem.getAudioInputStream( aaa, mmm );
        }
        if ( DEBUG )
        {
            out( "encoded stream: " + qqq );
        }
        if ( DEBUG )
        {
            out( "encoded stream's format: " + qqq.getFormat() );
        }
        int rrr = 0;
        try
        {
            rrr = AudioSystem.write( qqq, ddd, lll );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
}