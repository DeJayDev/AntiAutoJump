package com.gmail.nystromjp.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.ControlsListWidget;
import net.minecraft.client.gui.screen.options.ControlsOptionsScreen;
import net.minecraft.client.gui.screen.options.GameOptionsScreen;
import net.minecraft.client.gui.screen.options.MouseOptionsScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ControlsOptionsScreen.class)
public class ControlsOptionsScreenMixin extends GameOptionsScreen {

    @Shadow
    private ControlsListWidget keyBindingListWidget;
    @Shadow
    private ButtonWidget resetButton;

    public ControlsOptionsScreenMixin(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }

    @Redirect(
            method = "init()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/options/ControlsOptionsScreen;addButton(Lnet/minecraft/client/gui/widget/AbstractButtonWidget;)Lnet/minecraft/client/gui/widget/AbstractButtonWidget;",
                    ordinal = 1
            )
    )
    private <T extends AbstractButtonWidget> T dontAddJumpButton(ControlsOptionsScreen screen, T button) {
        return button;
    }

    @Redirect(
            method = "init()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/options/ControlsOptionsScreen;addButton(Lnet/minecraft/client/gui/widget/AbstractButtonWidget;)Lnet/minecraft/client/gui/widget/AbstractButtonWidget;",
                    ordinal = 0
            )
    )
    private <T extends AbstractButtonWidget> T changeMouseOptionsButtonPlacement(ControlsOptionsScreen screen, T button) {
        AbstractButtonWidget abstractButtonWidget = new ButtonWidget(this.width / 2 - 75, 18, 150, 20, new TranslatableText("options.mouse_settings"), (buttonWidget) -> {
            this.client.openScreen(new MouseOptionsScreen(this, this.gameOptions));
        });

        this.buttons.add(abstractButtonWidget);

        return (T) this.addChild(abstractButtonWidget);
    }

    /*@Override()
    public void init(){
        this.addButton(new ButtonWidget(this.width / 2 - 75, 18, 150, 20, new TranslatableText("options.mouse_settings"), (buttonWidget) -> {
            this.client.openScreen(new MouseOptionsScreen(this, this.gameOptions));
        }));

        this.keyBindingListWidget = new ControlsListWidget(((ControlsOptionsScreen) (Object) this), this.client);
        this.children.add(this.keyBindingListWidget);
        this.resetButton = (ButtonWidget)this.addButton(new ButtonWidget(this.width / 2 - 155, this.height - 29, 150, 20, new TranslatableText("controls.resetAll"), (buttonWidget) -> {
            KeyBinding[] var2 = this.gameOptions.keysAll;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                KeyBinding keyBinding = var2[var4];
                keyBinding.setBoundKey(keyBinding.getDefaultKey());
            }

            KeyBinding.updateKeysByCode();
        }));
        this.addButton(new ButtonWidget(this.width / 2 - 155 + 160, this.height - 29, 150, 20, ScreenTexts.DONE, (buttonWidget) -> {
            this.client.openScreen(this.parent);
        }));
    }*/
}