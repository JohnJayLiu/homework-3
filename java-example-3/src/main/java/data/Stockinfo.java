package data;

public class Stockinfo {

	private String ID;
	private String title;
	private String author;
	private String date;
	private String lastUpdate;
	private String content;
	private String answerAuthor;
	private String answer;
	public Stockinfo() {
		// TODO Auto-generated constructor stub
		ID=null;
		title =null;
		author =null;
		title=null;
		date =null;
		lastUpdate =null;
		content =null;
		answer =null;
		answerAuthor =null;
	}
	public int getAnswerLength() {
		return answer.length();
	}
	
	public void setID(String ID) {
		this.ID=ID;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public void setDate(String date) {
		this.date =date;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate =lastUpdate ;
	}
	public void setContent(String content) {
		this.content =content ;
	}
	public void setAnswerAuthor(String answerAuthor) {
		this.answerAuthor =answerAuthor ;
	}
	public void setAnswer(String answer) {
		this.answer =answer ;
	}
	public String getID() {
		return ID;
	}
	public String getTitle() {
		return title ;
	}
	public String getAuthor () {
		return author ;
	}
	public String getDate() {
		return date;
	}
	public String getLastUpdate() {
		return lastUpdate ;
	}
	public String getContent() {
		return content ;
	}
	public String getAnswerAuthor() {
		return answerAuthor ;
	}
	public String getAnswer() {
		return answer ;
	}
	public Object clone() {
		Stockinfo info=new Stockinfo();
		try {
			info=(Stockinfo )super.clone();
		}catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return info;
	}
}
