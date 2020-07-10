package fr.syrows.modernshop.components;

import com.google.gson.JsonObject;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class EasyTextComponent extends EasyComponent {

    public EasyTextComponent() {
        super(new TextComponent(""));
    }

    public EasyTextComponent(TextComponent component) {
        super(component);
    }

    public EasyComponent setText(String text) {
        this.getComponent().setText(parseColors(text));
        return this;
    }

    public EasyTextComponent replace(String toReplace, EasyComponent replaceWith) {
        this.replace(this, toReplace, replaceWith);
        return this;
    }

    public EasyTextComponent replace(String toReplace, TextComponent replaceWith) {
        this.replace(this, toReplace, new EasyTextComponent(replaceWith));
        return this;
    }

    public EasyTextComponent getFromJson(JsonObject object) {

        this.setText(object.has("text") ? object.get("text").getAsString() : "");

        super.getFromJson(object);

        return this;
    }

    public EasyTextComponent getFromYaml(ConfigurationSection section) {

        this.setText(section.contains("text") ? section.getString("text") : "");

        super.getFromYaml(section);

        return this;
    }

    private void replace(EasyTextComponent original, String toReplace, EasyComponent replaceWith) {

        List<BaseComponent> extra = original.getExtra() != null ? original.getExtra() : new ArrayList<>();

        List<BaseComponent> toIgnore = new ArrayList<>();

        if(original.getText().contains(toReplace)) {

            String originalText = original.getText();

            String[] array = original.getText().split(toReplace);

            // Removing text -> added with using part1 and part2.
            original.setText("");

            // If we have only "%something%" as text, array length will be 0.
            // So we don't have a part to add before the component to replace.
            if(array.length >= 1) {

                TextComponent part1 = new TextComponent();
                part1.setText(array[0]);
                part1.copyFormatting(original.getComponent(), ComponentBuilder.FormatRetention.ALL, true);

                // Adding part before the split as the first extra of the component.
                extra.add(0, part1);

                toIgnore.add(part1);
            }

            BaseComponent replaceWithComponent = replaceWith.getComponent();

            // Adding the replacer component.
            if(extra.size() == 0) extra.add(replaceWithComponent); // Setting it in the first position if there is no part1.
            else extra.add(1, replaceWithComponent); // Adding it after the part1 component if it exists.

            toIgnore.add(replaceWithComponent);

            if(array.length >= 2) {

                TextComponent part2 = new TextComponent();

                String text = originalText.replaceFirst(toReplace, "").substring(array[0].length());

                part2.setText(text);
                part2.copyFormatting(original.getComponent(), ComponentBuilder.FormatRetention.ALL, true);

                // Adding part after the replaced component.
                extra.add(extra.indexOf(replaceWithComponent) + 1, part2);

                toIgnore.add(part2);

                // This part may contain other replaces to apply so we'll also verify it.
                if(text.contains(toReplace)) this.replace(new EasyTextComponent(part2), toReplace, replaceWith);
            }
            original.setExtra(extra); // Setting modified extra list.
        }

        // Also checking all the extra component for replace.
        for(BaseComponent component : extra) {

            if(!(component instanceof TextComponent) || toIgnore.contains(component)) continue;

            EasyTextComponent toParse = new EasyTextComponent((TextComponent) component);

            this.replace(toParse, toReplace, replaceWith);
        }
    }

    public String getText() {
        return this.getComponent().getText();
    }

    @Override
    public TextComponent getComponent() {
        return (TextComponent) super.getComponent();
    }
}
