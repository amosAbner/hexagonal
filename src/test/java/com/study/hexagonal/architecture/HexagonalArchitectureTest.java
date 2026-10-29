package com.study.hexagonal.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(
        packages = "com.study.hexagonal",
        importOptions = ImportOption.DoNotIncludeTests.class
)
class HexagonalArchitectureTest {

    @ArchTest
    static final ArchRule domain_must_only_depend_on_domain_and_jdk =
            classes()
                    .that().resideInAPackage("com.study.hexagonal.application.core.domain..")
                    .should().onlyDependOnClassesThat()
                    .resideInAnyPackage(
                            "com.study.hexagonal.application.core.domain..",
                            "java.."
                    );

    @ArchTest
    static final ArchRule ports_must_only_depend_on_application_and_jdk =
            classes()
                    .that().resideInAPackage("com.study.hexagonal.application.ports..")
                    .should().onlyDependOnClassesThat()
                    .resideInAnyPackage(
                            "com.study.hexagonal.application..",
                            "java.."
                    );

    @ArchTest
    static final ArchRule use_cases_must_only_depend_on_application_and_jdk =
            classes()
                    .that().resideInAPackage("com.study.hexagonal.application.core.usecase..")
                    .should().onlyDependOnClassesThat()
                    .resideInAnyPackage(
                            "com.study.hexagonal.application..",
                            "java.."
                    );

    @ArchTest
    static final ArchRule application_must_not_depend_on_adapters_or_configuration =
            noClasses()
                    .that().resideInAPackage("com.study.hexagonal.application..")
                    .should().dependOnClassesThat()
                    .resideInAnyPackage(
                            "com.study.hexagonal.adapters..",
                            "com.study.hexagonal.config.."
                    );

    @ArchTest
    static final ArchRule inbound_adapters_must_not_depend_on_outbound_adapters =
            noClasses()
                    .that().resideInAPackage("com.study.hexagonal.adapters.in..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("com.study.hexagonal.adapters.out..");

    @ArchTest
    static final ArchRule outbound_adapters_must_not_depend_on_inbound_adapters =
            noClasses()
                    .that().resideInAPackage("com.study.hexagonal.adapters.out..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("com.study.hexagonal.adapters.in..");
}
