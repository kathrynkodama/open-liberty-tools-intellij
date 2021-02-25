package io.openliberty.tools.intellij.ls;

import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.WorkspaceService;
import org.wso2.lsp4intellij.client.languageserver.requestmanager.DefaultRequestManager;
import org.wso2.lsp4intellij.client.languageserver.wrapper.LanguageServerWrapper;

import java.util.concurrent.CompletableFuture;

public class LibertyRequestManager extends DefaultRequestManager {

    public LibertyRequestManager(LanguageServerWrapper wrapper, LanguageServer server, LanguageClient client, ServerCapabilities serverCapabilities) {
        super(wrapper, server, client, serverCapabilities);
    }

    @Override
    public CompletableFuture<Object> executeCommand(ExecuteCommandParams params) {
        if (checkStatus()) {
            try {
//                XMLWorkspaceService xmlWorkspaceService = (XMLWorkspaceService) getServer().getWorkspaceService();
                WorkspaceService workspaceService = getServer().getWorkspaceService();
                ServerCapabilities serverCapabilities = getServerCapabilities();
//                serverCapabilities.setExecuteCommandProvider(new ExecuteCommandOptions());
                return getServer().getWorkspaceService().executeCommand(params);
//                return serverCapabilities.getExecuteCommandProvider() != null ? workspaceService.executeCommand(params) : null;
            } catch (Exception e) {
//                crashed(e);
                return null;
            }
        }
        return null;
    }
}
