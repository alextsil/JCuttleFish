public class Test
{

    public FtpletResult onUploadStart ( final FtpSession session, final FtpRequest request ) throws FtpException, IOException
    {
        String fileName = request.getArgument();

        if ( !fileName.equals( "add" ) && !fileName.equals( "delete" ) )
        {
            session.write( new DefaultFtpReply( FtpReply.REPLY_553_REQUESTED_ACTION_NOT_TAKEN_FILE_NAME_NOT_ALLOWED, "Wrong file name \"" + fileName + "\"" ) );
            return FtpletResult.SKIP;
        }

        return super.onUploadStart( session, request );
    }

    public FtpletResult onUploadEnd ( final FtpSession session, final FtpRequest request ) throws FtpException, IOException
    {
        EOEditingContext ec = ERXEC.newEditingContext();
        ec.lock();
        try
        {
            String userRoot = session.getUser().getHomeDirectory();
            String currDir = session.getFileSystemView().getWorkingDirectory().getAbsolutePath();
            String fileName = request.getArgument();

            File f = new File( userRoot + currDir + fileName );
            BufferedReader br = new BufferedReader( new FileReader( f ) );

            if ( fileName.equals( "add" ) )
            {
                String line;
                while ( ( line = br.readLine() ) != null )
                {
                    String[] data = line.split( "," );
                    Product p = Product.createProduct( ec, data[ 2 ], data[ 1 ], data[ 0 ] );
                    ec.insertObject( p );
                }
            } else
            {
                String line;
                while ( ( line = br.readLine() ) != null )
                {
                    EOQualifier q = Product.REF.eq( line );
                    Product p = Product.fetchProduct( ec, q );
                    ec.deleteObject( p );
                }
            }

            ec.saveChanges();
            f.delete();
        } catch ( IOException ioe )
        {
            session.write( new DefaultFtpReply( FtpReply.REPLY_451_REQUESTED_ACTION_ABORTED, "Operation failed." ) );
            return FtpletResult.SKIP;
        } finally
        {
            ec.unlock();
            ec.dispose();
        }

        return super.onUploadEnd( session, request );
    }
}