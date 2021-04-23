package io.github.ytg1234.manhunt.util

// Thank you gdude, now I don't have to do this!

import net.minecraft.text.*
import net.minecraft.util.Formatting

// region: String to text
fun aqua(string: String) = LiteralText(string).setStyle(Style.EMPTY.withColor(Formatting.AQUA))!!
fun black(string: String) = LiteralText(string).setStyle(Style.EMPTY.withColor(Formatting.BLACK))!!
fun blue(string: String) = LiteralText(string).setStyle(Style.EMPTY.withColor(Formatting.BLUE))!!
fun darkAqua(string: String) = LiteralText(string).setStyle(Style.EMPTY.withColor(Formatting.DARK_AQUA))!!
fun darkBlue(string: String) = LiteralText(string).setStyle(Style.EMPTY.withColor(Formatting.DARK_BLUE))!!
fun darkGreen(string: String) = LiteralText(string).setStyle(Style.EMPTY.withColor(Formatting.DARK_GREEN))!!
fun darkPurple(string: String) = LiteralText(string).setStyle(Style.EMPTY.withColor(Formatting.DARK_PURPLE))!!
fun darkRed(string: String) = LiteralText(string).setStyle(Style.EMPTY.withColor(Formatting.DARK_RED))!!
fun darkGray(string: String) = LiteralText(string).setStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY))!!
fun gold(string: String) = LiteralText(string).setStyle(Style.EMPTY.withColor(Formatting.GOLD))!!
fun gray(string: String) = LiteralText(string).setStyle(Style.EMPTY.withColor(Formatting.GRAY))!!
fun green(string: String) = LiteralText(string).setStyle(Style.EMPTY.withColor(Formatting.GREEN))!!
fun lightPurple(string: String) = LiteralText(string).setStyle(Style.EMPTY.withColor(Formatting.LIGHT_PURPLE))!!
fun red(string: String) = LiteralText(string).setStyle(Style.EMPTY.withColor(Formatting.RED))!!
fun white(string: String) = LiteralText(string).setStyle(Style.EMPTY.withColor(Formatting.WHITE))!!
fun yellow(string: String) = LiteralText(string).setStyle(Style.EMPTY.withColor(Formatting.YELLOW))!!

fun bold(string: String) = LiteralText(string).setStyle(Style.EMPTY.withFormatting(Formatting.BOLD))!!
fun italic(string: String) = LiteralText(string).setStyle(Style.EMPTY.withFormatting(Formatting.ITALIC))!!
fun obfuscated(string: String) = LiteralText(string).setStyle(Style.EMPTY.withFormatting(Formatting.OBFUSCATED))!!
fun strikethrough(string: String) = LiteralText(string).setStyle(Style.EMPTY.withFormatting(Formatting.STRIKETHROUGH))!!
fun underline(string: String) = LiteralText(string).setStyle(Style.EMPTY.withFormatting(Formatting.UNDERLINE))!!

fun reset(string: String) = LiteralText(string).setStyle(Style.EMPTY.withFormatting(Formatting.RESET))!!
// endregion

// region: Mutable text
fun aqua(text: MutableText) = text.setStyle(text.style.withColor(Formatting.AQUA))!!
fun black(text: MutableText) = text.setStyle(text.style.withColor(Formatting.BLACK))!!
fun blue(text: MutableText) = text.setStyle(text.style.withColor(Formatting.BLUE))!!
fun darkAqua(text: MutableText) = text.setStyle(text.style.withColor(Formatting.DARK_AQUA))!!
fun darkBlue(text: MutableText) = text.setStyle(text.style.withColor(Formatting.DARK_BLUE))!!
fun darkGreen(text: MutableText) = text.setStyle(text.style.withColor(Formatting.DARK_GREEN))!!
fun darkPurple(text: MutableText) = text.setStyle(text.style.withColor(Formatting.DARK_PURPLE))!!
fun darkRed(text: MutableText) = text.setStyle(text.style.withColor(Formatting.DARK_RED))!!
fun darkGray(text: MutableText) = text.setStyle(text.style.withColor(Formatting.DARK_GRAY))!!
fun gold(text: MutableText) = text.setStyle(text.style.withColor(Formatting.GOLD))!!
fun gray(text: MutableText) = text.setStyle(text.style.withColor(Formatting.GRAY))!!
fun green(text: MutableText) = text.setStyle(text.style.withColor(Formatting.GREEN))!!
fun lightPurple(text: MutableText) = text.setStyle(text.style.withColor(Formatting.LIGHT_PURPLE))!!
fun red(text: MutableText) = text.setStyle(text.style.withColor(Formatting.RED))!!
fun white(text: MutableText) = text.setStyle(text.style.withColor(Formatting.WHITE))!!
fun yellow(text: MutableText) = text.setStyle(text.style.withColor(Formatting.YELLOW))!!

fun bold(text: MutableText) = text.setStyle(text.style.withFormatting(Formatting.BOLD))!!
fun italic(text: MutableText) = text.setStyle(text.style.withFormatting(Formatting.ITALIC))!!
fun obfuscated(text: MutableText) = text.setStyle(text.style.withFormatting(Formatting.OBFUSCATED))!!
fun strikethrough(text: MutableText) = text.setStyle(text.style.withFormatting(Formatting.STRIKETHROUGH))!!
fun underline(text: MutableText) = text.setStyle(text.style.withFormatting(Formatting.UNDERLINE))!!

fun reset(text: MutableText) = text.setStyle(text.style.withFormatting(Formatting.RESET))!!
fun click(text: MutableText, event: ClickEvent) = text.setStyle(text.style.withClickEvent(event))!!
fun hover(text: MutableText, event: HoverEvent) = text.setStyle(text.style.withHoverEvent(event))!!
// endregion

// region: Operators
operator fun MutableText.plus(other: Text): MutableText = this.append(other)
// endregion
