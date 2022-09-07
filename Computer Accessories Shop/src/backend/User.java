package backend;

public class User {
	public enum Permission {ADMIN, CUSTOMER};
	
	private String user_id;
	private String username;
	private String name;
	private String house_number;
	private String post_code;
	private String city;
	private Permission permission;
	// private Basket basket();
	
	
	public User (Permission permission, String user_id, String username, String name, String house_number, String post_code, String city) {
		this.permission = permission;
		this.user_id = user_id;
		this.username = username;
		this.name = name;
		this.house_number = house_number;
		this.post_code = post_code;
		this.city = city;
	}


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return the permission
	 */
	public Permission getPermission() {
		return permission;
	}
	
	public String getAddress() {
		return "No. "+this.house_number+", "+this.post_code+", "+this.city;
	}
}
