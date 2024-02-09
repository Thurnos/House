package org.informatics.configuration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
//import org.informatics.entity.building.Apartment;
import org.informatics.entity.base_entity.BaseEntity;
import org.informatics.entity.building.Apartment;
import org.informatics.entity.building.Building;
import org.informatics.entity.building.Owner;
import org.informatics.entity.company.Company;
import org.informatics.entity.company.Employee;
import org.informatics.entity.resident.Fee;
import org.informatics.entity.resident.pet.Pet;
import org.informatics.entity.resident.Resident;

import java.util.HashMap;
import java.util.Map;

public class SessionFactoryUtil {
    private static SessionFactory sessionFactory;






    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                        .configure("hibernate.cfg.xml")
                        .build();
                ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                        .configure()
                        .messageInterpolator(new ParameterMessageInterpolator())
                        .buildValidatorFactory();

                MetadataSources metadataSources = new MetadataSources(standardRegistry);

                    metadataSources.addAnnotatedClass(Company.class);
                    metadataSources.addAnnotatedClass(Employee.class);
                    metadataSources.addAnnotatedClass(Resident.class);
                    metadataSources.addAnnotatedClass(Owner.class);
                    metadataSources.addAnnotatedClass(Pet.class);
                    metadataSources.addAnnotatedClass(Apartment.class);
                    metadataSources.addAnnotatedClass(Building.class);
                    metadataSources.addAnnotatedClass(Fee.class);

                    metadataSources.addAnnotatedClass(BaseEntity.class);

                    Metadata metadata = metadataSources.getMetadataBuilder().build();
                sessionFactory = metadata.getSessionFactoryBuilder()
                        .applyValidatorFactory(validatorFactory)
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error initializing Hibernate SessionFactory: " + e.getMessage(), e);
            }
        }

        return sessionFactory;
    }
}
