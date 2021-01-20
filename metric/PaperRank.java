package metric;

public class PaperRank {
	private String pid;
//	private Double RankValue;
	private Integer year;
	private Integer citations;
//	private String title;
	private String field;
	public PaperRank() {
		this.pid=null;
//		this.RankValue=null;
		this.citations=null;
		this.year=null;
//		this.title=null;
		this.field=null;
	}
	public void initialize(String pid,int citations,int year,String field) {
		this.pid=pid;
		this.citations=citations;
//		this.RankValue=rankvalue;
		this.year=year;
//		this.title=title;
		this.field=field;
	}
//	public double getValue() {
//		return this.RankValue;
//	}
	public String getId() {
		return this.pid;
	}
	public int getYear() {
		return this.year;
	}
//	public String getTitle() {
//		return this.title;
//	}
	public int getCitations() {
		return this.citations;
	}
	public String getField() {
		return this.field;
	}
}
