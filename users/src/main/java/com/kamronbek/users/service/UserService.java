package com.kamronbek.users.service;

import com.kamronbek.users.dto.UserDto;
import com.kamronbek.users.entity.User;
import com.kamronbek.users.error.NotFoundException;
import com.kamronbek.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final String userNotFoundMessage = "User with id %d does not exist";
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(userNotFoundMessage, id)));
        return new UserDto(user.getId(), user.getUsername(), user.getNumberOfPosts());
    }

    public UserDto createUser(UserDto userDto) {
        User user = new User(null, userDto.getUsername(), 0L);
        userRepository.save(user);
        return new UserDto(user.getId(), user.getUsername(), user.getNumberOfPosts());
    }


    @Transactional
    public UserDto editUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(userNotFoundMessage, id)));
        if (userDto.getUsername() != null && "".equals(userDto.getUsername())) {
            user.setUsername(userDto.getUsername());
        }

        if (userDto.getNumberOfPosts() != null && userDto.getNumberOfPosts() >= 0) {
            user.setNumberOfPosts(userDto.getNumberOfPosts());
        }

        return new UserDto(user.getId(), user.getUsername(), user.getNumberOfPosts());
    }


    @Transactional
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format(userNotFoundMessage, id));
        }
    }
}
