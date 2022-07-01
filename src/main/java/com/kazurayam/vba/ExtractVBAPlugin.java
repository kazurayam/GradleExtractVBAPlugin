package com.kazurayam.vba;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import com.kazurayam.vba.tasks.ExtractVBATask;
public class ExtractVBAPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        // add the 'excelPath' extension object
        ExtractVBAExtension extension =
                project.getExtensions()
                        .create("excelPath", ExtractVBAExtension.class);
        // create "extractVBA" task
        project.getTasks().register("extractVBA", ExtractVBATask.class, task -> {
            task.getInput().set(extension.getInput());
        });
    }
}
