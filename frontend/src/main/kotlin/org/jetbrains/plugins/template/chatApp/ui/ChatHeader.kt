package org.jetbrains.plugins.template.chatApp.ui

import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.JBFont
import com.intellij.util.ui.JBUI
import org.jetbrains.plugins.template.ModularPluginFrontendBundle
import org.jetbrains.plugins.template.chatApp.ui.utils.ButtonUtils
import org.jetbrains.plugins.template.chatApp.ui.utils.ChatAppColors
import org.jetbrains.plugins.template.chatApp.ui.utils.ChatAppIcons
import org.jetbrains.plugins.template.chatApp.ui.utils.ChatUIConstants
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.border.CompoundBorder

class ChatHeader(private val onToggleSearch: (Boolean) -> Unit) : JPanel() {
    private var searchVisible = false

    init {
        setupAppearance()
        add(createTitleLabel(), BorderLayout.CENTER)
        add(createSearchButton(), BorderLayout.EAST)
    }

    private fun setupAppearance() {
        background = ChatAppColors.Panel.background
        layout = BorderLayout()
        border = CompoundBorder(
            JBUI.Borders.customLine(
                JBUI.CurrentTheme.CustomFrameDecorations.separatorForeground(),
                0, 0, ChatUIConstants.Input.BORDER_THICKNESS, 0
            ),
            JBUI.Borders.empty(
                ChatUIConstants.Spacing.LARGE,
                ChatUIConstants.Spacing.XLARGE
            )
        )
    }

    private fun createTitleLabel() = JBLabel(ModularPluginFrontendBundle.message("chat.header.title")).apply {
        font = JBFont.h3().asBold()
    }

    private fun createSearchButton() = ButtonUtils.createActionButton(
        icon = ChatAppIcons.Header.search,
        tooltip = ModularPluginFrontendBundle.message("chat.search.messages.button"),
        size = ChatUIConstants.Button.LARGE_ACTION_BUTTON_SIZE
    ) {
        searchVisible = !searchVisible
        onToggleSearch(searchVisible)
    }
}
