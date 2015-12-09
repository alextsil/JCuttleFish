public class Test
{

    public void test ()
    {
        int var1 = 5;
        String var2 = "test";
        String var3 = "test2322";

        var3 = var2 + var1;
        var3 = null;
    }

    public void test2 ( String var1, String var2 )
    {
        test( var1, var2 );
    }

    public static double var ( double[] a, int lo, int hi )
    {
        int length = hi - lo + 1;
        if ( lo < 0 || hi >= a.length || lo > hi )
            throw new IndexOutOfBoundsException( "Subarray indices out of bounds" );
        if ( length == 0 ) return Double.NaN;
        double avg = mean( a, lo, hi );
        double sum = 0.0;
        for ( int i = lo; i <= hi; i++ )
        {
            sum += ( a[ i ] - avg ) * ( a[ i ] - avg );
        }
        return sum / ( length - 1 );
    }

    public static void show ( boolean[][] a, boolean which )
    {
        int N = a.length;
        StdDraw.setXscale( -1, N );
        StdDraw.setYscale( -1, N );
        for ( int i = 0; i < N; i++ )
            for ( int j = 0; j < N; j++ )
                if ( a[ i ][ j ] == which )
                    StdDraw.filledSquare( j, N - i - 1, .5 );
    }

    public static void main ( String[] args )
    {
        if ( args.length != 2 )
        {
            printUsageAndExit();
        }
        File encodedFile = new File( args[ 0 ] );
        File pcmFile = new File( args[ 1 ] );
        AudioInputStream ais = null;
        try
        {
            ais = AudioSystem.getAudioInputStream( encodedFile );
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
        if ( ais == null )
        {
            out( "cannot open input file" );
            System.exit( 1 );
        }
        if ( DEBUG )
        {
            out( "AudioDecoder: ais: " + ais );
        }
        if ( DEBUG )
        {
            out( "AudioDecoder: ais AudioFormat: " + ais.getFormat() );
        }
        if ( DEBUG )
        {
            out( "AudioDecoder: ais length (frames): " + ais.getFrameLength() );
        }
        AudioFormat.Encoding targetEncoding = AudioFormat.Encoding.PCM_SIGNED;
        AudioInputStream pcmAIS = AudioSystem.getAudioInputStream( targetEncoding, ais );
        if ( DEBUG )
        {
            out( "AudioDecoder: pcmAIS: " + pcmAIS );
        }
        if ( DEBUG )
        {
            out( "AudioDecoder: pcmAIS AudioFormat: " + pcmAIS.getFormat() );
        }
        if ( DEBUG )
        {
            out( "AudioDecoder: pcmAIS length (frames): " + pcmAIS.getFrameLength() );
        }
        AudioFileFormat.Type fileType = AudioFileFormat.Type.AU;
        int nWrittenBytes = 0;
        try
        {
            nWrittenBytes = AudioSystem.write( pcmAIS, fileType, pcmFile );
            if ( DEBUG ) out( "AudioDecoder: written (bytes): " + nWrittenBytes );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    public static void main2 ( String[] args )
    {
        AudioFormat.Encoding targetEncoding = null;
        String strFileTypeName = null;
        String strFileTypeExtension = null;
        AudioFileFormat.Type fileType = null;
        List<String> vorbisComments = new ArrayList<String>();

        int nQuality = -1;
        int nBitrate = -1;

		/*
         *	Parsing of command-line options takes place...
		 */
        Getopt g = new Getopt( "AudioEncoder", args, "he:b:q:t:T:V:D" );
        int c;
        while ( ( c = g.getopt() ) != -1 )
        {
            switch ( c )
            {
                case 'h':
                    printUsageAndExit();

                case 'e':
                    targetEncoding = new AudioFormat.Encoding( g.getOptarg() );
                    if ( DEBUG ) out( "AudioEncoder.main(): using encoding: " + targetEncoding );
                    break;

                case 'q':
                    nQuality = Integer.parseInt( g.getOptarg() );
                    break;

                case 'b':
                    nBitrate = Integer.parseInt( g.getOptarg() );
                    break;

                case 't':
                    strFileTypeName = g.getOptarg();
                    break;

                case 'T':
                    strFileTypeExtension = g.getOptarg();
                    break;

                case 'V':
                    vorbisComments.add( g.getOptarg() );
                    if ( DEBUG ) out( "adding comment: " + g.getOptarg() );
                    break;

                case 'D':
                    DEBUG = true;
                    break;

                case '?':
                    printUsageAndExit();

                default:
                    out( "getopt() returned " + c );
                    break;
            }
        }
        if ( targetEncoding == null )
        {
            out( "AudioEncoder.main(): no encoding specified!" );
            printUsageAndExit();
        }

        if ( strFileTypeName != null && strFileTypeExtension != null )
        {
            fileType = new AudioFileFormat.Type( strFileTypeName,
                    strFileTypeExtension );
        } else
        {
            fileType = AudioFileFormat.Type.WAVE;
        }
        if ( DEBUG ) out( "AudioEncoder.main(): using file type: " + fileType );

		/* We make shure that there are only two more arguments, which
           we take as the filenames of the input and output soundfile. */
        int nOptionIndex = g.getOptind();
        if ( args.length - nOptionIndex != 2 )
        {
            printUsageAndExit();
        }
        File pcmFile = new File( args[ nOptionIndex ] );
        File encodedFile = new File( args[ nOptionIndex + 1 ] );

        AudioInputStream ais = null;
        try
        {
            ais = AudioSystem.getAudioInputStream( pcmFile );
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
        if ( ais == null )
        {
            out( "cannot open audio file" );
            System.exit( 1 );
        }
        AudioFormat targetAudioFormat = null;
        if ( vorbisComments.size() > 0 || nQuality != -1 || nBitrate != -1 )
        {
            Map<String, Object> properties = new HashMap<String, Object>();
            if ( nQuality != -1 )
            {
                properties.put( "quality", new Integer( nQuality ) );
            }
            if ( nBitrate != -1 )
            {
                properties.put( "bitrate", new Integer( nBitrate ) );
            }
            if ( vorbisComments.size() > 0 )
            {
                properties.put( "vorbis.comments", vorbisComments );
                if ( DEBUG ) out( "adding vorbis comments to properties map" );
            }
            AudioFormat sourceFormat = ais.getFormat();
            targetAudioFormat = new AudioFormat( targetEncoding,
                    sourceFormat.getSampleRate(),
                    AudioSystem.NOT_SPECIFIED,
                    sourceFormat.getChannels(),
                    AudioSystem.NOT_SPECIFIED,
                    AudioSystem.NOT_SPECIFIED,
                    sourceFormat.isBigEndian(),
                    properties );
        }
        AudioInputStream encodedAudioInputStream = null;
        if ( targetAudioFormat != null )
        {
            encodedAudioInputStream = AudioSystem.getAudioInputStream( targetAudioFormat, ais );
        } else
        {
            encodedAudioInputStream = AudioSystem.getAudioInputStream( targetEncoding, ais );
        }
        if ( DEBUG )
        {
            out( "encoded stream: " + encodedAudioInputStream );
        }
        if ( DEBUG )
        {
            out( "encoded stream's format: " + encodedAudioInputStream.getFormat() );
        }
        int nWrittenFrames = 0;
        try
        {
            nWrittenFrames = AudioSystem.write( encodedAudioInputStream, fileType, encodedFile );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
}