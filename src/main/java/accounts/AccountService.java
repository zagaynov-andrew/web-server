package accounts;

import java.util.HashMap;
import java.util.Map;

/**
 * A class for storing account information.
 */
public class AccountService {
    private final Map<String, UserProfile> loginToProfile;
    private final Map<String, UserProfile> sessionIdToProfile;

    /**
     * Constructor.
     */
    public AccountService() {
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
    }

    /**
     * Adds a new user.
     * @param userProfile User profile that is being added.
     */
    public void addNewUser(UserProfile userProfile) {
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    /**
     * Returns the user profile by login.
     * @param login user login
     * @return user profile by login
     */
    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    /**
     * Returns the user profile by session ID.
     * @param sessionId user session ID
     * @return user profile by session ID
     */
    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    /**
     * Adds a new session.
     * @param sessionId new session ID
     * @param userProfile profile of the user who owns the session
     */
    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    /**
     * Removes a session by id.
     * @param sessionId session id to delete
     */
    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
