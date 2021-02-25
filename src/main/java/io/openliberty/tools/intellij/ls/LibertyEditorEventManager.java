package io.openliberty.tools.intellij.ls;

import com.intellij.lang.annotation.Annotation;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.event.EditorMouseListener;
import com.intellij.openapi.editor.event.EditorMouseMotionListener;
import org.wso2.lsp4intellij.client.languageserver.ServerOptions;
import org.wso2.lsp4intellij.client.languageserver.requestmanager.RequestManager;
import org.wso2.lsp4intellij.client.languageserver.wrapper.LanguageServerWrapper;
import org.wso2.lsp4intellij.editor.EditorEventManager;
import org.wso2.lsp4intellij.listeners.LSPCaretListenerImpl;

import java.util.ArrayList;
import java.util.List;

public class LibertyEditorEventManager extends EditorEventManager {
    private volatile boolean codeActionSyncRequired = false;
    private List<Annotation> annotations = new ArrayList<>();


    public LibertyEditorEventManager(Editor editor, DocumentListener documentListener, EditorMouseListener mouseListener, EditorMouseMotionListener mouseMotionListener, LSPCaretListenerImpl caretListener, RequestManager requestManager, ServerOptions serverOptions, LanguageServerWrapper wrapper) {
        super(editor, documentListener, mouseListener, mouseMotionListener, caretListener, requestManager, serverOptions, wrapper);
    }

//    @Override
//    public void executeCommands(List<Command> commands) {
//        pool(() -> {
//            if (editor.isDisposed()) {
//                return;
//            }
//            commands.stream().map(c -> {
//                ExecuteCommandParams params = new ExecuteCommandParams();
//                params.setArguments(c.getArguments());
//                params.setCommand(c.getCommand());
//                // TODO: here try to convert a command to a code action
//                return getRequestManager().executeCommand(params);
//            }).filter(Objects::nonNull).forEach(f -> {
//                try {
//                    f.get(getTimeout(EXECUTE_COMMAND), TimeUnit.MILLISECONDS);
//                    wrapper.notifySuccess(Timeouts.EXECUTE_COMMAND);
//                } catch (TimeoutException te) {
//                    LOG.warn(te);
//                    wrapper.notifyFailure(Timeouts.EXECUTE_COMMAND);
//                } catch (JsonRpcException | ExecutionException | InterruptedException e) {
//                    LOG.warn(e);
//                    wrapper.crashed(e);
//                }
//            });
//        });
//    }

//    /**
//     * @return The current diagnostic annotations
//     */
//    @Override
//    public synchronized List<Annotation> getAnnotations() {
//        this.codeActionSyncRequired = false;
//        return this.annotations;
//    }
//
//    @Override
//    public synchronized boolean isCodeActionSyncRequired() {
//        return this.codeActionSyncRequired;
//    }
//
//    @Override
//    public void requestAndShowCodeActions() {
//        invokeLater(() -> {
//            if (editor.isDisposed()) {
//                return;
//            }
//            if (getAnnotations() == null) {
//                setAnnotations(new ArrayList<>());
//            }
//
//            // sends code action request.
//            int caretPos = editor.getCaretModel().getCurrentCaret().getOffset();
//            List<Either<Command, CodeAction>> codeActionResp = codeAction(caretPos);
//            if (codeActionResp == null || codeActionResp.isEmpty()) {
//                return;
//            }
//            codeActionResp.forEach(element -> {
//                if (element == null) {
//                    return;
//                }
//                if (element.isLeft()) {
//                    Command command = element.getLeft();
//                    annotations.forEach(annotation -> {
//                        int start = annotation.getStartOffset();
//                        int end = annotation.getEndOffset();
//                        if (start <= caretPos && end >= caretPos) {
//                            annotation.registerFix(new LSPCommandFix(FileUtils.editorToURIString(editor), command),
//                                    new TextRange(start, end));
//                            codeActionSyncRequired = true;
//                        }
//                    });
//                } else if (element.isRight()) {
//                    CodeAction codeAction = element.getRight();
//                    List<Diagnostic> diagnosticContext = codeAction.getDiagnostics();
//                    annotations.forEach(annotation -> {
//                        int start = annotation.getStartOffset();
//                        int end = annotation.getEndOffset();
//                        if (start <= caretPos && end >= caretPos) {
//                            annotation.registerFix(new LSPCodeActionFix(FileUtils.editorToURIString(editor),
//                                    codeAction), new TextRange(start, end));
//                            codeActionSyncRequired = true;
//                        }
//                    });
//
//                    // If the code actions does not have a diagnostics context, creates an intention action for
//                    // the current line.
////                    if ((diagnosticContext == null || diagnosticContext.isEmpty()) && !codeActionSyncRequired) {
////                        // Calculates text range of the current line.
////                        int line = editor.getCaretModel().getCurrentCaret().getLogicalPosition().line;
////                        int startOffset = editor.getDocument().getLineStartOffset(line);
////                        int endOffset = editor.getDocument().getLineEndOffset(line);
////                        TextRange range = new TextRange(startOffset, endOffset);
////
////                        Annotation annotation = this.anonHolder.createInfoAnnotation(range, codeAction.getTitle());
////                        annotation.registerFix(new LSPCodeActionFix(FileUtils.editorToURIString(editor), codeAction), range);
////
////                        this.annotations.add(annotation);
////                        diagnosticSyncRequired = true;
////                    }
//                }
//            });
//            // If code actions are updated, forcefully triggers the inspection tool.
//            if (codeActionSyncRequired) {
//                updateErrorAnnotations();
//            }
//
//        });
//
//    }
//
//    /**
//     * Triggers force full DaemonCodeAnalyzer execution.
//     */
//    private void updateErrorAnnotations() {
//        computableReadAction(() -> {
//            final PsiFile file = PsiDocumentManager.getInstance(getProject())
//                    .getCachedPsiFile(editor.getDocument());
//            if (file == null) {
//                return null;
//            }
//            LOG.debug("Triggering force full DaemonCodeAnalyzer execution.");
//            DaemonCodeAnalyzer.getInstance(getProject()).restart(file);
//            return null;
//        });
//    }
}
