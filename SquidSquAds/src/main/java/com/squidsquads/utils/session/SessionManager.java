package com.squidsquads.utils.session;

import com.squidsquads.model.Account;
import com.squidsquads.model.AdminType;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class SessionManager {

    private static SessionManager instance = null;
    private static final Integer TIMEOUT = 1000 * 60 * 30; // 30 minutes

    public static final Integer NO_SESSION = 0;

    private ArrayList<Session> sessions;

    private SessionManager() {
        sessions = new ArrayList<>();
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public String addSession(Account account) {

        Session session = findSessionByAccount(account);

        if (session != null) {
            renewSession(session);
            return session.getToken();
        }

        Date expire = generateExpire();
        String token = generateToken();

        session = new Session(
                account.getAdminTypeValue(),
                account.getAccountID(),
                account.getEmail(),
                token,
                expire
        );

        sessions.add(session);

        return token;
    }

    public boolean removeSession(String token) {

        return sessions.remove(findSessionByToken(token));
    }

    public void renewSession(String token) {

        renewSession(findSessionByToken(token));
    }

    private void renewSession(Session session) {

        session.setExpire(generateExpire());
    }

    public boolean isSessionValid(String token) {

        Session session = findSessionByToken(token);
        return session != null && session.getExpire().after(new Date());
    }

    public Integer getAccountIdForToken(String token) {

        Session session = findSessionByToken(token);
        return session == null ? NO_SESSION : session.getAccountId();
    }

    public AdminType getAdminTypeForToken(String token) {

        Session session = findSessionByToken(token);
        return session == null ? null : session.getAdminType();
    }

    private Date generateExpire() {
        return new Date(System.currentTimeMillis() + TIMEOUT);
    }

    private String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private Session findSessionByToken(String token) {
        return sessions.stream()
                .filter(s -> s.getToken().equals(token))
                .findFirst()
                .orElse(null);
    }

    private Session findSessionByAccount(Account account) {
        return sessions.stream()
                .filter(s -> s.getAccountId().equals(account.getAccountID()))
                .findFirst()
                .orElse(null);
    }
}
