public class Student
{
//
    private String rollNumber;
    private String name;
    private String standard;
    private int totalMarks;

    public Student ()
    {
    }

    public Student ( String rollNumber, String name, String standard, int totalMarks )
    {
        this.rollNumber = rollNumber;
        this.name = name;
        this.standard = standard;
        this.totalMarks = totalMarks;
    }

    public String getRollNumber ()
    {
        return rollNumber;
    }

    public void setRollNumber ( String rollNumber )
    {
        this.rollNumber = rollNumber;
    }

    public String getName ()
    {
        return name;
    }

    public void setName ( String name )
    {
        this.name = name;
    }

    public String getStandard ()
    {
        return standard;
    }

    public void setStandard ( String standard )
    {
        this.standard = standard;
    }

    public int getTotalMarks ()
    {
        return totalMarks;
    }

    public void setTotalMarks ( int totalMarks )
    {
        this.totalMarks = totalMarks;
    }

}
