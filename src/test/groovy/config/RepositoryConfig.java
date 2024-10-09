package config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(
        basePackages = "com.example.raid_planner.infrastructure"
)
@ImportAutoConfiguration({
        HibernateJpaAutoConfiguration.class,
        DataSourceAutoConfiguration.class
        })
@EnableJpaRepositories(
        basePackages = "com.example.raid_planner.infrastructure",
        considerNestedRepositories = true
)
@EntityScan(
        basePackages = "com.example.raid_planner.infrastructure"
)
public class RepositoryConfig {
}
