import java.util.List;

//This class saves information about obfuscation targets (classes, interfaces, enums, misc files)
//Each object represents a single obfuscation target

public class TargetObject {

	private String name;
	private String childOf; // extends
	private List<String> implementerOf; // implements
	private List<String> parentOf;
	private TargetType type;

	public TargetObject() {
	
	}

	public String getName() {
		return name;
	}

	public String getChildOf() {
		return childOf;
	}

	public List<String> getImplementerOf() {
		return implementerOf;
	}

	public List<String> getParentOf() {
		return parentOf;
	}

	public TargetType getType() {
		return type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setChildOf(String childOf) {
		this.childOf = childOf;
	}

	public void setImplementerOf(List<String> implementerOf) {
		this.implementerOf = implementerOf;
	}

	public void setParentOf(List<String> parentOf) {
		this.parentOf = parentOf;
	}

	public void setType(TargetType type) {
		this.type = type;
	}

}
