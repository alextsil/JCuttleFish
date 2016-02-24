package util;

import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import java.util.Comparator;


public class Comparators
{

    public static Comparator<VariableDeclarationStatement> byOccurenceOnOriginalFile =
            ( vds1, vds2 ) -> vds1.getStartPosition() - vds2.getStartPosition();

}
