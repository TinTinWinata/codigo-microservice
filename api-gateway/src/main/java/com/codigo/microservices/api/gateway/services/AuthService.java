package com.codigo.microservices.api.gateway.services;

import com.codigo.microservices.api.gateway.dto.AuthResponseDto;
import com.codigo.microservices.api.gateway.dto.UserDto;
import com.codigo.microservices.api.gateway.mapper.UserMapper;
import com.codigo.microservices.api.gateway.repository.UserRepository;
import com.codigo.microservices.api.gateway.utility.JwtUtility;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<AuthResponseDto> loginUser(UserDto userDto) {
        return Mono.fromCallable(() -> {
            var user = this.userRepository.findByPhoneNumber(userDto.getPhoneNumber()).orElse(null);
            if (user == null){
                user = this.userRepository.save(UserMapper.toEntity(userDto));
            }
            return new AuthResponseDto(JwtUtility.generateToken(user));
        });
    }
}
