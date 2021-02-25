package io.openliberty.tools.intellij.ls;

import com.intellij.openapi.diagnostic.Logger;
import org.eclipse.lsp4j.*;
import org.jetbrains.annotations.NotNull;
import org.wso2.lsp4intellij.client.ClientContext;
import org.wso2.lsp4intellij.client.DefaultLanguageClient;
import org.wso2.lsp4intellij.client.DynamicRegistrationMethods;
import org.wso2.lsp4intellij.editor.EditorEventManager;
import org.wso2.lsp4intellij.editor.EditorEventManagerBase;
import org.wso2.lsp4intellij.requests.WorkspaceEditHandler;
import org.wso2.lsp4intellij.utils.FileUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
//import org.eclipse.lemminx.customservice.XMLLanguageClientAPI;

public class LibertyLanguageClient extends DefaultLanguageClient {
    final private Logger LOG = Logger.getInstance(LibertyLanguageClient.class);

    @NotNull
    final private Map<String, DynamicRegistrationMethods> registrations = new ConcurrentHashMap<>();

    public LibertyLanguageClient(ClientContext context) {
        super(context);
    }

    @Override
    public CompletableFuture<ApplyWorkspaceEditResponse> applyEdit(ApplyWorkspaceEditParams params) {
        boolean response = WorkspaceEditHandler.applyEdit(params.getEdit(), "LSP edits");
        return CompletableFuture.supplyAsync(() -> new ApplyWorkspaceEditResponse(response));
    }

    @Override
    public CompletableFuture<Void> registerCapability(RegistrationParams params) {
        return CompletableFuture.runAsync(() -> params.getRegistrations().forEach(r -> {
            String id = r.getId();
            Optional<DynamicRegistrationMethods> method = DynamicRegistrationMethods.forName(r.getMethod());
            method.ifPresent(dynamicRegistrationMethods -> registrations.put(id, dynamicRegistrationMethods));
        }));
    }

    @Override
    public void publishDiagnostics(PublishDiagnosticsParams publishDiagnosticsParams) {
        String uri = FileUtils.sanitizeURI(publishDiagnosticsParams.getUri());
        List<Diagnostic> diagnostics = publishDiagnosticsParams.getDiagnostics();
        EditorEventManager manager = EditorEventManagerBase.forUri(uri);
        ClientContext context = getContext();
        LOG.info("---- context: " + context);
        if (manager != null) {
            manager.diagnostics(diagnostics);
        }
    }

    @Override
    public void logMessage(MessageParams messageParams) {
        String message = messageParams.getMessage();
        MessageType msgType = messageParams.getType();

        switch (msgType) {
            case Error:
                LOG.error(message);
                break;
            case Warning:
                LOG.warn(message);
                break;
            case Info:
            case Log:
                LOG.info(message);
                break;
            default:
                LOG.warn("Unknown message type '" + msgType + "' for " + message);
                break;
        }
    }

    @Override
    public CompletableFuture<List<Object>> configuration(ConfigurationParams configurationParams) {
        boolean insertSpaces = false;
        boolean tabSize = false;
        for (ConfigurationItem configParam : configurationParams.getItems()) {
            if (configParam.getSection().equals("xml.format.insertSpaces")){
                insertSpaces = true;
            }
            if (configParam.getSection().equals("xml.format.tabSize")) {
                tabSize = true;
            }
        }
        ConfigurationItem configurationItem = configurationParams.getItems().get(0);
        Map<String, Object> config = new HashMap<>(6, 1.f);
//        config.put("xml.format.insertSpaces", 1);
//        config.put("xml.format.tabSize", 4);
        return CompletableFuture.completedFuture(Collections.singletonList(config));

//        throw new UnsupportedOperationException();
    }

//    @JsonRequest("_xml.applyCodeAction")
//    public void applyCodeAction() {
//        LOG.info("code action");
//    }

}
