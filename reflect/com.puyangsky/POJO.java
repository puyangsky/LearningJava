import java.util.List;

/**
 * Created by puyangsky on 2016/11/20.
 */
public class POJO {
    private int age;
    private String name;
    private List<POJO> friends;

    public POJO() {
    }

    public POJO(int age, String name, List<POJO> friends) {
        this.age = age;
        this.name = name;
        this.friends = friends;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<POJO> getFriends() {
        return friends;
    }

    public void setFriends(List<POJO> friends) {
        this.friends = friends;
    }

    public String toString() {
        return "name: " + name + "\tage: " + age + "\tfriends" + friends;
    }
}
