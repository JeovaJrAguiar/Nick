package api.nick.service;

import api.nick.entity.login.Login;
import api.nick.entity.user.Role;
import api.nick.entity.user.User;
import api.nick.entity.user.UserDTO;
import api.nick.repository.LoginRepository;
import api.nick.repository.RoleRepository;
import api.nick.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long id) {
        try {
            Optional<User> user = repository.findById(id);
            if (user.isEmpty())
                throw new RuntimeException("Usuário não encontrado");
            return user;
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void addUser(UserDTO newUser){
        try {
            Optional<User> user = repository.findByEnrollment(newUser.enrollment());
            if (user.isPresent())
                throw new RuntimeException("Email já cadastrado");

            user = Optional.of(new User());
            user.get().setName(newUser.name());
            user.get().setEnrollment(newUser.enrollment());
            repository.saveAndFlush(user.get());

            Login login = new Login();
            login.setEnrollment(newUser.enrollment());
            login.setPassword(passwordEncoder.encode(newUser.password()));
            login.setRoles(getRoles());
            login.setUser(user.get());
            loginRepository.save(login);

        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void updateUserById(Long id, UserDTO updateUser){
        try {
            Optional<User> user = repository.findById(id);
            if (user.isEmpty())
                throw new RuntimeException("Usuário não encontrado");

            Optional<Login> login = loginRepository.findByUser(user.get().getId());
            if (login.isEmpty())
                throw new RuntimeException("Usuário não encontrado");

            if (!updateUser.name().isEmpty())
                user.get().setName(updateUser.name());

            if (!updateUser.enrollment().isEmpty()){
                user.get().setEnrollment(updateUser.enrollment());
                login.get().setEnrollment(updateUser.enrollment());
            }

            if (!updateUser.password().isEmpty())
                login.get().setPassword(updateUser.password());

            repository.save(user.get());
            loginRepository.save(login.get());

        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void deleteUserById(Long id){
        try {
            Optional<User> user = repository.findById(id);
            if (user.isEmpty())
                throw new RuntimeException("Usuário não encontrado");

            Optional<Login> login = loginRepository.findByUser(user.get().getId());
            if (login.isEmpty())
                throw new RuntimeException("Usuário não encontrado");

            repository.delete(user.get());
            loginRepository.delete(login.get());
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional(readOnly = true)
    private List<Role> getRoles(){
        try {
            List<Role> roles = new ArrayList<>();
            Role role = roleRepository.findByName("STUDENT");
            roles.add(role);
            return roles;
        }catch (Exception e){
            throw e;
        }
    }
}