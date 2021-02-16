package io.openliberty.tools.intellij.ls;

import com.intellij.openapi.application.PreloadingActivity;
import com.intellij.openapi.progress.ProgressIndicator;

import org.wso2.lsp4intellij.IntellijLanguageClient;
import org.wso2.lsp4intellij.client.languageserver.serverdefinition.RawCommandServerDefinition;

public class LibertyPreloadingActivity extends PreloadingActivity {

    @Override
    public void preload(ProgressIndicator indicator) {

//        // Starts LemMinx with -jar
//        String[] command = new String[] { "java", "-jar",
//                "/Users/kathrynkodama/devex/extensions/open-liberty-tools-intellij/src/main/resources/org.eclipse.lemminx-uber.jar" };

//        // Starts just LemMinx with Classpath
//        String[] command = new String[] { "java", "-cp",
//                "/Users/kathrynkodama/devex/extensions/open-liberty-tools-intellij/src/main/resources/*:.", "org.eclipse.lemminx.XMLServerLauncher"};

        // Starts LemMinx and adds Liberty LemMinx to the classpath
        String[] command = new String[]{"java", "-cp",
                "/Users/kathrynkodama/devex/extensions/open-liberty-tools-intellij/src/main/resources/org.eclipse.lemminx-uber.jar:/Users/kathrynkodama/devex/extensions/open-liberty-tools-intellij/src/main/resources/lemminx-liberty-1.0-SNAPSHOT-jar-with-dependencies.jar",
                "org.eclipse.lemminx.XMLServerLauncher"};

        IntellijLanguageClient.addServerDefinition(new RawCommandServerDefinition("xml", command));
    }

}
