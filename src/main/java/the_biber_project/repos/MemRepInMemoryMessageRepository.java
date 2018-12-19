package the_biber_project.repos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import the_biber_project.User;

public class MemRepInMemoryMessageRepository implements MemInter {

    private static AtomicLong counter = new AtomicLong();

    private final ConcurrentMap<Long, User> users = new ConcurrentHashMap<Long, User>();
    private final ArrayList<String> usersStr = new ArrayList<String>();

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

    public Optional<User> find(String name) {
        return users.entrySet().stream()
                .filter(e -> e.getValue().getUserName().equals(name))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    public void add(String name) {
        this.usersStr.add(name);
    }

    public void delete(Long id, String name) {
        this.usersStr.remove(name);
        System.out.println("Online: " + usersStr.size());
    }

    public List<String> getOtherUsers(String username) {
        return this.usersStr.stream().filter(user -> !user.equals(username)).collect(Collectors.toList());
    }

    public List<String> getAllUsers() {
        return this.usersStr;
    }
}
