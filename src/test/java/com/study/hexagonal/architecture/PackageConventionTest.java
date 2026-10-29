package com.study.hexagonal.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(
        packages = "com.study.hexagonal",
        importOptions = ImportOption.DoNotIncludeTests.class
)
class PackageConventionTest {

    @ArchTest
    static final ArchRule controllers_must_be_in_controller_packages =
            classes().that().haveSimpleNameEndingWith("Controller")
                    .should().resideInAPackage("..controller..");

    @ArchTest
    static final ArchRule mappers_must_be_in_mapper_packages =
            classes().that().haveSimpleNameEndingWith("Mapper")
                    .should().resideInAPackage("..mapper..");

    @ArchTest
    static final ArchRule requests_must_be_in_request_packages =
            classes().that().haveSimpleNameEndingWith("Request")
                    .should().resideInAPackage("..request..");

    @ArchTest
    static final ArchRule responses_must_be_in_response_packages =
            classes().that().haveSimpleNameEndingWith("Response")
                    .should().resideInAPackage("..response..");

    @ArchTest
    static final ArchRule entities_must_be_in_entity_packages =
            classes().that().haveSimpleNameEndingWith("Entity")
                    .should().resideInAPackage("..entity..");

    @ArchTest
    static final ArchRule repositories_must_be_in_repository_packages =
            classes().that().haveSimpleNameEndingWith("Repository")
                    .should().resideInAPackage("..repository..");

    @ArchTest
    static final ArchRule clients_must_be_in_client_packages =
            classes().that().haveSimpleNameEndingWith("Client")
                    .should().resideInAPackage("..client..");

    @ArchTest
    static final ArchRule consumers_must_be_in_consumer_packages =
            classes().that().haveSimpleNameEndingWith("Consumer")
                    .should().resideInAPackage("..consumer..");

    @ArchTest
    static final ArchRule adapters_must_be_in_adapter_packages =
            classes().that().haveSimpleNameEndingWith("Adapter")
                    .should().resideInAPackage("..adapters..");

    @ArchTest
    static final ArchRule use_cases_must_be_in_usecase_packages =
            classes().that().haveSimpleNameEndingWith("UseCase")
                    .should().resideInAPackage("..usecase..");

    @ArchTest
    static final ArchRule input_ports_must_be_in_input_port_packages =
            classes().that().haveSimpleNameEndingWith("InputPort")
                    .should().resideInAPackage("..ports.in..");

    @ArchTest
    static final ArchRule output_ports_must_be_in_output_port_packages =
            classes().that().haveSimpleNameEndingWith("OutputPort")
                    .should().resideInAPackage("..ports.out..");

    @ArchTest
    static final ArchRule configurations_must_be_in_config_packages =
            classes().that().haveSimpleNameEndingWith("Config")
                    .should().resideInAPackage("..config..");
}
