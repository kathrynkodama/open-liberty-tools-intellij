package io.openliberty.tools.intellij.ls;

import com.intellij.openapi.diagnostic.Logger;
import org.wso2.lsp4intellij.client.ClientContext;
import org.wso2.lsp4intellij.client.DefaultLanguageClient;

public class LibertyLanguageClient extends DefaultLanguageClient {
    final private Logger LOG = Logger.getInstance(LibertyLanguageClient.class);

    public LibertyLanguageClient(ClientContext context) {
        super(context);
    }

}
