package lebed.ecommerce.integration.github;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.List;

@ConfigurationProperties("eCommerce")
public class GitIntegrationProperties {

	private final Github github = new Github();

	private final Security security = new Security();

	public Github getGithub() {
		return this.github;
	}

	public Security getSecurity() {
		return this.security;
	}


	public static class Github {

		private String token;

		public String getToken() {
			return this.token;
		}

		public void setToken(String token) {
			this.token = token;
		}
	}

	public static class Security {

		private static final String I_AM = "nickitalebed1";

		private List<String> admins = Arrays.asList(I_AM);

		public List<String> getAdmins() {
			return admins;
		}

		public void setAdmins(List<String> admins) {
			this.admins = admins;
		}

	}

}
