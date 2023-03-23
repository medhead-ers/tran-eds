package com.medhead.ers.tran_eds_test.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "com.medhead.ers.tran_eds")
class NamingConventionTest {

    @ArchTest
    static final ArchRule EXCEPTION_SHOULD_BE_SUFFIXED =
            classes()
                    .that().resideInAPackage("..exception..")
                    .should().haveSimpleNameEndingWith("Exception");

    @ArchTest
    static final ArchRule CONTROLLER_SHOULD_NOT_HAVE_GUI_IN_NAME =
            classes()
                    .that().resideInAPackage("..controller..")
                    .should().haveSimpleNameNotContaining("Gui");

    @ArchTest
    static final ArchRule CONTROLLER_SHOULD_BE_SUFFIXED =
            classes()
                    .that().resideInAPackage("..controller..")
                    .or().areAnnotatedWith(Controller.class)
                    .should().haveSimpleNameEndingWith("Controller");

    @ArchTest
    static final ArchRule CLASSES_NAMED_CONTROLLER_SHOULD_RESIDE_IN_CONTROLLER_PACKAGE =
            classes()
                    .that().haveSimpleNameContaining("Controller")
                    .should().resideInAPackage("..controller..");

    @ArchTest
    static final ArchRule CLASSES_ANNOTATED_WITH_SERVICE_SHOULD_RESIDE_IN_SERVICE_PACKAGE =
            classes()
                    .that().areAnnotatedWith(Service.class)
                    .should().resideInAPackage("..service..");
}