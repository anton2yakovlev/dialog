package the_biber_project.repos;
import the_biber_project.User;

public interface MemInter {

    Iterable<User> findAll();

    User save(User userName);


}