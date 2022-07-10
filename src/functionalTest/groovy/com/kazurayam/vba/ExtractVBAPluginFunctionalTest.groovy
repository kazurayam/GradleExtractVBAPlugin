package com.kazurayam.vba

import org.apache.commons.io.FileUtils
import org.gradle.testkit.runner.GradleRunner
import spock.lang.Specification
import spock.lang.TempDir

import java.nio.file.Files
import java.util.stream.Collectors

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS
import java.nio.file.Path
import java.nio.file.Paths

class ExtractVBAPluginFunctionalTest extends Specification {

    @TempDir
    File testProjectDir
    File buildFile

    Path projectDir
    Path excelFile
    Path outputDir

    def setup() {
        projectDir = Paths.get(System.getProperty("user.dir"))
        excelFile = projectDir.resolve("src/functionalTest/resources/fixture")
                        .resolve("04請求書作成.xlsm")
        // make sure outputDir exists and is empty
        outputDir = projectDir.resolve("build/tmp/testOutput")
                .resolve(this.getClass().getSimpleName())
        if (Files.exists(outputDir)) {
            FileUtils.deleteDirectory(outputDir.toFile())
        }
        Files.createDirectories(outputDir)

        buildFile = new File(testProjectDir, 'build.gradle')
        buildFile << """
plugins {
    id 'com.kazurayam.extract-vba'
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

        Set<String> fileNames =
                Files.list(outputDir)
                        .filter(p -> ! Files.isDirectory(p))
                        .map(p -> p.getFileName().toString())
                        .collect(Collectors.toSet())

        // check if VBA modules are extracted as files.vba
        fileNames.contains("AnswerWriter.vba")
        fileNames.contains("Effect.vba")
        fileNames.contains("EffectMock.vba")
        fileNames.contains("G.vba")
        fileNames.contains("IEffect.vba")
        fileNames.contains("MockUtil.vba")
        fileNames.contains("Test_AnswerWriter.vba")

        result.task(":extractVBA").outcome == SUCCESS
    }
}