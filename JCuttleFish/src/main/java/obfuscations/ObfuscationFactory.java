package obfuscations;

import obfuscations.accessmodification.AccessModificationManager;
import obfuscations.enums.SupportedObfuscations;
import obfuscations.variablename.VariableNameManager;

public class ObfuscationFactory {

    public static IObfuscation buildObfuscation ( SupportedObfuscations type ) {
        IObfuscation obfuscation = null;

        switch ( type ) {

            case ACCESS_MODIFICATION:
                obfuscation = new AccessModificationManager();
                break;

            case VARIABLE_NAME:
                obfuscation = new VariableNameManager();
                break;

            default:
                break;
        }
        return obfuscation;
    }
}
