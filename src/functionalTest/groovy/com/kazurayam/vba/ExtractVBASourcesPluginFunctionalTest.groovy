package com.kazurayam.vba

import org.gradle.testkit.runner.GradleRunner
import spock.lang.Specification
import spock.lang.TempDir

class ExtractVBASourcesPluginFunctionalTest extends Specification {

    @TempDir
    File testProjectDir
    File buildFile

    def setup() {
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
    path = './foo.xlsm'
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
        result.output.contains("foo.xlsm")
        result.task(":extractVBA").outcome == SUCCESS
    }
}