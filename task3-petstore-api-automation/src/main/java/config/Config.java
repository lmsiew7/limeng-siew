package config;

public class Config {
    public static final String BASE_URL = System.getenv().getOrDefault("API_BASE_URL", "http://localhost:8080/api/v3");
}
