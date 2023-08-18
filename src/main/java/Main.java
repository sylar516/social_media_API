import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.query.Query;
import ru.effective_mobile.social_media_api.entity.*;

import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.URL, "jdbc:p6spy:mysql://localhost:3306/social_media");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "root");
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread"); //настройка нужна для корректной работы кэша при многопоточности
        properties.put(Environment.HBM2DDL_AUTO, "validate");

        SessionFactory sessionFactory = new Configuration()
                .setProperties(properties)
                .addAnnotatedClass(Friend.class)
                .addAnnotatedClass(FriendId.class)
                .addAnnotatedClass(Message.class)
                .addAnnotatedClass(Post.class)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();
//        User user = currentSession.get(User.class, 2);
//        Post post = new Post();
//        post.setHeader("Пост от Нинульки");
//        post.setText("первый её постик");
//        post.setUser(user);
//
//        currentSession.merge(post);

//        Query<User> userQuery = currentSession.createQuery("from User where id = 1", User.class);
//        User user = userQuery.getSingleResult();
//        List<Post> posts = user.getPosts();

//        Query<Post> postQuery = currentSession.createQuery("from Post where user.id = 1", Post.class);
//        List<Post> resultList = postQuery.getResultList();


//        Query<Friend> from_friend = currentSession.createQuery("from Friend", Friend.class);
//        List<Friend> resultList = from_friend.getResultList();

//        Query<Friend> friendQuery = currentSession.createQuery("from Friend where friendId.user.id = 2", Friend.class);
//        List<Friend> resultList = friendQuery.getResultList();

//        Query<User> from_user = currentSession.createQuery("from User", User.class);
//        List<User> resultList = from_user.getResultList();

//        User sender = currentSession.get(User.class, 3);
//        User receiver = currentSession.get(User.class, 2);
//
//        for (int i = 0; i < 5; i++) {
//            Message message = new Message();
//            message.setText("мяяяяяяяяяу");
//            message.setSender(sender);
//            message.setReceiver(receiver);
//            currentSession.merge(message);
//        }

        Query<Message> messageQuery = currentSession.
                createQuery("from Message where sender.id = 1 and receiver.id = 2 or sender.id = 2 and receiver.id = 1 order by sendDate asc", Message.class);
        List<Message> resultList = messageQuery.getResultList();

        currentSession.getTransaction().commit();

        resultList.forEach(System.out::println);

//        User user = resultList.get(2);
//        user.getFriends().forEach(System.out::println);
//        System.out.println(user.getFriends());

//        System.out.println(resultList);

//        System.out.println(posts);
//        System.out.println(resultList);
    }
}
