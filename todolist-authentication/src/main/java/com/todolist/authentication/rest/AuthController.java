package com.todolist.authentication.rest;

import com.todolist.authentication.model.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	private static final String SECRET_KEY =
			Base64.getUrlEncoder().encodeToString("my_very_secure_secret_key".getBytes());
	private final Map<Long, User> users = new HashMap<>();

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody User user) {
		Long id = !users.isEmpty() ? Collections.max(users.keySet()) : 1L;
		String encodedPassword = Base64.getUrlEncoder().encodeToString(user.password().getBytes());
		users.put(++id, new User(user.username(), encodedPassword));
		return ResponseEntity.status(HttpStatus.CREATED).body("User registered");
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) {
		String encodedPasswordFromStore = users.values().stream()
								.filter(u -> u.username().equals(user.username()))
								.findFirst()
								.map(User::password)
								.orElse(null);

		String encodedPasswordFromInput = Base64.getUrlEncoder().encodeToString(user.password().getBytes());

		if (Objects.isNull(encodedPasswordFromStore)
				|| !encodedPasswordFromStore.equals(encodedPasswordFromInput)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		}

		String token = Jwts.builder().subject(user.username())
				.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
				.compact();
		return ResponseEntity.ok(token);
	}

	@GetMapping("/validate")
	public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
		JwtParser jwtParser = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build();
		try {
			jwtParser.parse(token.replace("Bearer ", ""));
			return ResponseEntity.ok("Valid token");
		} catch (JwtException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
		}
	}
}
