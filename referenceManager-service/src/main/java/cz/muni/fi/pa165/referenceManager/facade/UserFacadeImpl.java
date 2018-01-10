package cz.muni.fi.pa165.referenceManager.facade;

import cz.muni.fi.pa165.referenceManager.dto.UserDTO;
import cz.muni.fi.pa165.referenceManager.dto.UserLoginDTO;
import cz.muni.fi.pa165.referenceManager.service.MappingService;
import cz.muni.fi.pa165.referenceManager.service.UserService;
import cz.muni.fi.pa165.referenceManager.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;

@Transactional
@Service
public class UserFacadeImpl implements UserFacade {

    @Inject
    private UserService userService;

    @Inject
    private MappingService mappingService;

    @Override
    public UserDTO findUserById(Long id) {
        User user = userService.findUserById(id);
        return mappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        User user = userService.findUserByEmail(email);
        return mappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public void registerUser(UserDTO userDTO, String plainPassword) {
        User user = mappingService.mapTo(userDTO, User.class);
        userService.registerUser(user, plainPassword);
        userDTO.setId(user.getId());
    }

    @Override
    public boolean authenticate(UserLoginDTO userLoginDTO) {
        User user = userService.findUserByEmail(userLoginDTO.getEmail());
        return userService.authenticate(user.getId(), userLoginDTO.getPassword());
    }

    @Override
    public void addReference(Long userId, Long referenceId) {
        userService.addReference(userId, referenceId);
    }

    @Override
    public void removeReference(Long userId, Long referenceId) {
        userService.removeReference(userId, referenceId);
    }

    @Override
    public void addTag(Long userId, Long tagId) {
        userService.addTag(userId, tagId);
    }

    @Override
    public void removeTag(Long userId, Long tagId) {
        userService.removeTag(userId, tagId);
    }

    @Override
    public void shareTag(Long userId, Long tagId) {
        userService.shareTag(userId, tagId);
    }

    @Override
    public void unshareTag(Long userId, Long tagId) {
        userService.unshareTag(userId, tagId);
    }

    @Override
    public Collection<UserDTO> getAllUsers() {
        return mappingService.mapTo(userService.getAllUsers(), UserDTO.class);
    }
}
