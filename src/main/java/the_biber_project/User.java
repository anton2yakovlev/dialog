package the_biber_project;

public class User {
    private Long id;
    public String userName;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName(){
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}

