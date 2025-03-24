package com.ecommerce.amarte.dto;

import java.util.List;

public record AuthResponse(String email, List<String> roles, String token) {}
