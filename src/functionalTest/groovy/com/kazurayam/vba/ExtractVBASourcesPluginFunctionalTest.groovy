package com.kazurayam.vba

import org.gradle.testkit.runner.GradleRunner
import spock.lang.Specification
import spock.lang.TempDir

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS
import java.nio.file.Path
import java.nio.file.Paths

class ExtractVBASourcesPluginFunctionalTest extends Specification {

    @TempDir
    File testProjectDir
    File buildFile

    Path projectDir
    Path excelFile
    Path outputDir


    def setup() {
        projectDir = Paths.get(System.getProperty("user.dir"))
        excelFile = projectDir.resolve("src/test/resources/fixture")
                        .resolve("UnitTestingExcelVBA.xlsm")
        outputDir = projectDir.resolve("build/tmp/testOutput")
                .resolve(this.getClass().getSimpleName())

        buildFile = new File(testProjectDir, 'build.gradle')
        buildFile << """
plugins {
    id 'com.kazurayam.extract-vba-sources'
}
"""
    }

    def "can successfully configure Path through extensions and verify it"() {
        buildFile << """
extractVBA {
    input = '${excelFile.toAbsolutePath().toString()}'
    outputDir = '${outputDir.toAbsolutePath().toString()}'
}
"""
        when:
        def result =
                GradleRunner.create()
                        .withProjectDir(testProjectDir)
                        .withArguments('extractVBA')
                        .withPluginClasspath()
                        .build()

        then:
        result.output.contains(excelFile.toAbsolutePath().toString())
        result.output.contains(this.getClass().getSimpleName())

        result.task(":extractVBA").outcome == SUCCESS
    }
}