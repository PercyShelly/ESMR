package metric;

public class AuthorRank {
	private String aid;
//	private Double RankValue;
	private Integer year;
//	private String name;
	private Integer citations;
	private String field;
	public AuthorRank() {
		this.aid=null;
//		this.RankValue=null;
		this.year=null;
//		this.name=null;
		this.citations=null;
		this.field=null;
	}
	public void initialize(String aid,int citations,int year,String field) {
		this.aid=aid;
//		this.RankValue=rankvalue;
		this.year=year;
//		this.name=name;
		this.citations=citations;
		this.field=field;
	}
//	public double getValue() {
//		return this.RankValue;
//	}
	public String getId() {
		return this.aid;
	}
	public int getYear() {
		return this.year;
	}
//	public String getname() {
//		return this.name;
//	}
	public int getCitations() {
		return this.citations;
	}
	public String getField() {
		return this.field;
	}
}
