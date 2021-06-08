package se.lexicon.data;

import se.lexicon.model.AppUser;

import java.util.Collection;
import java.util.Optional;

public interface AppUserDAO {

    static AppUserDAO getInstance(){
        return AppUserDAOImpl.getInstance();
    }

    AppUser persist(AppUser appUser);
    Collection<AppUser> findAll();
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findById(Integer id);
    AppUser update(AppUser appUser);
    boolean remove(Integer id);

}
