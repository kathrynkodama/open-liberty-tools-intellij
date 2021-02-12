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
        IntellijLanguageClient.addServerDefinition(new RawCommandServerDefinition("xml", command));
    }

}
