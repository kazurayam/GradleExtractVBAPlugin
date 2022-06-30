package com.kazurayam.vba.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

abstract public class ExtractVBA extends DefaultTask {

    @Input
    abstract public Property<String> getInput();

    public ExtractVBA() {
        getInput().convention("./sample.xlsm");
    }

    @TaskAction
    public void action() {
        System.out.println(String.format("Hello, %s", getInput().get()));
    }
}
