package io.openliberty.tools.intellij.ls;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.eclipse.lsp4j.CodeAction;
import org.jetbrains.annotations.NotNull;
import org.wso2.lsp4intellij.contributors.fixes.LSPCodeActionFix;
import org.wso2.lsp4intellij.editor.EditorEventManager;
import org.wso2.lsp4intellij.editor.EditorEventManagerBase;
import org.wso2.lsp4intellij.requests.WorkspaceEditHandler;

import java.util.Collections;

public class LibertyLSPCodeActionFix extends LSPCodeActionFix {

    private CodeAction codeAction;
    private String uri;

    public LibertyLSPCodeActionFix(String uri, CodeAction codeAction) {
        super(uri, codeAction);
        this.codeAction = codeAction;
        this.uri = uri;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile psiFile) {
        if (codeAction.getEdit() != null) {
            WorkspaceEditHandler.applyEdit(codeAction.getEdit(), codeAction.getTitle());
        }
        EditorEventManager manager = EditorEventManagerBase.forUri(uri);
        if (manager != null) {
            manager.executeCommands(Collections.singletonList(codeAction.getCommand()));
        }
    }
}
