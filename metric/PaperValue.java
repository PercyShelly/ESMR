package metric;

public class PaperValue {
	private String pid;
	private Double rankValue;
	public PaperValue() {
		this.pid=null;
		this.rankValue=null;
	}
	public void initialize(String pid,double value) {
		this.pid=pid;
		this.rankValue=value;
	}
	public String getID() {
		return this.pid;
	}
	public double getValue() {
		return this.rankValue;
	}
}
