package providers;

import java.io.Serializable;


public interface SourceCodeProvider<T extends Serializable>
{

    String get ( T o );
}
