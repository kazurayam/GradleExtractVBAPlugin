package com.kazurayam.vba.tasks;

import org.apache.poi.poifs.macros.VBAMacroExtractor;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
import java.io.File;
import java.io.IOException;

abstract public class ExtractVBATask extends DefaultTask {

    @Input
    abstract public Property<String> getInput();

    @Input
    abstract public Property<String> getOutputDir();

    @Input
    abstract public Property<String> getExtension();

    public ExtractVBATask() {
        getInput().convention("./sample.xlsm");
        getOutputDir().convention("./out");
        getExtension().convention(".vba");
    }

    @TaskAction
    public void action() throws IOException {
        File input = new File(getInput().get());
        File outputDir = new File(getOutputDir().get());
        String extension = getExtension().get();
        System.out.println(String.format("input=%s", input));
        System.out.println(String.format("outputDir=%s", outputDir));
        System.out.println(String.format("extension=%s", extension));
        VBAMacroExtractor extractor = new VBAMacroExtractor();
        extractor.extract(input, outputDir, extension);
    }
}
