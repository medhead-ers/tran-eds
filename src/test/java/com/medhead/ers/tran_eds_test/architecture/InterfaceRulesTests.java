package com.medhead.ers.tran_eds_test.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.medhead.ers.tran_eds")
public class InterfaceRulesTests {
    @ArchTest
    static final ArchRule INTERFACES_SHOULD_NOT_HAVE_NAMES_ENDING_WITH_THE_WORD_INTERFACE =
            noClasses().that().areInterfaces().should().haveNameMatching(".*Interface");

    @ArchTest
    static final ArchRule INTERFACES_SHOULD_NOT_HAVE_NAMES_CONTAINING_THE_WORD_INTERFACE =
            noClasses().that().areInterfaces().should().haveSimpleNameContaining("Interface");

    @ArchTest
    static final ArchRule INTERFACES_MUST_NOT_BE_PLACED_IN_IMPLEMENTATION_PACKAGES =
            noClasses().that().resideInAPackage("..implementation..").should().beInterfaces();
}
