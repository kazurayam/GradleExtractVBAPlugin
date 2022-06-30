package com.kazurayam.vba;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import com.kazurayam.vba.tasks.ExtractVBA;
public class ExtractVBASourcesPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        // add the 'excelPath' extension object
        ExtractVBASourcesExtension extension =
                project.getExtensions()
                        .create("excelPath", ExtractVBASourcesExtension.class);
        // create "extractVBA" task
        project.getTasks().register("extractVBA", ExtractVBA.class, task -> {
            task.getInput().set(extension.getInput());
        });
    }
}
