package entity;


public enum Role {
	
	CLIENT, ADMIN;
	
	public String getName(){
		return name().toLowerCase();
	}
}
