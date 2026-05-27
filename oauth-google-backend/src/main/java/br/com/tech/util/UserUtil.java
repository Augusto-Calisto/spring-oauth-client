package br.com.tech.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import br.com.tech.dto.AuthSuccessResponse;
import br.com.tech.entity.Role;
import br.com.tech.entity.User;

public class UserUtil {
	
	public static AuthSuccessResponse buildUserInfo(User user, OidcUser oidcUser, OAuth2AuthorizedClient authorizedClient) {		
		String providerName = authorizedClient.getClientRegistration().getRegistrationId();
				
		List<String> userRoles = user.getRoles().stream()
				.map(Role::getName)
				.collect(Collectors.toList());

		LocalDateTime lastLoginDateTime = user.getLastLoginDateTime();
				
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
		
		String formatDate = lastLoginDateTime.format(formatter);
		
		AuthSuccessResponse authSuccess = AuthSuccessResponse
											.builder()
												.name(user.getName())
												.email(user.getEmail())
												.familyName(oidcUser.getClaim("family_name"))
												.urlPicture(oidcUser.getClaim("picture"))
												.lastDateLogin(formatDate)
												.providerName(providerName)
												.token(oidcUser.getIdToken().getTokenValue())
												.scopes(authorizedClient.getClientRegistration().getScopes())
												.roles(userRoles)
											.build();
		
		return authSuccess;
	}
}