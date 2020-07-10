package fr.syrows.modernshop.components;

import com.google.gson.JsonObject;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class EasyComponent {

    private BaseComponent component;

    private EasyComponent() {

    } // Used for deserialization.

    public EasyComponent(BaseComponent component) {
        this.component = component;
    }

    public EasyComponent setColor(ChatColor color) {
        this.component.setColor(color);
        return this;
    }

    public EasyComponent setBold(boolean bold) {
        this.component.setBold(bold);
        return this;
    }

    public EasyComponent setItalic(boolean italic) {
        this.component.setItalic(italic);
        return this;
    }

    public EasyComponent setUnderlined(boolean underlined) {
        this.component.setUnderlined(underlined);
        return this;
    }

    public EasyComponent setObfuscated(boolean obfuscated) {
        this.component.setObfuscated(obfuscated);
        return this;
    }

    public EasyComponent setStrikethrough(boolean strikethrough) {
        this.component.setStrikethrough(strikethrough);
        return this;
    }

    public EasyComponent showText(String text) {
        BaseComponent[] base = new ComponentBuilder(parseColors(text)).create();
        this.component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, base));
        return this;
    }

    public EasyComponent runCommand(String command) {
        this.component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + command));
        return this;
    }

    public EasyComponent suggestCommand(String command) {
        this.component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + command));
        return this;
    }

    public EasyComponent openUrl(String url) {
        this.component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        return this;
    }

    public EasyComponent addExtra(EasyComponent component) {
        this.component.addExtra(component.getComponent());
        return this;
    }

    public EasyComponent setExtra(List<BaseComponent> extra) {
        if(extra != null && extra.size() > 0) this.component.setExtra(extra);
        return this;
    }

    protected EasyComponent getFromJson(JsonObject object) {

        if(object.has("showText"))
            this.showText(object.get("showText").getAsString());

        if(object.has("color"))
            this.setColor(ChatColor.valueOf(object.get("color").getAsString()));

        if(object.has("suggestCommand"))
            this.suggestCommand(object.get("suggestCommand").getAsString());

        if(object.has("runCommand"))
            this.runCommand(object.get("runCommand").getAsString());

        if(object.has("openUrl"))
            this.openUrl(object.get("openUrl").getAsString());

        this.setBold(object.has("bold") && object.get("bold").getAsBoolean());
        this.setItalic(object.has("italic") && object.get("italic").getAsBoolean());
        this.setObfuscated(object.has("obfuscated") && object.get("obfuscated").getAsBoolean());
        this.setStrikethrough(object.has("strikethrough") && object.get("strikethrough").getAsBoolean());

        return this;
    }

    protected EasyComponent getFromYaml(ConfigurationSection section) {

        if(section.contains("show-text"))
            this.showText(section.getString("show-text"));

        if(section.contains("color"))
            this.setColor(ChatColor.valueOf(section.getString("color")));

        if(section.contains("suggest-command"))
            this.suggestCommand(section.getString("suggest-command"));

        if(section.contains("run-command"))
            this.runCommand(section.getString("run-command"));

        if(section.contains("open-url"))
            this.openUrl(section.getString("open-url"));

        this.setBold(section.contains("bold") && section.getBoolean("bold"));
        this.setItalic(section.contains("italic") && section.getBoolean("italic"));
        this.setObfuscated(section.contains("obfuscated") && section.getBoolean("obfuscated"));
        this.setStrikethrough(section.contains("strikethrough") && section.getBoolean("strikethrough"));

        return this;
    }

    protected String parseColors(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public List<BaseComponent> getExtra() {
        return this.component.getExtra() != null ? this.component.getExtra() : new ArrayList<>();
    }

    public BaseComponent getComponent() {
        return this.component;
    }
}
