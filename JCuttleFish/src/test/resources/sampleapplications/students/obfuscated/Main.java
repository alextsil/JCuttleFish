package pack1;

import java.util.ArrayList;
import java.util.List;


public class Main
{
//
    public static void main ( String[] args )
    {
        Student student1 = new Student();
        student1.setName( "John" );
        student1.setRollNumber( "3333" );
        student1.setStandard( "123" );
        student1.setTotalMarks( 55 );

        Student student2 = new Student( "2920", "Alex", "123", 55 );

        List<Student> students = new ArrayList<Student>();
        students.add( student1 );
        students.add( student2 );
    }
}
