package io.openliberty.tools.intellij.ls;

import com.intellij.openapi.application.PreloadingActivity;
import com.intellij.openapi.progress.ProgressIndicator;

import org.wso2.lsp4intellij.IntellijLanguageClient;
import org.wso2.lsp4intellij.client.languageserver.serverdefinition.RawCommandServerDefinition;

public class LibertyPreloadingActivity extends PreloadingActivity {

    @Override
    public void preload(ProgressIndicator indicator) {

        String[] command = new String[] { "java", "-jar",
                "/Users/rzegray/Developer/github.com/openliberty/open-liberty-tools-intellij/src/main/resources/org.eclipse.lemminx-uber.jar" };
        // String[] command = new String[] { "java", "-cp",
        // "/Users/rzegray/Developer/github.com/openliberty/open-liberty-tools-intellij/src/main/resources/org.eclipse.lemminx-uber.jar:/Users/rzegray/Developer/github.com/openliberty/open-liberty-tools-intellij/src/main/resources/lemminx-liberty-1.0-SNAPSHOT-jar-with-dependencies.jar
        // org.eclipse.lemminx.XMLServerLauncher" };

        // String[] command2 = new String[] { "java", "-cp",
        // "/Users/rzegray/Developer/github.com/openliberty/open-liberty-tools-intellij/src/main/resources/org.eclipse.lemminx-uber.jar
        // org.eclipse.lemminx.XMLServerLauncher" };
        IntellijLanguageClient.addServerDefinition(new RawCommandServerDefinition("xml", command));
    }

}
