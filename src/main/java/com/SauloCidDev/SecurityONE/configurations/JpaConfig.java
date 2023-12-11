package com.SauloCidDev.SecurityONE.configurations;

import javax.sql.DataSource;
import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import jakarta.persistence.EntityManagerFactory;

// @Configuration
// public class JpaConfig {
//     @Bean
//     public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource dataSource) {
//         return builder
//                 .dataSource(dataSource)
//                 .packages("paquete.de.tu.entidad")
//                 .persistenceUnit("nombreDeLaUnidadDePersistencia")
//                 .build();
//     }
// }

@Configuration
@EnableJpaRepositories(basePackages = "com.SauloCidDev.SecurityONE.repositories")
public class JpaConfig {

    @Bean
    public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setPackagesToScan("com.SauloCidDev.SecurityONE.entities");
        factoryBean.setDataSource(dataSource);

        return factoryBean.getObject();
    }
}
