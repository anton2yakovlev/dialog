package the_biber_project.repos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import the_biber_project.User;

public class MemRepInMemoryMessageRepository implements MemInter {

    private static AtomicLong counter = new AtomicLong();

    private final ConcurrentMap<Long, User> users = new ConcurrentHashMap<Long, User>();
    public final ArrayList<String> usersStr = new ArrayList<String>();

    @Override
    public Iterable<User> findAll() {
        return this.users.values();
    }

    @Override
    public User save(User userName) {
        Long id = userName.getId();
        if (id == null) {
            id = counter.incrementAndGet();
            userName.setId(id);
        }
        this.users.put(id, userName);
        this.usersStr.add(userName.getUserName());
        return userName;
    }

    public void delete(Long id, String name) {
        System.out.println("sieze: " + users.size());
        this.users.remove((Long) id);
        this.usersStr.remove(name);
        System.out.println("sieze: " + users.size());
    }

}