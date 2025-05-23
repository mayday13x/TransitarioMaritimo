package pt.ipvc.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

//@EnableJpaRepositories(basePackages = {"pt.ipvc.database.repository"})
@SpringBootApplication
public class TransitarioMaritimoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransitarioMaritimoApplication.class, args);

    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/transitario_maritimo");
        dataSource.setUsername("postgres");
        dataSource.setPassword("martins00");
        //dataSource.setPassword("-Jfma2004");
        return dataSource;

    }

}
