package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
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

    public UserDto createUser(NewUserRequest request) {
        User user = UserMapper.toUser(request);
        checkEmailUniqueOrThrow(user);
        user = userRepository.createUser(user);
        return UserMapper.toUserDto(user);
    }

    public UserDto updateUser(Long id, UpdateUserRequest request) {
        User newUser = userRepository.findById(id)
                .map(user -> UserMapper.updateUserFields(user, request))
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
        checkEmailUniqueOrThrow(newUser);
        newUser = userRepository.updateUser(newUser);
        return UserMapper.toUserDto(newUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteUser(id);
    }

    public void clearData() {
        userRepository.clearData();
    }

    private void checkEmailUniqueOrThrow(User user) {
        if (userRepository.existsByEmail(user)) {
            log.warn("User {} already exists", user.getEmail());
            throw new ConflictException("User " + user.getEmail() + " already exists");
        }
    }
}
