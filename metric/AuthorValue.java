package metric;

public class AuthorValue {
	private String aid;
	private Double rankValue;
	public AuthorValue() {
		this.aid=null;
		this.rankValue=null;
	}
	public void initialize(String aid,double value) {
		this.aid=aid;
		this.rankValue=value;
	}
	public String getID() {
		return this.aid;
	}
	public double getValue() {
		return this.rankValue;
	}
}
