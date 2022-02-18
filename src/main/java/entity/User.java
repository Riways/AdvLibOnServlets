package entity;


public class User {

	private Integer id;

	private String username;

	private String password;

	private boolean enabled;
	
	private Role role;
	
	private String email;

	public User() {

	}

	public User(int id, String username, String password, boolean enabled, Role role, String email){
		this.id = id;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.role = role;
		this.email = email;
	}

	
	public User(String username, String password, boolean enabled, Role role, String email) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.role = role;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public  Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return id.equals(user.id);
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int hashcode;
		hashcode = prime + id.hashCode();
		return hashcode;
	}

	@Override
	public String toString() {
		return "User{" +
				"username : " + username  +
				'}';
	}
}
