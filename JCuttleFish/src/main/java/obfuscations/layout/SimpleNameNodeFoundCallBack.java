package obfuscations.layout;

import org.eclipse.jdt.core.dom.SimpleName;


public class SimpleNameNodeFoundCallBack extends BaseAstNodeFoundCallback
{

    public void addToCollection ( SimpleName simpleName )
    {
        super.addToCollection( simpleName );
    }
}
