package entity;


public enum Role {
	
	CLIENT, ADMIN;
	
	public String getName(){
		String name = name().toLowerCase();
		return name;
	}
}
