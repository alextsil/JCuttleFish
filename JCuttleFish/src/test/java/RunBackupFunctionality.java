import org.junit.Before;
import org.junit.Test;
import util.BackupFilesHelper;

import java.io.File;

public class RunBackupFunctionality {

    @Before
    public void setUp () {
    }

    @Test
    public void nan () {
        File targetDir = new File( "C:/test/backuptest" );
        File destDir = new File( "C:/1backup" );
        BackupFilesHelper.backupFiles( targetDir, destDir );
    }

}