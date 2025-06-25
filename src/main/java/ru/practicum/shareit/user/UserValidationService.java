package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserValidationService {
    private final UserRepository userRepository;

    public User isUserExistOrThrowNotFound(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("X-Sharer-User-Id not found"));
    }
}
