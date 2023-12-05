package client.server;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static EntityManagerFactory emf;
    public static void main(String[] args) throws Exception {
        Map<String, String> env = System.getenv();

        emf =  createEntityManagerFactory();

        Server.init();
    }
    public static EntityManagerFactory createEntityManagerFactory() throws Exception {
        // https://stackoverflow.com/questions/8836834/read-environment-variables-in-persistence-xml-file
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<String, Object>();

        String[] keys = new String[] {
                //"DATABASE_URL",
                "hibernate__connection__driver_class",
                "hibernate__connection__password",
                //"javax__persistence__jdbc.url",
                "hibernate__connection__username",
                "hibernate__hbm2ddl__auto",
                "hibernate__connection__url"};
                //"hibernate__connection__pool_size",
                //"hibernate__show_sql" };

        for (String key : keys) {

            try{
//                if (key.equals("DATABASE_URL")) {
//
//                    // https://devcenter.heroku.com/articles/connecting-heroku-postgres#connecting-in-java
//                    String value = env.get(key);
//                    URI dbUri = new URI(value);
//                    String username = dbUri.getUserInfo().split(":")[0];
//                    String password = dbUri.getUserInfo().split(":")[1];
//                    //javax.persistence.jdbc.url=jdbc:postgresql://localhost/dblibros
//                    value = "jdbc:mysql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();// + "?sslmode=require";
//                    configOverrides.put("javax.persistence.jdbc.url", value);
//                    configOverrides.put("javax.persistence.jdbc.user", username);
//                    configOverrides.put("javax.persistence.jdbc.password", password);
//                    configOverrides.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
//
//                    //  configOverrides.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//                }
                // no se pueden poner variables de entorno con "." en la key
                String key2 = key.replace("__",".");
                if (env.containsKey(key)) {
                    String value = env.get(key);
                    configOverrides.put(key2, value);
                }

            } catch(Exception ex){
                System.out.println("Error configurando " + key);
            }
        }
        System.out.println("Config overrides ----------------------");
        for (String key : configOverrides.keySet()) {
            System.out.println(key + ": " + configOverrides.get(key));
        }

        return Persistence.createEntityManagerFactory("db", configOverrides);
    }
}
