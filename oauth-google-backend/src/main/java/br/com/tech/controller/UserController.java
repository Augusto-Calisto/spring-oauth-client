package br.com.tech.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tech.dto.AuthSuccessResponse;
import br.com.tech.entity.User;
import br.com.tech.service.UserService;
import br.com.tech.util.UserUtil;

@RestController
@RequestMapping(value = "/user")
public class UserController {
	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/info")
	public ResponseEntity<AuthSuccessResponse> getUserInfo(@AuthenticationPrincipal OidcUser oidcUser, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {				
		if(oidcUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		String sub = oidcUser.getAttribute("sub");
		
		String providerName = authorizedClient.getClientRegistration().getRegistrationId();
		
		Optional<User> optional = userService.findBySubAndRegistrationId(sub, providerName);
		
		if(optional.isPresent()) {
			User user = optional.get();
			
			AuthSuccessResponse authSuccess = UserUtil.buildUserInfo(user, oidcUser, authorizedClient);
						
			LocalDate loginDate = user.getLastLoginDateTime().toLocalDate();
			
			if(loginDate.isBefore(LocalDate.now())) {
				userService.updateLastLoginDate(user.getId(), LocalDateTime.now());
			}
						
			return ResponseEntity.status(HttpStatus.OK).body(authSuccess);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}