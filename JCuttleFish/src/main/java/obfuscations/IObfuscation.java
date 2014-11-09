package obfuscations;

public interface IObfuscation {
	public void setParameters(String path);
	public boolean obfuscate();
}
