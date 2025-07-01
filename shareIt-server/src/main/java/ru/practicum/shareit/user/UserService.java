package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toUserDto)
                .toList();
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toUserDto)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional
    public UserDto createUser(NewUserRequest request) {
        User user = UserMapper.toUser(request);
        checkEmailUniqueOrThrow(user);
        user = userRepository.save(user);
        return UserMapper.toUserDto(user);
    }

    @Transactional
    public UserDto updateUser(Long id, UpdateUserRequest request) {
        checkEmailAndIdUniqueOrTrow(id, request.getEmail());
        User newUser = userRepository.findById(id)
                .map(user -> UserMapper.updateUserFields(user, request))
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
        newUser = userRepository.save(newUser);
        return UserMapper.toUserDto(newUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void clearData() {
        userRepository.deleteAll();
    }

    private void checkEmailUniqueOrThrow(User user) {
        boolean isUserByEmailExists = !userRepository.findByEmail(user.getEmail()).isEmpty();

        if (isUserByEmailExists) {
            log.warn("User {} already exists", user.getEmail());
            throw new ConflictException("User " + user.getEmail() + " already exists");
        }
    }

    private void checkEmailAndIdUniqueOrTrow(Long id, String email) {
        boolean isUserByEmailExists = !userRepository.findByEmailAndIdNot(email, id).isEmpty();

        if (isUserByEmailExists) {
            log.warn("User {} already exists", email);
            throw new ConflictException("User with email " + email + " already exists");
        }

    }
}
