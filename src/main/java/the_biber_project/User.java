package the_biber_project;

public class User {
    private Long id;
    private String userName;
    private String password;
    private String usercolor = "";

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName(){
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUsercolor() { return usercolor; }

    public void setUsercolor(String color) {
        this.usercolor = color;
    }

}

