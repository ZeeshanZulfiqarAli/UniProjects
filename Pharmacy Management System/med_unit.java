package application;

public class med_unit {
	private String name,exp,comp;
	private int id,quan;
	private float up;
	public med_unit(int id, String name, String comp, String exp, int quan, float up) {
		super();
		this.name = name;
		this.exp = exp;
		this.comp = comp;
		this.id = id;
		this.quan = quan;
		this.up = up;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	public String getComp() {
		return comp;
	}
	public void setComp(String comp) {
		this.comp = comp;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQuan() {
		return quan;
	}
	public void setQuan(int quan) {
		this.quan = quan;
	}
	public float getUp() {
		return up;
	}
	public void setUp(float up) {
		this.up = up;
	}
	
	
}
