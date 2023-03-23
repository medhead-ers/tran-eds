package com.medhead.ers.tran_eds_test.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "com.medhead.ers.tran_eds")
class DomainDrivenDesignPatternComplianceTest {
    @ArchTest
    static final ArchRule ONLY_APPLICATION_LAYER_AND_SERVICE_LAYER_SHOULD_ACCESS_SERVICE_LAYER =
            classes()
                    .that().resideInAPackage("..service..")
                    .should().onlyBeAccessed().byAnyPackage("..application..", "..service..");

    @ArchTest
    static final ArchRule DOMAIN_LAYER_SHOULD_BE_ACCESSED_ONLY_BY_APPLICATION_OR_DOMAIN_LAYER =
            classes()
                    .that().resideInAPackage("..domain..")
                    .should().onlyBeAccessed().byAnyPackage("..application..", "..domain..", "..utils..");

    @ArchTest
    static final ArchRule ONLY_SERVICES_SHOULD_ACCESS_REPOSITORIES =
            classes()
                    .that().resideInAPackage("..data.repository")
                    .should().onlyBeAccessed().byAnyPackage("..service..")
                    .allowEmptyShould(true);
}
