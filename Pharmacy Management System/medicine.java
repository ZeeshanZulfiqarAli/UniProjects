package application;

public class medicine {

	private int m_id;
	private String Generic_name,Brand_Name,comp,exp;
	public medicine(int m_id, String Generic_name, String Brand_Name, String comp, String exp) {
		super();
		this.m_id = m_id;
		this.Generic_name = Generic_name;
		this.Brand_Name = Brand_Name;
		this.comp = comp;
		this.exp = exp;
		//System.out.println(gName+"\t"+bName);
	}
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	public String getGeneric_name() {
		System.out.println("now: "+Generic_name);
		return Generic_name;
		
	}
	public void setGeneric_name(String Generic_name) {
		this.Generic_name = Generic_name;
	}
	public String getBrand_Name() {
		return Brand_Name;
	}
	public void setBrand_Name(String Brand_Name) {
		this.Brand_Name = Brand_Name;
	}
	public String getComp() {
		System.out.println("now: "+comp);
		return comp;
	}
	public void setComp(String comp) {
		this.comp = comp;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	
}
