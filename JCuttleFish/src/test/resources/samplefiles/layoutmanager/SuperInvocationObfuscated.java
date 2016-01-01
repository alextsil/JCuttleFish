public class a
{

    public FtpletResult onUploadStart ( final FtpSession aa, final FtpRequest bb ) throws FtpException, IOException
    {
        String aaa = bb.getArgument();

        if ( !aaa.equals( "add" ) && !aaa.equals( "delete" ) )
        {
            aa.write( new DefaultFtpReply( FtpReply.REPLY_553_REQUESTED_ACTION_NOT_TAKEN_FILE_NAME_NOT_ALLOWED, "Wrong file name \"" + aaa + "\"" ) );
            return FtpletResult.SKIP;
        }

        return super.onUploadStart( aa, bb );
    }

    public FtpletResult onUploadEnd ( final FtpSession aa, final FtpRequest bb ) throws FtpException, IOException
    {
        EOEditingContext aaa = ERXEC.newEditingContext();
        aaa.lock();
        try
        {
            String bbb = aa.getUser().getHomeDirectory();
            String ccc = aa.getFileSystemView().getWorkingDirectory().getAbsolutePath();
            String ddd = bb.getArgument();

            File eee = new File( bbb + ccc + ddd );
            BufferedReader fff = new BufferedReader( new FileReader( eee ) );

            if ( ddd.equals( "add" ) )
            {
                String ggg;
                while ( ( ggg = fff.readLine() ) != null )
                {
                    String[] hhh = ggg.split( "," );
                    Product iii = Product.createProduct( aaa, hhh[ 2 ], hhh[ 1 ], hhh[ 0 ] );
                    aaa.insertObject( iii );
                }
            } else
            {
                String jjj;
                while ( ( jjj = fff.readLine() ) != null )
                {
                    EOQualifier kkk = Product.REF.eq( jjj );
                    Product lll = Product.fetchProduct( aaa, kkk );
                    aaa.deleteObject( lll );
                }
            }

            aaa.saveChanges();
            eee.delete();
        } catch ( IOException ioe )
        {
            aa.write( new DefaultFtpReply( FtpReply.REPLY_451_REQUESTED_ACTION_ABORTED, "Operation failed." ) );
            return FtpletResult.SKIP;
        } finally
        {
            aaa.unlock();
            aaa.dispose();
        }

        return super.onUploadEnd( aa, bb );
    }
}