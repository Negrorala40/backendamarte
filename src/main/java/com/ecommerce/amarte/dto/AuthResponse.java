package com.ecommerce.amarte.dto;

import java.util.List;

public record AuthResponse(Long userId, List<String> roles, String token) {}
