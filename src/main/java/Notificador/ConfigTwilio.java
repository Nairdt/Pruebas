package Notificador;

import lombok.Getter;

@Getter
public class ConfigTwilio {
    public static final String ACCOUNT_SID = System.getenv("ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("AUTH_TOKEN");
    public static final String PHONE_FROM = System.getenv("PHONE_FROM");
    public static final String KEY_SENDGRID = System.getenv("KEY_SENDGRID");
}
