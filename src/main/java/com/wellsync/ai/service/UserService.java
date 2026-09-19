package com.wellsync.ai.service;

import com.wellsync.ai.dto.UserRequest;
import com.wellsync.ai.dto.UserResponse;
import com.wellsync.ai.entity.User;
import com.wellsync.ai.exception.ConflictException;
import com.wellsync.ai.exception.ResourceNotFoundException;
import com.wellsync.ai.mapper.UserMapper;
import com.wellsync.ai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse create(UserRequest request) {
        userRepository.findByEmail(request.getEmail()).ifPresent(existing -> {
            throw new ConflictException("User already exists with email: " + request.getEmail());
        });

        User user = userMapper.toEntity(request);
        if (request.getIsActive() != null) {
            user.setActive(request.getIsActive());
        }
        User saved = userRepository.saveAndFlush(user);
        return userMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public UserResponse getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponse update(UUID id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        userMapper.updateEntityFromRequest(request, user);
        if (request.getIsActive() != null) {
            user.setActive(request.getIsActive());
        }
        User updated = userRepository.saveAndFlush(user);
        return userMapper.toResponse(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
