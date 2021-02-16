package io.openliberty.tools.intellij.ls;

import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.openapi.application.PreloadingActivity;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.progress.ProgressIndicator;
import org.wso2.lsp4intellij.IntellijLanguageClient;
import org.wso2.lsp4intellij.client.languageserver.serverdefinition.RawCommandServerDefinition;

import java.io.File;
import java.util.Arrays;

public class LibertyPreloadingActivity extends PreloadingActivity {
    final private Logger LOG = Logger.getInstance(LibertyPreloadingActivity.class);

    @Override
    public void preload(ProgressIndicator indicator) {
        final File root = Arrays.stream(PluginManagerCore.getPlugins())
                .filter(d -> PluginId.getId("open-liberty.intellij").equals(d.getPluginId()))
                .findFirst()
                .map(d -> d.getPath())
                .orElseThrow(() -> new IllegalStateException("PluginDescriptor for open-liberty.intellij not found."));

        File lemminxJar = new File(root, "lib/org.eclipse.lemminx-uber.jar");
        File libertyLemminxJar = new File(root, "lib/lemminx-liberty-1.0-SNAPSHOT-jar-with-dependencies.jar");

        // Starts LemMinx and adds Liberty LemMinx extension to the classpath
        if (lemminxJar.exists() && libertyLemminxJar.exists()) {
            String[] command = new String[]{"java", "-cp",
                    lemminxJar.getAbsolutePath() + ":" + libertyLemminxJar.getAbsolutePath(),
                    "org.eclipse.lemminx.XMLServerLauncher"};

            RawCommandServerDefinition serverDefinition = new RawCommandServerDefinition("xml", command);
            LibertyLspExtensionManager extensionManager = new LibertyLspExtensionManager();
            IntellijLanguageClient.addExtensionManager("xml", extensionManager);
            IntellijLanguageClient.addServerDefinition(serverDefinition);
        } else {
            LOG.error("Could not start Liberty Language Server, jars do not exist: " +
                    lemminxJar.getAbsolutePath() + "; " + libertyLemminxJar.getAbsolutePath());
        }

    }

}
